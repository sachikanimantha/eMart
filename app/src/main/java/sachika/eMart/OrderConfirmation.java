package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class OrderConfirmation extends Activity {

    Toolbar mToolBar;
    RecyclerView rvItem;
    TextView tvItemsCount,tvTotalPrice,tvBuy;
    String cart_id="";
    ArrayList<Cart> selectionArray ;
    OrderConfirmationsAdapter adapter;
    double total=0;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int id;


    // paypal
    PayPalConfiguration m_conConfiguration;
    String m_payPalClientID="AdWiN4TvOSKpEQZd2E-hSME4J7T2ghBqC4155jiSy6hdrK3D9VAcFB58w-FgMLewHyDQDpccT8QV0t74";
    Intent m_servise;
    int m_paypalRequestCode = 999;//any number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        tvItemsCount = (TextView)findViewById(R.id.tvItemsCount);
        tvTotalPrice = (TextView)findViewById(R.id.tvTotalPrice);
        tvBuy = (TextView)findViewById(R.id.tvBuy);

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();
        id = pref.getInt("user_id",-1);
        Log.d("MyCartActivity",id+"");

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("Order Confirmation");
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

                        break;

                }
                return true;
            }
        });

        // payPal
        m_conConfiguration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test real product
                .clientId(m_payPalClientID);

        m_servise = new Intent(this, PayPalService.class);
        m_servise.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_conConfiguration);
        startService(m_servise); //paypal service, listning to calls to paypal app

        //getData From Intent
        if(getIntent().getSerializableExtra("selectionArray")!=null) {
            selectionArray= (ArrayList<Cart>) getIntent().getSerializableExtra("selectionArray");
            rvItem =(RecyclerView) findViewById(R.id.rvItems);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );

            rvItem.setLayoutManager(layoutManager);
            rvItem.setHasFixedSize(true);
            adapter = new OrderConfirmationsAdapter(selectionArray,OrderConfirmation.this);
            rvItem.setAdapter(adapter);

            tvItemsCount.setText("("+selectionArray.size()+" items)");

                for (int i=0;i<selectionArray.size();i++) {
                    total = total + selectionArray.get(i).total;
                    tvTotalPrice.setText(String.format("Rs. %.2f", total));
                }
        }

        for (int i=0;i<selectionArray.size();i++) {
            cart_id = cart_id + selectionArray.get(i).cart_id+",";
        }

    //Buy Products
        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                 String url = HTTPPaths.baseUrl+ "add_order.php";
                // String url = "http://sampletemp.96.lt/android/add_order.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonArray = new JSONObject(response);
                                    boolean success = jsonArray.getBoolean("success");

                                    if(success){
                                        //Toast.makeText(OrderConfirmation.this, "Payment success", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(OrderConfirmation.this, "Some error is occurred", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
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
                        params.put("cust_id",id+"");
                        params.put("amount",total+"");
                        params.put("cart_id",cart_id+"");
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                //Toast.makeText(OrderConfirmation.this, cart_id+"total is "+total, Toast.LENGTH_SHORT).show();


                    //Paypal
                    double payPalTotal = total/156;

                    PayPalPayment palPayment = new PayPalPayment(new BigDecimal(payPalTotal), "USD", "Payment with PayPal",
                            PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_conConfiguration);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT,palPayment);
                    startActivityForResult(intent, m_paypalRequestCode);

            }
        });//end buyProduct

    }//end OnCreate


    public void increaseQty(int p){

        final int ps =p;

        if(selectionArray.get(ps).qty >0 && selectionArray.get(ps).qty !=0) {

            selectionArray.get(ps).cart_qty++;
            selectionArray.get(ps).qty--;
            selectionArray.get(ps).total=selectionArray.get(ps).price*selectionArray.get(ps).cart_qty;
            adapter.notifyDataSetChanged();


                total = total+ selectionArray.get(ps).price;
                tvTotalPrice.setText(String.format("Rs. %.2f",total));

            //send data to server
           String url= HTTPPaths.baseUrl+ "cart_item_increase.php";
            // String url= "http://sampletemp.96.lt/android/cart_item_increase.php";
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
                    params.put("cart_id",selectionArray.get(ps).cart_id+"");
                    params.put("pro_id",selectionArray.get(ps).pid+"");
                    params.put("pro_qty",selectionArray.get(ps).qty+"");
                    params.put("cart_qty",selectionArray.get(ps).cart_qty+"");
                    params.put("cart_total",selectionArray.get(ps).total+"");
                    return params;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest); //end sending data to server

        }//end if
    }
    public void discreaseQty(final int position){
        if(selectionArray.get(position).cart_qty!=1) {
            selectionArray.get(position).cart_qty--;
            selectionArray.get(position).qty++;
            selectionArray.get(position).total = selectionArray.get(position).price * selectionArray.get(position).cart_qty;
            adapter.notifyDataSetChanged();


                total = total- selectionArray.get(position).price;
                tvTotalPrice.setText(String.format("Rs. %.2f",total));


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
                    params.put("cart_id",selectionArray.get(position).cart_id+"");
                    params.put("pro_id",selectionArray.get(position).pid+"");
                    params.put("pro_qty",selectionArray.get(position).qty+"");
                    params.put("cart_qty",selectionArray.get(position).cart_qty+"");
                    params.put("cart_total",selectionArray.get(position).total+"");
                    return params;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


        }

    }




    //========== PayPal

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == m_paypalRequestCode){
            if(requestCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    String state = confirmation.getProofOfPayment().getState();
                    if (state.equals("approved")){
                        Toast.makeText(this, "Payment Approved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Error in the payment", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(this, "Confirmation is null", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/



    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    Toast.makeText(this, "Payment is successfull", Toast.LENGTH_SHORT).show();

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
        }
    }





}
