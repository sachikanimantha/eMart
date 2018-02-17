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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class MyProfileActivity extends AppCompatActivity {
    //Toolbar
    private Toolbar mToolBar;
    EditText etUserName,etEmail,etMobile,etBirthday;
    ImageView ivProfile;
    Button btnUpdate;

    //SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int id;
    String name, email, pro_pic,mobile,birthday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //initialize Views
        etUserName = (EditText)findViewById(R.id.etUserName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        //get data from sharePreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();

        id = pref.getInt("user_id",-1);
        name = pref.getString("name","");
        email = pref.getString("email","");
        pro_pic = pref.getString("pro_pic","");
        mobile = pref.getString("mobile","");
        birthday = pref.getString("birthday","");

        String proPicUrl= HTTPPaths.baseUrl+ "img/"+pro_pic;
        // String proPicUrl="http://sampletemp.96.lt/android/img/"+pro_pic;
        Picasso.with(this)
                .load(proPicUrl)
                .placeholder(R.drawable.profile2)
                .error(android.R.drawable.stat_notify_error)
                .into(ivProfile);


        etUserName.setText(name);
        etEmail.setText(email);
        etMobile.setText(mobile);
        etBirthday.setText(birthday);

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.tb_main);
        mToolBar.setTitle("My Profile");

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
                        IntentIntegrator intentIntegrator = new IntentIntegrator(MyProfileActivity.this);
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


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uName = etUserName.getText().toString();
                final String uMobile = etMobile.getText().toString();
                final String uBirthday = etBirthday.getText().toString();
                final String uEmail = etEmail.getText().toString();


                //Update the customer details
                String url = HTTPPaths.baseUrl+ "my_profile.php";
                //  String url = "http://sampletemp.96.lt/android/my_profile.php";
                StringRequest stringRequest =new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success){
                                        Toast.makeText(MyProfileActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                        editor.putString("name",uName);
                                        editor.putString("email",uEmail);
                                        editor.putString("mobile",uMobile);
                                        editor.putString("birthday",uBirthday);
                                        editor.apply();
                                    }else {
                                        Toast.makeText(MyProfileActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
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
                        params.put("birthday",uBirthday);
                        params.put("email",uEmail);
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
