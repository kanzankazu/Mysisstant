package id.co.halloarif.catatanku.database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

import id.co.halloarif.catatanku.support.util.SessionUtil;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    public static final String DB_NM = "catatatan.db";
    public static final int DB_VER = 1;

    public static String TableGlobalData = "tabGlobalData";
    private static final String query_delete_table_GlobalData = "DROP TABLE IF EXISTS " + TableGlobalData;
    public static String KEY_GlobalData_dbver = "dbver";
    public static String KEY_GlobalData_eventId = "event_Id";
    public static String KEY_GlobalData_accountId = "accountId";
    public static String KEY_GlobalData_version_data_user = "version_data_user";
    public static String KEY_GlobalData_version_data_event = "version_data_event";
    private static final String query_add_table_GlobalData = "CREATE TABLE IF NOT EXISTS " + TableGlobalData + "("
            + KEY_GlobalData_dbver + " TEXT PRIMARY KEY , "
            + KEY_GlobalData_eventId + " TEXT, "
            + KEY_GlobalData_accountId + " TEXT, "
            + KEY_GlobalData_version_data_user + " TEXT, "
            + KEY_GlobalData_version_data_event + " TEXT) ";


    public SQLiteHelper(Context context) {
        super(context, DB_NM, null, DB_VER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_add_table_GlobalData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_GlobalData);

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_GlobalData);

        onCreate(db);

    }
}