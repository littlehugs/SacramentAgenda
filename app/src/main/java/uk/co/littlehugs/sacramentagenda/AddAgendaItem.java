package uk.co.littlehugs.sacramentagenda;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hugs on 12/02/16.
 */
public class AddAgendaItem extends AppCompatActivity {

    private DBHelper dh;
    private EditText etDate;
    private EditText etOpeningHymn;
    private EditText etOpeningPrayer;
    private EditText etSacramentHymn;
    private EditText etFirstSpeaker;
    private EditText etSecondSpeaker;
    private EditText etIntermediateHymn;
    private EditText etFinalSpeaker;
    private EditText etClosingHymn;
    private EditText etClosingPrayer;
    private String itemPos = null;
    private List<Agenda> agendaDetailsList;
    private String formattedDate;
    private String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_item);

        Intent intent = getIntent();
        itemPos = intent.getStringExtra("itemPosition");

        initializeVariables();

        if (itemPos != null) {
            populateFields(itemPos.toString());
        }


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        final Button saveButton = (Button) findViewById(R.id.saveAgendaButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //click action
                if (etDate.getText().toString().trim().length() == 0){
                    etDate.setError("Select a date");
                    cannotSave("You must set a date before saving");
                } else {
                    if (addNewAgenda()) {
                        finish();
                    } else {
                        cannotSave("Can't add or edit data");
                    }
                }

            }
        });
    }

    private void cannotSave(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean addNewAgenda() {
        String interHymn, openHymn, sacHymn, closeHymn;

        if (etIntermediateHymn.getText().toString().trim().length() > 0) {
            interHymn = etIntermediateHymn.getText().toString();
        } else {
            interHymn = "0";
        }
        if (etOpeningHymn.getText().toString().trim().length() > 0) {
            openHymn = etOpeningHymn.getText().toString();
        } else {
            openHymn = "0";
        }
        if (etSacramentHymn.getText().toString().trim().length() > 0) {
            sacHymn = etSacramentHymn.getText().toString();
        } else {
            sacHymn = "0";
        }
        if (etClosingHymn.getText().toString().trim().length() > 0) {
            closeHymn = etClosingHymn.getText().toString();
        } else {
            closeHymn = "0";
        }

        if (Integer.parseInt(openHymn) < 1 || Integer.parseInt(openHymn) > 341) {
            etOpeningHymn.setError("Not a valid hymn number");
            //Toast.makeText(this, "Opening Hymn number does not exist", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(sacHymn) < 1 || Integer.parseInt(sacHymn) > 341) {
                etSacramentHymn.setError("Not a valid hymn number");
                //Toast.makeText(this, "Sacrament Hymn number does not exist", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(closeHymn) < 1 || Integer.parseInt(closeHymn) > 341) {
            etClosingHymn.setError("Not a valid hymn number");
            //Toast.makeText(this, "Closing Hymn number does not exist", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(interHymn) < 0 || Integer.parseInt(interHymn) > 341) {
            etClosingHymn.setError("Not a valid hymn number");
            //Toast.makeText(this, "Closing Hymn number does not exist", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String trDate = etDate.getText().toString();
                Date tradeDate = null;
                tradeDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(trDate);
                formattedDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(tradeDate);

                formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());

                dh = new DBHelper(this);
                if (itemPos != null) {
                    Agenda agenda = new Agenda(formattedDate, Integer.parseInt(etOpeningHymn.getText().toString()),
                            etOpeningPrayer.getText().toString(), Integer.parseInt(etSacramentHymn.getText().toString()),
                            etFirstSpeaker.getText().toString(), etSecondSpeaker.getText().toString(), Integer.parseInt(interHymn),
                            etFinalSpeaker.getText().toString(), Integer.parseInt(etClosingHymn.getText().toString()), etClosingPrayer.getText().toString(),
                            formattedDateTime,"A");
                    dh.editAgenda(agenda, itemPos.toString());
                } else {
                    Agenda agenda = new Agenda(formattedDateTime, formattedDate, Integer.parseInt(etOpeningHymn.getText().toString()),
                            etOpeningPrayer.getText().toString(), Integer.parseInt(etSacramentHymn.getText().toString()),
                            etFirstSpeaker.getText().toString(), etSecondSpeaker.getText().toString(), Integer.parseInt(interHymn),
                            etFinalSpeaker.getText().toString(), Integer.parseInt(etClosingHymn.getText().toString()), etClosingPrayer.getText().toString(),
                            formattedDateTime,"A");
                    dh.addAgenda(agenda);
                }
                dh.closeDB();
                dh.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private void initializeVariables() {
        etDate = (EditText) findViewById(R.id.etDate);
        etOpeningHymn = (EditText) findViewById(R.id.etOpeningHymn);
        etOpeningPrayer = (EditText) findViewById(R.id.etOpeningPrayer);
        etSacramentHymn = (EditText) findViewById(R.id.etSacramentHymn);
        etFirstSpeaker = (EditText) findViewById(R.id.etFirstSpeaker);
        etSecondSpeaker = (EditText) findViewById(R.id.etSecondSpeaker);
        etIntermediateHymn = (EditText) findViewById(R.id.etInterdiateHymn);
        etFinalSpeaker = (EditText) findViewById(R.id.etFinalSpeaker);
        etClosingHymn = (EditText) findViewById(R.id.etClosingHymn);
        etClosingPrayer = (EditText) findViewById(R.id.etClosingPrayer);

    }

    private void populateFields(String pos) {
        dh = new DBHelper(this);
        agendaDetailsList = dh.getSingleAgenda(pos);

        String trDate=agendaDetailsList.get(0).getAGENDADATE();
        Date tradeDate = null;
        try {
            tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etDate.setText(formattedDate);
        etOpeningHymn.setText(String.valueOf(agendaDetailsList.get(0).getAGENDAOPENINGHYMN()));
        etOpeningPrayer.setText(agendaDetailsList.get(0).getAGENDAOPENINGPRAYER());
        etSacramentHymn.setText(String.valueOf(agendaDetailsList.get(0).getAGENDASACRAMENTHYMN()));
        etFirstSpeaker.setText(agendaDetailsList.get(0).getAGENDAFIRSTSPEAKER());
        etSecondSpeaker.setText(agendaDetailsList.get(0).getAGENDASECONDSPEAKER());
        etIntermediateHymn.setText(String.valueOf(agendaDetailsList.get(0).getAGENDAINTERMEDIATEHYMN()));
        etFinalSpeaker.setText(agendaDetailsList.get(0).getAGENDAFINALSPEAKER());
        etClosingHymn.setText(String.valueOf(agendaDetailsList.get(0).getAGENDACLOSINGHYMN()));
        etClosingPrayer.setText(agendaDetailsList.get(0).getAGENDACLOSINGPRAYER());
        //dh.close();
    }


}
