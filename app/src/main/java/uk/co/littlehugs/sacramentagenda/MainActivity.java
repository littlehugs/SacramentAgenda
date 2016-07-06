package uk.co.littlehugs.sacramentagenda;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private Handler uiThreadHandler = new Handler();
    private DBHelper db;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle sI = savedInstanceState;

//Create an instance of the splash view, and perform a setContentView()
        SplashView splashView = new SplashView(this);

        //Doing this allows you to access the "this" pointer of the main
        // activity inside the Runnable below.
        final MainActivity mainThis = this;

        // Set an event handler on the SplashView object, so that as soon
        // as it completes drawing we are
        // informed.  In response to that cue, we will *then* put up the main view,
        // replacing the content view of the main activity with that main view.
        splashView.setSplashEventHandler(new SplashView.SplashEvents() {
            @Override
            public void onSplashDrawComplete() {
                //Post the runnable that will put up the main view
                uiThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //This method is where you will have moved
                        // the entire initialization of your app's
                        // main display, which normally would have been
                        // in your onCreate() method prior to adding the
                        // splash display.
                        launchMainView(mainThis, sI);
                    }
                });
            }
        });

        //This will cause your splash view to draw.  When it finishes, it will trigger the code above.
        this.setContentView(splashView);

        //At this point, do *not* move directly on to performing a setContentView() on your main view.
        // If you do, you will never see the splash view at all.
        // You simply wait for the splash view to signal you that it has completed drawing itself, and
        // *then* call launchMainView(), which will itself call setContentView() again, passing it
        // your main view.




    }

    //Here is a stripped-down version of launchMainView().  You will typically have some additional
// initializations here - whatever might have been present originally in your onCreate() method.
    public void launchMainView(MainActivity mainThis, Bundle savedInstanceState) {
        //myRootView = new MyRootView(mainThis);

        //setContentView(myRootView);

       // final mainThis = mainThis;

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final ImageButton agendaItemButton = (ImageButton) findViewById(R.id.agendaImageButton);
        final ImageButton announcementButton = (ImageButton) findViewById(R.id.announcementImageButton);
        final ImageButton acknowledgementButton = (ImageButton) findViewById(R.id.acknowledgementImageButton);
        final ImageButton wardBusinessButton = (ImageButton) findViewById(R.id.wardbusinessImageButton);
        final ImageButton stakeBusinessButton = (ImageButton) findViewById(R.id.stakebusinessImageButton);
        final ImageButton viewAgendaButton = (ImageButton) findViewById(R.id.viewAgendaImageButton);

        db = new DBHelper(this);
        //Toast.makeText(this, "" + db.hymnsRowcount(), Toast.LENGTH_LONG).show();
        if (db.hymnsRowcount() == 0) {
            db.addAllHymns();
            db.closeDB();
        }

        if (db.lastSyncRowCount() == 0) {
            db.addFirstSync(0,"1");
            db.addFirstSync(1,"");
            db.closeDB();
        }
        db.close();

        //Toast.makeText(this, ""+System.currentTimeMillis(), Toast.LENGTH_LONG).show();


        agendaItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatesActivity.class);
                intent.putExtra("paramType", "Agenda");
                startActivity(intent);
            }
        });

        announcementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatesActivity.class);
                intent.putExtra("paramType", "Announcements");
                startActivity(intent);
            }
        });

        acknowledgementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatesActivity.class);
                intent.putExtra("paramType", "Acknowledgements");
                startActivity(intent);
            }
        });

        stakeBusinessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatesActivity.class);
                intent.putExtra("paramType", "Stake Business");
                startActivity(intent);
            }
        });

        wardBusinessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatesActivity.class);
                intent.putExtra("paramType", "Ward Business");
                startActivity(intent);
            }
        });


        viewAgendaButton.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAgendaActivity.class);
                startActivity(intent);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            String formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());
            db = new DBHelper(this);
            String lastSync = db.getLastSync(0);
            //String lastSync = "1";
            String webserviceUrl = db.getLastSync(1);
            //String webserviceUrl = "1";

            //Toast.makeText(this, "" + formattedDateTime, Toast.LENGTH_LONG).show();

            //Log.i("ann", "ANNOUNCEMENTS= " + db.getSyncAnnouncements(lastSync, formattedDateTime).replace("\\u0027","\\u005c\\u0027"));

           if (webserviceUrl != null && !webserviceUrl.isEmpty()) {
                new PostToUrl(this).execute("AGENDA= " + db.getSyncAgenda(lastSync, formattedDateTime).replace("\\u0027", "\\u005c\\u0027"), formattedDateTime, webserviceUrl);
                new GetFromUrl(this).execute(lastSync, formattedDateTime, "agenda", webserviceUrl);

                new PostToUrl(this).execute("ANNOUNCEMENTS= " + db.getSyncAnnouncements(lastSync, formattedDateTime).replace("\\u0027","\\u005c\\u0027"), formattedDateTime, webserviceUrl);
                new GetFromUrl(this).execute(lastSync, formattedDateTime, "announcements", webserviceUrl);

                new PostToUrl(this).execute("ACKNOWLEDGEMENTS= " + db.getSyncAcknowledgements(lastSync, formattedDateTime).replace("\\u0027", "\\u005c\\u0027"), formattedDateTime, webserviceUrl);
                new GetFromUrl(this).execute(lastSync, formattedDateTime, "acknowledgements", webserviceUrl);

                new PostToUrl(this).execute("STAKEBUSINESS= " + db.getSyncStakeBusiness(lastSync, formattedDateTime).replace("\\u0027", "\\u005c\\u0027"), formattedDateTime, webserviceUrl);
                new GetFromUrl(this).execute(lastSync, formattedDateTime, "stakebusiness", webserviceUrl);

                new PostToUrl(this).execute("WARDBUSINESS= " + db.getSyncWardBusiness(lastSync, formattedDateTime).replace("\\u0027","\\u005c\\u0027"), formattedDateTime, webserviceUrl);
                new GetFromUrl(this).execute(lastSync, formattedDateTime, "wardbusiness", webserviceUrl);
            } else {
                Toast.makeText(this, "Webservice not set", Toast.LENGTH_LONG).show();
            }
            db.close();
        }

        if (id == R.id.action_url_address){
            Intent intent = new Intent(MainActivity.this, AddWebservice.class);
            startActivity(intent);
        }

        if (id == R.id.test_url_address){
            db = new DBHelper(this);
            String webserviceUrl = db.getLastSync(1);
            new TestUrl(this).execute(webserviceUrl);
            db.close();
        }

        return super.onOptionsItemSelected(item);
    }

}
