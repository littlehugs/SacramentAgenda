package uk.co.littlehugs.sacramentagenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugs on 11/04/16.
 */
public class PostToUrl extends AsyncTask<String, Void, String> {
    private Context mContext;

    ProgressDialog dialog ;
    List<Agenda> arrAgenda = new ArrayList<Agenda>();
    URL url;
    private DBHelper dh;
    String line = "";

    private StringBuilder sbParams;
    private String paramsString;
    private DataOutputStream wr;
    private StringBuilder result;

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String formattedDateTime;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    //private static final String POST_URL = "http://www.littlehugs.co.uk/sacramentagenda/insert4.php";

    PostToUrl(android.content.Context context) {
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
        try{
            formattedDateTime = params[1];
            URL obj = new URL(params[2]);
            HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            //Log.e("JSONString:", params[0]);

            wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params[0]);
            wr.flush();
            wr.close();

            try {
                //Receive the response from the server
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    //Log.d("line", line);
                }

                //Log.d("JSON Parser", "agendaResult: " + result.toString());

                jsonArray = new JSONArray(result.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            connection.disconnect();

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);
        String tableName = "";
        String id = "";
        int success = 0;
        String message = "";

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }


            //Toast.makeText(mContext, jsonArray.toString(),Toast.LENGTH_LONG).show();
            try {
                if (result != null && !result.toString().contains("[] ")) {

                    dh = new DBHelper(mContext);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        tableName = jsonObject.getString("tableName");
                        id = jsonObject.getString("recordId");
                        success = jsonObject.getInt("success");
                        message = jsonObject.getString("message");
                        //success - insert=1, update=2, delete=3
                        if (success == 1 || success == 2) {
                            //Log.d("Success!", message + " - " + formattedDateTime);
                            if (tableName.contains("AGENDA")) {
                                dh.updateAgendaUpdateDate(id, formattedDateTime);
                            } else if (tableName.contains("ACKNOWLEDGEMENTS")) {
                                dh.updateAcknowledgementUpdateDate(id, formattedDateTime);
                            } else if (tableName.contains("ANNOUNCEMENTS")) {
                                dh.updateAnnouncementUpdateDate(id, formattedDateTime);
                            } else if (tableName.contains("STAKEBUSINESS")) {
                                dh.updateStakeBusinessUpdateDate(id, formattedDateTime);
                            } else if (tableName.contains("WARDBUSINESS")) {
                                dh.updateWardBusinessUpdateDate(id, formattedDateTime);
                            }
                        } else if (success == 3) {
                            if (tableName.contains("AGENDA")) {
                                dh.hardDeleteAgenda(id);
                            } else if (tableName.contains("ACKNOWLEDGEMENTS")) {
                                dh.hardDeleteAcknowledgements(id);
                            } else if (tableName.contains("ANNOUNCEMENTS")) {
                                dh.hardDeleteAnnouncements(id);
                            } else if (tableName.contains("STAKEBUSINESS")) {
                                dh.hardDeleteStakeBusiness(id);
                            } else if (tableName.contains("WARDBUSINESS")) {
                                dh.hardDeleteWardBusiness(id);
                            }
                        } else if (success == 4) {
                            if (tableName.contains("AGENDA")) {
                                dh.deleteAgenda(id, formattedDateTime);
                            } else if (tableName.contains("ACKNOWLEDGEMENTS")) {
                                dh.deleteAcknowledgements(id, formattedDateTime);
                            } else if (tableName.contains("ANNOUNCEMENTS")) {
                                dh.deleteAnnouncements(id, formattedDateTime);
                            } else if (tableName.contains("STAKEBUSINESS")) {
                                dh.deleteStakeBusiness(id, formattedDateTime);
                            } else if (tableName.contains("WARDBUSINESS")) {
                                dh.deleteWardBusiness(id, formattedDateTime);
                            }
                        } else {
                            Log.d("Failure", id + "--" + message);
                        }
                    }
                    //Toast.makeText(mContext, "'" + result.length() + "'", Toast.LENGTH_SHORT).show();
                    dh.addLastSync(formattedDateTime, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}
