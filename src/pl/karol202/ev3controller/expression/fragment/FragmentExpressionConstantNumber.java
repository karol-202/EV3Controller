package pl.karol202.ev3controller.expression.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.expression.constant.ExpressionConstantNumber;

public class FragmentExpressionConstantNumber extends FragmentExpression implements TextWatcher
{
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(!(expression instanceof ExpressionConstantNumber)) System.out.println("Expression in FragmentExpressionConstantNumber class isn't instance of ExpressionConstantNumber.");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_expression_constant_number, container, false);
		textView = (TextView) view.findViewById(R.id.editText_fragment_expression_constant_number);
		textView.setText(((ExpressionConstantNumber) expression).getConstant());
		textView.addTextChangedListener(this);
		return view;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		((ExpressionConstantNumber) expression).setConstant(Integer.parseInt(s.toString()));
	}
}