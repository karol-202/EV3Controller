package pl.karol202.ev3controller;

import android.content.SharedPreferences;
import pl.karol202.ev3controller.activity.ActivityMain;
import pl.karol202.ev3controller.control.Control;

import java.util.ArrayList;

public class Profile
{
	public static boolean MOTOR_LARGE = true;
	public static boolean MOTOR_MEDIUM = false;

	private String name;
	private boolean[] motorTypes;
	private ArrayList<Control> controls;

	public Profile()
	{
		name = ActivityMain.mainActivity.getString(R.string.name_new_profile);
		motorTypes = new boolean[4];
		controls = new ArrayList<Control>();
	}

	public Profile(String name, boolean[] motorTypes, ArrayList<Control> controls)
	{
		this.name = name;
		this.controls = controls;
		setMotorTypes(motorTypes);
	}

	public char getMotorType(int position)
	{
		if(position < 4)
		{
			return motorTypes[position] ? 'L' : 'M';
		}
		System.out.println("Motors types array have not valid length");
		return 'L';
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean[] getMotorTypes()
	{
		return motorTypes;
	}

	public void setMotorTypes(boolean[] motorTypes)
	{
		if(motorTypes.length != 4) System.out.println("Motor types array has not valid length.");
		this.motorTypes = motorTypes;
	}

	public ArrayList<Control> getControls()
	{
		return controls;
	}

	public static Profile loadProfile(int profile)
	{
		SharedPreferences prefs = ProfilesManager.prefs;
		String name = prefs.getString("profile" + profile + "name", "Bez nazwy");
		boolean[] motorTypes = new boolean[4];
		for(int j = 0; j < motorTypes.length; j++)
		{
			motorTypes[j] = prefs.getBoolean("profile" + profile + "motor" + j, true);
		}
		int controlsLength = prefs.getInt("profile" + profile + "controlsLength", 0);
		ArrayList<Control> controls = new ArrayList<Control>();
		for(int j = 0; j < controlsLength; j++)
		{
			controls.add(Control.loadControl(profile, j));
		}
		return new Profile(name, motorTypes, controls);
	}

	public void saveProfile(int profile)
	{
		SharedPreferences.Editor editor = ProfilesManager.editor;
		editor.putString("profile" + profile + "name", getName());
		boolean[] motorTypes = getMotorTypes();
		for(int j = 0; j < 4; j++)
		{
			editor.putBoolean("profile" + profile + "motor" + j, motorTypes[j]);
		}
		ArrayList<Control> controls = getControls();
		editor.putInt("profile" + profile + "controlsLength", controls.size());
		for(int j = 0; j < controls.size(); j++)
		{
			controls.get(j).saveControl(profile, j);
		}
	}

	public void removeProfile(int profile)
	{
		SharedPreferences.Editor editor = ProfilesManager.editor;
		editor.remove("profile" + profile + "name");
		for(int j = 0; j < 4; j++)
		{
			editor.remove("profile" + profile + "motor" + j);
		}
		editor.remove("profile" + profile + "controlsLength");
		for(int j = 0; j < getControls().size(); j++)
		{
			controls.get(j).removeControl(profile, j);
		}
	}
}
