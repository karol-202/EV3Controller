package pl.karol202.ev3controller.expression.constant;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import pl.karol202.ev3controller.expression.Expression;
import pl.karol202.ev3controller.expression.ExpressionManager;
import pl.karol202.ev3controller.expression.ExpressionType;

public abstract class ExpressionConstant<T> implements Expression<T>
{
	protected T constant;

	@Override
	public T getValue()
	{
		return constant;
	}

	public T getConstant() { return constant; }

	public void setConstant(T constant) { this.constant = constant; }
}
