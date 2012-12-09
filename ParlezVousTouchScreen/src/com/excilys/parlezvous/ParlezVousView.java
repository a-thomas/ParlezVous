package com.excilys.parlezvous;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class ParlezVousView extends SurfaceView implements OnDoubleTapListener, OnGestureListener {

	private List<Circle> circles = new ArrayList<Circle>();
	private Circle currentCircle;
	private Paint paint = new Paint();
	private GestureDetector gestureDetector;

	public ParlezVousView(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(context, this);
		gestureDetector.setOnDoubleTapListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawCircles(canvas);
	}

	private void drawCircles(Canvas canvas) {
		paint.setColor(Color.RED);
		for (Circle circle : circles) {
			canvas.drawCircle(circle.x, circle.y, circle.rayon, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			currentCircle = null;
			break;
		case MotionEvent.ACTION_MOVE:
			if (currentCircle == null) {
				currentCircle = getCircleAtPosition(x, y);
			}
			if (currentCircle != null) {
				currentCircle.x = x;
				currentCircle.y = y;
			} else {
				currentCircle = new Circle();
				currentCircle.x = x;
				currentCircle.y = y;
				circles.add(currentCircle);
			}
			break;
		}

		invalidate();

		return true;
	}

	private Circle getCircleAtPosition(float x, float y) {
		for (Circle circle : circles) {
			if ((x >= circle.x - circle.rayon && x <= circle.x + circle.rayon) //
					&& (y >= circle.y - circle.rayon && y <= circle.y + circle.rayon))//
			{
				return circle;
			}
		}
		return null;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {

		float x = e.getX();
		float y = e.getY();

		Circle touchedCircle = getCircleAtPosition(x, y);
		circles.remove(touchedCircle);

		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
