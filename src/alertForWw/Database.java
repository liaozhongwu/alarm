package alertForWw;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{
	public static final String DB_NAME = "alarm";
	public static final int DB_VERSION = 1;
	private static Database instance;
	private SQLiteDatabase database;
	private Cursor cursor;
	
	public static Database getInstance(AlarmApplication application){
		if(instance == null)
			instance = new Database(application);
		return instance;
	}
	private Database(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists TIMINGALARM ("
				 + "ID integer primary key,"
				 + "hour interger,"
				 + "minute integer,"
				 + "repeatable integer,"
				 + "repeatable_sunday integer,"
				 + "repeatable_monday integer,"
				 + "repeatable_tuesday integer,"
				 + "repeatable_wednesday integer,"
				 + "repeatable_thursday integer,"
				 + "repeatable_friday integer,"
				 + "repeatable_saturday integer,"
				 + "shake integer,"
				 + "remark text,"
				 + "awake integer"
				 + ")");
		db.execSQL("create table if not exists SLEEPALARM ("
				 + "ID integer primary key,"
				 + "hour interger,"
				 + "minute integer,"
				 + "shake integer,"
				 + "remark text,"
				 + "awake integer"
				 + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	public void insert(TimingAlarm timingAlarm){
		database = getReadableDatabase();
		cursor = database.query("TIMINGALARM", null, null, null, null, null, null);
		int maxID = 0;
		while(cursor.moveToNext()){
			if(cursor.getInt(0) > maxID) maxID = cursor.getInt(0);
		}
		timingAlarm.setID(maxID + 1);
		database = getWritableDatabase();
		String insert = "insert into TIMINGALARM values("
				+ timingAlarm.getID() + ","
				+ timingAlarm.getHour() + ","
				+ timingAlarm.getMinute() + ","
				+ (timingAlarm.getRepeatable().isRepeatable() ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(0) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(1) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(2) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(3) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(4) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(5) ? 1 : 0) + ","
				+ (timingAlarm.getRepeatable().isRepeatable(6) ? 1 : 0) + ","
				+ (timingAlarm.isShake() ? 1 : 0) + ","
				+ "'" + ((timingAlarm.getRemark() == null) ? "" : timingAlarm.getRemark()) + "',"
				+ (timingAlarm.isAwake() ? 1 : 0) + ")";
		database.execSQL(insert);
		close();
	}
	public ArrayList<TimingAlarm> queryTimingAlarm(){
		database = getReadableDatabase();
		cursor = database.query("TIMINGALARM", null, null, null, null, null, "hour DESC,minute DESC");
		ArrayList<TimingAlarm> alarms = new ArrayList<TimingAlarm>();
		while(cursor.moveToNext()){
			TimingAlarm alarm = new TimingAlarm();
			alarm.setID(cursor.getInt(0));
			alarm.setHour(cursor.getInt(1));
			alarm.setMinute(cursor.getInt(2));
			Repeatable option = new Repeatable();
			option.setRepeatable(cursor.getInt(3) == 0 ? false : true);
			option.setRepeatable(0, cursor.getInt(4) == 0 ? false : true);
			option.setRepeatable(1, cursor.getInt(5) == 0 ? false : true);
			option.setRepeatable(2, cursor.getInt(6) == 0 ? false : true);
			option.setRepeatable(3, cursor.getInt(7) == 0 ? false : true);
			option.setRepeatable(4, cursor.getInt(8) == 0 ? false : true);
			option.setRepeatable(5, cursor.getInt(9) == 0 ? false : true);
			option.setRepeatable(6, cursor.getInt(10) == 0 ? false : true);
			alarm.setRepeatable(option);
			alarm.setShake(cursor.getInt(11) == 0 ? false : true);
			alarm.setRemark(cursor.getString(12));
			alarm.setAwake(cursor.getInt(13) == 0 ? false : true);
			alarms.add(alarm);
		}
		close();
		return alarms;
	}
	public void delete(TimingAlarm alarm){
		database = getWritableDatabase();
		String delete = "delete from TIMINGALARM where ID = "+ alarm.getID();
		database.execSQL(delete);
		close();
	}
	public void update(TimingAlarm timingAlarm){
		database = getWritableDatabase();
		String insert = "update TIMINGALARM set "
				+ "hour = " + timingAlarm.getHour() + ","
				+ "minute = " + timingAlarm.getMinute() + ","
				+ "repeatable = " + (timingAlarm.getRepeatable().isRepeatable() ? 1 : 0) + ","
				+ "repeatable_sunday = " + (timingAlarm.getRepeatable().isRepeatable(0) ? 1 : 0) + ","
				+ "repeatable_monday = " + (timingAlarm.getRepeatable().isRepeatable(1) ? 1 : 0) + ","
				+ "repeatable_tuesday = " + (timingAlarm.getRepeatable().isRepeatable(2) ? 1 : 0) + ","
				+ "repeatable_wednesday = " + (timingAlarm.getRepeatable().isRepeatable(3) ? 1 : 0) + ","
				+ "repeatable_thursday = " + (timingAlarm.getRepeatable().isRepeatable(4) ? 1 : 0) + ","
				+ "repeatable_friday = " + (timingAlarm.getRepeatable().isRepeatable(5) ? 1 : 0) + ","
				+ "repeatable_saturday = " + (timingAlarm.getRepeatable().isRepeatable(6) ? 1 : 0) + ","
				+ "shake = " + (timingAlarm.isShake() ? 1 : 0) + ","
				+ "remark = '" + ((timingAlarm.getRemark() == null) ? "" : timingAlarm.getRemark()) + "'," 
				+ "awake = " + (timingAlarm.isAwake() ? 1 : 0) + " "
				+ "where ID = " + timingAlarm.getID();
		database.execSQL(insert);
		close();
	}
	public void updateAwake(TimingAlarm timingAlarm){
		database = getWritableDatabase();
		String insert = "update TIMINGALARM set "
				+ "awake = " + (timingAlarm.isAwake() ? 1 : 0) + " "
				+ "where ID = " + timingAlarm.getID();
		database.execSQL(insert);
		close();
	}
	public void insert(SleepAlarm sleepAlarm){
		database = getReadableDatabase();
		cursor = database.query("SLEEPALARM", null, null, null, null, null, null);
		int maxID = 0;
		while(cursor.moveToNext()){
			if(cursor.getInt(0) > maxID) maxID = cursor.getInt(0);
		}
		sleepAlarm.setID(maxID + 1);
		database = getWritableDatabase();
		String insert = "insert into SLEEPALARM values("
				+ sleepAlarm.getID() + ","
				+ sleepAlarm.getHour() + ","
				+ sleepAlarm.getMinute() + ","
				+ (sleepAlarm.isShake() ? 1 : 0) + ","
				+ "'" + ((sleepAlarm.getRemark() == null) ? "" : sleepAlarm.getRemark()) + "',"
				+ (sleepAlarm.isAwake() ? 1 : 0) + ")";
		database.execSQL(insert);
		close();
	}
	public ArrayList<SleepAlarm> querySleepAlarm(){
		database = getReadableDatabase();
		cursor = database.query("SLEEPALARM", null, null, null, null, null, "hour DESC,minute DESC");
		ArrayList<SleepAlarm> alarms = new ArrayList<SleepAlarm>();
		while(cursor.moveToNext()){
			SleepAlarm alarm = new SleepAlarm();
			alarm.setID(cursor.getInt(0));
			alarm.setHour(cursor.getInt(1));
			alarm.setMinute(cursor.getInt(2));
			alarm.setShake(cursor.getInt(3) == 0 ? false : true);
			alarm.setRemark(cursor.getString(4));
			alarm.setAwake(cursor.getInt(5) == 0 ? false : true);
			alarms.add(alarm);
		}
		close();
		return alarms;
	}
	public void delete(SleepAlarm alarm){
		database = getWritableDatabase();
		String delete = "delete from SLEEPALARM where ID = "+ alarm.getID();
		database.execSQL(delete);
		close();
	}
	public void update(SleepAlarm sleepAlarm){
		database = getWritableDatabase();
		String insert = "update SLEEPALARM set "
				+ "hour = " + sleepAlarm.getHour() + ","
				+ "minute = " + sleepAlarm.getMinute() + ","
				+ "shake = " + (sleepAlarm.isShake() ? 1 : 0) + ","
				+ "remark = '" + ((sleepAlarm.getRemark() == null) ? "" : sleepAlarm.getRemark()) + "'," 
				+ "awake = " + (sleepAlarm.isAwake() ? 1 : 0) + " "
				+ "where ID = " + sleepAlarm.getID();
		database.execSQL(insert);
		close();
	}
	public void updateAwake(SleepAlarm sleepAlarm){
		database = getWritableDatabase();
		String insert = "update SLEEPALARM set "
				+ "awake = " + (sleepAlarm.isAwake() ? 1 : 0) + " "
				+ "where ID = " + sleepAlarm.getID();
		database.execSQL(insert);
		close();
	}
	public void close(){
		if(database != null)
			database.close();
		if(cursor != null)
			cursor.close();
	}
}
