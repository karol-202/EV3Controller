package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import pl.karol202.ev3controller.dialog.DialogMotorsTypes;
import pl.karol202.ev3controller.Profile;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;

public class ActivityEditProfile extends Activity implements AdapterView.OnItemClickListener
{
	private Profile profile;
	private int profileId;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		listView = (ListView) findViewById(R.id.listView_properties);
		listView.setOnItemClickListener(this);
		profileId = getIntent().getIntExtra("profileId", -1);
		if(profileId == -1) System.out.println("Profile id not passed to the profile edit activity!");
		if(profileId >= ProfilesManager.profiles.size())
		{
			ProfilesManager.profiles.add(new Profile());
			ProfilesManager.saveProfiles();
		}
		profile = ProfilesManager.profiles.get(profileId);
		setTitle("Profil: " + profile.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_edit_profile, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_apply_profile:
			finish();
			break;
		case R.id.item_delete_profile:
			showDeleteDialog();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		switch(position)
		{
		case 0:
			showNameDialog();
			break;
		case 1:
			new DialogMotorsTypes(this, profile).show();
			break;
		case 2:
			Intent intent = new Intent(this, ActivityControls.class);
			intent.putExtra("profileId", profileId);
			startActivity(intent);
			break;
		}
	}

	public void showDeleteDialog()
	{
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle(R.string.title_dialog_delete);
		dialogBuilder.setMessage(R.string.text_delete);
		dialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ProfilesManager.removeProfile(profileId);
				dialog.cancel();
				finish();
			}
		});
		dialogBuilder.setNegativeButton("Nie", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		dialogBuilder.create().show();
	}

	public void showNameDialog()
	{
		final Dialog dialog = new Dialog(this);
		dialog.setTitle(R.string.title_dialog_profile_name);
		dialog.setContentView(R.layout.dialog_profile_name);
		final EditText editTextName = (EditText) dialog.findViewById(R.id.editText_profile_name);
		final Button buttonAccept = (Button) dialog.findViewById(R.id.button_profile_name);
		editTextName.setText(profile.getName());
		buttonAccept.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				profile.setName(editTextName.getText().toString());
				ProfilesManager.saveProfiles();
				setTitle("Profil: " + profile.getName());
				dialog.cancel();
			}
		});
		dialog.show();
	}
}