package uk.co.littlehugs.sacramentagenda;

/**
 * Created by hugs on 12/02/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class DatesActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private TextView totalList;
    private TextView headerTitle;
    private SwipeLayout swipeLayout;
    private String viewType;
    private DBHelper db;
    private ArrayList<String> datesLocList;
    private List<Agenda> agendaDetailsList;
    private Agenda agendaDetails = new Agenda();
    private List<Announcements> announcementsDetailsList;
    private Announcements announcementsDetails = new Announcements();
    private List<Acknowledgements> acknowledgementsDetailsList;
    private Acknowledgements acknowledgementsDetails = new Acknowledgements();
    private List<StakeBusiness> stakeBusinessDetailsList;
    private StakeBusiness stakeBusinessDetails = new StakeBusiness();
    private List<WardBusiness> wardBusinessDetailsList;
    private WardBusiness wardBusinessDetails = new WardBusiness();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);
        Intent intent = getIntent();
        viewType = intent.getStringExtra("paramType");

        listView = (ListView)findViewById(R.id.list_item);

        datesLocList = new ArrayList<>();
        setListViewHeader();

        final TextView addItem = (TextView) findViewById(R.id.total);
            addItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (viewType.contains("Agenda")) {
                        Intent intent = new Intent(DatesActivity.this, AddAgendaItem.class);
                        startActivity(intent);
                    } else if (viewType.contains("Announcements")) {
                        Intent intent = new Intent(DatesActivity.this, AddAnnouncementItem.class);
                        startActivity(intent);
                    } else if (viewType.contains("Acknowledgements")) {
                        Intent intent = new Intent(DatesActivity.this, AddAcknowledgementsItem.class);
                        startActivity(intent);
                    } else if (viewType.contains("Stake Business")) {
                        Intent intent = new Intent(DatesActivity.this, AddStakeBusinessItem.class);
                        startActivity(intent);
                    } else if (viewType.contains("Ward Business")) {
                        Intent intent = new Intent(DatesActivity.this, AddWardBusinessItem.class);
                        startActivity(intent);
                    }
                }
            });

        final TextView addItem2 = (TextView) findViewById(R.id.headerTitle);
        addItem2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (viewType.contains("Agenda")) {
                    Intent intent = new Intent(DatesActivity.this, AddAgendaItem.class);
                    startActivity(intent);
                } else if (viewType.contains("Announcements")) {
                    Intent intent = new Intent(DatesActivity.this, AddAnnouncementItem.class);
                    startActivity(intent);
                } else if (viewType.contains("Acknowledgements")) {
                    Intent intent = new Intent(DatesActivity.this, AddAcknowledgementsItem.class);
                    startActivity(intent);
                } else if (viewType.contains("Stake Business")) {
                    Intent intent = new Intent(DatesActivity.this, AddStakeBusinessItem.class);
                    startActivity(intent);
                } else if (viewType.contains("Ward Business")) {
                    Intent intent = new Intent(DatesActivity.this, AddWardBusinessItem.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
            datesLocList = new ArrayList<>();
            if (viewType.contains("Agenda")) {
                headerTitle.setText("Sacrament Agendas");
                getAgenda();
                setListViewAdapter();
            } else if (viewType.contains("Announcements")) {
                headerTitle.setText("Announcements");
                getAnnouncements();
                setListViewAdapter();
            } else if (viewType.contains("Acknowledgements")) {
                headerTitle.setText("Acknowledgements");
                getAcknowledgements();
                setListViewAdapter();
            } else if (viewType.contains("Stake Business")) {
                headerTitle.setText("Stake Business");
                getStakeBusiness();
                setListViewAdapter();
            } else if (viewType.contains("Ward Business")) {
                headerTitle.setText("Ward Business");
                getWardBusiness();
                setListViewAdapter();
            } else {
                headerTitle.setText("Unknown Selection");
                getAgenda();
                setListViewAdapter();
            }
    }

    private void getAgenda() {
            db = new DBHelper(this);
            agendaDetailsList = db.getAllAgenda();

            int lenAllAgenda = db.agendaRowcount();
            for (int i=0; i<lenAllAgenda; i++) {
                agendaDetails = agendaDetailsList.get(i);
                datesLocList.add(""+agendaDetails.getAGENDA_ID());
            }
        db.closeDB();
            db.close();
    }

    private void getAnnouncements() {
        db = new DBHelper(this);
        announcementsDetailsList = db.getAllAnnouncements();

        int lenAllAnnouncements = db.announcementsRowcount();
        for (int i=0; i<lenAllAnnouncements; i++) {
            announcementsDetails = announcementsDetailsList.get(i);
                datesLocList.add(""+announcementsDetails.getANNOUNCEMENTS_ID());
        }
        db.closeDB();
        db.close();
    }

    private void getAcknowledgements() {
        db = new DBHelper(this);
        acknowledgementsDetailsList = db.getAllAcknowledgements();

        int lenAllAcknowledgements = db.acknowledgementsRowcount();
        for (int i=0; i<lenAllAcknowledgements; i++) {
            acknowledgementsDetails = acknowledgementsDetailsList.get(i);
                datesLocList.add(""+acknowledgementsDetails.getACKNOWLEDGEMENT_ID());
        }
        db.closeDB();
        db.close();
    }

    private void getStakeBusiness() {
        db = new DBHelper(this);
        stakeBusinessDetailsList = db.getAllStakeBusiness();

        int lenAllStakeBusiness = db.stakeBusinessRowcount();
        for (int i=0; i<lenAllStakeBusiness; i++) {
            stakeBusinessDetails = stakeBusinessDetailsList.get(i);
                datesLocList.add(""+stakeBusinessDetails.getSTAKEBUSINESS_ID());
        }
        db.closeDB();
        db.close();
    }

    private void getWardBusiness() {
        db = new DBHelper(this);
        wardBusinessDetailsList = db.getAllWardBusiness();

        int lenAllWardBusiness = db.wardBusinessRowcount();
        for (int i=0; i<lenAllWardBusiness; i++) {
            wardBusinessDetails = wardBusinessDetailsList.get(i);
            datesLocList.add(""+wardBusinessDetails.getWARDBUSINESS_ID());
        }
        db.closeDB();
        db.close();
    }

    private void setListViewHeader() {
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.header_listview, listView, false);
        headerTitle = (TextView) header.findViewById(R.id.headerTitle);
        totalList = (TextView) header.findViewById(R.id.total);
        swipeLayout = (SwipeLayout)header.findViewById(R.id.swipe_layout);
        setSwipeViewFeatures();
        listView.addHeaderView(header);

            if (viewType.contains("Agenda")) {
                headerTitle.setText("Sacrament Agendas");
                getAgenda();
                setListViewAdapter();
            } else if (viewType.contains("Announcements")) {
                headerTitle.setText("Announcements");
                getAnnouncements();
                setListViewAdapter();
            } else if (viewType.contains("Acknowledgements")) {
                headerTitle.setText("Acknowledgements");
                getAcknowledgements();
                setListViewAdapter();
            } else if (viewType.contains("Stake Business")) {
                headerTitle.setText("Stake Business");
                getStakeBusiness();
                setListViewAdapter();
            } else if (viewType.contains("Ward Business")) {
                headerTitle.setText("Ward Business");
                getWardBusiness();
                setListViewAdapter();
            } else {
                headerTitle.setText("Unknown Selection");
                getAgenda();
                setListViewAdapter();
            }
    }

    private void setSwipeViewFeatures() {
        //set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //Log.i(TAG, "onClose");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //Log.i(TAG, "on swiping");
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                //Log.i(TAG, "on start open");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //Log.i(TAG, "the BottomView totally show");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                //Log.i(TAG, "the BottomView totally close");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });
    }

    private void setListViewAdapter() {

        if (viewType.contains("Agenda")) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, datesLocList,agendaDetailsList);
        } else if (viewType.contains("Announcements")) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, datesLocList,announcementsDetailsList);
        } else if (viewType.contains("Acknowledgements")) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, datesLocList,acknowledgementsDetailsList);
        } else if (viewType.contains("Stake Business")) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, datesLocList,stakeBusinessDetailsList);
        } else if (viewType.contains("Ward Business")) {
            adapter = new ListViewAdapter(this, R.layout.item_listview, datesLocList,wardBusinessDetailsList);
        }

        listView.setAdapter(adapter);
        totalList.setText("Click here to\nadd a new item");
    }

    public void updateAdapter(int position) {
        this.datesLocList.remove(position);
        if (viewType.contains("Agenda")) {
            this.agendaDetailsList.remove(position);
        } else if (viewType.contains("Announcements")) {
            announcementsDetailsList.remove(position);
        } else if (viewType.contains("Acknowledgements")) {
            acknowledgementsDetailsList.remove(position);
        } else if (viewType.contains("Stake Business")) {
            stakeBusinessDetailsList.remove(position);
        } else if (viewType.contains("Ward Business")) {
            wardBusinessDetailsList.remove(position);
        }
        adapter.notifyDataSetChanged(); //update adapter
            totalList.setText("Click here to\nadd a new item");
    }
}
