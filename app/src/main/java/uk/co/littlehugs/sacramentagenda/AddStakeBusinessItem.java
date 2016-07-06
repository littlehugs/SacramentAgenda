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
public class AddStakeBusinessItem extends AppCompatActivity {

    private List<StakeBusiness> stakeBusinessDetailsList;
    private DBHelper dh;
    private EditText etDate;
    private EditText etStakeBusiness;
    private String itemPos = null;
    private String formattedDate;
    private String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stakebusiness_item);

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

        final Button saveButton = (Button) findViewById(R.id.saveStakeBusinessButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //click action
                if (etDate.getText().toString().trim().length() == 0){
                    etDate.setError("Select a date");
                    cannotSave("You must set a date before saving");
                } else {
                    if (addNewStakeBusiness()) {
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

    private boolean addNewStakeBusiness() {
        try {
            String trDate = etDate.getText().toString();
            Date tradeDate = null;
            tradeDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(tradeDate);
            formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());

             dh = new DBHelper(this);
            //dh.openDB();
            if (itemPos != null) {
                StakeBusiness stakeBusiness = new StakeBusiness(formattedDate, etStakeBusiness.getText().toString(),formattedDateTime, "A");
                dh.editStakeBusiness(stakeBusiness, itemPos.toString());
            } else {
                StakeBusiness stakeBusiness = new StakeBusiness(formattedDateTime,formattedDate,
                        etStakeBusiness.getText().toString(),formattedDateTime, "A");

                dh.addStakeBusiness(stakeBusiness);
            }
            dh.closeDB();
            dh.close();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    private void initializeVariables() {
        etDate = (EditText) findViewById(R.id.etDate);
        etStakeBusiness = (EditText) findViewById(R.id.etStakeBusiness);
    }

    private void populateFields(String pos) {
        dh = new DBHelper(this);
        stakeBusinessDetailsList = dh.getSingleStakeBusiness(pos);
        String trDate=stakeBusinessDetailsList.get(0).getSTAKEBUSINESSDATE();
        Date tradeDate = null;
        try {
            tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etDate.setText(formattedDate);
        etStakeBusiness.setText(stakeBusinessDetailsList.get(0).getSTAKEBUSINESS());
        dh.close();
    }

}
