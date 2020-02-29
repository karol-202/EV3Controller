package pl.karol202.ev3controller.expression.constant;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import pl.karol202.ev3controller.expression.ExpressionManager;
import pl.karol202.ev3controller.expression.ExpressionType;

public class ExpressionConstantNumber extends ExpressionConstant<Integer>
{
	@Override
	public int getTypeCode()
	{
		return ExpressionType.CONSTANT_NUMBER.ordinal();
	}

	@Override
	public int getName()
	{
		return ExpressionType.CONSTANT_NUMBER.getName();
	}

	@Override
	public int getIcon()
	{
		return ExpressionType.CONSTANT_NUMBER.getIcon();
	}

	@Override
	public Class<? extends Fragment> getFragment()
	{
		return ExpressionType.CONSTANT_NUMBER.getFragment();
	}

	@Override
	public Class<? extends Activity> getEditActivity()
	{
		return ExpressionType.CONSTANT_NUMBER.getEditActivity();
	}

	@Override
	public void loadExpression(int expression)
	{
		SharedPreferences prefs = ExpressionManager.prefs;
		constant = prefs.getInt("expression" + expression + "constant", -1);
	}

	@Override
	public void saveExpression(int expression)
	{
		SharedPreferences.Editor editor = ExpressionManager.editor;
		editor.putInt("expression" + expression + "constant", constant);
	}

	@Override
	public void removeExpression(int expression)
	{
		SharedPreferences.Editor editor = ExpressionManager.editor;
		editor.remove("expression" + expression + "constant");
	}
}
