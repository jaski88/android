package pl.fancycode.speedometer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

public class LocationService implements LocationListener {
	
	
    public interface Listener
    {
        void onSpeedChanged( float speed );
        void onAccChanged( float acc );
    }
    
	LocationManager _locationManager;
	public Listener _listener;
	
	private static final int REFRESH_RATE = 1000;
	private static final int SIGNAL_LIVE_TIME = 5000;
	
	long _signal_updated_at = 0;
	Handler _hnd = new Handler();
	


    public LocationService(Activity act, Listener _listener) {
		super();
		this._listener = _listener;
		
		_locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
		
		_hnd.postDelayed(updateSignalStrength, 0);
	}
    
    public void start( ){
		_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    
	public void stop() {
		_locationManager.removeUpdates(this);
	}

    
	public Runnable updateSignalStrength = new Runnable() {

		public void run() {
			long now = System.currentTimeMillis();
			boolean valid_signal_present = (now < _signal_updated_at + SIGNAL_LIVE_TIME);
			
			if( !valid_signal_present )
			{
				_listener.onAccChanged( 0f );
			}
			
			_hnd.postDelayed(this, REFRESH_RATE);
		}

	};

	@Override
	public void onLocationChanged(Location location) {
        _listener.onSpeedChanged( location.getSpeed( ) );
        _listener.onAccChanged( Math.round( location.getAccuracy( ) ) );
		_signal_updated_at = System.currentTimeMillis();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
