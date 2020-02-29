package pl.karol202.ev3controller.expression.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.dialog.DialogExpressionType;
import pl.karol202.ev3controller.expression.ActivityExpression;
import pl.karol202.ev3controller.expression.Expression;
import pl.karol202.ev3controller.expression.ExpressionManager;
import pl.karol202.ev3controller.expression.ExpressionType;

public class FragmentNoExpression extends FragmentExpression
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_expression_empty, container, false);
		return view;
	}
}
