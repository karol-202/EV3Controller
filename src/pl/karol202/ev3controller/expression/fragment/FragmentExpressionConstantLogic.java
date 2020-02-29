package pl.karol202.ev3controller.expression.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantLogic;

public class FragmentExpressionConstantLogic extends FragmentExpression implements CompoundButton.OnCheckedChangeListener
{
	private CompoundButton compoundButton;
	private ExpressionConstantLogic expression;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(!(super.expression instanceof ExpressionConstantLogic)) System.out.println("Expression in FragmentExpressionConstantLogic class isn't instance of ExpressionConstantLogic.");
		this.expression = (ExpressionConstantLogic) super.expression;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = null;
		if(expression.useSwitch())
		{
			view = inflater.inflate(R.layout.fragment_expression_constant_logic_switch, container, false);
			compoundButton = (CompoundButton) view.findViewById(R.id.switch_fragment_expression_constant_logic);
		}
		else
		{
			//view = inflater.inflate(R.layout.fragment_expression_constant_logic_toggle, container, false);
			//compoundButton = (CompoundButton) view.findViewById(R.id.toggle_fragment_expression_constant_logic);
		}
		compoundButton.setChecked(expression.getConstant());
		compoundButton.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		expression.setConstant(isChecked);
	}
}