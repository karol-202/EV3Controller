package pl.karol202.ev3controller.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pl.karol202.ev3controller.Controller.ResultListener;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.Result;
import pl.karol202.ev3controller.adapter.AdapterCommandsTypesList;
import pl.karol202.ev3controller.adapter.AdapterControlsTypesList;
import pl.karol202.ev3controller.command.CommandType;
import pl.karol202.ev3controller.control.ControlType;

import java.util.ArrayList;
import java.util.Arrays;

public class DialogControlType
{
	public interface ControlTypeListener
	{
		void onControlTypeSelected(ControlType type);
	}

	private Dialog dialog;
	private ControlTypeListener typeListener;
	private ArrayList<ControlType> typesList;

	public DialogControlType(Activity activity, ControlTypeListener listener)
	{
		this.dialog = new Dialog(activity);
		this.typeListener = listener;
		this.typesList = new ArrayList<ControlType>(Arrays.asList(ControlType.values()));

		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.dialog_controls_types, null);
		dialog.setTitle(R.string.title_dialog_controls_types);
		dialog.setContentView(view);
		ArrayAdapter<ControlType> listAdapter = new AdapterControlsTypesList(activity, typesList);
		ListView listViewTypes = (ListView) view.findViewById(R.id.listView_controls_types);
		listViewTypes.setAdapter(listAdapter);
		listViewTypes.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
			{
				typeListener.onControlTypeSelected(typesList.get(position));
				dialog.cancel();
			}
		});
	}

	public void show()
	{
		dialog.show();
	}
}
