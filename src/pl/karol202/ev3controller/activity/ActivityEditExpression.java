package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.expression.Expression;
import pl.karol202.ev3controller.expression.ExpressionManager;

public abstract class ActivityEditExpression extends Activity
{
	protected Expression expression;
	protected int expressionId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		expressionId = getIntent().getIntExtra("expressionId", -1);
		if(expressionId == -1)
		{
			expressionId = ExpressionManager.expressions.size();
			ExpressionManager.expressions.add(createNewExpression());
			ExpressionManager.saveExpressions();
		}
		expression = ExpressionManager.expressions.get(expressionId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_edit_expression, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_delete_expression:
			ExpressionManager.removeExpression(expressionId);
			finish();
			return true;
		case R.id.item_apply_expression:
			applyExpression();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public abstract Expression createNewExpression();

	protected void applyExpression() {}
}
