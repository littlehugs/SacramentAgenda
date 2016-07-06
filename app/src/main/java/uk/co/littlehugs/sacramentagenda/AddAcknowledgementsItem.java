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
 * Created by hugs on 15/02/16.
 */
public class AddAcknowledgementsItem extends AppCompatActivity {

    private DBHelper dh;
    private EditText etDate;
    private EditText etAcknowledgementPresiding;
    private EditText etAcknowledgementConducting;
    private EditText etAcknowledgementStakeVisitor1;
    private EditText etAcknowledgementStakeCalling1;
    private EditText etAcknowledgementStakeVisitor2;
    private EditText etAcknowledgementStakeCalling2;
    private EditText etAcknowledgementStakeVisitor3;
    private EditText etAcknowledgementStakeCalling3;
    private String itemPos = null;
    private List<Acknowledgements> acknowledgementsDetailsList;
    private String formattedDate;
    private String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledgment_item);

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

        final Button saveButton = (Button) findViewById(R.id.saveAcknowledgementsButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //click action
                if (etDate.getText().toString().trim().length() == 0){
                    etDate.setError("Select a date");
                    cannotSave("You must set a date before saving");
                } else {
                    if (addNewAcknowledgements()) {
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

    private boolean addNewAcknowledgements() {
        try{
            String trDate = etDate.getText().toString();
            Date tradeDate = null;
            tradeDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(tradeDate);
            formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());

            dh = new DBHelper(this);
            dh.openDB();
            if (itemPos != null) {
                Acknowledgements acknowledgements = new Acknowledgements(formattedDate, etAcknowledgementPresiding.getText().toString(),
                        etAcknowledgementConducting.getText().toString(), etAcknowledgementStakeVisitor1.getText().toString(),
                        etAcknowledgementStakeCalling1.getText().toString(), etAcknowledgementStakeVisitor2.getText().toString(),
                        etAcknowledgementStakeCalling2.getText().toString(), etAcknowledgementStakeVisitor3.getText().toString(),
                        etAcknowledgementStakeCalling3.getText().toString(),formattedDateTime,"A");
                dh.editAcknowledgements(acknowledgements, itemPos.toString());
            } else {
                Acknowledgements acknowledgements = new Acknowledgements(formattedDateTime, formattedDate, etAcknowledgementPresiding.getText().toString(),
                        etAcknowledgementConducting.getText().toString(), etAcknowledgementStakeVisitor1.getText().toString(),
                        etAcknowledgementStakeCalling1.getText().toString(), etAcknowledgementStakeVisitor2.getText().toString(),
                        etAcknowledgementStakeCalling2.getText().toString(), etAcknowledgementStakeVisitor3.getText().toString(),
                        etAcknowledgementStakeCalling3.getText().toString(),formattedDateTime,"A");
                dh.addAcknowledgements(acknowledgements);
            }
            dh.closeDB();
            dh.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }

    private void initializeVariables() {
        etDate = (EditText) findViewById(R.id.etDate);
        etAcknowledgementPresiding = (EditText) findViewById(R.id.etAcknowledgementPresiding);
        etAcknowledgementConducting = (EditText) findViewById(R.id.etAcknowledgementConducting);
        etAcknowledgementStakeVisitor1 = (EditText) findViewById(R.id.etAcknowledgementStakeVisitor1);
        etAcknowledgementStakeCalling1 = (EditText) findViewById(R.id.etAcknowledgementStakeCalling1);
        etAcknowledgementStakeVisitor2 = (EditText) findViewById(R.id.etAcknowledgementStakeVisitor2);
        etAcknowledgementStakeCalling2 = (EditText) findViewById(R.id.etAcknowledgementStakeCalling2);
        etAcknowledgementStakeVisitor3 = (EditText) findViewById(R.id.etAcknowledgementStakeVisitor3);
        etAcknowledgementStakeCalling3 = (EditText) findViewById(R.id.etAcknowledgementStakeCalling3);
    }

    private void populateFields(String pos) {
        dh = new DBHelper(this);
        acknowledgementsDetailsList = dh.getSingleAcknowledgements(pos);

        String trDate=acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTDATE();
        Date tradeDate = null;
        try {
            tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        etDate.setText(formattedDate);
        etAcknowledgementPresiding.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTPRESIDING());
        etAcknowledgementConducting.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTCONDUCTING());
        etAcknowledgementStakeVisitor1.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR1());
        etAcknowledgementStakeCalling1.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING1());
        etAcknowledgementStakeVisitor2.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR2());
        etAcknowledgementStakeCalling2.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING2());
        etAcknowledgementStakeVisitor3.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKEVISITOR3());
        etAcknowledgementStakeCalling3.setText(acknowledgementsDetailsList.get(0).getACKNOWLEDGEMENTSTAKECALLING3());

    }


}
