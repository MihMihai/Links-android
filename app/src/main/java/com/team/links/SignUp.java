package com.team.links;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText name;
    private EditText password;
    private EditText confirmPassword;
    private Button birthdayDate;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        birthdayDate = (Button) findViewById(R.id.date);
        signUp = (Button) findViewById(R.id.signup_button);

        signUp.setOnClickListener(this);
        birthdayDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                makeSignUpRequest();
                break;
            case R.id.date:
                showDatePickerDialog();
                break;
        }
    }

    private void makeSignUpRequest() {
        HashMap<String,String> params = new HashMap<>();
        params.put("email",email.getText().toString());
        params.put("name",name.getText().toString());
        try {
            Date birthday = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(birthdayDate.getText().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);
            params.put("birth_day", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            params.put("birth_month",new SimpleDateFormat("MMMM",Locale.US).format(calendar.getTime()));
            params.put("birth_year", String.valueOf(calendar.get(Calendar.YEAR)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.put("password",password.getText().toString());

        for(Map.Entry<String,String> entry : params.entrySet())
            Log.e(entry.getKey(),entry.getValue());

        if(params.size() != 6)
            ActivityController.showToastMessage(this,Constants.somethingWrong);

        PostRequestAsync signUpRequest = new PostRequestAsync(Constants.signUp,this,params,false);
        signUpRequest.execute();
    }

    public void showDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        final int cYear = calendar.get(Calendar.YEAR);
        final int cMonth = calendar.get(Calendar.MONTH);
        final int cDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                if(month>9) {
                    if(day>9)
                        birthdayDate.setText(year + "-" + month + "-" + day);
                    else
                        birthdayDate.setText(year + "-" + month + "-0" + day);
                }
                else {
                    if(day>9)
                        birthdayDate.setText(year + "-0" + month + "-" + day);
                    else
                        birthdayDate.setText(year + "-0" + month + "-0" + day);
                }
            }
        }, cYear,cMonth,cDay);
        datePickerDialog.show();
    }
}
