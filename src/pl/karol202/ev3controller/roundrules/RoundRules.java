package pl.karol202.ev3controller.roundrules;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public interface RoundRules
{
	void onDraw(Canvas canvas, Rect bounds, ArrayList<Rect> otherBounds);

	Rect roundBounds(Rect bounds);
}
