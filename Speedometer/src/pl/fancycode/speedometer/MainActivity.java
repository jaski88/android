package pl.fancycode.speedometer;

import pl.fancycode.speedometer.R;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements LocationService.Listener {

	LocationService _locationService;
	AppLogic _app;
	
	TextView speed_tv, unit_tv, signal_tv;
	
	@Override
	protected void onStart() {
		super.onStart();
		_locationService.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		_locationService.stop( );
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_app = new AppLogic( getApplicationContext() ); 

		setupUI();
		updateUI();

		_locationService = new LocationService( (Activity) this , (LocationService.Listener) this);

	}

	public void setupUI() {
		speed_tv = (TextView) findViewById(R.id.speed_tv);
		unit_tv = (TextView) findViewById(R.id.unit_tv);
		signal_tv = (TextView) findViewById(R.id.signal_tv);
	}

	public void updateUI( ) {
		speed_tv.setText(_app.getSpeed());
		signal_tv.setText( _app.getAcc());
		unit_tv.setText(_app.getUnit());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.units:
			showSettingsDiaglog( );
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}
	
	private void showSettingsDiaglog() {
		AlertDialog.Builder builder = new AlertDialog.Builder( this );

		builder.setTitle(R.string.menu_unit)
        .setItems( AppLogic.UNIT_STRING, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idx) {
            	_app.setUnit(idx);
            	_app.storeSettings();
        		updateUI();
        }} );

		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	public void onSpeedChanged(float speed) {
		_app.setSpeed(speed);
		updateUI();
	}

	@Override
	public void onAccChanged(float acc) {
		_app.setAcc(acc);
		updateUI();
	}
}
