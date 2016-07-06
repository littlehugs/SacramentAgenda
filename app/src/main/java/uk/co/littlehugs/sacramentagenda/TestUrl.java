package uk.co.littlehugs.sacramentagenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hugs on 29/04/16.
 */
public class TestUrl extends AsyncTask<String, Void, String> {
    private Context mContext;
    ProgressDialog dialog ;
    private StringBuilder result;
    private JSONArray jsonArray;

    TestUrl(android.content.Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // show progress dialog when downloading
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("Attempting sync...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            URL obj = new URL(params[0]+"?webtest=t");
            HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            try {
                //Receive the response from the server
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                //Log.d("JSON Result", "JSONResult: " + result.toString());
                if (result.length() > 1) {
                    jsonArray = new JSONArray(result.toString());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                //webServiceFailed();
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        try {
            if (result != null && result.length() > 1) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(mContext,"Web Service Connection Successful",Toast.LENGTH_LONG).show();
                    } else {
                        webServiceFailed();
                    }
                }
            } else {
                webServiceFailed();
            }
            } catch (Exception e) {
            e.printStackTrace();
            webServiceFailed();
            }
    }

    private void webServiceFailed(){
        Toast.makeText(mContext,"Web Service Connection Failed",Toast.LENGTH_LONG).show();
    }


}
