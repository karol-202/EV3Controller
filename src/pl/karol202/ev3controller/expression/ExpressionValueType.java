package pl.karol202.ev3controller.expression;

import pl.karol202.ev3controller.R;

public enum ExpressionValueType
{
	NUMBER(R.drawable.arrow_left),
	LOGIC(R.drawable.arrow_right);

	private final int icon;

	ExpressionValueType(int icon)
	{
		this.icon = icon;
	}

	public int getIcon() { return icon; }
}
