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
import pl.karol202.ev3controller.adapter.AdapterControlsList;
import pl.karol202.ev3controller.control.ControlType;
import pl.karol202.ev3controller.dialog.DialogCommandType;
import pl.karol202.ev3controller.dialog.DialogControlType;

public class ActivityControls extends Activity implements AdapterView.OnItemClickListener
{
	private final DialogControlType.ControlTypeListener listener = new DialogControlType.ControlTypeListener()
	{
		@Override
		public void onControlTypeSelected(ControlType type)
		{
			addControl(type);
		}
	};

	private ArrayAdapter listAdapter;
	private ListView listView;
	private int profileId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controls);
		profileId = getIntent().getIntExtra("profileId", -1);
		if(profileId == -1 || profileId >= ProfilesManager.profiles.size()) System.out.println("Given profile id is not valid");
		listAdapter = new AdapterControlsList(this, ProfilesManager.profiles.get(profileId).getControls());
		listView = (ListView) findViewById(R.id.listView_profile_controls);
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
		inflater.inflate(R.menu.menu_activity_controls, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_apply_controls:
			finish();
			break;
		case R.id.item_add_control:
			DialogControlType dialog = new DialogControlType(this, listener);
			dialog.show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		editControl(position);
	}

	public void addControl(ControlType controlType)
	{
		Intent intent = new Intent(this, controlType.getEditActivity());
		intent.putExtra("profileId", profileId);
		startActivity(intent);
	}

	public void editControl(int position)
	{
		Intent intent = new Intent(this, ProfilesManager.profiles.get(profileId).getControls().get(position).getEditActivity());
		intent.putExtra("profileId", profileId);
		intent.putExtra("controlId", position);
		startActivity(intent);
	}
}