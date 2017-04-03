package com.team.links;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mihai on 13.03.2017.
 */

public class RequestHandler {

    //private static final String baseUrl = "http://188.26.165.190/api";
    private static final String baseUrl = "http://188.27.105.45/api"; //Current IP

    static String Get(String path, HashMap<String, String> queryParamers) {
        String baseURL = baseUrl + path + "?";
        int i=0;
        for(Map.Entry<String,String> entry : queryParamers.entrySet()) {
            i++;
            baseURL+=entry.getKey() + "=" + entry.getValue();
            if(i!=queryParamers.size()) {
                baseURL += "&";
            }
        }
        return baseURL;
    }

    static JSONObject performPostCall(final String path, final HashMap<String,String> postDataParams, final Activity context,
                                      boolean reqAuth) {
        final String[] response = {""};
        final JSONObject[] json = {null};
        URL url;

        try {
            url = new URL(baseUrl + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            /*if(reqAuth) {
                conn.setRequestProperty("Authorization",Data.user.getAccesToken());
            }*/

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            InputStream readStream = null;

            if(responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line = br.readLine()) != null) {
                    response[0] += line;
                }
            }
            else {
                String line;
                if(conn.getErrorStream() != null)
                    readStream = conn.getErrorStream();
                else if(conn.getInputStream() != null)
                    readStream  = conn.getInputStream();
                if(readStream != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(readStream));
                    while((line = br.readLine()) != null) {
                        response[0] += line;
                    }
                }
            }
            json[0] = new JSONObject(response[0]);
            String status = "";
            if(json[0].has("status")) {
                status = json[0].getString("status");
            }
            else {
                status = json[0].getString("error");
                final String description = json[0].getString("description"); Log.e("decription",description + " ");
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityController.showToastMessage(context,description);
                    }
                });
                json[0] = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json[0];
    }

    private static String getPostDataString(HashMap<String,String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String,String> entry : params.entrySet()) {
            if(first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return result.toString();
    }
}
