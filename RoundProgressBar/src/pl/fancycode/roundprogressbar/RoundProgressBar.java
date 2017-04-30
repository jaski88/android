package pl.fancycode.roundprogressbar;

import pl.jaskurzynski.roundprogressbar.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class RoundProgressBar extends View {

	private Paint paintBackground;
	private Paint paintProgress;

	private int colorForground = Color.parseColor("#7D8A2E");
	private int colorBackground = Color.parseColor("#C9D787");

	private RectF rectF;

	public int _progress = 20;
	public int _size = 0;

	private int shapeWidth = 300;
	private int shapeHeight = 300;

	private int _width_forground = 40;
	private int _width_background = 60;

	public RoundProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setupAttributes(attrs);
		setupPaint();
	}

	private void setupAttributes(AttributeSet attrs) {
		// Obtain a typed array of attributes
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar, 0, 0);
		// Extract custom attributes into member variables
		try {
			colorForground = a.getColor(
					R.styleable.RoundProgressBar_foreground,
					Color.parseColor("#7D8A2E"));
			colorBackground = a.getColor(
					R.styleable.RoundProgressBar_background,
					Color.parseColor("#C9D787"));

			_progress = a.getInt(R.styleable.RoundProgressBar_progress, 60);
		} finally {
			// TypedArray objects are shared and must be recycled.
			a.recycle();
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int size = 0;
	    if (getWidth() > getHeight()) {
	        size = getHeight();
	    } else {
	        size = getWidth();
	    }

		int marginTop = _width_background / 2 + getPaddingTop();
		int marginBottom = _width_background / 2 + getPaddingBottom();

		int marginLeft =  _width_background / 2 +  getPaddingLeft();
		int marginRight =   _width_background / 2 + getPaddingRight();
		
		
		int left = ( getWidth() / 2 ) - ( size / 2 ) + marginLeft;
		int right = ( getWidth() / 2 ) + ( size / 2 ) - marginRight;

		int top = ( getHeight() / 2 ) - ( size / 2 ) + marginTop;
		int bottom = ( getHeight() / 2 ) + ( size / 2 ) - marginBottom;

		rectF = new RectF( left, top, right, bottom);
		
		
		canvas.drawArc(rectF, 270, 360, false, paintBackground);
		canvas.drawArc(rectF, 270, (float) (3.6 * _progress), false, paintProgress);

	}

	public void setProgress(int progress) {
		_progress = progress;
		invalidate();
	}

	private void setupPaint( ) {

		paintBackground = new Paint();
		paintBackground.setStyle(Style.STROKE);
		paintBackground.setAntiAlias(true);
		paintBackground.setStrokeCap(Paint.Cap.ROUND);
		paintBackground.setStrokeWidth(_width_background);
		paintBackground.setColor(colorBackground);

		paintProgress = new Paint();
		paintProgress.setStyle(Style.STROKE);
		paintProgress.setAntiAlias(true);
		paintProgress.setStrokeCap(Paint.Cap.ROUND);
		paintProgress.setStrokeWidth(_width_forground);
		paintProgress.setColor(colorForground);

	}

	public int getColorForground() {
		return colorForground;
	}

	public void setColorForground(int colorForground) {
		this.colorForground = colorForground;
		setupPaint();
		invalidate();
	}

	public int getColorBackground() {
		return colorBackground;
	}

	public void setColorBackground(int colorBackground) {
		this.colorBackground = colorBackground;
		setupPaint();
		invalidate();
	}
	
	public void setColor(int colorForground, int colorBackground) {
		this.colorForground = colorForground;
		this.colorBackground = colorBackground;

		setupPaint();
		invalidate();
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
		setupPaint();
		invalidate();
	}

}
