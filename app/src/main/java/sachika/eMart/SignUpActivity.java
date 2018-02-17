package sachika.eMart;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout tilUserName,tilEmail,tilPassword,tilPasswordCoonfirmed;
    EditText etUserName,etEmail,etPassword,etPasswordConfirmed;
    ImageView ivSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeWidget();

        try {
            YoYo.with(Techniques.ZoomInUp)
                    .duration(700)
                    .playOn(tilUserName);
        }catch (Exception e){

        }

        try {
            YoYo.with(Techniques.ZoomInUp)
                    .duration(800)
                    .playOn(tilEmail);
        }catch (Exception e){

        }

        try {
            YoYo.with(Techniques.ZoomInUp)
                    .duration(900)
                    .playOn(tilPassword);
        }catch (Exception e){

        }

        try {
            YoYo.with(Techniques.ZoomInUp)
                    .duration(1000)
                    .playOn(tilPasswordCoonfirmed);
        }catch (Exception e){

        }

        try {
            YoYo.with(Techniques.RollIn)
                    .duration(1000)
                    .playOn(ivSignUp);
        }catch (Exception e){

        }




        ivSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void initializeWidget(){
        tilUserName = (TextInputLayout)findViewById(R.id.textInputLayout);
        tilEmail = (TextInputLayout)findViewById(R.id.textInputLayout2);
        tilPassword = (TextInputLayout)findViewById(R.id.textInputLayout3);
        tilPasswordCoonfirmed = (TextInputLayout)findViewById(R.id.textInputLayout4);

        etUserName  = (EditText) findViewById(R.id.etUserName);
        etEmail  = (EditText) findViewById(R.id.etEmail);
        etPassword  = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmed  = (EditText) findViewById(R.id.etConfirmPassword);

        ivSignUp = (ImageView)findViewById(R.id.ivSignUp);

    }

    public  void signUp(){
        boolean isValid = true;

        if(etUserName.getText().toString().isEmpty()){
            isValid = false;
            tilUserName.setError("User Name is mandatory");
        }
        else{
            tilUserName.setErrorEnabled(false);
        }

        if(etEmail.getText().toString().isEmpty()){
            isValid = false;
            tilEmail.setError("Email is mandatory");
        }
        else{
            tilEmail.setErrorEnabled(false);
        }

        if(etPassword.getText().toString().isEmpty()){
            isValid = false;
            tilPassword.setError("Password is mandatory");
        }
        else{
            tilPassword.setErrorEnabled(false);
        }

        if(etPasswordConfirmed.getText().toString().isEmpty()){
            isValid = false;
            tilPasswordCoonfirmed.setError("Enter your");
        }
        else{
            tilPasswordCoonfirmed.setErrorEnabled(false);
        }

        if(!etPassword.getText().toString().equals(etPasswordConfirmed.getText().toString())){
            isValid = false;
            tilPasswordCoonfirmed.setError("Password is not match");
        }
        else{
            tilPasswordCoonfirmed.setErrorEnabled(false);
        }


        if(isValid){
            Toast.makeText(SignUpActivity.this, "Successfully registered...", Toast.LENGTH_SHORT).show();
        }
    }

}
