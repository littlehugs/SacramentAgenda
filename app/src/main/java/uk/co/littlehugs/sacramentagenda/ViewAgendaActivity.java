package uk.co.littlehugs.sacramentagenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hugs on 12/02/16.
 */
public class ViewAgendaActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> datesList;
    private DBHelper db;
    private List<Agenda> agendaDetailsList;
    private Agenda agendaDetails = new Agenda();
    private ArrayAdapter<String> adapter;
    private String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agenda);

        listView = (ListView)findViewById(R.id.list_item);

        datesList = new ArrayList<>();

        getAgenda();

        adapter = new ArrayAdapter<String>(this, R.layout.my_list, datesList);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                String trDate = (String) listView.getItemAtPosition(position);
                Date tradeDate = null;
                try {
                    tradeDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(trDate);
                    String itemValue = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(tradeDate);

                    Intent intent = new Intent(ViewAgendaActivity.this, AgendaActivity.class);
                    intent.putExtra("agendaDate", itemValue);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getAgenda() {
        try {
        db = new DBHelper(this);
        agendaDetailsList = db.getAllAgenda();

        int lenAllAgenda = db.agendaRowcount();
        for (int i = 0; i < lenAllAgenda; i++) {
            agendaDetails = agendaDetailsList.get(i);
            datesList.add(getFormattedDate(agendaDetails.getAGENDADATE()));
        }
            db.closeDB();
        db.close();
        } catch (Exception e) {
            Toast.makeText(this, "There are no agenda items yet", Toast.LENGTH_LONG).show();
        }
    }

    private String getFormattedDate(String trDate){
        Date tradeDate = null;
        try {
            tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    return formattedDate;
    }


}
