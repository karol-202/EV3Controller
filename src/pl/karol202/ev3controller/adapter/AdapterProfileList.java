package pl.karol202.ev3controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import pl.karol202.ev3controller.Profile;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.Robot;

import java.util.ArrayList;

public class AdapterProfileList extends ArrayAdapter<Profile>
{
	private Context context;

	public AdapterProfileList(Context context, ArrayList<Profile> items)
	{
		super(context, R.layout.list_profiles_row, items);
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View view;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_profiles_row, parent, false);
		}
		else view = convertView;
		TextView textView = (TextView) view.findViewById(R.id.text_profile_name);
		RadioButton radioButton = (RadioButton) view.findViewById(R.id.radio_profile_selected);
		textView.setText(getItem(position).getName());
		radioButton.setOnCheckedChangeListener(null);
		radioButton.setChecked(position == ProfilesManager.selectedProfile);
		radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				System.out.println("checkedChanaged");
				System.out.println(buttonView.getText() + "   " + isChecked);
				if(isChecked)
				{
					ProfilesManager.selectedProfile = position;
					ProfilesManager.saveProfiles();
					notifyDataSetChanged();
					Robot.rbt.initRobotComponents();
				}
			}
		});
		return view;
	}
}
