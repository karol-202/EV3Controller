package pl.karol202.ev3controller.expression.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.dialog.DialogExpressionType;
import pl.karol202.ev3controller.expression.ActivityExpression;
import pl.karol202.ev3controller.expression.Expression;
import pl.karol202.ev3controller.expression.ExpressionManager;
import pl.karol202.ev3controller.expression.ExpressionType;

public abstract class FragmentExpression extends Fragment implements View.OnClickListener, DialogExpressionType.ExpressionTypeListener
{
	protected Expression expression;
	protected int expressionId;
	protected int[] filter;

	protected ImageView buttonEditExpression;

	public FragmentExpression()
	{
		if(!(getActivity() instanceof ActivityExpression)) System.out.println("Overlaying activity doesn't implement interface ActivityExpression.");
		this.expressionId = getArguments().getInt("expressionId", -1);
		if(expressionId == -1) System.out.println("ExpressionId has not been sent to FragmentExpression.");
		this.filter = getArguments().getIntArray("filter");
		this.expression = ExpressionManager.expressions.size() > expressionId ? ExpressionManager.expressions.get(expressionId) : null;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		buttonEditExpression = (ImageView) view.findViewById(R.id.button_edit_expression);
		if (buttonEditExpression == null) System.out.println("There is no edit button in this fragment.");
		buttonEditExpression.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if(expression != null)
		{
			Class editActivity = expression.getEditActivity();
			if (editActivity != null)
			{
				Intent intent = new Intent(getActivity(), editActivity);
				getActivity().startActivity(intent);
				return;
			}
		}
		newExpression();
	}

	@Override
	public void onExpressionTypeSelected(ExpressionType type)
	{
		try
		{
			ExpressionManager.expressions.add(type.getExpression().newInstance());
			ExpressionManager.saveExpressions();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Class editActivity = type.getEditActivity();
		if(editActivity != null)
		{
			Intent intent = new Intent(getActivity(), editActivity);
			getActivity().startActivity(intent);
		}
		else((ActivityExpression) getActivity()).refreshFragments();
	}

	public void newExpression()
	{
		DialogExpressionType dialog = new DialogExpressionType(getActivity(), this, filter);
		dialog.show();
	}
}
