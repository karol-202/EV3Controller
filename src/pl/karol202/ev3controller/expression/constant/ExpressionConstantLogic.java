package pl.karol202.ev3controller.expression.constant;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import pl.karol202.ev3controller.expression.ExpressionManager;
import pl.karol202.ev3controller.expression.ExpressionType;

public class ExpressionConstantLogic extends ExpressionConstant<Boolean>
{
	private boolean useSwitch;

	@Override
	public int getTypeCode()
	{
		return ExpressionType.CONSTANT_LOGIC.ordinal();
	}

	@Override
	public int getName()
	{
		return ExpressionType.CONSTANT_LOGIC.getName();
	}

	@Override
	public int getIcon()
	{
		return ExpressionType.CONSTANT_LOGIC.getIcon();
	}

	@Override
	public Class<? extends Fragment> getFragment()
	{
		return ExpressionType.CONSTANT_LOGIC.getFragment();
	}

	@Override
	public Class<? extends Activity> getEditActivity()
	{
		return ExpressionType.CONSTANT_LOGIC.getEditActivity();
	}

	@Override
	public void loadExpression(int expression)
	{
		SharedPreferences prefs = ExpressionManager.prefs;
		constant = prefs.getBoolean("expression" + expression + "constant", false);
		useSwitch = prefs.getBoolean("expression" + expression + "useSwitch", true);
	}

	@Override
	public void saveExpression(int expression)
	{
		SharedPreferences.Editor editor = ExpressionManager.editor;
		editor.putBoolean("expression" + expression + "constant", constant);
		editor.putBoolean("expression" + expression + "useSwitch", useSwitch);
	}

	@Override
	public void removeExpression(int expression)
	{
		SharedPreferences.Editor editor = ExpressionManager.editor;
		editor.remove("expression" + expression + "constant");
		editor.remove("expression" + expression + "useSwitch");
	}

	public boolean useSwitch() { return useSwitch; }

	public void setUseSwitch(boolean useSwitch) { this.useSwitch = useSwitch; }
}