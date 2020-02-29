package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.CommandType;

import java.util.ArrayList;

public class AdapterCommandsTypesList extends ArrayAdapter<CommandType>
{
    private Context context;

    public AdapterCommandsTypesList(Context context, ArrayList<CommandType> items)
    {
        super(context, R.layout.list_commands_row, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_commands_row, parent, false);
        } else view = convertView;
        ImageView imageViewCommand = (ImageView) view.findViewById(R.id.image_command);
        TextView textViewCommandName = (TextView) view.findViewById(R.id.text_command_name);
        imageViewCommand.setImageResource(getItem(position).getImage());
        textViewCommandName.setText(getItem(position).getName());
        return view;
    }
}
