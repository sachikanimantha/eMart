package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kosalgeek.android.json.JsonConverter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class ProductDetails extends AppCompatActivity {
    Toolbar mToolBar;
    TextView tvProductPrice,tvProductDescription,tvCount,tvCart,tvAddCart,tvProductName;
    ImageView ivProductImage,ivMinus,ivPlus;
    int count=1;
    int qty;
    int  newCount=0;
    //static int  newCount=0;
    Product productArray;
    ArrayList<Cart> arrayList;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tvProductPrice =(TextView)findViewById(R.id.tvProductPrice);
        tvCount =(TextView)findViewById(R.id.tvCount);
        tvProductDescription =(TextView)findViewById(R.id.tvProductDescription);
        tvCart =(TextView)findViewById(R.id.tvCart);
        tvAddCart =(TextView)findViewById(R.id.tvAddCart);
        tvProductName =(TextView)findViewById(R.id.tvProductName);

        ivProductImage =(ImageView) findViewById(R.id.ivProductImage);
        ivMinus =(ImageView) findViewById(R.id.ivMinus);
        ivPlus =(ImageView) findViewById(R.id.ivPlus);

        //get data from intent
        if(getIntent().getSerializableExtra("productArray")!=null) {
            productArray = (Product) getIntent().getSerializableExtra("productArray");
        }

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();
        id = pref.getInt("user_id",-1);


        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("Product Details");
        mToolBar.inflateMenu(R.menu.menu_main);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.cart:
                        Intent intent = new Intent(getApplicationContext(),ProductDetails.class);
                        startActivity(intent);
                        break;

                    case  R.id.category:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ProductDetails.this);
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                        intentIntegrator.setPrompt("Scan QR");
                        intentIntegrator.setCameraId(0);
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setBarcodeImageEnabled(false);
                        intentIntegrator.initiateScan();
                        break;

                }
                return true;
            }
        });

        //Get Data from server
        final String url= HTTPPaths.baseUrl+ "product_detials.php";
        //final String url="http://sampletemp.96.lt/android/product_detials.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList = new JsonConverter<Cart>().toArrayList(response,Cart.class);
                        tvProductDescription.setText(arrayList.get(0).description);
                        tvProductName.setText(arrayList.get(0).pname);
                        tvProductPrice.setText(String.format("Rs. %.2f",arrayList.get(0).price));
                        qty=(arrayList.get(0).qty)-1;
                        String urlImage=HTTPPaths.baseUrl+ "img/"+arrayList.get(0).pimg;
                        // String urlImage="http://sampletemp.96.lt/android/img/"+arrayList.get(0).pimg;
                        Picasso.with(ProductDetails.this)
                                .load(urlImage)
                                .placeholder(R.drawable.back)
                                .error(android.R.drawable.stat_notify_error)
                                .into(ivProductImage);
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
                Map<String,String> params = new HashMap<>();
                params.put("pid",productArray.pid+"");
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        //increase and decrease product quantity
        ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count!=1) {
                    count--;
                    tvCount.setText(count + "");
                    qty++;


                }
            }
        });

        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(ProductDetails.this, newCount +"b", Toast.LENGTH_SHORT).show();
                if(qty >0 && qty!=0){

                    count++;
                    newCount++;
                   // Toast.makeText(ProductDetails.this, newCount +"+", Toast.LENGTH_SHORT).show();
                    tvCount.setText(count+"");
                    qty--;

                }

            }
        });


        //Add to Cart
        tvAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id==-1 || id<1){
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                }else {

                   // Toast.makeText(ProductDetails.this, newCount + "bs", Toast.LENGTH_SHORT).show();
                    String url = HTTPPaths.baseUrl+ "add_to_cart.php";
                    // String url = "http://sampletemp.96.lt/android/add_to_cart.php";

                    StringRequest addCartString = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");

                                        if (success){
                                            Toast.makeText(ProductDetails.this, "Item is added to your cart", Toast.LENGTH_SHORT).show();
                                        }else {

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
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("cust_id", id + "");
                            params.put("pro_id", arrayList.get(0).pid + "");
                            params.put("cart_qty", count + "");
                            params.put("new_count", newCount + "");
                            params.put("total", count * arrayList.get(0).price + "");
                            params.put("qty", qty + "");
                            return params;
                        }
                    };
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(addCartString);
                    newCount = 0;
                    //Toast.makeText(ProductDetails.this, newCount + " add", Toast.LENGTH_SHORT).show();
                }

            }//end Onclick

        });//end setOnclick

        //go to the Cart
        tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==-1 || id<1){
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(),MyCartActivity.class);
                    startActivity(intent);
                }
            }
        });



    }//end OnCreate

    //QrCode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this, "You Canceled the scan", Toast.LENGTH_SHORT).show();
            }else {
                String qrResult=result.getContents();
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, qrResult, Toast.LENGTH_SHORT).show();
                String array[] = qrResult.split(" ");
                int pro_id=Integer.parseInt(array[0]);
                int sub_cat_id=Integer.parseInt(array[1]);
                int brand_id=Integer.parseInt(array[2]);


            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
