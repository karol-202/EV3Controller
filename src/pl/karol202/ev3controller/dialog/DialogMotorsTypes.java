package pl.karol202.ev3controller.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import pl.karol202.ev3controller.Profile;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.Robot;
import pl.karol202.ev3controller.adapter.AdapterMotorsList;

import java.util.ArrayList;
import java.util.Arrays;

public class DialogMotorsTypes
{
	private Activity activity;
	private Profile profile;
	private AlertDialog dialog;
	private ArrayList<Boolean> motorList;
	private ArrayAdapter<Boolean> listAdapter;

	public DialogMotorsTypes(Activity editActivity, Profile editedProfile)
	{
		this.activity = editActivity;
		this.profile = editedProfile;
		this.motorList = new ArrayList<Boolean>(Arrays.<Boolean> asList(toBooleanObjects(profile.getMotorTypes())));

		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.dialog_profile_motors, null);
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
		dialogBuilder.setTitle(R.string.title_dialog_profile_motors);
		dialogBuilder.setView(view);
		dialogBuilder.setPositiveButton(R.string.button_motors_ok, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				profile.setMotorTypes(toBooleanPrimitives(motorList.toArray(new Boolean[motorList.size()])));
				ProfilesManager.saveProfiles();
				Robot.rbt.initRobotComponents();
				Toast.makeText(activity, R.string.message_motors_saved, Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});
		listAdapter = new AdapterMotorsList(activity, motorList);
		ListView listViewMotors = (ListView) view.findViewById(R.id.listView_profile_motors);
		listViewMotors.setAdapter(listAdapter);
		listViewMotors.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				motorList.set(position, !motorList.get(position));
				listAdapter.notifyDataSetChanged();
			}
		});

		this.dialog = dialogBuilder.create();
	}

	public void show()
	{
		dialog.show();
	}

	public Boolean[] toBooleanObjects(boolean[] primitives)
	{
		Boolean[] booleans = new Boolean[primitives.length];
		for(int i = 0; i < primitives.length; i++)
		{
			booleans[i] = primitives[i];
		}
		return booleans;
	}

	public boolean[] toBooleanPrimitives(Boolean[] booleans)
	{
		boolean[] primitives = new boolean[booleans.length];
		for(int i = 0; i < booleans.length; i++)
		{
			primitives[i] = booleans[i];
		}
		return primitives;
	}
}
