package pl.fancycode.tabatatimer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class HistoryDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tabatatimer.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "history";
    
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORKOUT = "workout";
    public static final String COLUMN_PAUSE = "pause";
    public static final String COLUMN_ROUNDS = "rounds";
    public static final String COLUMN_DATE = "date";

    
    public static final String CREATE_SQL = 
    		String.format("create table %s (%s integer primary key autoincrement, %s integer, %s integer, %s integer, %s long )", TABLE_NAME, COLUMN_ID, COLUMN_WORKOUT, COLUMN_PAUSE, COLUMN_ROUNDS, COLUMN_DATE );


    public HistoryDB(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
//
//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        //_createTable(db);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        _createTable(db);
    }

    protected void _createTable( SQLiteDatabase db )
    {
        db.execSQL( CREATE_SQL );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        _createTable(db);
    }

    public boolean insertRow( DBRow row )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORKOUT, row.workout);
        contentValues.put(COLUMN_PAUSE, row.pause);
        contentValues.put(COLUMN_ROUNDS, row.rounds);
        contentValues.put(COLUMN_DATE, row.date);

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getRow(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME + " where _id="+id+"", null );
        return res;
    }

    public int countRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

//    public boolean updateRow(Integer id, String content)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_CONTENT, content);
//        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Integer deleteRow (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<CustomAdapterRow> getAllRows()
    {
        ArrayList<CustomAdapterRow> array_list = new ArrayList<CustomAdapterRow>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_NAME + " order by date desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
        	DBRow row = new DBRow( );
        	row.workout = res.getInt(res.getColumnIndex(COLUMN_WORKOUT));
        	row.pause = res.getInt(res.getColumnIndex(COLUMN_PAUSE));
        	row.rounds = res.getInt(res.getColumnIndex(COLUMN_ROUNDS));
        	row.date = res.getLong(res.getColumnIndex(COLUMN_DATE));
    	
            array_list.add( row );
            res.moveToNext();
        }
        return array_list;
    }
    
    static public class DBRow implements CustomAdapterRow
    {
    	public int workout;
    	public int pause;
    	public int rounds;
    	public long date;
    	
    	public String toString( )
    	{
    		return "Date: 10-04-2016, Rounds: " + rounds + ", Workout " + workout + ", Pause: " + pause ;
    	}

		@Override
		public String getTitle() {
			
	        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        return s.format(new Date(date));
		}

		@Override
		public String getContent() {
			return "Rounds: " + rounds + ", Workout " + workout + ", Pause: " + pause ;
		}
    }
}