package pl.fancycode.tabatatimer;

import pl.fancycode.tabatatimer.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = "TabataTracker";
	
	public SettingsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
		
		
		initSummary( getPreferenceScreen() );
		
		

	}

	public void initSummary( Preference p )
	{
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGroup = (PreferenceGroup) p;
            for (int i = 0; i < pGroup.getPreferenceCount(); i++) {
                initSummary(pGroup.getPreference(i));
            }
        }
        else
        {
        	updateSummary( p );
        }
	}
	
	public void updateSummary( Preference p)
	{
		if (p instanceof EditTextPreference) {
			EditTextPreference editTextPref = (EditTextPreference) p;
			Log.d(TAG, "updateSummary " + editTextPref.getText());

			
			p.setSummary(editTextPref.getText());
		}
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.d(TAG, "onSharedPreferenceChanged");
		Preference p = findPreference(key);
		updateSummary( p );
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}