package uk.co.littlehugs.sacramentagenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AgendaActivity extends AppCompatActivity {
    private String agendaDate;
    private String formattedDate;
    private String agendaHTML;
    private String agendaHTML2;
    private String agendaHTML3;
    private String agendaHTML4;
    private DBHelper db;
    private List<Agenda> agendaDetailsList;
    private List<Announcements> announcementsDetailsList;
    private List<Acknowledgements> acknowledgementsDetailsList;
    private List<StakeBusiness> stakeBusinessDetailsList;
    private List<WardBusiness> wardBusinessDetailsList;
    private TextView agendaText;
    private TextView agendaText2;
    private TextView agendaText3;
    private TextView agendaText4;
    private List<WardBusiness> wbReleases = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbCalling = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbOrdinations = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbAssignements = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbBlessings = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbConfirmations = new ArrayList<WardBusiness>();
    private List<WardBusiness> wbNewMember = new ArrayList<WardBusiness>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Intent intent = getIntent();
        agendaDate = intent.getStringExtra("agendaDate");

        try {
            Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(agendaDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initializeVariables();
        getAgendaInfo();
        makeAgenda();

    }

    private void initializeVariables() {

        agendaText = (TextView) findViewById(R.id.agendaText1);
        agendaText2 = (TextView) findViewById(R.id.agendaText2);
        agendaText3 = (TextView) findViewById(R.id.agendaText3);
        agendaText4 = (TextView) findViewById(R.id.agendaText4);
        agendaHTML = "<b>" + formattedDate + "</b>";
    }

    private void getAgendaInfo() {
        db = new DBHelper(this);
        agendaDetailsList = db.getWeekAgenda(agendaDate);
        announcementsDetailsList = db.getWeekAnnouncements(agendaDate);
        acknowledgementsDetailsList = db.getWeekAcknowledgements(agendaDate);
        stakeBusinessDetailsList = db.getWeekStakeBusiness(agendaDate);
        wardBusinessDetailsList = db.getWeekWardBusiness(agendaDate);
        db.close();
    }

    private void makeAgenda() {
        agendaHTML = agendaHTML + ("<p>We welcome you to sacrament meeting today</p>");

        //acknowledgements
        if (acknowledgementsDetailsList != null && acknowledgementsDetailsList.size() >0) {
            if (acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTPRESIDING().contains(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTCONDUCTING())) {
                agendaHTML = agendaHTML + "<p>Presiding and conducting this sacrament meeting will be me, ";
                agendaHTML = agendaHTML + "" + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTPRESIDING() + ".<p>";
            } else {
                agendaHTML = agendaHTML + "<p>Presiding in today sacrament meeting will be ";
                agendaHTML = agendaHTML + "" + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTPRESIDING();
                agendaHTML = agendaHTML + " and he has asked that I, ";
                agendaHTML = agendaHTML + "" + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTCONDUCTING();
                agendaHTML = agendaHTML + " conduct this meeting.</p>";
            }


            if (acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR1() != null && !acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR1().isEmpty()) {
                agendaHTML = agendaHTML + "<p>We wish to acknowledge ";
                agendaHTML = agendaHTML + (acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR1() + ", " + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING1());
                if (acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR2() != null && !acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR2().isEmpty()) {
                    agendaHTML = agendaHTML + (" and " + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR2() + ", " + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING2());
                    if (acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR3() != null && !acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR3().isEmpty()) {
                        agendaHTML = agendaHTML + (" and " + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR3() + ", " + acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING3());
                        agendaHTML = agendaHTML + (" who are visiting with us today.</p>");
                    } else {
                        agendaHTML = agendaHTML + (" who are visiting with us today.</p>");
                    }
                } else {
                    agendaHTML = agendaHTML + (" who is visiting with us today.</p>");
                }
            }
        }
        //announcements

        if (announcementsDetailsList != null && announcementsDetailsList.size() >0) {
            agendaHTML = agendaHTML + "<br><p><b>Announcements</b><br>";

            for (int i = 0; i < announcementsDetailsList.size(); i++) {
                if (i == 0 ) {
                    agendaHTML = agendaHTML + (announcementsDetailsList.get(i).getANNOUNCEMENTS() + "</p>");
                } else {
                    agendaHTML = agendaHTML + ("<p>" + announcementsDetailsList.get(i).getANNOUNCEMENTS() + "</p>");
                }
            }
        } else {
            agendaHTML = agendaHTML + "<p><b>Announcements</b><br>";
            agendaHTML = agendaHTML + "There are no announcements today, please refer to the ward calendar for upcoming events.</p>";
        }

        //Opening Hymn
        agendaHTML = agendaHTML + "<p>The opening hymn will be hymn number " + agendaDetailsList.get(0).getAGENDAOPENINGHYMN() + ", ";
        agendaHTML = agendaHTML + " " + db.getHymn(agendaDetailsList.get(0).getAGENDAOPENINGHYMN());
        agendaHTML = agendaHTML + ", and the invocation will be offered by " + agendaDetailsList.get(0).getAGENDAOPENINGPRAYER() + ".</p>";

        agendaHTML2 = "";
        //Stake Business
        if (stakeBusinessDetailsList != null && stakeBusinessDetailsList.size() >0) {
            agendaHTML2 = agendaHTML2 + "<br><p><b>Stake Business</b>";
            for (int i = 0; i < stakeBusinessDetailsList.size(); i++) {
                try {
                    agendaHTML2 = agendaHTML2 + "<br>We will now hand the time over to " + stakeBusinessDetailsList.get(i).getSTAKEBUSINESS() + " to conduct the stake business. </p>";
                } catch (Exception e) {
                    Log.i("StakeBusiness", "Stake Business Error");
                }
            }
        }

        //Ward Business
        if (wardBusinessDetailsList != null && wardBusinessDetailsList.size() >0) {
            agendaHTML2 = agendaHTML2 + ("<br><b>Ward Business</b>");

            for (int i = 0; i < wardBusinessDetailsList.size(); i++) {
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Release")) {
                    wbReleases.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Call")) {
                    wbCalling.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Ordination")) {
                    wbOrdinations.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Assignment")) {
                    wbAssignements.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Blessing")) {
                    wbBlessings.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("Confirmation")) {
                    wbConfirmations.add(wardBusinessDetailsList.get(i));
                }
                if (wardBusinessDetailsList.get(i).getWARDBUSINESSPURPOSE().contains("New Member")) {
                    wbNewMember.add(wardBusinessDetailsList.get(i));
                }
            }

            //Confirmations
            if (wbConfirmations != null && wbConfirmations.size() > 0) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Confirmations</b><br>");
                for (int i = 0; i < wbConfirmations.size(); i++) {
                    agendaHTML2 = agendaHTML2 + (wbConfirmations.get(i).getWARDBUSINESSNAME() + " will be confirmed.  Could " + wbConfirmations.get(i).getWARDBUSINESSNAME()
                            + " and those who are participating please come forward to perform the confirmation.</p>");
                }
            }

            //New Members
            if (wbNewMember != null && wbNewMember.size() > 0) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Welcoming New Members</b><br>");
                agendaHTML2 = agendaHTML2 + ("Would ");
                for (int i = 0; i < wbNewMember.size(); i++) {
                    if (i < 1) {
                        agendaHTML2 = agendaHTML2 + (wbNewMember.get(i).getWARDBUSINESSNAME());
                    } else {
                        if (i == wbNewMember.size()-1) {
                            agendaHTML2 = agendaHTML2 + (" and " + wbNewMember.get(i).getWARDBUSINESSNAME());
                        } else {
                            agendaHTML2 = agendaHTML2 + (", " + wbNewMember.get(i).getWARDBUSINESSNAME());
                        }
                    }
                }
                agendaHTML2 = agendaHTML2 + (" please stand. <br>We propose that");
                for (int j = 0; j < wbNewMember.size(); j++) {
                    if (j < 1) {
                        agendaHTML2 = agendaHTML2 + (wbNewMember.get(j).getWARDBUSINESSNAME());
                    } else {
                        if (j == wbNewMember.size()-1) {
                            agendaHTML2 = agendaHTML2 + (" and " + wbNewMember.get(j).getWARDBUSINESSNAME());
                        } else {
                            agendaHTML2 = agendaHTML2 + (", " + wbNewMember.get(j).getWARDBUSINESSNAME());
                        }
                    }
                }
                agendaHTML2 = agendaHTML2 + (" be welcomed into full fellowship in the Sunderland Ward, Sunderland Stake, of The Church of Jesus Christ of Latter-day Saints.  " +
                        "Those in favour may manifest it by the uplifted hand.</p>");
            }

            //Blessings
            if (wbBlessings != null && wbBlessings.size() > 0) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Blessings</b><br>");
                for (int i = 0; i < wbBlessings.size(); i++) {
                    agendaHTML2 = agendaHTML2 + (wbBlessings.get(i).getWARDBUSINESSNAME() + " will now be blessed.  Could those who are participating please come forward to perform the blessing.</p>");
                }
            }

            //Ordinations
            if (wbOrdinations != null && wbOrdinations.size() == 1) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Ordinations</b><br>");
                agendaHTML2 = agendaHTML2 + ("Would " + wbOrdinations.get(0).getWARDBUSINESSNAME() + " please stand. ");
                agendaHTML2 = agendaHTML2 + ("<br>We propose that " + wbOrdinations.get(0).getWARDBUSINESSNAME() + " receive the Aaronic Priesthood and ordained a " + wbOrdinations.get(0).getWARDBUSINESSCALLING());
                agendaHTML2 = agendaHTML2 + ("<br>OR");
                agendaHTML2 = agendaHTML2 + ("<br>We propose that " + wbOrdinations.get(0).getWARDBUSINESSNAME() + " be ordained a " + wbOrdinations.get(0).getWARDBUSINESSCALLING());
                agendaHTML2 = agendaHTML2 + ("<br>Those in favour may manifest it by the uplifted hand");
                agendaHTML2 = agendaHTML2 + ("<br>[Pause briefly for sustaining vote.]");
                agendaHTML2 = agendaHTML2 + ("<br>Those opposed, if any, may manifest it.</p>");
            } else if (wbOrdinations != null && wbOrdinations.size() > 1) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Ordinations</b><br>");
                agendaHTML2 = agendaHTML2 + ("Would ");
                for (int i = 0; i < wbOrdinations.size(); i++) {
                    if (i > 0) {
                        if (i == wbOrdinations.size()-1) {
                            agendaHTML2 = agendaHTML2 + (" and " + wbOrdinations.get(i).getWARDBUSINESSNAME());
                        } else {
                            agendaHTML2 = agendaHTML2 + (", " + wbOrdinations.get(i).getWARDBUSINESSNAME());
                        }
                    } else {
                        agendaHTML2 = agendaHTML2 + (wbOrdinations.get(i).getWARDBUSINESSNAME());
                    }
                }
                agendaHTML2 = agendaHTML2 + (" please stand.");

                for (int j = 0; j < wbOrdinations.size(); j++) {
                    agendaHTML2 = agendaHTML2 + ("<br>We propose that " + wbOrdinations.get(j).getWARDBUSINESSNAME() + " receive the Aaronic Priesthood and ordained a " + wbOrdinations.get(0).getWARDBUSINESSCALLING());
                    agendaHTML2 = agendaHTML2 + ("<br>OR");
                    agendaHTML2 = agendaHTML2 + ("<br>We propose that " + wbOrdinations.get(j).getWARDBUSINESSNAME() + " be ordained a " + wbOrdinations.get(j).getWARDBUSINESSCALLING());
                }
                agendaHTML2 = agendaHTML2 + ("<br>Those in favour may manifest it by the uplifted hand");
                agendaHTML2 = agendaHTML2 + ("<br>[Pause briefly for sustaining vote.]");
                agendaHTML2 = agendaHTML2 + ("<br>Those opposed, if any, may manifest it.</p>");
            }

            //Release
            if (wbReleases != null && wbReleases.size() == 1) {
                agendaHTML2 = agendaHTML2 + "<p><b>Releases</b><br>";
                agendaHTML2 = agendaHTML2 + wbReleases.get(0).getWARDBUSINESSNAME() + " has been released as " + wbReleases.get(0).getWARDBUSINESSCALLING() +
                        ", and we propose that he [or she] be given a vote of thanks for his [or her] service." +
                        "<br>Those who wish to express their appreciation may manifest it by the uplifted hand</p>";
            } else if (wbReleases != null && wbReleases.size() > 1) {
                agendaHTML2 = agendaHTML2 + "<p><b>Releases</b><br>";
                for (int i = 0; i < wbReleases.size(); i++) {
                    agendaHTML2 = agendaHTML2 + wbReleases.get(i).getWARDBUSINESSNAME() + " has been released as " + wbReleases.get(i).getWARDBUSINESSCALLING() + "<br>";
                }
                agendaHTML2 = agendaHTML2 + (" and we propose that they be given a vote of thanks for their service." +
                        "<br>Those who wish to express their appreciation may manifest it by the uplifted hand</p>");
            }

            //Callings
            if (wbCalling != null && wbCalling.size() == 1) {
                agendaHTML2 = agendaHTML2 + "<br><p><b>Callings</b><br>";
                agendaHTML2 = agendaHTML2 + "Would " + wbCalling.get(0).getWARDBUSINESSNAME() + " please stand.";
                agendaHTML2 = agendaHTML2 + (wbCalling.get(0).getWARDBUSINESSNAME() + " has been called as " + wbCalling.get(0).getWARDBUSINESSCALLING() +
                        ", and we propose that he [or she] be sustained.<br>Those in favour may manifest it by the uplifted hand");
                agendaHTML2 = agendaHTML2 + ("<br>[Pause briefly for sustaining vote.]");
                agendaHTML2 = agendaHTML2 + ("<br>Those opposed, if any, may manifest it.</p>");
            } else if (wbCalling != null && wbCalling.size() > 1) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Callings</b><br>");
                agendaHTML2 = agendaHTML2 + ("Would ");
                for (int i = 0; i < wbCalling.size(); i++) {
                    if (i > 0) {
                        if (i == wbCalling.size()-1) {
                            agendaHTML2 = agendaHTML2 + (" and " + wbCalling.get(i).getWARDBUSINESSNAME());
                        } else {
                            agendaHTML2 = agendaHTML2 + (", " + wbCalling.get(i).getWARDBUSINESSNAME());
                        }
                    } else {
                            agendaHTML2 = agendaHTML2 + (wbCalling.get(i).getWARDBUSINESSNAME());
                    }
                }
                agendaHTML2 = agendaHTML2 + (" please stand.  ");

                for (int j = 0; j < wbCalling.size(); j++) {
                    agendaHTML2 = agendaHTML2 + ("<br>" + wbCalling.get(j).getWARDBUSINESSNAME() + " has been called as " + wbCalling.get(j).getWARDBUSINESSCALLING());
                }
                agendaHTML2 = agendaHTML2 + (", and we propose that they be sustained.<br>Those in favour may manifest it by the uplifted hand");
                agendaHTML2 = agendaHTML2 + ("<br>[Pause briefly for sustaining vote.]");
                agendaHTML2 = agendaHTML2 + ("<br>Those opposed, if any, may manifest it.</p>");
            }


            //Assignments
            if (wbAssignements != null && wbAssignements.size() == 1) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Assignments</b><br>");
                agendaHTML2 = agendaHTML2 + ("For your information " + wbAssignements.get(0).getWARDBUSINESSNAME() + " has been assigned as " + wbAssignements.get(0).getWARDBUSINESSCALLING() + "</p>");
            } else if (wbAssignements != null && wbAssignements.size() > 1) {
                agendaHTML2 = agendaHTML2 + ("<br><p><b>Assignments</b><br>");
                agendaHTML2 = agendaHTML2 + ("For your information ");
                for (int i = 0; i < wbAssignements.size(); i++) {
                    agendaHTML2 = agendaHTML2 + (wbAssignements.get(i).getWARDBUSINESSNAME() + " has been assigned as " + wbAssignements.get(i).getWARDBUSINESSCALLING() + "</p>");
                }
            }


        }

        //Sacrament
        agendaHTML2 = agendaHTML2 + ("<p>We will now prepare to partake the sacrament by singing hymn number " + agendaDetailsList.get(0).getAGENDASACRAMENTHYMN() + ", ");
        agendaHTML2 = agendaHTML2 + (db.getHymn(agendaDetailsList.get(0).getAGENDASACRAMENTHYMN()));
        agendaHTML2 = agendaHTML2 + (".  After which the sacrament will be administered by the Priesthood.</p>");

        agendaHTML3 = "<p>We would like to thanks you for the reverence shown during the ordinance of the Sacrament.</p>";
        if (agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER() != null && !agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER().isEmpty() &
                //agendaDetailsList.get(0).getAGENDASECONDSPEAKER() != null && !agendaDetailsList.get(0).getAGENDASECONDSPEAKER().isEmpty() &
                agendaDetailsList.get(0).getAGENDAFINALSPEAKER() != null && !agendaDetailsList.get(0).getAGENDAFINALSPEAKER().isEmpty()) {

            //Speakers
            if (agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER() != null && !agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER().isEmpty()) {
                agendaHTML3 = agendaHTML3 + "<p>Our first speaker will be " + agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER();
            }

            if (agendaDetailsList.get(0).getAGENDASECONDSPEAKER() != null && !agendaDetailsList.get(0).getAGENDASECONDSPEAKER().isEmpty()) {
                agendaHTML3 = agendaHTML3 + ("<br>We will then hear from our second speaker, " + agendaDetailsList.get(0).getAGENDASECONDSPEAKER());
            }

            //Intermediate Hymn
            if (agendaDetailsList.get(0).getAGENDAINTERMEDIATEHYMN() != 0) {
                agendaHTML3 = agendaHTML3 + ("<br>We will then stand to sing the intermediate hymn, " + agendaDetailsList.get(0).getAGENDAINTERMEDIATEHYMN());
                agendaHTML3 = agendaHTML3 + (" " + db.getHymn(agendaDetailsList.get(0).getAGENDAINTERMEDIATEHYMN()) + "</p>");
            }

            //Final Speaker
            if (agendaDetailsList.get(0).getAGENDAFINALSPEAKER() != null && !agendaDetailsList.get(0).getAGENDAFINALSPEAKER().isEmpty()) {
                agendaHTML4 = ("<p>We will now hear from our final speaker, " + agendaDetailsList.get(0).getAGENDAFINALSPEAKER());
            }

            //Closing Hymn
            if (agendaDetailsList.get(0).getAGENDACLOSINGHYMN() != 0) {
                agendaHTML4 = agendaHTML4 + ("<br>Following the talk by " + agendaDetailsList.get(0).getAGENDAFINALSPEAKER() +
                        " we will sing the closing hymn, " + agendaDetailsList.get(0).getAGENDACLOSINGHYMN());
                agendaHTML4 = agendaHTML4 + (", " + db.getHymn(agendaDetailsList.get(0).getAGENDACLOSINGHYMN()));
            }

            //Closing Prayer
            agendaHTML4 = agendaHTML4 + ("<br>The benediction will then be offered by " + agendaDetailsList.get(0).getAGENDACLOSINGPRAYER() + "</p><br><br><br><br>");

        } else {
            //Testimony Meeting
            agendaHTML3 = ("<p><b>Testimony Meeting</b>");
            agendaHTML3 = agendaHTML3 + ("<br>Bear a brief testimony then invite members of the congregation to bear their testimony and share their faith promoting experiences. Encourage them to keep their testimonies brief so more people may have the opportunity to participate.</p>");
            agendaHTML4 =  ("<p><b>Closing the Meeting</b>");
            agendaHTML4 = agendaHTML4 + ("<br>We would like to thank all those who have borne their testimony.  We will close the meeting by singing hymn number ");
            agendaHTML4 = agendaHTML4 + (agendaDetailsList.get(0).getAGENDACLOSINGHYMN() +", " + db.getHymn(agendaDetailsList.get(0).getAGENDACLOSINGHYMN()));
            //Closing Prayer
            agendaHTML4 = agendaHTML4 + ("<br>The benediction will then be offered by " + agendaDetailsList.get(0).getAGENDACLOSINGPRAYER() + "</p><br><br><br><br>");
        }

        agendaText.setText(Html.fromHtml(agendaHTML));
        agendaText2.setText(Html.fromHtml(agendaHTML2));
        agendaText3.setText(Html.fromHtml(agendaHTML3));
        agendaText4.setText(Html.fromHtml(agendaHTML4));
    }



}
