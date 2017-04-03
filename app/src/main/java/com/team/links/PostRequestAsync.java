package com.team.links;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Mihai on 13.03.2017.
 */

public class PostRequestAsync extends AsyncTask<Void,Void,Void> {

    private Activity context;
    private HashMap<String, String> params = new HashMap<>();
    private String path;
    private boolean reqAuth;
    private JSONObject response;

    public PostRequestAsync(String path, Activity context, HashMap<String, String> params, boolean reqAuth) {
        this.path = path;
        this.context = context;
        this.params = params;
        this.reqAuth = reqAuth;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        response = RequestHandler.performPostCall(path, params, context, reqAuth);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (response != null) {
            switch (path) {
                case Constants.login:
                    try {
                        //Toast.makeText(context, response.getString("access_token"), Toast.LENGTH_LONG).show();
                        ActivityController.showToastMessage(context,response.getString("access_token"));
                        Intent loginIntent = new Intent(context,MainActivity.class);
                        context.startActivity(loginIntent);
                        context.finish();
                        /*Data.user = new User();
                        Data.user.setAccesToken(json[0].getString("acces_token"));*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constants.signUp:
                    ActivityController.showToastMessage(context,"Succesful signup! Check database! :P");
                    context.finish();
                    break;
                case Constants.forgotPassword:
                    ActivityController.showToastMessage(context,"Email sent, check yo'self!");
                    context.finish();
                    break;
            }
        }
        /*else {
            ActivityController.showToastMessage(context,Constants.somethingWrong);
        }*/
    }
}