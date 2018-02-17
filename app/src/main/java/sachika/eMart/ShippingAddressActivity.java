package sachika.eMart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class ShippingAddressActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;

    //Views
    EditText etName,etStreet,etCity,etProvince,etMobile;
    Button btnChangeAddress;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int id;
    String name, province, street,mobile,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        //initialize views
        etName = (EditText)findViewById(R.id.etName);
        etStreet = (EditText)findViewById(R.id.etStreet);
        etCity = (EditText)findViewById(R.id.etCity);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etProvince = (EditText)findViewById(R.id.etProvince);
        btnChangeAddress = (Button)findViewById(R.id.btnChangeAddress);

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();

        id = pref.getInt("user_id",-1);
        name = pref.getString("name","");
        street = pref.getString("street","");
        city = pref.getString("city","");
        mobile = pref.getString("mobile","");
        province = pref.getString("province","");

        //set data to Views
        etName.setText(name);
        etStreet.setText(street);
        etMobile.setText(mobile);
        etCity.setText(city);
        etProvince.setText(province);


        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("Shipping Address");

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(ShippingAddressActivity.this);
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

        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uName = etName.getText().toString();
                final String uMobile = etMobile.getText().toString();
                final String uCity = etCity.getText().toString();
                final String uProvince = etProvince.getText().toString();
                final String uStreet = etStreet.getText().toString();

                //Update the customer details
                 String url = HTTPPaths.baseUrl+ "change_address.php";
                //String url = "http://sampletemp.96.lt/android/change_address.php";
                StringRequest stringRequest =new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success){
                                        Toast.makeText(ShippingAddressActivity.this, "Shipping address is updated", Toast.LENGTH_SHORT).show();
                                        editor.putString("name",uName);
                                        editor.putString("city",uCity);
                                        editor.putString("mobile",uMobile);
                                        editor.putString("province",uProvince);
                                        editor.putString("street",uStreet);
                                        editor.apply();
                                    }else {
                                        Toast.makeText(ShippingAddressActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
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
                        Map<String,String> params = new HashMap<>();
                        params.put("cust_id",id+"");
                        params.put("name",uName);
                        params.put("mobile",uMobile);
                        params.put("city",uCity);
                        params.put("street",uStreet);
                        params.put("province",uProvince);
                        return params;
                    }
                };//end StringRequest
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });

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
