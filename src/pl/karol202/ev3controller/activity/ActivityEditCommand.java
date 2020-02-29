package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.Command;
import pl.karol202.ev3controller.command.CommandsManager;

public abstract class ActivityEditCommand extends Activity
{
    protected Command command;
    protected int commandsListId;
    protected int commandId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        commandsListId = getIntent().getIntExtra("commandsListId", -1);
        commandId = getIntent().getIntExtra("commandId", -1);
        if(commandsListId == -1 || commandsListId >= CommandsManager.lists.size()) System.out.println("Not valid commands list!");
        if(commandId == -1)
        {
            commandId = CommandsManager.lists.get(commandsListId).getCommands().size();
            CommandsManager.lists.get(commandsListId).getCommands().add(createNewCommand());
            CommandsManager.saveCommands();
        }
        command = CommandsManager.lists.get(commandsListId).getCommands().get(commandId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_edit_command, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.item_delete_command:
                CommandsManager.removeCommand(commandsListId, commandId);
                finish();
                return true;
            case R.id.item_apply_command:
                applyCommand();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public abstract Command createNewCommand();

    protected void applyCommand() {}
}
