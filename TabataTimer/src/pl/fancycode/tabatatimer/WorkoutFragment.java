package pl.fancycode.tabatatimer;

import pl.fancycode.tabatatimer.AppLogic.AppLogicListener;
import pl.fancycode.roundprogressbar.RoundProgressBar;
import pl.fancycode.tabatatimer.R;
import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class WorkoutFragment extends Fragment implements AppLogicListener {

	private static final String TAG = "TabataTracker";

//	private static final String COLOR_GREEN = "#79BD8F";
//	private static final String COLOR_RED = "#FF6138";

	boolean _is_running = false;

	TextView timer_tv, pause_tv;//, round_tv;
	RoundProgressBar workout_pb, round_pb;//, pause_pb, round_pb;

	Button start_btn;
	Vibrator v;

	AppLogic app;

	public void updateLabels() {
		//timer_tv.setText(String.valueOf(Config.workout));
		//pause_tv.setText(String.valueOf(Config.pause));
		//round_tv.setText(String.valueOf(Config.rounds));
		
		// workout_tv.setText("Workout time: " + workout + "s");
		// rounds_tv.setText("Rounds: " + rounds);
		// pause_tv.setText("Pause time: " + pause + "s");
		// training_tv.setText("Training time: " + training + "s");
	}

	public void updateButton() {
		String _label = (_is_running ? "Stop" : "Start") + " workout";
		start_btn.setText(_label);

	}

	OnClickListener start_listener = new OnClickListener() {

		public void onClick(View v) {

			if (_is_running == false) {
				_is_running = true;
				app.start();
				updateButton();
				addHistory();

			} else {
				_is_running = false;
				app.stop();
				resetUI();
			}

		}
	};

	@Override
	public void updateProgress(int progress, double time) {
//		double t = (double) time / 100; 
//		Log.d(TAG, "time: " + time + "," + t);
		timer_tv.setText(String.valueOf(time));
		workout_pb.setProgress(progress);
	}



	public WorkoutFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Config.read( getActivity() );

		app = new AppLogic(Config.workout, Config.pause, Config.prep, Config.rounds);
		app.register_listener(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_workout, container,
				false);

		start_btn = (Button) rootView.findViewById(R.id.start_btn);
		start_btn.setOnClickListener(start_listener);

		//workout_tv = (TextView) rootView.findViewById(R.id.workout_tv);
		//round_tv = (TextView) rootView.findViewById(R.id.round_tv);
		timer_tv = (TextView) rootView.findViewById(R.id.timer_tv);
		
		
		workout_pb = (RoundProgressBar) rootView.findViewById(R.id.progressBar);
		//pause_pb = (RoundProgressBar) rootView.findViewById(R.id.progressBarRest);
		round_pb = (RoundProgressBar) rootView.findViewById(R.id.progressBarRound);
		round_pb.setColor(Const.fg_prep, Const.bg_prep );

		
		updateLabels();
		resetUI();

		return rootView;
	}

	@Override
	public void updateUI( int type, int round, int roundProcent ) {
	
		if( type == 0 )
		{		
			workout_pb.setColor(Const.fg_pause, Const.bg_pause );
		}
		else
		{
			workout_pb.setColor(Const.fg_workout, Const.bg_workout);
		}
		
		if (Config.vibration_notification == true) {
			v = (Vibrator) getActivity().getSystemService(
					Context.VIBRATOR_SERVICE);
			v.vibrate(1000L);
		}

		if (Config.sound_notification == true) {
			MediaPlayer mp = MediaPlayer.create(getActivity().getBaseContext(),
					R.raw.sound);
			mp.start();
		}
		
		//round_tv.setText( String.valueOf( round ) );
		
		round_pb.setProgress(roundProcent);
		
		

	}

	@Override
	public void stop() {
		resetUI();
	}

	private void resetUI( ) {
		_is_running = false;

		//round_tv.setText( String.valueOf( Config.rounds ) );
		
		Log.d(TAG, "rounds" + Config.rounds );

		workout_pb.setProgress(0);

		updateButton();
	}

	
	private void addHistory(){
		if (Config.history_enabled) {
			HistoryDB db = new HistoryDB(getActivity().getBaseContext());
			HistoryDB.DBRow row = new HistoryDB.DBRow();
			row.workout = Config.workout;
			row.pause = Config.pause;
			row.rounds = Config.rounds;
			row.date = System.currentTimeMillis();
			db.insertRow(row);
			Log.d(TAG, "db row inserted");
		}
	}

}