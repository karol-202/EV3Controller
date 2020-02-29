package pl.karol202.ev3controller.command;

import android.os.AsyncTask;
import android.widget.Toast;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.Robot;
import pl.karol202.ev3controller.activity.ActivityMain;

import java.util.ArrayList;

public class CommandWorker extends AsyncTask<Command, Integer, Void>
{
	public static void executeCommandsList(int listId)
	{
		CommandsList commandsList = CommandsManager.lists.get(listId);
		if(commandsList.containsErrors()) Toast.makeText(ActivityMain.mainActivity, R.string.message_commands_list_error, Toast.LENGTH_SHORT);
		ArrayList<Command> commands = commandsList.getCommands();
		new CommandWorker().execute(commands.toArray(new Command[commands.size()]));
	}

	@Override
	protected Void doInBackground(Command... params)
	{
		if(!Robot.rbt.checkConnected()) return null;
		for(Command command : params)
		{
			command.run();
		}
		return null;
	}
}
