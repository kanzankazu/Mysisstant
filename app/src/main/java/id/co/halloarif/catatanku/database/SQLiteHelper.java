package id.co.halloarif.catatanku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.co.halloarif.catatanku.support.util.SessionUtil;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    public static final String DB_NM = "catatatan.db";
    public static final int DB_VER = 2;

    public static String TableAlarm = "tabAlarmData";
    private static final String query_delete_table_Alarm = "DROP TABLE IF EXISTS " + TableAlarm;
    public static String KEY_GlobalData_id = "id";
    public static String KEY_GlobalData_alarm_id = "alarm_id";
    public static String KEY_GlobalData_alarm_hour = "alarm_hour";
    public static String KEY_GlobalData_alarm_minute = "alarm_minute";
    public static String KEY_GlobalData_alarm_day = "alarm_day";
    public static String KEY_GlobalData_alarm_friend = "alarm_friend";
    public static String KEY_GlobalData_alarm_friend_no = "alarm_friend_no";
    public static String KEY_GlobalData_alarm_voice = "alarm_voice";
    public static String KEY_GlobalData_alarm_voice_uri = "alarm_voice_uri";
    public static String KEY_GlobalData_alarm_ringtone = "alarm_ringtone";
    public static String KEY_GlobalData_alarm_ringtone_uri = "alarm_ringtone_uri";
    private static final String query_add_table_Alarm = "CREATE TABLE IF NOT EXISTS " + TableAlarm + "("
            + KEY_GlobalData_id + " TEXT PRIMARY KEY , "
            + KEY_GlobalData_alarm_id + " TEXT, "
            + KEY_GlobalData_alarm_hour + " INTEGER, "
            + KEY_GlobalData_alarm_minute + " INTEGER, "
            + KEY_GlobalData_alarm_day + " TEXT, "
            + KEY_GlobalData_alarm_friend + " TEXT, "
            + KEY_GlobalData_alarm_friend_no + " TEXT, "
            + KEY_GlobalData_alarm_voice + " TEXT, "
            + KEY_GlobalData_alarm_voice_uri + " TEXT, "
            + KEY_GlobalData_alarm_ringtone + " TEXT, "
            + KEY_GlobalData_alarm_ringtone_uri + " TEXT) ";

    public SQLiteHelper(Context context) {
        super(context, DB_NM, null, DB_VER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_add_table_Alarm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_Alarm);

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        SessionUtil.removeAllSharedPreferences();

        db.execSQL(query_delete_table_Alarm);

        onCreate(db);

    }
}