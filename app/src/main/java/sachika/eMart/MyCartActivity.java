package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class MyCartActivity extends AppCompatActivity implements View.OnLongClickListener {

    Toolbar mToolBar;
    RecyclerView rvCartItems;
    CartAdapter moreCategoryAdapter;
    Button btnCheckout,btnHome;
    CardView cvCartItems,cvGrandTotal;
    //adapter to
    boolean isActionMode = false;
    public TextView tvGranTotalAmount,tvItemCount;

    //onclickListener checkbox
    ArrayList<Cart> selectionItems = new ArrayList<>();
    ArrayList<Cart> proCatArray;
    Cart cart = new Cart();
    double total = 0;
    int count = 0;
    boolean isChecked=false;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();

        id = pref.getInt("user_id",-1);
        Log.d("MyCartActivity",id+"");

        tvGranTotalAmount =(TextView)findViewById(R.id.tvGranTotalAmount);
        tvItemCount = (TextView)findViewById(R.id.tvItemCount);
        cvCartItems = (CardView) findViewById(R.id.cvCartItems);
        cvGrandTotal = (CardView) findViewById(R.id.cvGrandTotal);

        rvCartItems =(RecyclerView)findViewById(R.id.rvCartItems);


        //Order
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnHome = (Button) findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (selectionItems.size()==0){
                        Toast.makeText(MyCartActivity.this, "Please Select Items", Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
                        intent.putExtra("selectionArray", selectionItems);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Toast.makeText(MyCartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("My Cart");
        mToolBar.inflateMenu(R.menu.menu_main);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.delete:
                        isActionMode =false;
                        CartAdapter cartAdapter = (CartAdapter)moreCategoryAdapter;
                        cartAdapter.deleteItems(selectionItems);
                        break;

                }
                return true;
            }
        });



        //cart


        String url = HTTPPaths.baseUrl+ "cart.php";
        //String url = "http://sampletemp.96.lt/android/cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        proCatArray = new JsonConverter<Cart>().toArrayList(response,Cart.class);

                        if(proCatArray.get(0).success){
                            btnHome.setVisibility(View.GONE);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            rvCartItems.setLayoutManager(layoutManager);
                            rvCartItems.setHasFixedSize(true);
                            moreCategoryAdapter = new CartAdapter(MyCartActivity.this,proCatArray,tvGranTotalAmount);
                            rvCartItems.setAdapter(moreCategoryAdapter);

                        }else{
                            Toast.makeText(MyCartActivity.this, "Your Cart is empty..", Toast.LENGTH_SHORT).show();
                            cvCartItems.setVisibility(View.GONE);
                            cvGrandTotal.setVisibility(View.GONE);
                            btnCheckout.setVisibility(View.GONE);

                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("MyCartActivity",id+"");
                Map<String,String> params = new HashMap<>();
                params.put("cust_id",id+"");
                return params;
            }
        };


        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        return true;

    }
    // implement activity from onLongClickListner:: On View holder class initialize the method
    @Override
    public boolean onLongClick(View view) {
        mToolBar.getMenu().clear();
        mToolBar.inflateMenu(R.menu.actionbar_menu);
        isActionMode = true;
        moreCategoryAdapter.notifyDataSetChanged();
        return true;
    }

    //adapter class.checkbox onclickListener
    public void prepare(View view,int position){

        if(((CheckBox)view).isChecked()){
            cart = proCatArray.get(position);
            selectionItems.add(cart);
            isChecked = true;
            total=total+selectionItems.get(position).total;
            tvGranTotalAmount.setText("Rs."+String.format("%.2f",total));
            count++;
        }else {
            cart = proCatArray.get(position);
            total=total-selectionItems.get(position).total;
            tvGranTotalAmount.setText("Rs."+String.format("%.2f",total));
            selectionItems.remove(cart);
            isChecked = false;
            count--;
        }
        tvItemCount.setText("("+count+" items)");
    }

    public void increaseQty(int p){

        final int ps =p;

        if(proCatArray.get(ps).qty >0 && proCatArray.get(ps).qty !=0) {

            proCatArray.get(ps).cart_qty++;
            proCatArray.get(ps).qty--;
            proCatArray.get(ps).total=proCatArray.get(ps).price*proCatArray.get(ps).cart_qty;
            moreCategoryAdapter.notifyDataSetChanged();

            if (isChecked){
                total = total+ proCatArray.get(ps).price;
                tvGranTotalAmount.setText(String.format("Rs. %.2f",total));
            }

            //send data to server
           String url= HTTPPaths.baseUrl+ "cart_item_increase.php";
            //  String url= "http://sampletemp.96.lt/android/cart_item_increase.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                }else {
                                    //Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cart_id",proCatArray.get(ps).cart_id+"");
                    params.put("pro_id",proCatArray.get(ps).pid+"");
                    params.put("pro_qty",proCatArray.get(ps).qty+"");
                    params.put("cart_qty",proCatArray.get(ps).cart_qty+"");
                    params.put("cart_total",proCatArray.get(ps).total+"");
                    return params;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest); //end sending data to server

        }//end if
    }

    public void discreaseQty(final int position){
        if(proCatArray.get(position).cart_qty!=1) {
            proCatArray.get(position).cart_qty--;
            proCatArray.get(position).qty++;
            proCatArray.get(position).total = proCatArray.get(position).price * proCatArray.get(position).cart_qty;
            moreCategoryAdapter.notifyDataSetChanged();

            if (isChecked){
                total = total- proCatArray.get(position).price;
                tvGranTotalAmount.setText(String.format("Rs. %.2f",total));
            }

            //send data to server
            String url= HTTPPaths.baseUrl+ "cart_item_discrease.php";
            // String url= "http://sampletemp.96.lt/android/cart_item_discrease.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success){
                                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                }else {
                                    // Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("cart_id",proCatArray.get(position).cart_id+"");
                    params.put("pro_id",proCatArray.get(position).pid+"");
                    params.put("pro_qty",proCatArray.get(position).qty+"");
                    params.put("cart_qty",proCatArray.get(position).cart_qty+"");
                    params.put("cart_total",proCatArray.get(position).total+"");
                    return params;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


        }

    }


}
