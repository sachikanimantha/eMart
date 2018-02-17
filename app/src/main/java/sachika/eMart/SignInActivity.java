
package sachika.eMart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

public class SignInActivity extends AppCompatActivity {

    TextView tvForgetPassword;
    Button btnSignIn;
    EditText etUserName,etPassword;
    TextInputLayout tilUserName,tilPassword;

    AlertDialog.Builder builder;

    String userName,password ;

    ArrayList<User> arrayList;


    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Views
        tilUserName = (TextInputLayout) findViewById(R.id.textInputLayout5);
        tilPassword = (TextInputLayout) findViewById(R.id.textInputLayout6);
        etUserName =(EditText)findViewById(R.id.etUserName);
        etPassword =(EditText)findViewById(R.id.etPassword);
        builder = new AlertDialog.Builder(SignInActivity.this);
        tvForgetPassword = (TextView)findViewById(R.id.tvForgetPassword);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //SharedPreferences
        pref = getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        editor =pref.edit();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               userName = etUserName.getText().toString();
               password = etPassword.getText().toString();

               if(userName.isEmpty() || password.isEmpty()){
                   builder.setTitle("Something went wrong.");
                   alertDailog("Enter valid username and password.");
               }
               else {
                   String url = HTTPPaths.baseUrl+ "sign_in.php";
                   // String url = "http://sampletemp.96.lt/android/sign_in.php";
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                           new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                  // arrayList= new JsonConverter<User>().toArrayList(response,User.class);
                                   try {
                                       JSONObject jsonResponse = new JSONObject(response);
                                       boolean success = jsonResponse.getBoolean("success");

                                       if (success){
                                           editor.putInt("user_id",jsonResponse.getInt("user_id"));
                                           editor.putString("user_name",jsonResponse.getString("user_name"));
                                           editor.putString("email",jsonResponse.getString("email"));
                                           editor.putString("pro_pic",jsonResponse.getString("pro_pic"));
                                           editor.putString("name",jsonResponse.getString("name"));
                                           editor.putString("pro_pic",jsonResponse.getString("pro_pic"));
                                           editor.putString("mobile",jsonResponse.getString("mobile"));
                                           editor.putString("birthday",jsonResponse.getString("birthday"));
                                           editor.putString("street",jsonResponse.getString("street"));
                                           editor.putString("city",jsonResponse.getString("city"));
                                           editor.putString("province",jsonResponse.getString("province"));
                                           editor.apply();

                                           Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                           startActivity(intent);
                                           finish();

                                          /* Log.d("SignInActivity",pref.getString("user_name",""));
                                           int id = pref.getInt("user_id",-1);
                                           Log.d("SignInActivity",id+"");
*/
//                                           pref.edit().clear().commit();


                                       }else {
                                           Toast.makeText(SignInActivity.this, "Invalid User Name or Password", Toast.LENGTH_SHORT).show();
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
                           params.put("user_name",userName);
                           params.put("password",password);
                           return params;
                       }
                   };

                   MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest); //end sending data to server

               }//end else of view check
           }//onClick
       });//btnClickListener


    }//end onCreate

    public void alertDailog(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etUserName.setText("");
                etPassword.setText("");
            }

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
