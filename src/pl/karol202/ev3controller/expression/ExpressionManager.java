package pl.karol202.ev3controller.expression;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pl.karol202.ev3controller.activity.ActivityMain;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantNumber;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantLogic;

import java.util.ArrayList;

public class ExpressionManager
{
	private static final int EXPRESSION_CONSTANT_INTEGER_POSITIVE = 0;
	private static final int EXPRESSION_CONSTANT_LOGIC = 1;

	public static SharedPreferences prefs;
	public static SharedPreferences.Editor editor;
	public static ArrayList<Expression> expressions;

	public static void init()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.mainActivity);
		editor = prefs.edit();
		expressions = new ArrayList<Expression>();
		loadExpressions();
	}

	public static void loadExpressions()
	{
		int length = prefs.getInt("expressionsLength", 0);
		for(int i = 0; i < length; i++)
		{
			int expressionType = prefs.getInt("expression" + i + "type", -1);
			Expression expression = null;
			switch(expressionType)
			{
			case EXPRESSION_CONSTANT_INTEGER_POSITIVE:
				expression = new ExpressionConstantNumber();
				break;
			case EXPRESSION_CONSTANT_LOGIC:
				expression = new ExpressionConstantLogic();
				break;
			}
			expression.loadExpression(i);
			expressions.add(expression);
		}
	}

	public static void saveExpressions()
	{
		int length = expressions.size();
		editor.putInt("expressionsLength", length);
		for(int i = 0; i < length; i++)
		{
			Expression expression = expressions.get(i);
			editor.putInt("expression" + i + "type", expression.getTypeCode());
			expression.saveExpression(i);
		}
		editor.commit();
	}

	public static void removeExpression(int expression)
	{
		if(expression < 0 || expression >= expressions.size()) System.out.println("Trying to delate non-existing expression.");
		expressions.get(expression).removeExpression(expression);
		expressions.remove(expression);
		editor.commit();
	}
}
