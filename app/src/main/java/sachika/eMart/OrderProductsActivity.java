package sachika.eMart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class OrderProductsActivity extends AppCompatActivity {
    Toolbar mToolBar;
    int order_id;
    RecyclerView rvOrderSummaryProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_products);

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("Order Summary");

        //set the back arrow in the toolbar
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        //get data from intent
        if(getIntent().getSerializableExtra("order")!=null){
            Order order = (Order) getIntent().getSerializableExtra("order");
            order_id = order.order_id;
        }

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(OrderProductsActivity.this);
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

        //set RecyclerView to Order summary products
        rvOrderSummaryProducts = (RecyclerView)findViewById(R.id.rvOrderSummaryProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvOrderSummaryProducts.setLayoutManager(layoutManager);
        rvOrderSummaryProducts.setHasFixedSize(true);

        String url = HTTPPaths.baseUrl+ "order_summary.php";
        //String url = "http://sampletemp.96.lt/android/order_summary.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<OrderSummary> arrayList = new JsonConverter<OrderSummary>().toArrayList(response,OrderSummary.class);
                        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(OrderProductsActivity.this,arrayList);
                        rvOrderSummaryProducts.setAdapter(orderSummaryAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("order_id",order_id+"");
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }//end onCrete
}
