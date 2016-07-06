package uk.co.littlehugs.sacramentagenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
 * Created by hugs on 18/04/16.
 */
public class GetFromUrl extends AsyncTask<String, Void, String> {
    private Context mContext;
private DBHelper dh;
    ProgressDialog dialog ;
    private String formattedDateTime;
    private String lastSync;
    private StringBuilder result;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    GetFromUrl(android.content.Context context) {
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
            formattedDateTime = params[1];
            lastSync = params[0];
            URL obj = new URL(params[3]+"?lastupdate="+lastSync +"&table="+params[2] +"&currentupdate="+formattedDateTime);
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
                dh = new DBHelper(mContext);
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("tableName").contains("agenda")) {
                        Agenda agenda = createAgenda(jsonObject);
                        if (agenda.getAGENDA_ID().contains("0")){
                            if (jsonObject.getString("sqlType").contains("insert")) {
                                dh.addAgenda(agenda);
                            } else if (jsonObject.getString("sqlType").contains("update")) {
                                if (dh.getSingleAgenda(jsonObject.getString("agendaId")).size() == 0){
                                    //Toast.makeText(mContext,"Insert",Toast.LENGTH_LONG).show();
                                    dh.addAgenda(agenda);
                                } else {
                                    //Toast.makeText(mContext,"Update",Toast.LENGTH_LONG).show();
                                    dh.editAgenda(agenda, jsonObject.getString("agendaId"));
                                }

                            } else if (jsonObject.getString("sqlType").contains("delete")){
                                dh.hardDeleteAgenda(jsonObject.getString("agendaId"));
                            }
                        }
                    } else if (jsonObject.getString("tableName").contains("acknowledgements")) {
                        Acknowledgements acknowledgements = createAcknowledgements(jsonObject);
                        if (acknowledgements.getACKNOWLEDGEMENT_ID().contains("0")){
                            if (jsonObject.getString("sqlType").contains("insert")) {
                                dh.addAcknowledgements(acknowledgements);
                            } else if (jsonObject.getString("sqlType").contains("update")) {
                                if (dh.getSingleAcknowledgements(jsonObject.getString("acknowledgementId")).size() == 0) {
                                    dh.addAcknowledgements(acknowledgements);
                                } else {
                                    dh.editAcknowledgements(acknowledgements, jsonObject.getString("acknowledgementId"));
                                }
                            } else if (jsonObject.getString("sqlType").contains("delete")){
                                dh.hardDeleteAcknowledgements(jsonObject.getString("acknowledgementId"));
                            }
                        }
                    } else if (jsonObject.getString("tableName").contains("announcements")) {
                        Announcements announcements = createAnnouncements(jsonObject);
                        if (announcements.getANNOUNCEMENTS_ID().contains("0")){
                            if (jsonObject.getString("sqlType").contains("insert")) {
                                dh.addAnnouncements(announcements);
                            } else if (jsonObject.getString("sqlType").contains("update")) {
                                if (dh.getSingleAnnouncements(jsonObject.getString("announcementsId")).size() == 0) {
                                    dh.addAnnouncements(announcements);
                                } else {
                                    dh.editAnnouncements(announcements, jsonObject.getString("announcementsId"));
                                }
                            } else if (jsonObject.getString("sqlType").contains("delete")){
                                dh.hardDeleteAnnouncements(jsonObject.getString("announcementsId"));
                            }
                        }
                    } else if (jsonObject.getString("tableName").contains("stakebusiness")) {
                        StakeBusiness stakeBusiness = createStakeBusiness(jsonObject);
                        if (stakeBusiness.getSTAKEBUSINESS_ID().contains("0")){
                            if (jsonObject.getString("sqlType").contains("insert")) {
                                dh.addStakeBusiness(stakeBusiness);
                            } else if (jsonObject.getString("sqlType").contains("update")) {
                                if (dh.getSingleStakeBusiness(jsonObject.getString("stakeBusinessId")).size() == 0) {
                                    dh.addStakeBusiness(stakeBusiness);
                                } else {
                                    dh.editStakeBusiness(stakeBusiness, jsonObject.getString("stakeBusinessId"));
                                }
                            } else if (jsonObject.getString("sqlType").contains("delete")){
                                dh.hardDeleteStakeBusiness(jsonObject.getString("stakeBusinessId"));
                            }
                        }
                    } else if (jsonObject.getString("tableName").contains("wardbusiness")) {
                        WardBusiness wardBusiness = createWardBusiness(jsonObject);
                        if (wardBusiness.getWARDBUSINESS_ID().contains("0")){
                            if (jsonObject.getString("sqlType").contains("insert")) {
                                dh.addWardBusiness(wardBusiness);
                            } else if (jsonObject.getString("sqlType").contains("update")) {
                                if (dh.getSingleWardBusiness(jsonObject.getString("wardBusinessId")).size() == 0) {
                                    dh.addWardBusiness(wardBusiness);
                                } else {
                                    dh.editWardBusiness(wardBusiness, jsonObject.getString("wardBusinessId"));
                                }
                            } else if (jsonObject.getString("sqlType").contains("delete")){
                                dh.hardDeleteWardBusiness(jsonObject.getString("wardBusinessId"));
                            }
                        }
                    }
                }
                //Toast.makeText(mContext, "'" + result.length() + "'", Toast.LENGTH_SHORT).show();
                dh.addLastSync(formattedDateTime, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Agenda createAgenda(JSONObject jsonObject) {
        try {
            return new Agenda(jsonObject.getString("agendaId"),
                    jsonObject.getString("agendaDate"), jsonObject.getInt("agendaOpeningHymn"),
                    jsonObject.getString("agendaOpeningPrayer"), jsonObject.getInt("agendaSacramentHymn"),
                    jsonObject.getString("agendaFirstSpeaker"), jsonObject.getString("agendaSecondSpeaker"),
                    jsonObject.getInt("agendaIntermediateHymn"), jsonObject.getString("agendaFinalSpeaker"),
                    jsonObject.getInt("agendaClosingHymn"), jsonObject.getString("agendaClosingPrayer"),
                    jsonObject.getString("agendaUpdateDate"), jsonObject.getString("agendaStatus")
            );
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return new Agenda();
    }

    private Acknowledgements createAcknowledgements(JSONObject jsonObject) {
        try{
            return  new Acknowledgements(jsonObject.getString("acknowledgementId"),
                    jsonObject.getString("acknowledgementDate"), jsonObject.getString("acknowledgementPresiding")
                    , jsonObject.getString("acknowledgementConducting"), jsonObject.getString("acknowledgementStakeVisitor1"), jsonObject.getString("acknowledgementStakeCalling1")
                    , jsonObject.getString("acknowledgementStakeVisitor2"), jsonObject.getString("acknowledgementStakeCalling2"), jsonObject.getString("acknowledgementStakeVisitor3")
                    , jsonObject.getString("acknowledgementStakeCalling3"), jsonObject.getString("acknowledgementUpdateDate"), jsonObject.getString("acknowledgementStatus")
                    );
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return  new Acknowledgements();
    }

    private Announcements createAnnouncements(JSONObject jsonObject){
        try{
            return new Announcements(jsonObject.getString("announcementsId"),jsonObject.getString("announcementsDate"),
                    jsonObject.getString("announcements"), jsonObject.getString("announcementUpdateDate"),jsonObject.getString("announcementStatus"));
        } catch (JSONException j){
            j.printStackTrace();
        }
        return new Announcements();
    }

    private StakeBusiness createStakeBusiness(JSONObject jsonObject) {
        try{
            return new StakeBusiness(jsonObject.getString("stakeBusinessId"), jsonObject.getString("stakeDate"), jsonObject.getString("stakeBusiness"),
                    jsonObject.getString("stakeBusinessUpdateDate"),jsonObject.getString("stakeBusinessStatus"));
        } catch (JSONException j){
            j.printStackTrace();
        }
        return  new StakeBusiness();
    }

    private WardBusiness createWardBusiness(JSONObject jsonObject){
        try{
            return  new WardBusiness(jsonObject.getString("wardBusinessId"),jsonObject.getString("wardBusinessDate"),jsonObject.getString("wardBusinessPurpose"),
                    jsonObject.getString("wardBusinessName"),jsonObject.getString("wardBusinessCalling"),jsonObject.getString("wardBusinessUpdateDate"),
                    jsonObject.getString("wardBusinessStatus"));
        } catch (JSONException j){
            j.printStackTrace();
        }
        return new WardBusiness();
    }

}
