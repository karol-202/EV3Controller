package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.Command;

import java.util.ArrayList;

public class AdapterCommandsList extends ArrayAdapter<Command>
{
	private Context context;

	public AdapterCommandsList(Context context, ArrayList<Command> items)
	{
		super(context, R.layout.list_commands_row, items);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_commands_row, parent, false);
		}
		else view = convertView;
		ImageView imageViewCommand = (ImageView) view.findViewById(R.id.image_command);
		TextView textViewCommandName = (TextView) view.findViewById(R.id.text_command_name);
		ImageView imageViewError = (ImageView) view.findViewById(R.id.image_command_error);
		Command command = getItem(position);
		imageViewCommand.setImageResource(command.getIcon());
		textViewCommandName.setText(command.getName());
		imageViewError.setVisibility(command.isError() ? View.VISIBLE : View.GONE);
		return view;
	}
}
