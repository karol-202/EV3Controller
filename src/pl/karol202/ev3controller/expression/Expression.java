package pl.karol202.ev3controller.expression;

import android.app.Activity;
import android.app.Fragment;

public interface Expression<T>
{
	int getTypeCode();

	int getName();

	int getIcon();

	Class<? extends Fragment> getFragment();

	Class<? extends Activity> getEditActivity();

	T getValue();

	void loadExpression(int expresssion);

	void saveExpression(int expression);

	void removeExpression(int expression);
}
