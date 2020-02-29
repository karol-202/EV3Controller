package pl.karol202.ev3controller;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pl.karol202.ev3controller.activity.ActivityMain;
import pl.karol202.ev3controller.control.Control;

import java.util.ArrayList;

public class ProfilesManager
{
	public static SharedPreferences prefs;
	public static SharedPreferences.Editor editor;
	public static ArrayList<Profile> profiles;
	public static int selectedProfile;

	public static void init()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.mainActivity);
		editor = prefs.edit();
		profiles = new ArrayList<Profile>();
		loadProfiles();
	}

	public static Profile getSelectedProfile()
	{
		return selectedProfile < profiles.size() && selectedProfile >= 0 ? profiles.get(selectedProfile) : null;
	}

	public static void loadProfiles()
	{
		int length = prefs.getInt("profilesLength", 0);
		for(int i = 0; i < length; i++)
		{
			profiles.add(Profile.loadProfile(i));
		}
		selectedProfile = prefs.getInt("selectedProfile", -1);
	}

	public static void saveProfiles()
	{
		editor.putInt("profilesLength", profiles.size());
		for(int i = 0; i < profiles.size(); i++)
		{
			profiles.get(i).saveProfile(i);
		}
		editor.putInt("selectedProfile", selectedProfile);
		editor.commit();
	}

	public static void removeProfile(int profileId)
	{
		if(profileId < 0 || profileId >= profiles.size()) System.out.println("Trying to delete non-existing profile");
		if(profileId == selectedProfile) selectedProfile = -1;
		if(profileId < selectedProfile) selectedProfile--;
		profiles.get(profileId).removeProfile(profileId);
		profiles.remove(profileId);
		editor.commit();
	}

	public static void removeControl(int profileId, int controlId)
	{
		if(profileId < 0 || profileId >= profiles.size()) System.out.println("Trying to delete non-existing profile");
		ArrayList<Control> controls = profiles.get(profileId).getControls();
		if(controlId < 0 || controlId >= controls.size()) System.out.println("Trying to delete non-existing control");
		controls.get(controlId).removeControl(profileId, controlId);
		controls.remove(controlId);
		editor.commit();
	}
}
