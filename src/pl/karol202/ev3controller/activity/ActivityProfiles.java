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
import pl.karol202.ev3controller.adapter.AdapterProfileList;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;

public class ActivityProfiles extends Activity implements AdapterView.OnItemClickListener
{
	private ArrayAdapter listAdapter;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profiles);
		listAdapter = new AdapterProfileList(this, ProfilesManager.profiles);
		listView = (ListView) findViewById(R.id.listView_profiles);
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
		inflater.inflate(R.menu.menu_activity_profiles, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_add_profile:
			editProfile(ProfilesManager.profiles.size());
			return true;
		case R.id.item_apply_profiles:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		editProfile(position);
	}

	public void editProfile(int position)
	{
		Intent intent = new Intent(this, ActivityEditProfile.class);
		intent.putExtra("profileId", position);
		startActivity(intent);
	}
}