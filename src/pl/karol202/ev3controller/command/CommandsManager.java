package pl.karol202.ev3controller.command;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pl.karol202.ev3controller.activity.ActivityMain;

import java.util.ArrayList;

public class CommandsManager
{
	public static SharedPreferences prefs;
	public static SharedPreferences.Editor editor;
	public static ArrayList<CommandsList> lists;

	public static void init()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(ActivityMain.mainActivity);
		editor = prefs.edit();
		lists = new ArrayList<CommandsList>();
		loadCommands();
	}

	public static void loadCommands()
	{
		int length = prefs.getInt("commandsListsLength", 0);
		for(int i = 0; i < length; i++)
		{
			lists.add(CommandsList.loadCommandsList(i));
		}
	}

	public static void saveCommands()
	{
		int length = lists.size();
		editor.putInt("commandsListsLength", length);
		for(int i = 0; i < length; i++)
		{
			lists.get(i).saveCommandsList(i);
		}
		editor.commit();
	}

	public static void removeCommandsList(int commandsListId)
	{
		if(commandsListId < -1 || commandsListId >= lists.size()) System.out.println("Trying to delete non-existing commands list");
		lists.get(commandsListId).removeCommandsList(commandsListId);
		lists.remove(commandsListId);
		editor.commit();
	}

	public static void removeCommand(int commandsListId, int commandId)
	{
		if(commandsListId < -1 || commandsListId >= lists.size()) System.out.println("Trying to delete non-existing command");
		lists.get(commandsListId).getCommands().get(commandId).removeCommand(commandsListId, commandId);
		lists.get(commandsListId).getCommands().remove(commandId);
		editor.commit();
	}
}
