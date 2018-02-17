package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class
MyOrdersActivity extends AppCompatActivity {

    Toolbar mToolBar;

    RecyclerView rvMyOrders;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();
        id = pref.getInt("user_id",-1);

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("My Orders");

        //set the back arrow in the toolbar
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        mToolBar.inflateMenu(R.menu.menu_main);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.cart:
                        Intent intent = new Intent(getApplicationContext(),MyCartActivity.class);
                        startActivity(intent);
                        break;

                    case  R.id.category:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(MyOrdersActivity.this);
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

        //Set Orders RecyclerView
        rvMyOrders = (RecyclerView)findViewById(R.id.rvMyOrders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvMyOrders.setLayoutManager(layoutManager);
        rvMyOrders.setHasFixedSize(true);

        String url= HTTPPaths.baseUrl+ "order.php";
        // String url="http://sampletemp.96.lt/android/order.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Order> arrayList = new JsonConverter<Order>().toArrayList(response,Order.class);
                        OrderAdapter orderAdapter = new OrderAdapter(MyOrdersActivity.this,arrayList);
                        rvMyOrders.setAdapter(orderAdapter);
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
                params.put("cust_id",id+"");
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }//end onCreate

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
