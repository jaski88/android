package pl.fancycode.speedometer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppLogic {

	public static final float km_h = 3.6f;
	public static final float m_s = 1f;
	public static final float mph = 2.236936f;
	public static final float ft_s = 3.280840f;

	public final static String[] UNIT_STRING = new String[] { "km/h", "m/s", "mph", "ft/s" };
	public final static float[] UNIT_VALUE = new float[] { km_h, m_s, mph, ft_s };

	private static final String UNIT_PREF = "unit";
	private static final String SETTINGS_TAG = "pl.jaskurzynski.Speedometer";
	
	private static final int DEFAULT_UNIT = 0;

	SharedPreferences mPrefs;

	int _unit = 0;
	float _speed = 0f;
	float _acc = 0f;

	public AppLogic(Context ctx) {
		mPrefs = ctx.getSharedPreferences(SETTINGS_TAG, Context.MODE_PRIVATE);
		_unit = mPrefs.getInt(UNIT_PREF, DEFAULT_UNIT);
		
		Log.d("", "Current unit " + _unit);
	}
	
	public void setUnit(int unit) {
		if( unit < UNIT_VALUE.length )
		{
			_unit = unit;
		}
		else
		{
			Log.e( "" , "Wrong unit");
		}
	}

	public void setSpeed(float _speed) {
		this._speed = _speed;
	}

	public void setAcc(float _acc) {
		this._acc = _acc;
	}

	public String getSpeed( )
	{	
		if( _unit >= UNIT_VALUE.length )
		{
			_unit = DEFAULT_UNIT;
		}
		return String.valueOf( Math.round(_speed  * UNIT_VALUE[_unit] ) );
	}
	
	public String getUnit( )
	{
		if( _unit >= UNIT_VALUE.length )
		{
			_unit = DEFAULT_UNIT;
		}
		return UNIT_STRING[_unit];
	}
	
	public String getAcc( )
	{
		return _acc + " m";
	}

	public void storeSettings() {
		SharedPreferences.Editor ed = mPrefs.edit();
		ed.putInt(UNIT_PREF, _unit);
		ed.commit();
		Log.d("", "Current unit " + _unit);
	}


}
