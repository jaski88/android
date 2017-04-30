package pl.fancycode.tabatatimer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config {
	
	private static final String WORKOUT_TIME_PREF = "workout_time";
	private static final String PAUSE_TIME_PREF = "pause_time";
	private static final String ROUNDS_COUNT_PREF = "rounds_count";
	private static final String HISTORY_ENABLED_PREF = "history_enabled";
	private static final String VIBRATION_NOTIFICATION_PREF = "vibration_notification";
	private static final String SOUND_NOTIFICATION_PREF = "sound_notification";
	
	public static int workout = 20;
	public static int training = 0;
	public static int pause = 10;
	public static int rounds = 8;
	public static int prep = 3;

	public static boolean vibration_notification = true;
	public static boolean sound_notification = true;
	public static boolean history_enabled = true;
	
	public static boolean read(Context ctx) {

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences( ctx );
		boolean success = true;
		try {
			workout = Integer.valueOf(sp.getString(WORKOUT_TIME_PREF, "20"));
			pause = Integer.valueOf(sp.getString(PAUSE_TIME_PREF, "10"));
			rounds = Integer.valueOf(sp.getString(ROUNDS_COUNT_PREF, "8"));
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		vibration_notification = sp.getBoolean(VIBRATION_NOTIFICATION_PREF,
				true);
		sound_notification = sp.getBoolean(SOUND_NOTIFICATION_PREF, true);
		history_enabled = sp.getBoolean(HISTORY_ENABLED_PREF, true);

		training = rounds * (workout + pause);
		return success;
	}
	

}
