package pl.karol202.ev3controller.roundrules;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class RulesButton implements RoundRules
{
	private final Paint hintPaint;

	public RulesButton()
	{
		hintPaint = new Paint();
		hintPaint.setColor(Color.GREEN);
		hintPaint.setStrokeWidth(2);
	}

	@Override
	public void onDraw(Canvas canvas, Rect bounds, ArrayList<Rect> otherBounds)
	{
		if(bounds.width() == bounds.height())
		{
			canvas.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, hintPaint);
		}
	}

	@Override
	public Rect roundBounds(Rect bounds)
	{
		Rect round = new Rect();
		round.left = Math.round(bounds.left / 10) * 10;
		round.top = Math.round(bounds.top / 10) * 10;
		round.right = Math.round(bounds.right / 10) * 10;
		round.bottom = Math.round(bounds.bottom / 10) * 10;
		return round;
	}
}
