package uk.co.littlehugs.sacramentagenda;

/**
 * Created by hugs on 12/02/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugs on 02/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "ldsmeeting.db";
    // tasks table name
    private static final String TABLE_HYMNS = "hymns";
    private static final String TABLE_AGENDA = "agenda";
    private static final String TABLE_ACKNOWLEDGEMENT = "acknowledgements";
    private static final String TABLE_STAKEBUSINESS = "stakebusiness";
    private static final String TABLE_WARDBUSINESS = "wardbusiness";
    private static final String TABLE_ANNOUNCEMENTS = "announcements";
    private static final String TABLE_LASTSYNC = "lastsync";

    private static final String HYMN_ID = "id";
    private static final String HYMN_NUM = "hymn_number";
    private static final String HYMN_NAME = "hymn_name";


    private static final String AGENDA_ID = "agendaId";
    private static final String AGENDADATE = "agendaDate";
    private static final String AGENDAOPENINGHYMN = "agendaOpeningHymn";
    private static final String AGENDAOPENINGPRAYER = "agendaOpeningPrayer";
    private static final String AGENDASACRAMENTHYMN = "agendaSacramentHymn";
    private static final String AGENDAFIRSTSPEAKER = "agendaFirstSpeaker";
    private static final String AGENDASECONDSPEAKER = "agendaSecondSpeaker";
    private static final String AGENDAINTERMEDIATEHYMN = "agendaIntermediateHymn";
    private static final String AGENDAFINALSPEAKER = "agendaFinalSpeaker";
    private static final String AGENDACLOSINGHYMN = "agendaClosingHymn";
    private static final String AGENDACLOSINGPRAYER = "agendaClosingPrayer";
    private static final String AGENDAUPDATEDATE = "agendaUpdateDate";
    private static final String AGENDASTATUS = "agendaStatus";
    //

    private static final String ACKNOWLEDGEMENT_ID = "acknowledgementId";
    private static final String ACKNOWLEDGEMENTDATE = "acknowledgementDate";
    private static final String ACKNOWLEDGEMENTPRESIDING = "acknowledgementPresiding";
    private static final String ACKNOWLEDGEMENTCONDUCTING = "acknowledgementConducting";
    private static final String ACKNOWLEDGEMENTSTAKEVISITOR1 = "acknowledgementStakeVisitor1";
    private static final String ACKNOWLEDGEMENTSTAKECALLING1 = "acknowledgementStakeCalling1";
    private static final String ACKNOWLEDGEMENTSTAKEVISITOR2 = "acknowledgementStakeVisitor2";
    private static final String ACKNOWLEDGEMENTSTAKECALLING2 = "acknowledgementStakeCalling2";
    private static final String ACKNOWLEDGEMENTSTAKEVISITOR3 = "acknowledgementStakeVisitor3";
    private static final String ACKNOWLEDGEMENTSTAKECALLING3 = "acknowledgementStakeCalling3";
    private static final String ACKNOWLEDGEMENTUPDATDATE = "acknowledgementUpdateDate";
    private static final String ACKNOWLEDGEMENTSTATUS = "acknowledgementStatus";


    private static final String ANNOUNCEMENTS_ID = "announcementsId";
    private static final String ANNOUNCEMENTSDATE = "announcementsDate";
    private static final String ANNOUNCEMENTS = "announcements";
    private static final String ANNOUNCEMENTSUPDATEDATE = "announcementUpdateDate";
    private static final String ANNOUNCEMENTSSTATUS = "announcementStatus";


    //private static final Boolean STAKEBUSINESSSTAKEBUS_YN = "stakeStakeBus_YN";
    private static final String STAKEBUSINESS_ID = "stakeBusinessId";
    private static final String STAKEBUSINESSDATE = "stakeDate";
    private static final String STAKEBUSINESS = "stakeBusiness";
    private static final String STAKEBUSINESSUPDATEDATE = "stakeBusinessUpdateDate";
    private static final String STAKEBUSINESSSTATUS = "stakeBusinessStatus";


    //private static final Boolean WARDBUSINESSWARDBUS_YN = "wardWardBus_YN";
    // Released/Sustained or Additional Business
    private static final String WARDBUSINESS_ID = "wardBusinessId";
    private static final String WARDBUSINESSDATE = "wardBusinessDate";
    private static final String WARDBUSINESSPURPOSE = "wardBusinessPurpose";
    private static final String WARDBUSINESSNAME = "wardBusinessName";
    private static final String WARDBUSINESSCALLING = "wardBusinessCalling";
    private static final String WARDBUSINESSUPDATEDATE = "wardBusinessUpdateDate";
    private static final String WARDBUSINESSSTATUS = "wardBusinessStatus";

    //Date and time of last successful sync
    private static final String SYNC_ID = "syncID";
    private static final String LASTSYNC = "lastSync";



    private SQLiteDatabase dbase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_HYMNS + " ( "
                + HYMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HYMN_NUM
                + " TEXT, " + HYMN_NAME + " TEXT)";
        db.execSQL(sql);

        //addAllHymns();

        String sqlAgenda = "CREATE TABLE IF NOT EXISTS " + TABLE_AGENDA + " ( "
                + AGENDA_ID + " INTEGER PRIMARY KEY, "
                + AGENDADATE + " TEXT, " + AGENDAOPENINGHYMN + " INTEGER, " + AGENDAOPENINGPRAYER + " TEXT, " +
                AGENDASACRAMENTHYMN + " INTEGER, " + AGENDAFIRSTSPEAKER + " TEXT, " + AGENDASECONDSPEAKER + " TEXT, " +
                AGENDAINTERMEDIATEHYMN + " INTEGER, " +
                AGENDAFINALSPEAKER + " TEXT, " + AGENDACLOSINGHYMN + " INTEGER, " +  AGENDACLOSINGPRAYER
                + " TEXT, " + AGENDAUPDATEDATE + " INTEGER, " + AGENDASTATUS + " TEXT)";
        db.execSQL(sqlAgenda);

        String sqlAcknowledgement = "CREATE TABLE IF NOT EXISTS " + TABLE_ACKNOWLEDGEMENT + " ( "
                + ACKNOWLEDGEMENT_ID + " INTEGER PRIMARY KEY, "
                + ACKNOWLEDGEMENTDATE + " TEXT, " + ACKNOWLEDGEMENTPRESIDING + " TEXT, " + ACKNOWLEDGEMENTCONDUCTING + " TEXT, " +
                ACKNOWLEDGEMENTSTAKEVISITOR1 + " TEXT, " + ACKNOWLEDGEMENTSTAKECALLING1 + " TEXT, " +
                ACKNOWLEDGEMENTSTAKEVISITOR2 + " TEXT, " + ACKNOWLEDGEMENTSTAKECALLING2 + " TEXT, " +
                ACKNOWLEDGEMENTSTAKEVISITOR3 + " TEXT, " + ACKNOWLEDGEMENTSTAKECALLING3 + " TEXT, " +
                ACKNOWLEDGEMENTUPDATDATE + " INTEGER, " + ACKNOWLEDGEMENTSTATUS + " TEXT)";
        db.execSQL(sqlAcknowledgement);

        String sqlAnnouncements = "CREATE TABLE IF NOT EXISTS " + TABLE_ANNOUNCEMENTS + " ( "
                + ANNOUNCEMENTS_ID + " INTEGER PRIMARY KEY, "
                + ANNOUNCEMENTSDATE + " TEXT, " + ANNOUNCEMENTS + " TEXT, " +
                ANNOUNCEMENTSUPDATEDATE + " INTEGER, " + ANNOUNCEMENTSSTATUS + " TEXT)";
        db.execSQL(sqlAnnouncements);

        String sqlStakeBusiness = "CREATE TABLE IF NOT EXISTS " + TABLE_STAKEBUSINESS + " ( "
                + STAKEBUSINESS_ID + " INTEGER PRIMARY KEY, "
                + STAKEBUSINESSDATE + " TEXT, " + STAKEBUSINESS + " TEXT, " +
                STAKEBUSINESSUPDATEDATE + " INTEGER, " + STAKEBUSINESSSTATUS + " TEXT)";
        db.execSQL(sqlStakeBusiness);

        String sqlWardBusiness = "CREATE TABLE IF NOT EXISTS " + TABLE_WARDBUSINESS + " ( "
                + WARDBUSINESS_ID + " INTEGER PRIMARY KEY, "
                + WARDBUSINESSDATE + " TEXT, " + WARDBUSINESSPURPOSE + " TEXT, " + WARDBUSINESSNAME + " TEXT, " +
                WARDBUSINESSCALLING + " TEXT, " + WARDBUSINESSUPDATEDATE + " INTEGER, " + WARDBUSINESSSTATUS + " TEXT)";
        db.execSQL(sqlWardBusiness);

        String sqlLastSync = "CREATE  TABLE IF  NOT  EXISTS " +  TABLE_LASTSYNC + " ( "
                + SYNC_ID + "  INTEGER PRIMARY  KEY  DEFAULT  0, "
                + LASTSYNC + " INTEGER DEFAULT  0)";
        db.execSQL(sqlLastSync);
    }

    public SQLiteDatabase openDB() {
        dbase = this.getWritableDatabase();
        return dbase;
    }

    public synchronized void closeDB() {
        if(dbase != null) {
            dbase.close();
        }
        super.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYMNS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNOUNCEMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACKNOWLEDGEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAKEBUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WARDBUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LASTSYNC);
        // Create tables again
        onCreate(db);
    }

    public void addHymns(Hymns hymns) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(HYMN_NUM, hymns.getHYMN_NUM());
            values.put(HYMN_NAME, hymns.getHYMN_NAME());
            // Inserting Row
            dbase.insert(TABLE_HYMNS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addAgenda(Agenda agenda) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(AGENDA_ID, agenda.getAGENDA_ID());
            values.put(AGENDADATE, agenda.getAGENDADATE());
            values.put(AGENDAOPENINGHYMN, agenda.getAGENDAOPENINGHYMN());
            values.put(AGENDAOPENINGPRAYER, agenda.getAGENDAOPENINGPRAYER());
            values.put(AGENDASACRAMENTHYMN, agenda.getAGENDASACRAMENTHYMN());
            values.put(AGENDAFIRSTSPEAKER, agenda.getAGENDAFIRSTSPEAKER());
            values.put(AGENDASECONDSPEAKER, agenda.getAGENDASECONDSPEAKER());
            values.put(AGENDAINTERMEDIATEHYMN, agenda.getAGENDAINTERMEDIATEHYMN());
            values.put(AGENDAFINALSPEAKER, agenda.getAGENDAFINALSPEAKER());
            values.put(AGENDACLOSINGHYMN, agenda.getAGENDACLOSINGHYMN());
            values.put(AGENDACLOSINGPRAYER, agenda.getAGENDACLOSINGPRAYER());
            values.put(AGENDAUPDATEDATE, agenda.getAGENDAUPDATEDATE());
            values.put(AGENDASTATUS, agenda.getAGENDASTATUS());
            // Inserting Row
            dbase.insert(TABLE_AGENDA, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public  void deleteAgenda(String del_id, String updateDate) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(AGENDAUPDATEDATE, updateDate);
            values.put(AGENDASTATUS, "D");
            dbase.update(TABLE_AGENDA, values, AGENDA_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void hardDeleteAgenda(String del_id){
        dbase = this.getWritableDatabase();
        try{
            dbase.delete(TABLE_AGENDA, AGENDA_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void editAgenda(Agenda agenda, String edit_id) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(AGENDADATE, agenda.getAGENDADATE());
            values.put(AGENDAOPENINGHYMN, agenda.getAGENDAOPENINGHYMN());
            values.put(AGENDAOPENINGPRAYER, agenda.getAGENDAOPENINGPRAYER());
            values.put(AGENDASACRAMENTHYMN, agenda.getAGENDASACRAMENTHYMN());
            values.put(AGENDAFIRSTSPEAKER, agenda.getAGENDAFIRSTSPEAKER());
            values.put(AGENDASECONDSPEAKER, agenda.getAGENDASECONDSPEAKER());
            values.put(AGENDAINTERMEDIATEHYMN, agenda.getAGENDAINTERMEDIATEHYMN());
            values.put(AGENDAFINALSPEAKER, agenda.getAGENDAFINALSPEAKER());
            values.put(AGENDACLOSINGHYMN, agenda.getAGENDACLOSINGHYMN());
            values.put(AGENDACLOSINGPRAYER, agenda.getAGENDACLOSINGPRAYER());
            values.put(AGENDAUPDATEDATE, agenda.getAGENDAUPDATEDATE());
            values.put(AGENDASTATUS, agenda.getAGENDASTATUS());
            dbase.update(TABLE_AGENDA, values, AGENDA_ID + "=" + edit_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void updateAgendaUpdateDate(String agenda_id, String update_id) {
        dbase = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(AGENDAUPDATEDATE, update_id);
            dbase.update(TABLE_AGENDA, values, AGENDA_ID + "=" + agenda_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addAcknowledgements(Acknowledgements acknowledgements) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(ACKNOWLEDGEMENT_ID, acknowledgements.getACKNOWLEDGEMENT_ID());
            values.put(ACKNOWLEDGEMENTDATE, acknowledgements.getACKNOWLEDGEMENTDATE());
            values.put(ACKNOWLEDGEMENTPRESIDING, acknowledgements.getACKNOWLEDGEMENTPRESIDING());
            values.put(ACKNOWLEDGEMENTCONDUCTING, acknowledgements.getACKNOWLEDGEMENTCONDUCTING());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR1, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR1());
            values.put(ACKNOWLEDGEMENTSTAKECALLING1, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING1());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR2, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR2());
            values.put(ACKNOWLEDGEMENTSTAKECALLING2, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING2());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR3, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR3());
            values.put(ACKNOWLEDGEMENTSTAKECALLING3, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING3());
            values.put(ACKNOWLEDGEMENTUPDATDATE, acknowledgements.getACKNOWLEDGEMENTUPDATEDATE());
            values.put(ACKNOWLEDGEMENTSTATUS, acknowledgements.getACKNOWLEDGEMENTSTATUS());
            // Inserting Row
            dbase.insert(TABLE_ACKNOWLEDGEMENT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void editAcknowledgements(Acknowledgements acknowledgements, String edit_id) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ACKNOWLEDGEMENTDATE, acknowledgements.getACKNOWLEDGEMENTDATE());
            values.put(ACKNOWLEDGEMENTPRESIDING, acknowledgements.getACKNOWLEDGEMENTPRESIDING());
            values.put(ACKNOWLEDGEMENTCONDUCTING, acknowledgements.getACKNOWLEDGEMENTCONDUCTING());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR1, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR1());
            values.put(ACKNOWLEDGEMENTSTAKECALLING1, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING1());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR2, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR2());
            values.put(ACKNOWLEDGEMENTSTAKECALLING2, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING2());
            values.put(ACKNOWLEDGEMENTSTAKEVISITOR3, acknowledgements.getACKNOWLEDGEMENTSTAKEVISITOR3());
            values.put(ACKNOWLEDGEMENTSTAKECALLING3, acknowledgements.getACKNOWLEDGEMENTSTAKECALLING3());
            values.put(ACKNOWLEDGEMENTUPDATDATE, acknowledgements.getACKNOWLEDGEMENTUPDATEDATE());
            values.put(ACKNOWLEDGEMENTSTATUS, acknowledgements.getACKNOWLEDGEMENTSTATUS());
            dbase.update(TABLE_ACKNOWLEDGEMENT, values, ACKNOWLEDGEMENT_ID + "=" + edit_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public  void deleteAcknowledgements(String del_id, String updateDate) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ACKNOWLEDGEMENTUPDATDATE, updateDate);
            values.put(ACKNOWLEDGEMENTSTATUS, "D");
            dbase.update(TABLE_ACKNOWLEDGEMENT, values, ACKNOWLEDGEMENT_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void hardDeleteAcknowledgements(String del_id) {
        dbase = this.getWritableDatabase();
        try {
            dbase.delete(TABLE_ACKNOWLEDGEMENT, ACKNOWLEDGEMENT_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void updateAcknowledgementUpdateDate(String id, String update_id) {
        dbase = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(ACKNOWLEDGEMENTUPDATDATE, update_id);
            dbase.update(TABLE_ACKNOWLEDGEMENT, values, ACKNOWLEDGEMENT_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addAnnouncements(Announcements announcements) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(ANNOUNCEMENTS_ID, announcements.getANNOUNCEMENTS_ID());
            values.put(ANNOUNCEMENTSDATE, announcements.getANNOUNCEMENTSDATE());
            values.put(ANNOUNCEMENTS, announcements.getANNOUNCEMENTS());
            values.put(ANNOUNCEMENTSUPDATEDATE, announcements.getANNOUNCEMENTSUPDATEDATE());
            values.put(ANNOUNCEMENTSSTATUS, announcements.getANNOUNCEMENTSSTATUS());
            // Inserting Row
            dbase.insert(TABLE_ANNOUNCEMENTS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void editAnnouncements(Announcements announcements, String edit_id) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            //values.put(ANNOUNCEMENTS_ID, announcements.getANNOUNCEMENTS_ID());
            values.put(ANNOUNCEMENTSDATE, announcements.getANNOUNCEMENTSDATE());
            values.put(ANNOUNCEMENTS, announcements.getANNOUNCEMENTS());
            values.put(ANNOUNCEMENTSUPDATEDATE, announcements.getANNOUNCEMENTSUPDATEDATE());
            values.put(ANNOUNCEMENTSSTATUS, announcements.getANNOUNCEMENTSSTATUS());
            dbase.update(TABLE_ANNOUNCEMENTS, values, ANNOUNCEMENTS_ID + "=" + edit_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void deleteAnnouncements(String del_id, String updateDate) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ANNOUNCEMENTSUPDATEDATE, updateDate);
            values.put(ANNOUNCEMENTSSTATUS, "D");
            dbase.update(TABLE_ANNOUNCEMENTS, values, ANNOUNCEMENTS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void hardDeleteAnnouncements(String del_id) {
        dbase = this.getWritableDatabase();
        try {
            dbase.delete(TABLE_ANNOUNCEMENTS, ANNOUNCEMENTS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }


    public void updateAnnouncementUpdateDate(String id, String update_id) {
        dbase = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(ANNOUNCEMENTSUPDATEDATE, update_id);
            dbase.update(TABLE_ANNOUNCEMENTS, values, ANNOUNCEMENTS_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }
    public void addStakeBusiness(StakeBusiness stakeBusiness) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(STAKEBUSINESS_ID, stakeBusiness.getSTAKEBUSINESS_ID());
            values.put(STAKEBUSINESSDATE, stakeBusiness.getSTAKEBUSINESSDATE());
            values.put(STAKEBUSINESS, stakeBusiness.getSTAKEBUSINESS());
            values.put(STAKEBUSINESSUPDATEDATE, stakeBusiness.getSTAKEBUSINESSUPDATEDATE());
            values.put(STAKEBUSINESSSTATUS, stakeBusiness.getSTAKEBUSINESSSTATUS());
            // Inserting Row
            dbase.insert(TABLE_STAKEBUSINESS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void editStakeBusiness(StakeBusiness stakeBusiness, String edit_id) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            //values.put(STAKEBUSINESS_ID, stakeBusiness.getSTAKEBUSINESS_ID());
            values.put(STAKEBUSINESSDATE, stakeBusiness.getSTAKEBUSINESSDATE());
            values.put(STAKEBUSINESS, stakeBusiness.getSTAKEBUSINESS());
            values.put(STAKEBUSINESSUPDATEDATE, stakeBusiness.getSTAKEBUSINESSUPDATEDATE());
            values.put(STAKEBUSINESSSTATUS, stakeBusiness.getSTAKEBUSINESSSTATUS());
            dbase.update(TABLE_STAKEBUSINESS, values, STAKEBUSINESS_ID + "=" + edit_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void deleteStakeBusiness(String del_id, String updateDate) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(STAKEBUSINESSUPDATEDATE, updateDate);
            values.put(STAKEBUSINESSSTATUS, "D");
            dbase.update(TABLE_STAKEBUSINESS, values, STAKEBUSINESS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void hardDeleteStakeBusiness(String del_id) {
        dbase = this.getWritableDatabase();
        try {
            dbase.delete(TABLE_STAKEBUSINESS, STAKEBUSINESS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void updateStakeBusinessUpdateDate(String id, String update_id) {
        dbase = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(STAKEBUSINESSUPDATEDATE, update_id);
            dbase.update(TABLE_STAKEBUSINESS, values, STAKEBUSINESS_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addWardBusiness(WardBusiness wardBusiness) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(WARDBUSINESS_ID, wardBusiness.getWARDBUSINESS_ID());
            values.put(WARDBUSINESSDATE, wardBusiness.getWARDBUSINESSDATE());
            values.put(WARDBUSINESSPURPOSE, wardBusiness.getWARDBUSINESSPURPOSE());
            values.put(WARDBUSINESSNAME, wardBusiness.getWARDBUSINESSNAME());
            values.put(WARDBUSINESSCALLING, wardBusiness.getWARDBUSINESSCALLING());
            values.put(WARDBUSINESSUPDATEDATE, wardBusiness.getWARDBUSINESSUPDATEDATE());
            values.put(WARDBUSINESSSTATUS, wardBusiness.getWARDBUSINESSSTATUS());
            // Inserting Row
            dbase.insert(TABLE_WARDBUSINESS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void editWardBusiness(WardBusiness wardBusiness, String edit_id) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(WARDBUSINESSDATE, wardBusiness.getWARDBUSINESSDATE());
            values.put(WARDBUSINESSPURPOSE, wardBusiness.getWARDBUSINESSPURPOSE());
            values.put(WARDBUSINESSNAME, wardBusiness.getWARDBUSINESSNAME());
            values.put(WARDBUSINESSCALLING, wardBusiness.getWARDBUSINESSCALLING());
            values.put(WARDBUSINESSUPDATEDATE, wardBusiness.getWARDBUSINESSUPDATEDATE());
            values.put(WARDBUSINESSSTATUS, wardBusiness.getWARDBUSINESSSTATUS());
            dbase.update(TABLE_WARDBUSINESS, values, WARDBUSINESS_ID + "=" + edit_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public  void deleteWardBusiness(String del_id, String updateDate) {
        dbase = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(WARDBUSINESSUPDATEDATE, updateDate);
            values.put(WARDBUSINESSSTATUS, "D");
            dbase.update(TABLE_WARDBUSINESS, values, WARDBUSINESS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void hardDeleteWardBusiness(String del_id) {
        dbase = this.getWritableDatabase();
        try {
            dbase.delete(TABLE_WARDBUSINESS, WARDBUSINESS_ID + "=" + del_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void updateWardBusinessUpdateDate(String id, String update_id) {
        dbase = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(WARDBUSINESSUPDATEDATE, update_id);
            dbase.update(TABLE_WARDBUSINESS, values, WARDBUSINESS_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addFirstSync(int id, String strVal) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try{
            values.put(SYNC_ID, id);
            values.put(LASTSYNC, strVal);
            dbase.insert(TABLE_LASTSYNC, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public void addLastSync(String lastSync, int id) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(LASTSYNC, lastSync);
            dbase.update(TABLE_LASTSYNC, values, SYNC_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.close();
        }
    }

    public String getLastSync(int id) {
        String selectQuery = "SELECT " + LASTSYNC + " FROM " + TABLE_LASTSYNC + " WHERE " + SYNC_ID + "=" + id;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String sync = cursor.getString(0);
        cursor.close();
        dbase.close();
        return sync;
    }

    public List<Hymns> getAllHymns() {
        Cursor cursor = null;
        try {
            List<Hymns> hymnsList = new ArrayList<Hymns>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_HYMNS;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Hymns hymns = new Hymns();
                    hymns.setHYMN_ID(cursor.getInt(0));
                    hymns.setHYMN_NUM(cursor.getInt(1));
                    hymns.setHYMN_NAME(cursor.getString(2));
                    hymnsList.add(hymns);
                } while (cursor.moveToNext());
            }
            // return hymns list
            return hymnsList;
        } finally  {
            if (cursor != null) {
                cursor.close();}
            closeDB();
        }
    }

    //get hymn name of one hymn
    public String getHymn(int h_num) {
        String selectQuery = "SELECT hymn_name FROM " + TABLE_HYMNS + " WHERE " + HYMN_NUM + "=" + h_num;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String hName = cursor.getString(0);
        cursor.close();
        dbase.close();
        return hName;

    }

    public List<Agenda> getSingleAgenda(String pos) {
        Cursor cursor = null;
        try {
            List<Agenda> agendaList = new ArrayList<Agenda>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_AGENDA + " WHERE " + AGENDA_ID + "=" + pos;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Agenda agenda = new Agenda();
                    agenda.setAGENDA_ID(cursor.getString(0));
                    agenda.setAGENDADATE(cursor.getString(1));
                    agenda.setAGENDAOPENINGHYMN(cursor.getInt(2));
                    agenda.setAGENDAOPENINGPRAYER(cursor.getString(3));
                    agenda.setAGENDASACRAMENTHYMN(cursor.getInt(4));
                    agenda.setAGENDAFIRSTSPEAKER(cursor.getString(5));
                    agenda.setAGENDASECONDSPEAKER(cursor.getString(6));
                    agenda.setAGENDAINTERMEDIATEHYMN(cursor.getInt(7));
                    agenda.setAGENDAFINALSPEAKER(cursor.getString(8));
                    agenda.setAGENDACLOSINGHYMN(cursor.getInt(9));
                    agenda.setAGENDACLOSINGPRAYER(cursor.getString(10));
                    agenda.setAGENDAUPDATEDATE(cursor.getString(11));
                    agenda.setAGENDASTATUS(cursor.getString(12));
                    agendaList.add(agenda);
                } while (cursor.moveToNext());
            }
            // return agenda list
            return agendaList;
        } finally  {
            if (cursor != null) {
                cursor.close();}
            closeDB();
        }
    }


    public List<Agenda> getAllAgenda() {
        Cursor cursor = null;
        try {
            List<Agenda> agendaList = new ArrayList<Agenda>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_AGENDA + " WHERE " + AGENDASTATUS + "='A' ORDER BY " + AGENDADATE + " DESC";
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Agenda agenda = new Agenda();
                    agenda.setAGENDA_ID(cursor.getString(0));
                    agenda.setAGENDADATE(cursor.getString(1));
                    agenda.setAGENDAOPENINGHYMN(cursor.getInt(2));
                    agenda.setAGENDAOPENINGPRAYER(cursor.getString(3));
                    agenda.setAGENDASACRAMENTHYMN(cursor.getInt(4));
                    agenda.setAGENDAFIRSTSPEAKER(cursor.getString(5));
                    agenda.setAGENDASECONDSPEAKER(cursor.getString(6));
                    agenda.setAGENDAINTERMEDIATEHYMN(cursor.getInt(7));
                    agenda.setAGENDAFINALSPEAKER(cursor.getString(8));
                    agenda.setAGENDACLOSINGHYMN(cursor.getInt(9));
                    agenda.setAGENDACLOSINGPRAYER(cursor.getString(10));
                    agenda.setAGENDAUPDATEDATE(cursor.getString(11));
                    agenda.setAGENDASTATUS(cursor.getString(12));
                    agendaList.add(agenda);
                } while (cursor.moveToNext());
            }
            // return agenda list
            return agendaList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<Agenda> getWeekAgenda(String curDate) {
        Cursor cursor = null;
        try {
            List<Agenda> agendaList = new ArrayList<Agenda>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_AGENDA + " WHERE "+ AGENDADATE +"=" + curDate;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Agenda agenda = new Agenda();
                    agenda.setAGENDA_ID(cursor.getString(0));
                    agenda.setAGENDADATE(cursor.getString(1));
                    agenda.setAGENDAOPENINGHYMN(cursor.getInt(2));
                    agenda.setAGENDAOPENINGPRAYER(cursor.getString(3));
                    agenda.setAGENDASACRAMENTHYMN(cursor.getInt(4));
                    agenda.setAGENDAFIRSTSPEAKER(cursor.getString(5));
                    agenda.setAGENDASECONDSPEAKER(cursor.getString(6));
                    agenda.setAGENDAINTERMEDIATEHYMN(cursor.getInt(7));
                    agenda.setAGENDAFINALSPEAKER(cursor.getString(8));
                    agenda.setAGENDACLOSINGHYMN(cursor.getInt(9));
                    agenda.setAGENDACLOSINGPRAYER(cursor.getString(10));
                    agenda.setAGENDAUPDATEDATE(cursor.getString(11));
                    agenda.setAGENDASTATUS(cursor.getString(12));
                    agendaList.add(agenda);
                } while (cursor.moveToNext());
            }
            // return agenda list
            return agendaList;
        } finally  {
            if (cursor != null) {
                cursor.close();}
            closeDB();
        }
    }

    public String getSyncAgenda(String lastSync, String updateDate)  {
        Cursor cursor = null;
        try {
            List<Agenda> agendaList = new ArrayList<Agenda>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_AGENDA + " WHERE "+ AGENDAUPDATEDATE +">" + lastSync;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Agenda agenda = new Agenda();
                    agenda.setAGENDA_ID(cursor.getString(0));
                    agenda.setAGENDADATE(cursor.getString(1));
                    agenda.setAGENDAOPENINGHYMN(cursor.getInt(2));
                    agenda.setAGENDAOPENINGPRAYER(cursor.getString(3));
                    agenda.setAGENDASACRAMENTHYMN(cursor.getInt(4));
                    agenda.setAGENDAFIRSTSPEAKER(cursor.getString(5));
                    agenda.setAGENDASECONDSPEAKER(cursor.getString(6));
                    agenda.setAGENDAINTERMEDIATEHYMN(cursor.getInt(7));
                    agenda.setAGENDAFINALSPEAKER(cursor.getString(8));
                    agenda.setAGENDACLOSINGHYMN(cursor.getInt(9));
                    agenda.setAGENDACLOSINGPRAYER(cursor.getString(10));
                    agenda.setAGENDAUPDATEDATE(updateDate);
                    agenda.setAGENDASTATUS(cursor.getString(12));
                    agendaList.add(agenda);
                    Log.i("Sync Agenda", "" + cursor.getString(0));
                } while (cursor.moveToNext());
            }
            // return agenda list
            return new Gson().toJson(agendaList);
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }

    }


    public List<Acknowledgements> getAllAcknowledgements() {
        Cursor cursor = null;
        try {
            List<Acknowledgements> acknowledgementsList = new ArrayList<Acknowledgements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ACKNOWLEDGEMENT + " WHERE " + ACKNOWLEDGEMENTSTATUS + "='A' ORDER BY " + ACKNOWLEDGEMENTDATE + " DESC";
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Acknowledgements acknowledgements = new Acknowledgements();
                    acknowledgements.setACKNOWEDGEMENT_ID(cursor.getString(0));
                    acknowledgements.setACKNOWLEDGEMENTDATE(cursor.getString(1));
                    acknowledgements.setACKNOWLEDGEMENTPRESIDING(cursor.getString(2));
                    acknowledgements.setACKNOWLEDGEMENTCONDUCTING(cursor.getString(3));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR1(cursor.getString(4));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING1(cursor.getString(5));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR2(cursor.getString(6));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING2(cursor.getString(7));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR3(cursor.getString(8));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING3(cursor.getString(9));
                    acknowledgements.setACKNOWLEDGEMENTUPDATEDATE(cursor.getString(10));
                    acknowledgements.setACKNOWLEDGEMENTSTATUS(cursor.getString(11));
                    acknowledgementsList.add(acknowledgements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return acknowledgementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<Acknowledgements> getSingleAcknowledgements(String pos) {
        Cursor cursor = null;
        try {
            List<Acknowledgements> acknowledgementsList = new ArrayList<Acknowledgements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ACKNOWLEDGEMENT + " WHERE " + ACKNOWLEDGEMENT_ID + "=" + pos;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Acknowledgements acknowledgements = new Acknowledgements();
                    acknowledgements.setACKNOWEDGEMENT_ID(cursor.getString(0));
                    acknowledgements.setACKNOWLEDGEMENTDATE(cursor.getString(1));
                    acknowledgements.setACKNOWLEDGEMENTPRESIDING(cursor.getString(2));
                    acknowledgements.setACKNOWLEDGEMENTCONDUCTING(cursor.getString(3));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR1(cursor.getString(4));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING1(cursor.getString(5));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR2(cursor.getString(6));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING2(cursor.getString(7));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR3(cursor.getString(8));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING3(cursor.getString(9));
                    acknowledgements.setACKNOWLEDGEMENTUPDATEDATE(cursor.getString(10));
                    acknowledgements.setACKNOWLEDGEMENTSTATUS(cursor.getString(11));
                    acknowledgementsList.add(acknowledgements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return acknowledgementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<Acknowledgements> getWeekAcknowledgements(String curDate) {
        Cursor cursor = null;
        try {
            List<Acknowledgements> acknowledgementsList = new ArrayList<Acknowledgements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ACKNOWLEDGEMENT + " WHERE "+ ACKNOWLEDGEMENTDATE +"=" + curDate;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Acknowledgements acknowledgements = new Acknowledgements();
                    acknowledgements.setACKNOWEDGEMENT_ID(cursor.getString(0));
                    acknowledgements.setACKNOWLEDGEMENTDATE(cursor.getString(1));
                    acknowledgements.setACKNOWLEDGEMENTPRESIDING(cursor.getString(2));
                    acknowledgements.setACKNOWLEDGEMENTCONDUCTING(cursor.getString(3));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR1(cursor.getString(4));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING1(cursor.getString(5));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR2(cursor.getString(6));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING2(cursor.getString(7));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR3(cursor.getString(8));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING3(cursor.getString(9));
                    acknowledgements.setACKNOWLEDGEMENTUPDATEDATE(cursor.getString(10));
                    acknowledgements.setACKNOWLEDGEMENTSTATUS(cursor.getString(11));
                    acknowledgementsList.add(acknowledgements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return acknowledgementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public String getSyncAcknowledgements(String lastSync, String updateDate) {
        Cursor cursor = null;
        try {
            List<Acknowledgements> acknowledgementsList = new ArrayList<Acknowledgements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ACKNOWLEDGEMENT + " WHERE "+ ACKNOWLEDGEMENTUPDATDATE +">" + lastSync;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Acknowledgements acknowledgements = new Acknowledgements();
                    acknowledgements.setACKNOWEDGEMENT_ID(cursor.getString(0));
                    acknowledgements.setACKNOWLEDGEMENTDATE(cursor.getString(1));
                    acknowledgements.setACKNOWLEDGEMENTPRESIDING(cursor.getString(2));
                    acknowledgements.setACKNOWLEDGEMENTCONDUCTING(cursor.getString(3));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR1(cursor.getString(4));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING1(cursor.getString(5));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR2(cursor.getString(6));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING2(cursor.getString(7));
                    acknowledgements.setACKNOWLEDGEMENTSTAKEVISITOR3(cursor.getString(8));
                    acknowledgements.setACKNOWLEDGEMENTSTAKECALLING3(cursor.getString(9));
                    acknowledgements.setACKNOWLEDGEMENTUPDATEDATE(updateDate);
                    acknowledgements.setACKNOWLEDGEMENTSTATUS(cursor.getString(11));
                    acknowledgementsList.add(acknowledgements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return  new Gson().toJson(acknowledgementsList);
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<Announcements> getAllAnnouncements() {
        Cursor cursor = null;
        try {
            List<Announcements> announcementsList = new ArrayList<Announcements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS  + " WHERE " + ANNOUNCEMENTSSTATUS + "='A' ORDER BY " + ANNOUNCEMENTSDATE + " DESC";
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Announcements announcements = new Announcements();
                    announcements.setANNOUNCEMENTS_ID(cursor.getString(0));
                    announcements.setANNOUNCEMENTSDATE(cursor.getString(1));
                    announcements.setANNOUNCEMENTS(cursor.getString(2));
                    announcements.setANNOUNCEMENTSUPDATEDATE(cursor.getString(3));
                    announcements.setANNOUNCEMENTSSTATUS(cursor.getString(4));
                    announcementsList.add(announcements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return announcementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<Announcements> getSingleAnnouncements(String pos) {
        Cursor cursor = null;
        try {
            List<Announcements> announcementsList = new ArrayList<Announcements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS + " WHERE " + ANNOUNCEMENTS_ID + "=" + pos;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Announcements announcements = new Announcements();
                    announcements.setANNOUNCEMENTS_ID(cursor.getString(0));
                    announcements.setANNOUNCEMENTSDATE(cursor.getString(1));
                    announcements.setANNOUNCEMENTS(cursor.getString(2));
                    announcements.setANNOUNCEMENTSUPDATEDATE(cursor.getString(3));
                    announcements.setANNOUNCEMENTSSTATUS(cursor.getString(4));
                    announcementsList.add(announcements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return announcementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }


    public List<Announcements> getWeekAnnouncements(String curDate) {
        Cursor cursor = null;
        try {
            List<Announcements> announcementsList = new ArrayList<Announcements>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS + " WHERE "+ ANNOUNCEMENTSDATE +"=" + curDate;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Announcements announcements = new Announcements();
                    announcements.setANNOUNCEMENTS_ID(cursor.getString(0));
                    announcements.setANNOUNCEMENTSDATE(cursor.getString(1));
                    announcements.setANNOUNCEMENTS(cursor.getString(2));
                    announcements.setANNOUNCEMENTSUPDATEDATE(cursor.getString(3));
                    announcements.setANNOUNCEMENTSSTATUS(cursor.getString(4));
                    announcementsList.add(announcements);
                } while (cursor.moveToNext());
            }
            // return announcements list
            return announcementsList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public String getSyncAnnouncements(String lastSync, String updateDate) {
        Cursor cursor = null;
        try {
            List<Announcements> announcementsList = new ArrayList<Announcements>();
            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_ANNOUNCEMENTS + " WHERE "+ ANNOUNCEMENTSUPDATEDATE +">" + lastSync;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Announcements announcements = new Announcements();
                    announcements.setANNOUNCEMENTS_ID(cursor.getString(0));
                    announcements.setANNOUNCEMENTSDATE(cursor.getString(1));
                    announcements.setANNOUNCEMENTS(cursor.getString(2));
                    announcements.setANNOUNCEMENTSUPDATEDATE(updateDate);
                    announcements.setANNOUNCEMENTSSTATUS(cursor.getString(4));
                    announcementsList.add(announcements);
                } while (cursor.moveToNext());
            }
            return new Gson().toJson(announcementsList);
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<StakeBusiness> getAllStakeBusiness() {
        Cursor cursor = null;
        try {
            List<StakeBusiness> stakeBusinessList = new ArrayList<StakeBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_STAKEBUSINESS  + " WHERE " + STAKEBUSINESSSTATUS + "='A' ORDER BY " + STAKEBUSINESSDATE + " DESC";
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    StakeBusiness stakeBusiness = new StakeBusiness();
                    stakeBusiness.setSTAKEBUSINESS_ID(cursor.getString(0));
                    stakeBusiness.setSTAKEBUSINESSDATE(cursor.getString(1));
                    stakeBusiness.setSTAKEBUSINESS(cursor.getString(2));
                    stakeBusiness.setSTAKEBUSINESSUPDATEDATE(cursor.getString(3));
                    stakeBusiness.setSTAKEBUSINESSSTATUS(cursor.getString(4));
                    stakeBusinessList.add(stakeBusiness);
                } while (cursor.moveToNext());
            }
            // return stakeBusiness list
            return stakeBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<StakeBusiness> getSingleStakeBusiness(String pos) {
        Cursor cursor = null;
        try {
            List<StakeBusiness> stakeBusinessList = new ArrayList<StakeBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_STAKEBUSINESS + " WHERE " + STAKEBUSINESS_ID + "=" + pos;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    StakeBusiness stakeBusiness = new StakeBusiness();
                    stakeBusiness.setSTAKEBUSINESS_ID(cursor.getString(0));
                    stakeBusiness.setSTAKEBUSINESSDATE(cursor.getString(1));
                    stakeBusiness.setSTAKEBUSINESS(cursor.getString(2));
                    stakeBusiness.setSTAKEBUSINESSUPDATEDATE(cursor.getString(3));
                    stakeBusiness.setSTAKEBUSINESSSTATUS(cursor.getString(4));
                    stakeBusinessList.add(stakeBusiness);
                } while (cursor.moveToNext());
            }
            // return stakeBusiness list
            return stakeBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<StakeBusiness> getWeekStakeBusiness(String curDate) {
        Cursor cursor = null;
        try {
            List<StakeBusiness> stakeBusinessList = new ArrayList<StakeBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_STAKEBUSINESS + " WHERE "+ STAKEBUSINESSDATE +"=" + curDate;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    StakeBusiness stakeBusiness = new StakeBusiness();
                    stakeBusiness.setSTAKEBUSINESS_ID(cursor.getString(0));
                    stakeBusiness.setSTAKEBUSINESSDATE(cursor.getString(1));
                    stakeBusiness.setSTAKEBUSINESS(cursor.getString(2));
                    stakeBusiness.setSTAKEBUSINESSUPDATEDATE(cursor.getString(3));
                    stakeBusiness.setSTAKEBUSINESSSTATUS(cursor.getString(4));
                    stakeBusinessList.add(stakeBusiness);
                } while (cursor.moveToNext());
            }
            // return stakeBusiness list
            return stakeBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public String getSyncStakeBusiness(String lastSync, String updateDate) {
        Cursor cursor = null;
        try {
            List<StakeBusiness> stakeBusinessList = new ArrayList<StakeBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_STAKEBUSINESS + " WHERE "+ STAKEBUSINESSUPDATEDATE +">" + lastSync;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    StakeBusiness stakeBusiness = new StakeBusiness();
                    stakeBusiness.setSTAKEBUSINESS_ID(cursor.getString(0));
                    stakeBusiness.setSTAKEBUSINESSDATE(cursor.getString(1));
                    stakeBusiness.setSTAKEBUSINESS(cursor.getString(2));
                    stakeBusiness.setSTAKEBUSINESSUPDATEDATE(updateDate);
                    stakeBusiness.setSTAKEBUSINESSSTATUS(cursor.getString(4));
                    stakeBusinessList.add(stakeBusiness);
                } while (cursor.moveToNext());
            }
            return new Gson().toJson(stakeBusinessList);
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }


    public List<WardBusiness> getAllWardBusiness() {
        Cursor cursor = null;
        try {
            List<WardBusiness> wardBusinessList = new ArrayList<WardBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_WARDBUSINESS  + " WHERE " + WARDBUSINESSSTATUS + "='A' ORDER BY " + WARDBUSINESSDATE + " DESC";
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    WardBusiness wardBusiness = new WardBusiness();
                    wardBusiness.setWARDBUSINESS_ID(cursor.getString(0));
                    wardBusiness.setWARDBUSINESSDATE(cursor.getString(1));
                    wardBusiness.setWARDBUSINESSPURPOSE(cursor.getString(2));
                    wardBusiness.setWARDBUSINESSNAME(cursor.getString(3));
                    wardBusiness.setWARDBUSINESSCALLING(cursor.getString(4));
                    wardBusiness.setWARDBUSINESSUPDATEDATE(cursor.getString(5));
                    wardBusiness.setWARDBUSINESSSTATUS(cursor.getString(6));
                    wardBusinessList.add(wardBusiness);
                } while (cursor.moveToNext());
            }
            // return wardBusiness list
            return wardBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<WardBusiness> getSingleWardBusiness(String pos) {
        Cursor cursor = null;
        try {
            List<WardBusiness> wardBusinessList = new ArrayList<WardBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_WARDBUSINESS + " WHERE " + WARDBUSINESS_ID + "=" + pos;
            dbase=this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    WardBusiness wardBusiness = new WardBusiness();
                    wardBusiness.setWARDBUSINESS_ID(cursor.getString(0));
                    wardBusiness.setWARDBUSINESSDATE(cursor.getString(1));
                    wardBusiness.setWARDBUSINESSPURPOSE(cursor.getString(2));
                    wardBusiness.setWARDBUSINESSNAME(cursor.getString(3));
                    wardBusiness.setWARDBUSINESSCALLING(cursor.getString(4));
                    wardBusiness.setWARDBUSINESSUPDATEDATE(cursor.getString(5));
                    wardBusiness.setWARDBUSINESSSTATUS(cursor.getString(6));
                    wardBusinessList.add(wardBusiness);
                } while (cursor.moveToNext());
            }
            // return wardBusiness list
            return wardBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public List<WardBusiness> getWeekWardBusiness(String curDate) {
        Cursor cursor = null;
        try {
            List<WardBusiness> wardBusinessList = new ArrayList<WardBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_WARDBUSINESS + " WHERE " + WARDBUSINESSDATE + "=" + curDate;
            dbase = this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    WardBusiness wardBusiness = new WardBusiness();
                    wardBusiness.setWARDBUSINESS_ID(cursor.getString(0));
                    wardBusiness.setWARDBUSINESSDATE(cursor.getString(1));
                    wardBusiness.setWARDBUSINESSPURPOSE(cursor.getString(2));
                    wardBusiness.setWARDBUSINESSNAME(cursor.getString(3));
                    wardBusiness.setWARDBUSINESSCALLING(cursor.getString(4));
                    wardBusiness.setWARDBUSINESSUPDATEDATE(cursor.getString(5));
                    wardBusiness.setWARDBUSINESSSTATUS(cursor.getString(6));
                    wardBusinessList.add(wardBusiness);
                } while (cursor.moveToNext());
            }
            // return wardBusiness list
            return wardBusinessList;
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public String getSyncWardBusiness(String lastSync, String updateDate) {
        Cursor cursor = null;
        try {
            List<WardBusiness> wardBusinessList = new ArrayList<WardBusiness>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_WARDBUSINESS + " WHERE " + WARDBUSINESSUPDATEDATE + ">" + lastSync;
            dbase = this.getReadableDatabase();
            cursor = dbase.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    WardBusiness wardBusiness = new WardBusiness();
                    wardBusiness.setWARDBUSINESS_ID(cursor.getString(0));
                    wardBusiness.setWARDBUSINESSDATE(cursor.getString(1));
                    wardBusiness.setWARDBUSINESSPURPOSE(cursor.getString(2));
                    wardBusiness.setWARDBUSINESSNAME(cursor.getString(3));
                    wardBusiness.setWARDBUSINESSCALLING(cursor.getString(4));
                    wardBusiness.setWARDBUSINESSUPDATEDATE(updateDate);
                    wardBusiness.setWARDBUSINESSSTATUS(cursor.getString(6));
                    wardBusinessList.add(wardBusiness);
                } while (cursor.moveToNext());
            }
            return new Gson().toJson(wardBusinessList);
        } finally  {
            if (cursor != null){
                cursor.close();}
            closeDB();
        }
    }

    public int hymnsRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_HYMNS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public int agendaRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_AGENDA + " WHERE "+ AGENDASTATUS + "= 'A'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public int announcementsRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_ANNOUNCEMENTS + " WHERE "+ ANNOUNCEMENTSSTATUS + "= 'A'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public int acknowledgementsRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_ACKNOWLEDGEMENT + " WHERE "+ ACKNOWLEDGEMENTSTATUS + "= 'A'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public int stakeBusinessRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_STAKEBUSINESS + " WHERE "+ STAKEBUSINESSSTATUS + "= 'A'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        return row;
    }

    public int wardBusinessRowcount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_WARDBUSINESS + " WHERE "+ WARDBUSINESSSTATUS + "= 'A'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public int lastSyncRowCount()
    {
        String selectQuery = "SELECT  * FROM " + TABLE_LASTSYNC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int row = cursor.getCount();
        cursor.close();
        db.close();
        return row;
    }

    public void addAllHymns() {
        Hymns h1= new Hymns(1,"The Morning Breaks");
        this.addHymns(h1);
        Hymns h2= new Hymns(2,"The Spirit of God");
        this.addHymns(h2);
        Hymns h3= new Hymns(3,"Now Let Us Rejoice");
        this.addHymns(h3);
        Hymns h4= new Hymns(4,"Truth Eternal");
        this.addHymns(h4);
        Hymns h5= new Hymns(5,"High on the Mountain Top");
        this.addHymns(h5);
        Hymns h6= new Hymns(6,"Redeemer of Israel");
        this.addHymns(h6);
        Hymns h7= new Hymns(7,"Israel, Israel, God Is Calling");
        this.addHymns(h7);
        Hymns h8= new Hymns(8,"Awake and Arise");
        this.addHymns(h8);
        Hymns h9= new Hymns(9,"Come, Rejoice");
        this.addHymns(h9);
        Hymns h10= new Hymns(10,"Come, Sing to the Lord");
        this.addHymns(h10);
        Hymns h11= new Hymns(11,"What Was Witnessed in the Heavens?");
        this.addHymns(h11);
        Hymns h12= new Hymns(12,"'Twas Witnessed in the Morning Sky*");
        this.addHymns(h12);
        Hymns h13= new Hymns(13,"An Angel from on High");
        this.addHymns(h13);
        Hymns h14= new Hymns(14,"Sweet Is the Peace the Gospel Brings");
        this.addHymns(h14);
        Hymns h15= new Hymns(15,"I Saw a Mighty Angel Fly*");
        this.addHymns(h15);
        Hymns h16= new Hymns(16,"What Glorious Scenes Mine Eyes Behold");
        this.addHymns(h16);
        Hymns h17= new Hymns(17,"Awake, Ye Saints of God, Awake!");
        this.addHymns(h17);
        Hymns h18= new Hymns(18,"The Voice of God Again Is Heard");
        this.addHymns(h18);
        Hymns h19= new Hymns(19,"We Thank Thee, O God, for a Prophet");
        this.addHymns(h19);
        Hymns h20= new Hymns(20,"God of Power, God of Right");
        this.addHymns(h20);
        Hymns h21= new Hymns(21,"Come, Listen to a Prophet's Voice");
        this.addHymns(h21);
        Hymns h22= new Hymns(22,"We Listen to a Prophet's Voice");
        this.addHymns(h22);
        Hymns h23= new Hymns(23,"We Ever Pray for Thee");
        this.addHymns(h23);
        Hymns h24= new Hymns(24,"God Bless Our Prophet Dear");
        this.addHymns(h24);
        Hymns h25= new Hymns(25,"Now We'll Sing with One Accord");
        this.addHymns(h25);
        Hymns h26= new Hymns(26,"Joseph Smith's First Prayer");
        this.addHymns(h26);
        Hymns h27= new Hymns(27,"Praise to the Man");
        this.addHymns(h27);
        Hymns h28= new Hymns(28,"Saints, Behold How Great Jehovah");
        this.addHymns(h28);
        Hymns h29= new Hymns(29,"A Poor Wayfaring Man of Grief");
        this.addHymns(h29);
        Hymns h30= new Hymns(30,"Come, Come, Ye Saints");
        this.addHymns(h30);
        Hymns h31= new Hymns(31,"O God, Our Help in Ages Past");
        this.addHymns(h31);
        Hymns h32= new Hymns(32,"The Happy Day at Last Has Come");
        this.addHymns(h32);
        Hymns h33= new Hymns(33,"Our Mountain Home So Dear");
        this.addHymns(h33);
        Hymns h34= new Hymns(34,"O Ye Mountains High");
        this.addHymns(h34);
        Hymns h35= new Hymns(35,"For the Strength of the Hills");
        this.addHymns(h35);
        Hymns h36= new Hymns(36,"They, the Builders of the Nation");
        this.addHymns(h36);
        Hymns h37= new Hymns(37,"The Wintry Day, Descending to Its Close");
        this.addHymns(h37);
        Hymns h38= new Hymns(38,"Come, All Ye Saints of Zion");
        this.addHymns(h38);
        Hymns h39= new Hymns(39,"O Saints of Zion");
        this.addHymns(h39);
        Hymns h40= new Hymns(40,"Arise, O Glorious Zion");
        this.addHymns(h40);
        Hymns h41= new Hymns(41,"Let Zion in Her Beauty Rise");
        this.addHymns(h41);
        Hymns h42= new Hymns(42,"Hail to the Brightness of Zion's Glad Morning!");
        this.addHymns(h42);
        Hymns h43= new Hymns(43,"Zion Stands with Hills Surrounded");
        this.addHymns(h43);
        Hymns h44= new Hymns(44,"Beautiful Zion, Built Above");
        this.addHymns(h44);
        Hymns h45= new Hymns(45,"Lead Me into Life Eternal");
        this.addHymns(h45);
        Hymns h46= new Hymns(46,"Glorious Things of Thee Are Spoken");
        this.addHymns(h46);
        Hymns h47= new Hymns(47,"We Will Sing of Zion");
        this.addHymns(h47);
        Hymns h48= new Hymns(48,"Glorious Things Are Sung of Zion");
        this.addHymns(h48);
        Hymns h49= new Hymns(49,"Adam-ondi-Ahman");
        this.addHymns(h49);
        Hymns h50= new Hymns(50,"Come, Thou Glorious Day of Promise");
        this.addHymns(h50);
        Hymns h51= new Hymns(51,"Sons of Michael, He Approaches");
        this.addHymns(h51);
        Hymns h52= new Hymns(52,"The Day Dawn Is Breaking");
        this.addHymns(h52);
        Hymns h53= new Hymns(53,"Let Earth's Inhabitants Rejoice");
        this.addHymns(h53);
        Hymns h54= new Hymns(54,"Behold, the Mountain of the Lord*");
        this.addHymns(h54);
        Hymns h55= new Hymns(55,"Lo, the Mighty God Appearing!");
        this.addHymns(h55);
        Hymns h56= new Hymns(56,"Softly Beams the Sacred Dawning");
        this.addHymns(h56);
        Hymns h57= new Hymns(57,"We're Not Ashamed to Own Our Lord");
        this.addHymns(h57);
        Hymns h58= new Hymns(58,"Come, Ye Children of the Lord");
        this.addHymns(h58);
        Hymns h59= new Hymns(59,"Come, O Thou King of Kings");
        this.addHymns(h59);
        Hymns h60= new Hymns(60,"Battle Hymn of the Republic");
        this.addHymns(h60);
        Hymns h61= new Hymns(61,"Raise Your Voices to the Lord");
        this.addHymns(h61);
        Hymns h62= new Hymns(62,"All Creatures of Our God and King*");
        this.addHymns(h62);
        Hymns h63= new Hymns(63,"Great King of Heaven");
        this.addHymns(h63);
        Hymns h64= new Hymns(64,"On This Day of Joy and Gladness");
        this.addHymns(h64);
        Hymns h65= new Hymns(65,"Come, All Ye Saints Who Dwell on Earth");
        this.addHymns(h65);
        Hymns h66= new Hymns(66,"Rejoice, the Lord Is King!");
        this.addHymns(h66);
        Hymns h67= new Hymns(67,"Glory to God on High");
        this.addHymns(h67);
        Hymns h68= new Hymns(68,"A Mighty Fortress Is Our God");
        this.addHymns(h68);
        Hymns h69= new Hymns(69,"All Glory, Laud, and Honor");
        this.addHymns(h69);
        Hymns h70= new Hymns(70,"Sing Praise to Him");
        this.addHymns(h70);
        Hymns h71= new Hymns(71,"With Songs of Praise");
        this.addHymns(h71);
        Hymns h72= new Hymns(72,"Praise to the Lord, the Almighty");
        this.addHymns(h72);
        Hymns h73= new Hymns(73,"Praise the Lord with Heart and Voice");
        this.addHymns(h73);
        Hymns h74= new Hymns(74,"Praise Ye the Lord");
        this.addHymns(h74);
        Hymns h75= new Hymns(75,"In Hymns of Praise");
        this.addHymns(h75);
        Hymns h76= new Hymns(76,"God of Our Fathers, We Come unto Thee");
        this.addHymns(h76);
        Hymns h77= new Hymns(77,"Great Is the Lord");
        this.addHymns(h77);
        Hymns h78= new Hymns(78,"God of Our Fathers, Whose Almighty Hand");
        this.addHymns(h78);
        Hymns h79= new Hymns(79,"With All the Power of Heart and Tongue");
        this.addHymns(h79);
        Hymns h80= new Hymns(80,"God of Our Fathers, Known of Old");
        this.addHymns(h80);
        Hymns h81= new Hymns(81,"Press Forward, Saints");
        this.addHymns(h81);
        Hymns h82= new Hymns(82,"For All the Saints*");
        this.addHymns(h82);
        Hymns h83= new Hymns(83,"Guide Us, O Thou Great Jehovah");
        this.addHymns(h83);
        Hymns h84= new Hymns(84,"Faith of Our Fathers");
        this.addHymns(h84);
        Hymns h85= new Hymns(85,"How Firm a Foundation");
        this.addHymns(h85);
        Hymns h86= new Hymns(86,"How Great Thou Art*");
        this.addHymns(h86);
        Hymns h87= new Hymns(87,"God Is Love");
        this.addHymns(h87);
        Hymns h88= new Hymns(88,"Great God, Attend While Zion Sings");
        this.addHymns(h88);
        Hymns h89= new Hymns(89,"The Lord Is My Light");
        this.addHymns(h89);
        Hymns h90= new Hymns(90,"From All That Dwell below the Skies");
        this.addHymns(h90);
        Hymns h91= new Hymns(91,"Father, Thy Children to Thee Now Raise");
        this.addHymns(h91);
        Hymns h92= new Hymns(92,"For the Beauty of the Earth");
        this.addHymns(h92);
        Hymns h93= new Hymns(93,"Prayer of Thanksgiving");
        this.addHymns(h93);
        Hymns h94= new Hymns(94,"Come, Ye Thankful People");
        this.addHymns(h94);
        Hymns h95= new Hymns(95,"Now Thank We All Our God");
        this.addHymns(h95);
        Hymns h96= new Hymns(96,"Dearest Children, God Is Near You");
        this.addHymns(h96);
        Hymns h97= new Hymns(97,"Lead, Kindly Light");
        this.addHymns(h97);
        Hymns h98= new Hymns(98,"I Need Thee Every Hour");
        this.addHymns(h98);
        Hymns h99= new Hymns(99,"Nearer, Dear Savior, to Thee");
        this.addHymns(h99);
        Hymns h100= new Hymns(100,"Nearer, My God, to Thee");
        this.addHymns(h100);
        Hymns h101= new Hymns(101,"Guide Me to Thee");
        this.addHymns(h101);
        Hymns h102= new Hymns(102,"Jesus, Lover of My Soul");
        this.addHymns(h102);
        Hymns h103= new Hymns(103,"Precious Savior, Dear Redeemer");
        this.addHymns(h103);
        Hymns h104= new Hymns(104,"Jesus, Savior, Pilot Me");
        this.addHymns(h104);
        Hymns h105= new Hymns(105,"Master, the Tempest Is Raging");
        this.addHymns(h105);
        Hymns h106= new Hymns(106,"God Speed the Right");
        this.addHymns(h106);
        Hymns h107= new Hymns(107,"Lord, Accept Our True Devotion");
        this.addHymns(h107);
        Hymns h108= new Hymns(108,"The Lord Is My Shepherd");
        this.addHymns(h108);
        Hymns h109= new Hymns(109,"The Lord My Pasture Will Prepare");
        this.addHymns(h109);
        Hymns h110= new Hymns(110,"Cast Thy Burden upon the Lord");
        this.addHymns(h110);
        Hymns h111= new Hymns(111,"Rock of Ages");
        this.addHymns(h111);
        Hymns h112= new Hymns(112,"Savior, Redeemer of My Soul");
        this.addHymns(h112);
        Hymns h113= new Hymns(113,"Our Savior's Love");
        this.addHymns(h113);
        Hymns h114= new Hymns(114,"Come unto Him");
        this.addHymns(h114);
        Hymns h115= new Hymns(115,"Come, Ye Disconsolate");
        this.addHymns(h115);
        Hymns h116= new Hymns(116,"Come, Follow Me");
        this.addHymns(h116);
        Hymns h117= new Hymns(117,"Come unto Jesus");
        this.addHymns(h117);
        Hymns h118= new Hymns(118,"Ye Simple Souls Who Stray");
        this.addHymns(h118);
        Hymns h119= new Hymns(119,"Come, We That Love the Lord");
        this.addHymns(h119);
        Hymns h120= new Hymns(120,"Lean on My Ample Arm");
        this.addHymns(h120);
        Hymns h121= new Hymns(121,"I'm a Pilgrim, I'm a Stranger");
        this.addHymns(h121);
        Hymns h122= new Hymns(122,"Though Deepening Trials");
        this.addHymns(h122);
        Hymns h123= new Hymns(123,"Oh, May My Soul Commune with Thee");
        this.addHymns(h123);
        Hymns h124= new Hymns(124,"Be Still, My Soul*");
        this.addHymns(h124);
        Hymns h125= new Hymns(125,"How Gentle God's Commands");
        this.addHymns(h125);
        Hymns h126= new Hymns(126,"How Long, O Lord Most Holy and True");
        this.addHymns(h126);
        Hymns h127= new Hymns(127,"Does the Journey Seem Long?");
        this.addHymns(h127);
        Hymns h128= new Hymns(128,"When Faith Endures");
        this.addHymns(h128);
        Hymns h129= new Hymns(129,"Where Can I Turn for Peace?");
        this.addHymns(h129);
        Hymns h130= new Hymns(130,"Be Thou Humble");
        this.addHymns(h130);
        Hymns h131= new Hymns(131,"More Holiness Give Me");
        this.addHymns(h131);
        Hymns h132= new Hymns(132,"God Is in His Holy Temple");
        this.addHymns(h132);
        Hymns h133= new Hymns(133,"Father in Heaven");
        this.addHymns(h133);
        Hymns h134= new Hymns(134,"I Believe in Christ");
        this.addHymns(h134);
        Hymns h135= new Hymns(135,"My Redeemer Lives");
        this.addHymns(h135);
        Hymns h136= new Hymns(136,"I Know That My Redeemer Lives");
        this.addHymns(h136);
        Hymns h137= new Hymns(137,"Testimony");
        this.addHymns(h137);
        Hymns h138= new Hymns(138,"Bless Our Fast, We Pray");
        this.addHymns(h138);
        Hymns h139= new Hymns(139,"In Fasting We Approach Thee");
        this.addHymns(h139);
        Hymns h140= new Hymns(140,"Did You Think to Pray?");
        this.addHymns(h140);
        Hymns h141= new Hymns(141,"Jesus, the Very Thought of Thee");
        this.addHymns(h141);
        Hymns h142= new Hymns(142,"Sweet Hour of Prayer");
        this.addHymns(h142);
        Hymns h143= new Hymns(143,"Let the Holy Spirit Guide*");
        this.addHymns(h143);
        Hymns h144= new Hymns(144,"Secret Prayer");
        this.addHymns(h144);
        Hymns h145= new Hymns(145,"Prayer Is the Soul's Sincere Desire");
        this.addHymns(h145);
        Hymns h146= new Hymns(146,"Gently Raise the Sacred Strain");
        this.addHymns(h146);
        Hymns h147= new Hymns(147,"Sweet Is the Work");
        this.addHymns(h147);
        Hymns h148= new Hymns(148,"Sabbath Day");
        this.addHymns(h148);
        Hymns h149= new Hymns(149,"As the Dew from Heaven Distilling");
        this.addHymns(h149);
        Hymns h150= new Hymns(150,"O Thou Kind and Gracious Father");
        this.addHymns(h150);
        Hymns h151= new Hymns(151,"We Meet, Dear Lord");
        this.addHymns(h151);
        Hymns h152= new Hymns(152,"God Be with You Till We Meet Again");
        this.addHymns(h152);
        Hymns h153= new Hymns(153,"Lord, We Ask Thee Ere We Part");
        this.addHymns(h153);
        Hymns h154= new Hymns(154,"Father, This Hour Has Been One of Joy");
        this.addHymns(h154);
        Hymns h155= new Hymns(155,"We Have Partaken of Thy Love");
        this.addHymns(h155);
        Hymns h156= new Hymns(156,"Sing We Now at Parting");
        this.addHymns(h156);
        Hymns h157= new Hymns(157,"Thy Spirit, Lord, Has Stirred Our Souls");
        this.addHymns(h157);
        Hymns h158= new Hymns(158,"Before Thee, Lord, I Bow My Head");
        this.addHymns(h158);
        Hymns h159= new Hymns(159,"Now the Day Is Over");
        this.addHymns(h159);
        Hymns h160= new Hymns(160,"Softly Now the Light of Day");
        this.addHymns(h160);
        Hymns h161= new Hymns(161,"The Lord Be with Us");
        this.addHymns(h161);
        Hymns h162= new Hymns(162,"Lord, We Come before Thee Now");
        this.addHymns(h162);
        Hymns h163= new Hymns(163,"Lord, Dismiss Us with Thy Blessing");
        this.addHymns(h163);
        Hymns h164= new Hymns(164,"Great God, to Thee My Evening Song");
        this.addHymns(h164);
        Hymns h165= new Hymns(165,"Abide with Me; 'Tis Eventide");
        this.addHymns(h165);
        Hymns h166= new Hymns(166,"Abide with Me!");
        this.addHymns(h166);
        Hymns h167= new Hymns(167,"Come, Let Us Sing an Evening Hymn");
        this.addHymns(h167);
        Hymns h168= new Hymns(168,"As the Shadows Fall");
        this.addHymns(h168);
        Hymns h169= new Hymns(169,"As Now We Take the Sacrament");
        this.addHymns(h169);
        Hymns h170= new Hymns(170,"God, Our Father, Hear Us Pray");
        this.addHymns(h170);
        Hymns h171= new Hymns(171,"With Humble Heart");
        this.addHymns(h171);
        Hymns h172= new Hymns(172,"In Humility, Our Savior");
        this.addHymns(h172);
        Hymns h173= new Hymns(173,"While of These Emblems We Partake");
        this.addHymns(h173);
        Hymns h174= new Hymns(174,"While of These Emblems We Partake");
        this.addHymns(h174);
        Hymns h175= new Hymns(175,"O God, the Eternal Father");
        this.addHymns(h175);
        Hymns h176= new Hymns(176,"'Tis Sweet to Sing the Matchless Love");
        this.addHymns(h176);
        Hymns h177= new Hymns(177,"'Tis Sweet To Sing the Matchless Love");
        this.addHymns(h177);
        Hymns h178= new Hymns(178,"O Lord of Hosts");
        this.addHymns(h178);
        Hymns h179= new Hymns(179,"Again, Our Dear Redeeming Lord");
        this.addHymns(h179);
        Hymns h180= new Hymns(180,"Father in Heaven, We Do Believe");
        this.addHymns(h180);
        Hymns h181= new Hymns(181,"Jesus of Nazareth, Savior and King");
        this.addHymns(h181);
        Hymns h182= new Hymns(182,"We'll Sing All Hail to Jesus' Name");
        this.addHymns(h182);
        Hymns h183= new Hymns(183,"In Remembrance of Thy Suffering");
        this.addHymns(h183);
        Hymns h184= new Hymns(184,"Upon the Cross of Calvary");
        this.addHymns(h184);
        Hymns h185= new Hymns(185,"Reverently and Meekly Now");
        this.addHymns(h185);
        Hymns h186= new Hymns(186,"Again We Meet around the Board");
        this.addHymns(h186);
        Hymns h187= new Hymns(187,"God Loved Us, So He Sent His Son");
        this.addHymns(h187);
        Hymns h188= new Hymns(188,"Thy Will, O Lord, Be Done");
        this.addHymns(h188);
        Hymns h189= new Hymns(189,"O Thou, Before the World Began");
        this.addHymns(h189);
        Hymns h190= new Hymns(190,"In Memory of the Crucified");
        this.addHymns(h190);
        Hymns h191= new Hymns(191,"Behold the Great Redeemer Die");
        this.addHymns(h191);
        Hymns h192= new Hymns(192,"He Died! The Great Redeemer Died");
        this.addHymns(h192);
        Hymns h193= new Hymns(193,"I Stand All Amazed");
        this.addHymns(h193);
        Hymns h194= new Hymns(194,"There Is a Green Hill Far Away");
        this.addHymns(h194);
        Hymns h195= new Hymns(195,"How Great the Wisdom and the Love");
        this.addHymns(h195);
        Hymns h196= new Hymns(196,"Jesus, Once of Humble Birth");
        this.addHymns(h196);
        Hymns h197= new Hymns(197,"O Savior, Thou Who Wearest a Crown");
        this.addHymns(h197);
        Hymns h198= new Hymns(198,"That Easter Morn");
        this.addHymns(h198);
        Hymns h199= new Hymns(199,"He Is Risen!");
        this.addHymns(h199);
        Hymns h200= new Hymns(200,"Christ the Lord Is Risen Today");
        this.addHymns(h200);
        Hymns h201= new Hymns(201,"Joy to the World");
        this.addHymns(h201);
        Hymns h202= new Hymns(202,"Oh, Come, All Ye Faithful");
        this.addHymns(h202);
        Hymns h203= new Hymns(203,"Angels We Have Heard on High");
        this.addHymns(h203);
        Hymns h204= new Hymns(204,"Silent Night");
        this.addHymns(h204);
        Hymns h205= new Hymns(205,"Once in Royal David's City");
        this.addHymns(h205);
        Hymns h206= new Hymns(206,"Away in a Manger");
        this.addHymns(h206);
        Hymns h207= new Hymns(207,"It Came upon the Midnight Clear");
        this.addHymns(h207);
        Hymns h208= new Hymns(208,"O Little Town of Bethlehem");
        this.addHymns(h208);
        Hymns h209= new Hymns(209,"Hark! The Herald Angels Sing");
        this.addHymns(h209);
        Hymns h210= new Hymns(210,"With Wondering Awe");
        this.addHymns(h210);
        Hymns h211= new Hymns(211,"While Shepherds Watched Their Flocks");
        this.addHymns(h211);
        Hymns h212= new Hymns(212,"Far, Far Away on Judea's Plains");
        this.addHymns(h212);
        Hymns h213= new Hymns(213,"The First Noel");
        this.addHymns(h213);
        Hymns h214= new Hymns(214,"I Heard the Bells on Christmas Day");
        this.addHymns(h214);
        Hymns h215= new Hymns(215,"Ring Out, Wild Bells");
        this.addHymns(h215);
        Hymns h216= new Hymns(216,"We Are Sowing");
        this.addHymns(h216);
        Hymns h217= new Hymns(217,"Come, Let Us Anew");
        this.addHymns(h217);
        Hymns h218= new Hymns(218,"We Give Thee But Thine Own");
        this.addHymns(h218);
        Hymns h219= new Hymns(219,"Because I Have Been Given Much*");
        this.addHymns(h219);
        Hymns h220= new Hymns(220,"Lord, I Would Follow Thee");
        this.addHymns(h220);
        Hymns h221= new Hymns(221,"Dear to the Heart of the Shepherd");
        this.addHymns(h221);
        Hymns h222= new Hymns(222,"Hear Thou Our Hymn, O Lord");
        this.addHymns(h222);
        Hymns h223= new Hymns(223,"Have I Done Any Good?");
        this.addHymns(h223);
        Hymns h224= new Hymns(224,"I Have Work Enough to Do");
        this.addHymns(h224);
        Hymns h225= new Hymns(225,"We Are Marching On to Glory");
        this.addHymns(h225);
        Hymns h226= new Hymns(226,"Improve the Shining Moments");
        this.addHymns(h226);
        Hymns h227= new Hymns(227,"There Is Sunshine in My Soul Today");
        this.addHymns(h227);
        Hymns h228= new Hymns(228,"You Can Make the Pathway Bright");
        this.addHymns(h228);
        Hymns h229= new Hymns(229,"Today, While the Sun Shines");
        this.addHymns(h229);
        Hymns h230= new Hymns(230,"Scatter Sunshine");
        this.addHymns(h230);
        Hymns h231= new Hymns(231,"Father, Cheer Our Souls Tonight");
        this.addHymns(h231);
        Hymns h232= new Hymns(232,"Let Us Oft Speak Kind Words");
        this.addHymns(h232);
        Hymns h233= new Hymns(233,"Nay, Speak No Ill");
        this.addHymns(h233);
        Hymns h234= new Hymns(234,"Jesus, Mighty King in Zion");
        this.addHymns(h234);
        Hymns h235= new Hymns(235,"Should You Feel Inclined to Censure");
        this.addHymns(h235);
        Hymns h236= new Hymns(236,"Lord, Accept into Thy Kingdom");
        this.addHymns(h236);
        Hymns h237= new Hymns(237,"Do What Is Right");
        this.addHymns(h237);
        Hymns h238= new Hymns(238,"Behold Thy Sons and Daughters, Lord");
        this.addHymns(h238);
        Hymns h239= new Hymns(239,"Choose the Right");
        this.addHymns(h239);
        Hymns h240= new Hymns(240,"Know This, That Every Soul Is Free");
        this.addHymns(h240);
        Hymns h241= new Hymns(241,"Count Your Blessings");
        this.addHymns(h241);
        Hymns h242= new Hymns(242,"Praise God, from Whom All Blessings Flow");
        this.addHymns(h242);
        Hymns h243= new Hymns(243,"Let Us All Press On");
        this.addHymns(h243);
        Hymns h244= new Hymns(244,"Come Along, Come Along");
        this.addHymns(h244);
        Hymns h245= new Hymns(245,"This House We Dedicate to Thee");
        this.addHymns(h245);
        Hymns h246= new Hymns(246,"Onward, Christian Soldiers");
        this.addHymns(h246);
        Hymns h247= new Hymns(247,"We Love Thy House, O God");
        this.addHymns(h247);
        Hymns h248= new Hymns(248,"Up, Awake, Ye Defenders of Zion");
        this.addHymns(h248);
        Hymns h249= new Hymns(249,"Called to Serve");
        this.addHymns(h249);
        Hymns h250= new Hymns(250,"We Are All Enlisted");
        this.addHymns(h250);
        Hymns h251= new Hymns(251,"Behold! A Royal Army");
        this.addHymns(h251);
        Hymns h252= new Hymns(252,"Put Your Shoulder to the Wheel");
        this.addHymns(h252);
        Hymns h253= new Hymns(253,"Like Ten Thousand Legions Marching");
        this.addHymns(h253);
        Hymns h254= new Hymns(254,"True to the Faith");
        this.addHymns(h254);
        Hymns h255= new Hymns(255,"Carry On");
        this.addHymns(h255);
        Hymns h256= new Hymns(256,"As Zion's Youth in Latter Days");
        this.addHymns(h256);
        Hymns h257= new Hymns(257,"Rejoice! A Glorious Sound Is Heard");
        this.addHymns(h257);
        Hymns h258= new Hymns(258,"O Thou Rock of Our Salvation");
        this.addHymns(h258);
        Hymns h259= new Hymns(259,"Hope of Israel");
        this.addHymns(h259);
        Hymns h260= new Hymns(260,"Who's on the Lord's Side?");
        this.addHymns(h260);
        Hymns h261= new Hymns(261,"Thy Servants Are Prepared");
        this.addHymns(h261);
        Hymns h262= new Hymns(262,"Go, Ye Messengers of Glory");
        this.addHymns(h262);
        Hymns h263= new Hymns(263,"Go Forth with Faith");
        this.addHymns(h263);
        Hymns h264= new Hymns(264,"Hark, All Ye Nations!");
        this.addHymns(h264);
        Hymns h265= new Hymns(265,"Arise, O God, and Shine");
        this.addHymns(h265);
        Hymns h266= new Hymns(266,"The Time Is Far Spent");
        this.addHymns(h266);
        Hymns h267= new Hymns(267,"How Wondrous and Great");
        this.addHymns(h267);
        Hymns h268= new Hymns(268,"Come, All Whose Souls Are Lighted");
        this.addHymns(h268);
        Hymns h269= new Hymns(269,"Jehovah, Lord of Heaven and Earth");
        this.addHymns(h269);
        Hymns h270= new Hymns(270,"I'll Go Where You Want Me to Go");
        this.addHymns(h270);
        Hymns h271= new Hymns(271,"Oh, Holy Words of Truth and Love");
        this.addHymns(h271);
        Hymns h272= new Hymns(272,"Oh Say, What Is Truth?");
        this.addHymns(h272);
        Hymns h273= new Hymns(273,"Truth Reflects upon Our Senses");
        this.addHymns(h273);
        Hymns h274= new Hymns(274,"The Iron Rod");
        this.addHymns(h274);
        Hymns h275= new Hymns(275,"Men Are That They Might Have Joy");
        this.addHymns(h275);
        Hymns h276= new Hymns(276,"Come Away to the Sunday School");
        this.addHymns(h276);
        Hymns h277= new Hymns(277,"As I Search the Holy Scriptures");
        this.addHymns(h277);
        Hymns h278= new Hymns(278,"Thanks for the Sabbath School");
        this.addHymns(h278);
        Hymns h279= new Hymns(279,"Thy Holy Word");
        this.addHymns(h279);
        Hymns h280= new Hymns(280,"Welcome, Welcome, Sabbath Morning");
        this.addHymns(h280);
        Hymns h281= new Hymns(281,"Help Me Teach with Inspiration");
        this.addHymns(h281);
        Hymns h282= new Hymns(282,"We Meet Again in Sabbath School");
        this.addHymns(h282);
        Hymns h283= new Hymns(283,"The Glorious Gospel Light Has Shone");
        this.addHymns(h283);
        Hymns h284= new Hymns(284,"If You Could Hie to Kolob*");
        this.addHymns(h284);
        Hymns h285= new Hymns(285,"God Moves in a Mysterious Way");
        this.addHymns(h285);
        Hymns h286= new Hymns(286,"Oh, What Songs of the Heart");
        this.addHymns(h286);
        Hymns h287= new Hymns(287,"Rise, Ye Saints, and Temples Enter");
        this.addHymns(h287);
        Hymns h288= new Hymns(288,"How Beautiful Thy Temples, Lord");
        this.addHymns(h288);
        Hymns h289= new Hymns(289,"Holy Temples on Mount Zion");
        this.addHymns(h289);
        Hymns h290= new Hymns(290,"Rejoice, Ye Saints of Latter Days");
        this.addHymns(h290);
        Hymns h291= new Hymns(291,"Turn Your Hearts");
        this.addHymns(h291);
        Hymns h292= new Hymns(292,"O My Father");
        this.addHymns(h292);
        Hymns h293= new Hymns(293,"Each Life That Touches Ours for Good");
        this.addHymns(h293);
        Hymns h294= new Hymns(294,"Love at Home");
        this.addHymns(h294);
        Hymns h295= new Hymns(295,"O Love That Glorifies the Son");
        this.addHymns(h295);
        Hymns h296= new Hymns(296,"Our Father, by Whose Name");
        this.addHymns(h296);
        Hymns h297= new Hymns(297,"From Homes of Saints Glad Songs Arise");
        this.addHymns(h297);
        Hymns h298= new Hymns(298,"Home Can Be a Heaven on Earth");
        this.addHymns(h298);
        Hymns h299= new Hymns(299,"Children of Our Heavenly Father*");
        this.addHymns(h299);
        Hymns h300= new Hymns(300,"Families Can Be Together Forever");
        this.addHymns(h300);
        Hymns h301= new Hymns(301,"I Am a Child of God");
        this.addHymns(h301);
        Hymns h302= new Hymns(302,"I Know My Father Lives");
        this.addHymns(h302);
        Hymns h303= new Hymns(303,"Keep the Commandments");
        this.addHymns(h303);
        Hymns h304= new Hymns(304,"Teach Me to Walk in the Light");
        this.addHymns(h304);
        Hymns h305= new Hymns(305,"The Light Divine");
        this.addHymns(h305);
        Hymns h306= new Hymns(306,"God's Daily Care");
        this.addHymns(h306);
        Hymns h307= new Hymns(307,"In Our Lovely Deseret");
        this.addHymns(h307);
        Hymns h308= new Hymns(308,"Love One Another");
        this.addHymns(h308);
        Hymns h309= new Hymns(309,"As Sisters in Zion (Women)");
        this.addHymns(h309);
        Hymns h310= new Hymns(310,"A Key Was Turned in Latter Days (Women)");
        this.addHymns(h310);
        Hymns h311= new Hymns(311,"We Meet Again as Sisters (Women)");
        this.addHymns(h311);
        Hymns h312= new Hymns(312,"We Ever Pray for Thee (Women)");
        this.addHymns(h312);
        Hymns h313= new Hymns(313,"God Is Love (Women)");
        this.addHymns(h313);
        Hymns h314= new Hymns(314,"How Gentle God's Commands (Women)");
        this.addHymns(h314);
        Hymns h315= new Hymns(315,"Jesus, the Very Thought of Thee (Women)");
        this.addHymns(h315);
        Hymns h316= new Hymns(316,"The Lord Is My Shepherd (Women)");
        this.addHymns(h316);
        Hymns h317= new Hymns(317,"Sweet Is the Work (Women)");
        this.addHymns(h317);
        Hymns h318= new Hymns(318,"Love at Home (Women)");
        this.addHymns(h318);
        Hymns h319= new Hymns(319,"Ye Elders of Israel (Men)");
        this.addHymns(h319);
        Hymns h320= new Hymns(320,"The Priesthood of Our Lord (Men)");
        this.addHymns(h320);
        Hymns h321= new Hymns(321,"Ye Who Are Called to Labor (Men)");
        this.addHymns(h321);
        Hymns h322= new Hymns(322,"Come, All Ye Sons of God (Men)");
        this.addHymns(h322);
        Hymns h323= new Hymns(323,"Rise Up, O Men of God (Men's Choir)");
        this.addHymns(h323);
        Hymns h324= new Hymns(324,"Rise Up, O Men of God (Men)");
        this.addHymns(h324);
        Hymns h325= new Hymns(325,"See the Mighty Priesthood Gathered (Men's Choir)");
        this.addHymns(h325);
        Hymns h326= new Hymns(326,"Come, Come, Ye Saints (Men's Choir)");
        this.addHymns(h326);
        Hymns h327= new Hymns(327,"Go, Ye Messengers of Heaven (Men's Choir)");
        this.addHymns(h327);
        Hymns h328= new Hymns(328,"An Angel from on High");
        this.addHymns(h328);
        Hymns h329= new Hymns(329,"Thy Servants Are Prepared (Men's Choir)");
        this.addHymns(h329);
        Hymns h330= new Hymns(330,"See, the Mighty Angel Flying (Men's Choir)");
        this.addHymns(h330);
        Hymns h331= new Hymns(331,"Oh Say, What Is Truth? (Men's Choir)");
        this.addHymns(h331);
        Hymns h332= new Hymns(332,"Come, O Thou King of Kings (Men's Choir)");
        this.addHymns(h332);
        Hymns h333= new Hymns(333,"High on the Mountain Top (Men's Choir)");
        this.addHymns(h333);
        Hymns h334= new Hymns(334,"I Need Thee Every Hour (Men's Choir)");
        this.addHymns(h334);
        Hymns h335= new Hymns(335,"Brightly Beams Our Father's Mercy (Men's Choir)");
        this.addHymns(h335);
        Hymns h336= new Hymns(336,"School Thy Feelings (Men's Choir)");
        this.addHymns(h336);
        Hymns h337= new Hymns(337,"O Home Beloved (Men's Choir)");
        this.addHymns(h337);
        Hymns h338= new Hymns(338,"America the Beautiful");
        this.addHymns(h338);
        Hymns h339= new Hymns(339,"My Country, 'Tis of Thee");
        this.addHymns(h339);
        Hymns h340= new Hymns(340,"The Star-Spangled Banner");
        this.addHymns(h340);
        Hymns h341= new Hymns(341,"God Save the King");
        this.addHymns(h341);
    }

}
