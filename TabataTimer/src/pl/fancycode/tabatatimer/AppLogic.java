package pl.fancycode.tabatatimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Handler;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AppLogic {

	private static final String TAG = "TabataTracker";
	private static final int REFRESH_RATE = 100;
	private static final int TO_MILISECONDS = 1000;

	public int _workout_time = 0;
	public int _pause_time = 0;
	public int _prep_time = 0;

	int _current_round = 0;
	public int _rounds = 0;

	Round _round;
	Handler _hnd = new Handler();
	AppLogicListener _listener;

	enum RoundType {
		PREP, PAUSE, WORKOUT
	}

	public interface AppLogicListener {
		public void updateProgress(int progress, double time);
		public void stop();
		public void updateUI(int type, int round,int round_proc);
	}

	public AppLogic(int workout_time, int pause_time, int prep_time, int rounds) {
		_workout_time = workout_time;
		_pause_time = pause_time;
		_prep_time = prep_time;
		_rounds = rounds;
	}

	public boolean hasNextRound() {
		return _current_round > 0 ;
	}

	public void start() {
		_current_round = _rounds;
		_hnd.postDelayed(updateTimer, REFRESH_RATE);
	}

	public void stop() {
		_hnd.removeCallbacks(updateTimer);
		_round = null;
	}

	public void register_listener(AppLogicListener listener) {
		_listener = listener;
	}
	
	public int getRoundProcent()
	{
		int procent = Math.round( ( (float ) _current_round ) / _rounds * 100 );
		Log.e(TAG, "Round procent: " + procent );
		return procent;
	}


	public class Round {

		private int _length;
		private long _end_time;
		private RoundType _type;
		private long _current_time;

		public Round(int length, RoundType type) {
			_length = length * TO_MILISECONDS;
			_type = type;
		}

		public RoundType getType() {
			return _type;
		}

		public void start() {
			_end_time = System.currentTimeMillis() + _length;
		}

		public void freeze() {
			_current_time = System.currentTimeMillis();
		}

		public int getLength() {
			return _length;
		}

		public double getTimeLeft() {
			return (double) Math.round( (_end_time - _current_time) / 100 ) / 10;
		}

		public int getProgress() {
			long progress = 100 - (((_end_time - _current_time) * 100) / _length);
			return (int) progress;
		}

		public boolean hasEnded() {
			return _end_time <= _current_time;
		}

		public RoundType getNextRoundType() {
			return (_type == RoundType.WORKOUT) ? RoundType.PAUSE
					: RoundType.WORKOUT;
		}
		

	}

	public Runnable updateTimer = new Runnable() {

		public void run() {

			if (_round == null) {
				_round = new Round(_prep_time, RoundType.PREP);
				_round.start();
				_listener.updateUI(2, _current_round, getRoundProcent( ));
			}

			_round.freeze();

			if (_round.hasEnded()) {
				Log.e(TAG, "ended");

				if (!hasNextRound()) {
					_listener.updateUI(2, 0, getRoundProcent( ));
					_listener.stop();
					stop( );
					Log.e(TAG, "finished");
					return;
				}
				
				RoundType next = _round.getNextRoundType( );
				if( next == RoundType.PAUSE )
				{
					_round = new Round(_pause_time, RoundType.PAUSE);
					_listener.updateUI(0, _current_round, getRoundProcent( ));
				}
				else
				{
					_round = new Round(_workout_time, RoundType.WORKOUT);
					_current_round--;
//					_listener.updateRound("Round " + _current_round + " / "
//							+ _rounds);
					_listener.updateUI(1, _current_round, getRoundProcent( ));
				}

				_round.start();
				_round.freeze();

			}

			_listener.updateProgress(_round.getProgress(),
					_round.getTimeLeft() );

			_hnd.postDelayed(this, REFRESH_RATE);
		}

	};

}
