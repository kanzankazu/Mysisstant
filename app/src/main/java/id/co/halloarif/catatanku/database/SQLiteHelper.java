package id.co.halloarif.catatanku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.support.util.SessionUtil;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Databases information
    public static final String DB_NM = "catatatan.db";
    public static final int DB_VER = 4;

    public static String TableAlarm = "tabAlarmData";
    private static final String query_delete_table_Alarm = "DROP TABLE IF EXISTS " + TableAlarm;
    public static String KEY_alarm_id = "alarm_id";
    public static String KEY_alarm_sub_id = "alarm_sub_id";
    public static String KEY_alarm_title = "alarm_title";
    public static String KEY_alarm_hour = "alarm_hour";
    public static String KEY_alarm_minute = "alarm_minute";
    public static String KEY_alarm_day = "alarm_day";
    public static String KEY_alarm_friend = "alarm_friend";
    public static String KEY_alarm_friend_no = "alarm_friend_no";
    public static String KEY_alarm_voice = "alarm_voice";
    public static String KEY_alarm_voice_uri = "alarm_voice_uri";
    public static String KEY_alarm_ringtone = "alarm_ringtone";
    public static String KEY_alarm_ringtone_uri = "alarm_ringtone_uri";
    public static String KEY_alarm_is_active = "alarm_is_active";
    private static final String query_add_table_Alarm = "CREATE TABLE IF NOT EXISTS " + TableAlarm + "("
            + KEY_alarm_id + " TEXT PRIMARY KEY , "
            + KEY_alarm_sub_id + " TEXT, "
            + KEY_alarm_title + " TEXT, "
            + KEY_alarm_hour + " INTEGER, "
            + KEY_alarm_minute + " INTEGER, "
            + KEY_alarm_day + " TEXT, "
            + KEY_alarm_friend + " TEXT, "
            + KEY_alarm_friend_no + " TEXT, "
            + KEY_alarm_voice + " TEXT, "
            + KEY_alarm_voice_uri + " TEXT, "
            + KEY_alarm_ringtone + " TEXT, "
            + KEY_alarm_ringtone_uri + " TEXT, "
            + KEY_alarm_is_active + " INTEGER) ";

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

    /*alarm*/

    public void alarmSave(AlarmModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_alarm_id, model.getAlarm_id());
        contentValues.put(KEY_alarm_sub_id, model.getAlarm_sub_id());
        contentValues.put(KEY_alarm_title, model.getAlarm_title());
        contentValues.put(KEY_alarm_hour, model.getAlarm_hour());
        contentValues.put(KEY_alarm_minute, model.getAlarm_minute());
        contentValues.put(KEY_alarm_day, model.getAlarm_day());
        contentValues.put(KEY_alarm_friend, model.getAlarm_friend());
        contentValues.put(KEY_alarm_friend_no, model.getAlarm_friend_no());
        contentValues.put(KEY_alarm_voice, model.getAlarm_voice());
        contentValues.put(KEY_alarm_voice_uri, model.getAlarm_voice_uri());
        contentValues.put(KEY_alarm_ringtone, model.getAlarm_ringtone());
        contentValues.put(KEY_alarm_ringtone_uri, model.getAlarm_ringtone_uri());
        contentValues.put(KEY_alarm_is_active, 1);

        db.insert(TableAlarm, null, contentValues);
        db.close();
    }

    public void alarmUpdate(AlarmModel model, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_alarm_sub_id, model.getAlarm_sub_id());
        contentValues.put(KEY_alarm_title, model.getAlarm_title());
        contentValues.put(KEY_alarm_hour, model.getAlarm_hour());
        contentValues.put(KEY_alarm_minute, model.getAlarm_minute());
        contentValues.put(KEY_alarm_day, model.getAlarm_day());
        contentValues.put(KEY_alarm_friend, model.getAlarm_friend());
        contentValues.put(KEY_alarm_friend_no, model.getAlarm_friend_no());
        contentValues.put(KEY_alarm_voice, model.getAlarm_voice());
        contentValues.put(KEY_alarm_voice_uri, model.getAlarm_voice_uri());
        contentValues.put(KEY_alarm_ringtone, model.getAlarm_ringtone());
        contentValues.put(KEY_alarm_ringtone_uri, model.getAlarm_ringtone_uri());

        db.update(TableAlarm, contentValues, KEY_alarm_id + " = ? ", new String[]{id});
        db.close();
    }

    public ArrayList<AlarmModel> alarmsGet() {
        ArrayList<AlarmModel> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAlarm, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                AlarmModel model = new AlarmModel();
                model.setAlarm_id(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_id)));
                model.setAlarm_sub_id(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_sub_id)));
                model.setAlarm_title(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_title)));
                model.setAlarm_hour(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_hour)));
                model.setAlarm_minute(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_minute)));
                model.setAlarm_day(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_day)));
                model.setAlarm_friend(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_friend)));
                model.setAlarm_friend_no(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_friend_no)));
                model.setAlarm_voice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_voice)));
                model.setAlarm_voice_uri(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_voice_uri)));
                model.setAlarm_ringtone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_ringtone)));
                model.setAlarm_ringtone_uri(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_ringtone_uri)));
                model.setAlarm_is_Active(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_alarm_ringtone_uri)));
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        return modelList;
    }

    public ArrayList<String> alarmsGetAllSubId(@Nullable String id) {
        ArrayList<String> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAlarm, null, KEY_alarm_id + " = ? ", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                modelList.add(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_sub_id)));
            } while (cursor.moveToNext());
        }
        return modelList;
    }

    public String alarmsGetAllSubIdString(@Nullable String id) {
        StringBuilder stringBuilder = new StringBuilder();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAlarm, null, KEY_alarm_id + " = ? ", new String[]{id}, null, null, null);
        int columnCount = cursor.getColumnCount();
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                if (count != columnCount) {
                    stringBuilder.append(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_sub_id)) + ",");
                } else {
                    stringBuilder.append(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_sub_id)));
                }
                count++;
            } while (cursor.moveToNext());
        }
        return stringBuilder.toString();
    }

    public AlarmModel alarmGetOne(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableAlarm, null, KEY_alarm_id + " = ? ", new String[]{id}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AlarmModel model = new AlarmModel();
        model.setAlarm_id(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_id)));
        model.setAlarm_sub_id(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_sub_id)));
        model.setAlarm_title(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_title)));
        model.setAlarm_hour(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_hour)));
        model.setAlarm_minute(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_minute)));
        model.setAlarm_day(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_day)));
        model.setAlarm_friend(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_friend)));
        model.setAlarm_friend_no(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_friend_no)));
        model.setAlarm_voice(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_voice)));
        model.setAlarm_voice_uri(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_voice_uri)));
        model.setAlarm_ringtone(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_ringtone)));
        model.setAlarm_ringtone_uri(cursor.getString(cursor.getColumnIndexOrThrow(KEY_alarm_ringtone_uri)));
        // return model
        return model;
    }

    public void alarmDeleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TableAlarm);
        db.close();
    }

    public void alarmDelete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableAlarm, KEY_alarm_id + " = ?", new String[]{id});
        db.close();
    }

    public void alarmDeleteSubId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableAlarm, KEY_alarm_sub_id + " = ?", new String[]{id});
        db.close();
    }
    /*alarm*/
}