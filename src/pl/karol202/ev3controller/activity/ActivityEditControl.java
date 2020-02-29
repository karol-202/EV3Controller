package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import pl.karol202.ev3controller.control.Control;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;

public abstract class ActivityEditControl extends Activity implements OnClickListener
{
	private final int REQUEST_EDIT_BOUNDS = 0;

	protected Control control;
	protected int profileId;
	protected int controlId;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		profileId = getIntent().getIntExtra("profileId", -1);
		controlId = getIntent().getIntExtra("controlId", -1);
		if(profileId == -1 || profileId >= ProfilesManager.profiles.size()) System.out.println("Not valid profile!");
		if(controlId == -1)
		{
			controlId = ProfilesManager.profiles.get(profileId).getControls().size();
			ProfilesManager.profiles.get(profileId).getControls().add(createNewControl(getString(R.string.name_new_control), new Rect(100, 100, 300, 250)));
			ProfilesManager.saveProfiles();
		}
		control = ProfilesManager.profiles.get(profileId).getControls().get(controlId);
	}

	public abstract Control createNewControl(String name, Rect bounds);

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_edit_control, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_delete_control:
			ProfilesManager.removeControl(profileId, controlId);
			finish();
			return true;
		case R.id.item_apply_control:
			applyControl();
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void applyControl() {}

	protected void editBounds()
	{
		Intent intent = new Intent(this, ActivityEditBounds.class);
		intent.putExtra("profileId", profileId);
		intent.putExtra("controlId", controlId);
		startActivityForResult(intent, REQUEST_EDIT_BOUNDS);
	}

	protected void editCommands(int commandsListId)
	{
		Intent intent = new Intent(this, ActivityCommands.class);
		intent.putExtra("commandsListId", commandsListId);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case REQUEST_EDIT_BOUNDS:
			if(resultCode == ActivityEditBounds.RESULT_BOUNDS_EDITED)
			{
				Rect bounds = data.getParcelableExtra("bounds");
				control.setBounds(bounds);
				ProfilesManager.saveProfiles();
			}
			break;
		}
	}
}
