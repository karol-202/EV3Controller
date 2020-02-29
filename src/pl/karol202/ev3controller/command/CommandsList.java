package pl.karol202.ev3controller.command;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class CommandsList
{
	private ArrayList<Command> commands;

	public CommandsList()
	{
		this.commands = new ArrayList<Command>();
	}

	public void addCommand(Command command)
	{
		this.commands.add(command);
	}

	public boolean containsErrors()
	{
		for(Command command : commands)
		{
			if(command.isError()) return true;
		}
		return false;
	}

	public static CommandsList loadCommandsList(int commandsList)
	{
		SharedPreferences prefs = CommandsManager.prefs;
		CommandsList commands = new CommandsList();
		int length = prefs.getInt("list" + commandsList + "commandsLength", 0);
		for(int i = 0; i < length; i++)
		{
			int commandType = prefs.getInt("list" + commandsList + "command" + i + "type", -1);
			Command command = null;
			switch(commandType)
			{
			case Command.TYPE_MOTOR:
				command = new CommandMotor();
				break;
			case Command.TYPE_MOTOR_STOP:
				command = new CommandMotorStop();
				break;
			default:
				System.out.println("Command type not set!");
			}
			command.loadCommand(commandsList, i);
			commands.addCommand(command);
		}
		return commands;
	}

	public void saveCommandsList(int commandsList)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		int length = commands.size();
		editor.putInt("list" + commandsList + "commandsLength", length);
		for(int i = 0; i < length; i++)
		{
			Command command = commands.get(i);
			editor.putInt("list" + commandsList + "command" + i + "type", command.getTypeCode());
			command.saveCommand(commandsList, i);
		}
	}

	public void removeCommandsList(int commandsList)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		editor.remove("list" + commandsList + "commandslength");
		for(int i = 0; i < commands.size(); i++)
		{
			commands.get(i).removeCommand(commandsList, i);
		}
	}

	public ArrayList<Command> getCommands()
	{
		return commands;
	}
}
