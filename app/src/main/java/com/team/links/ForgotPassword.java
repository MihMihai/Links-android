package com.team.links;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private Button sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.email);
        sendEmail = (Button) findViewById(R.id.send_email);

        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_email:
                makeForgotPasswordRequest();
                break;
        }
    }
    private void makeForgotPasswordRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email.getText().toString());

        for(Map.Entry<String,String> entry : params.entrySet())
            Log.e(entry.getKey(),entry.getValue());

        if(params.size() != 1)
            ActivityController.showToastMessage(this,Constants.somethingWrong);

        PostRequestAsync forgotPasswordRequest = new PostRequestAsync(Constants.forgotPassword,this,params,false);
        forgotPasswordRequest.execute();
    }
}
