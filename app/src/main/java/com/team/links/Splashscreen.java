package com.team.links;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class Splashscreen extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        TextView forgotPassword = (TextView) findViewById(R.id.forgot_password);
        TextView signup = (TextView) findViewById(R.id.signup);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                HashMap<String,String> params = new HashMap<>();
                params.put("email",email.getText().toString());
                params.put("password",password.getText().toString());

                PostRequestAsync loginRequest = new PostRequestAsync(Constants.login,this,params,false);
                loginRequest.execute();
                break;
            case R.id.forgot_password:
                Intent forgotPasswordIntent = new Intent(this,ForgotPassword.class);
                startActivity(forgotPasswordIntent);
                break;
            case R.id.signup:
                Intent signupIntent = new Intent(this,SignUp.class);
                startActivity(signupIntent);
                break;
        }
    }
}
