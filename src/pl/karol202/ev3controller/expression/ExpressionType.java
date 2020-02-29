package pl.karol202.ev3controller.expression;

import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.activity.ActivityEditExpression;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantLogic;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantNumber;
import pl.karol202.ev3controller.expression.fragment.FragmentExpression;
import pl.karol202.ev3controller.expression.fragment.FragmentExpressionConstantNumber;
import pl.karol202.ev3controller.expression.fragment.FragmentExpressionConstantLogic;

public enum ExpressionType
{
	CONSTANT_NUMBER(ExpressionValueType.NUMBER, R.string.name_expression_constant_integer_positive, ExpressionConstantNumber.class, FragmentExpressionConstantNumber.class, null),
	CONSTANT_LOGIC(ExpressionValueType.LOGIC, R.string.name_expression_constant_logic, ExpressionConstantLogic.class, FragmentExpressionConstantLogic.class, null);

	private final ExpressionValueType valueType;
	private final int name;
	private final Class<? extends Expression> expression;
	private final Class<? extends FragmentExpression> fragment;
	private final Class<? extends ActivityEditExpression> editActivity;

	ExpressionType(ExpressionValueType valueType, int name, Class<? extends Expression> expression,
	               Class<? extends FragmentExpression> fragment, Class<? extends ActivityEditExpression> editActivity)
	{
		this.valueType = valueType;
		this.name = name;
		this.expression = expression;
		this.fragment = fragment;
		this.editActivity = editActivity;
	}

	public ExpressionValueType getValueType() { return valueType; }

	public int getName()
	{
		return name;
	}

	public int getIcon()
	{
		return valueType.getIcon();
	}

	public Class<? extends Expression> getExpression() { return expression; }

	public Class<? extends FragmentExpression> getFragment()
	{
		return fragment;
	}

	public Class<? extends ActivityEditExpression> getEditActivity()
	{
		return editActivity;
	}
}
