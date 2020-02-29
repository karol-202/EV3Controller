package pl.karol202.ev3controller;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import pl.karol202.ev3controller.control.Control;
import pl.karol202.ev3controller.roundrules.RoundRules;

import java.util.ArrayList;

public class BoundsEditor implements View.OnTouchListener
{
	public interface BoundsChangeListener
	{
		public void boundsChanged(Rect bounds);
	}

	private static final int TYPE_SIDE_LEFT = 0;
	private static final int TYPE_SIDE_TOP = 1;
	private static final int TYPE_SIDE_RIGHT = 2;
	private static final int TYPE_SIDE_BOTTOM = 3;
	private static final int TYPE_CORNER_TOP_LEFT = 4;
	private static final int TYPE_CORNER_TOP_RIGHT = 5;
	private static final int TYPE_CORNER_BOTTOM_LEFT = 6;
	private static final int TYPE_CORNER_BOTTOM_RIGHT = 7;
	private static final int TYPE_MOVE = 8;

	private Rect bounds;
	private ArrayList<Rect> otherBounds;
	private RoundRules roundRules;
	private BoundsChangeListener listener;
	private int moveType;
	private float lastX;
	private float lastY;

	public BoundsEditor(Control control, ArrayList<Rect> otherBounds, BoundsChangeListener listener)
	{
		this.bounds = new Rect(control.getBounds());
		this.roundRules = control.getRoundRules();
		this.listener = listener;
		this.moveType = -1;
		this.otherBounds = new ArrayList<Rect>();
		for(Rect bounds : otherBounds)
		{
			this.otherBounds.add(new Rect(bounds));
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e)
	{
		boolean result = true;
		if(e.getAction() == MotionEvent.ACTION_DOWN)
		{
			float leftDist = Math.abs(bounds.left - e.getX());
			float topDist = Math.abs(bounds.top - e.getY());
			float rightDist = Math.abs(bounds.right - e.getX());
			float bottomDist = Math.abs(bounds.bottom - e.getY());

			boolean left = leftDist < 50;
			boolean top = topDist < 50;
			boolean right = rightDist < 50;
			boolean bottom = bottomDist < 50;

			boolean sideLeft = left && (e.getY() > bounds.top && e.getY() < bounds.bottom);
			boolean sideTop = top && (e.getX() > bounds.left && e.getX() < bounds.right);
			boolean sideRight = right && (e.getY() > bounds.top && e.getY() < bounds.bottom);
			boolean sideBottom = bottom && (e.getX() > bounds.left && e.getX() < bounds.right);

			boolean cornerTopLeft = top && left;
			boolean cornerTopRight = top && right;
			boolean cornerBottomLeft = bottom && left;
			boolean cornerBottomRight = bottom && right;

			boolean move = bounds.contains((int) e.getX(), (int) e.getY()) && (((leftDist < 30 && rightDist < 30) || (topDist < 30 && bottomDist < 30)) ||
						   !(leftDist < 30 || topDist < 30 || rightDist < 30 || bottomDist < 30));

			if(sideLeft) moveType = TYPE_SIDE_LEFT;
			if(sideTop) moveType = TYPE_SIDE_TOP;
			if(sideRight) moveType = TYPE_SIDE_RIGHT;
			if(sideBottom) moveType = TYPE_SIDE_BOTTOM;

			if(cornerTopLeft) moveType = TYPE_CORNER_TOP_LEFT;
			if(cornerTopRight) moveType = TYPE_CORNER_TOP_RIGHT;
			if(cornerBottomLeft) moveType = TYPE_CORNER_BOTTOM_LEFT;
			if(cornerBottomRight) moveType = TYPE_CORNER_BOTTOM_RIGHT;

			if(move) moveType = TYPE_MOVE;
			result = moveType != -1;
		}
		else if(e.getAction() == MotionEvent.ACTION_MOVE)
		{
			float deltaX = e.getX() - lastX;
			float deltaY = e.getY() - lastY;
			switch(moveType)
			{
			case TYPE_SIDE_LEFT:
				bounds.left += deltaX;
				break;
			case TYPE_SIDE_TOP:
				bounds.top += deltaY;
				break;
			case TYPE_SIDE_RIGHT:
				bounds.right += deltaX;
				break;
			case TYPE_SIDE_BOTTOM:
				bounds.bottom += deltaY;
				break;
			case TYPE_CORNER_TOP_LEFT:
				bounds.top += deltaY;
				bounds.left += deltaX;
				break;
			case TYPE_CORNER_TOP_RIGHT:
				bounds.top += deltaY;
				bounds.right += deltaX;
				break;
			case TYPE_CORNER_BOTTOM_LEFT:
				bounds.bottom += deltaY;
				bounds.left += deltaX;
				break;
			case TYPE_CORNER_BOTTOM_RIGHT:
				bounds.bottom += deltaY;
				bounds.right += deltaX;
				break;
			case TYPE_MOVE:
				bounds.offset((int) deltaX, (int) deltaY);
				break;
			default:
				result = false;
			}
			listener.boundsChanged(roundRules.roundBounds(bounds));
		}
		else if(e.getAction() == MotionEvent.ACTION_UP)
		{
			moveType = -1;
			bounds = roundRules.roundBounds(bounds);
		}
		lastX = e.getX();
		lastY = e.getY();
		return result;
	}
}
