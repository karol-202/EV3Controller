package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.karol202.ev3controller.R;

import java.util.ArrayList;

public class AdapterMotorsList extends ArrayAdapter<Boolean>
{
	private Context context;

	public AdapterMotorsList(Context context, ArrayList<Boolean> items)
	{
		super(context, R.layout.list_motors_types_row, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_motors_types_row, parent, false);
		}
		else view = convertView;
		TextView textViewName = (TextView) view.findViewById(R.id.text_motor_name);
		TextView textViewType = (TextView) view.findViewById(R.id.text_motor_type);
		String motorName = "Silnik ";
		switch(position)
		{
		case 0: motorName += "A"; break;
		case 1: motorName += "B"; break;
		case 2: motorName += "C"; break;
		case 3: motorName += "D"; break;
		default: motorName += (position - 4); break;
		}
		String motorType = getItem(position) ? "Duży" : "Średni";
		textViewName.setText(motorName);
		textViewType.setText(motorType);
		return view;
	}
}
