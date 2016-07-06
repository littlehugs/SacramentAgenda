package uk.co.littlehugs.sacramentagenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hugs on 26/04/16.
 */
public class AddWebservice extends AppCompatActivity {
    private DBHelper dh;
    private EditText etUrlAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        etUrlAddress = (EditText) findViewById(R.id.etUrlAddress);
        populateFields();

        final Button saveButton = (Button) findViewById(R.id.saveUrlAddressButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //click action
                    addNewUrl(etUrlAddress.getText().toString());
                    finish();

            }
        });

    }

    private void addNewUrl(String strUrl) {
        dh = new DBHelper(this);
        dh.addLastSync(strUrl, 1);
        dh.closeDB();
dh.close();
    }

    private void populateFields() {
        dh = new DBHelper(this);
        etUrlAddress.setText(dh.getLastSync(1));
    }


}
