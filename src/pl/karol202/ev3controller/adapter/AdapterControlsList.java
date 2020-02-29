package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.karol202.ev3controller.control.Control;
import pl.karol202.ev3controller.R;

import java.util.ArrayList;

public class AdapterControlsList extends ArrayAdapter<Control>
{
	private Context context;

	public AdapterControlsList(Context context, ArrayList<Control> items)
	{
		super(context, R.layout.list_controls_row, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_controls_row, parent, false);
		}
		else view = convertView;
		ImageView imageViewControl = (ImageView) view.findViewById(R.id.image_control);
		TextView textViewControl = (TextView) view.findViewById(R.id.text_control_name);
		int image = getItem(position).getIcon();
		if(image != -1) imageViewControl.setImageResource(image);
		textViewControl.setText(getItem(position).getName());
		return view;
	}
}
