package uk.co.littlehugs.sacramentagenda;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hugs on 16/02/16.
 */
public class AddWardBusinessItem extends AppCompatActivity {

    private DBHelper dh;
    private EditText etDate;
    private Spinner etWardBusinessPurpose;
    private EditText etWardBusinessName;
    private EditText etWardBusinessCalling;
    private String itemPos = null;
    private List<WardBusiness> wardBusinessDetailsList;
    private ArrayAdapter<String> dataAdapter;
    private String formattedDate = "";
    private String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardbusiness_item);

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

        final Button saveButton = (Button) findViewById(R.id.saveWardBusinessButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //click action
                if (etDate.getText().toString().trim().length() == 0){
                    etDate.setError("Select a date");
                    cannotSave("You must set a date before saving");
                } else {
                    if (addNewWardBusiness()) {
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

    private boolean addNewWardBusiness() {
        try {
            String trDate = etDate.getText().toString();
            Date tradeDate = null;
            tradeDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(tradeDate);
            formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());

            dh = new DBHelper(this);
            //dh.openDB();
            if (itemPos != null) {
                WardBusiness wardBusiness = new WardBusiness(formattedDate, etWardBusinessPurpose.getSelectedItem().toString(),
                        etWardBusinessName.getText().toString(),etWardBusinessCalling.getText().toString(),formattedDateTime, "A");
                dh.editWardBusiness(wardBusiness, itemPos.toString());
            } else {
                WardBusiness wardBusiness = new WardBusiness(formattedDateTime, formattedDate,
                        etWardBusinessPurpose.getSelectedItem().toString(),
                        etWardBusinessName.getText().toString(),etWardBusinessCalling.getText().toString(),formattedDateTime, "A");
                dh.addWardBusiness(wardBusiness);
            }
            dh.closeDB();
            dh.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initializeVariables() {
        etDate = (EditText) findViewById(R.id.etDate);
        etWardBusinessPurpose = (Spinner) findViewById(R.id.etWardBusinessPurpose);
        etWardBusinessName = (EditText) findViewById(R.id.etWardBusinessName);
        etWardBusinessCalling = (EditText) findViewById(R.id.etWardBusinessCalling);
        List<String> purposeList = new ArrayList<String>();
        purposeList.add("Release");
        purposeList.add("Call");
        purposeList.add("Ordination");
        purposeList.add("Assignment");
        purposeList.add("Blessing");
        purposeList.add("Confirmation");
        purposeList.add("New Member");

        dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,purposeList);
        etWardBusinessPurpose.setAdapter(dataAdapter);
    }

    private void populateFields(String pos) {
        dh = new DBHelper(this);
        wardBusinessDetailsList = dh.getSingleWardBusiness(pos);
        String trDate=wardBusinessDetailsList.get(0).getWARDBUSINESSDATE();
        try {
            Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etDate.setText(formattedDate);
        etWardBusinessPurpose.setSelection(dataAdapter.getPosition(wardBusinessDetailsList.get(0).getWARDBUSINESSPURPOSE()));
        etWardBusinessName.setText(wardBusinessDetailsList.get(0).getWARDBUSINESSNAME());
        etWardBusinessCalling.setText(wardBusinessDetailsList.get(0).getWARDBUSINESSCALLING());
        dh.close();
    }
}
