package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pl.karol202.ev3controller.*;
import pl.karol202.ev3controller.adapter.AdapterCommandsList;
import pl.karol202.ev3controller.command.CommandType;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.control.ControlType;
import pl.karol202.ev3controller.dialog.DialogCommandType;
import pl.karol202.ev3controller.dialog.DialogControlType;

public class ActivityCommands extends Activity implements AdapterView.OnItemClickListener
{
	private final DialogCommandType.CommandTypeListener listener = new DialogCommandType.CommandTypeListener()
    {
        @Override
        public void onCommandTypeSelected(CommandType type)
        {
            addCommand(type);
        }
    };

	private ArrayAdapter listAdapter;
	private ListView listView;
	private int commandsListId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commands);
		commandsListId = getIntent().getIntExtra("commandsListId", -1);
		if(commandsListId == -1 || commandsListId >= CommandsManager.lists.size()) System.out.println("Commands list id not sent to commands activity.");
		listAdapter = new AdapterCommandsList(this, CommandsManager.lists.get(commandsListId).getCommands());
		listView = (ListView) findViewById(R.id.listView_commands);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_commands, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_add_command:
			DialogCommandType dialog = new DialogCommandType(this, listener);
			dialog.show();
			break;
		case R.id.item_apply_commands:
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		editCommand(position);
	}

	public void addCommand(CommandType commandType)
	{
		Intent intent = new Intent(this, commandType.getEditActivity());
		intent.putExtra("commandsListId", commandsListId);
		startActivity(intent);
	}

	public void editCommand(int position)
	{
		Intent intent = new Intent(this, CommandsManager.lists.get(commandsListId).getCommands().get(position).getEditActivity());
		intent.putExtra("commandsListId", commandsListId);
		intent.putExtra("commandId", position);
		startActivity(intent);
	}
}
