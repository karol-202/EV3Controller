package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsoluteLayout;
import android.widget.TextView;
import android.widget.Toast;
import pl.karol202.ev3controller.*;
import pl.karol202.ev3controller.Controller.ResultListener;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.control.Control;
import pl.karol202.ev3controller.expression.ExpressionManager;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityMain extends Activity
{
	private final ResultListener listener = new ResultListener()
	{
		@Override
		public void onPostExecute(Result result)
		{
			switch(result.getResultCode())
			{
			case Result.RESULT_CONNECTED:
				update();
				Toast.makeText(mainActivity, "Połączono", Toast.LENGTH_LONG).show();
				break;
			case Result.RESULT_DISCONNECTED:
				update();
				Toast.makeText(mainActivity, "Rozłączono", Toast.LENGTH_LONG).show();
				break;
			case Result.RESULT_EXCEPTION:
				update();
				Toast.makeText(mainActivity, "Błąd", Toast.LENGTH_LONG).show();
				break;
			case Result.RESULT_NOT_CONNECTED:
				Toast.makeText(mainActivity, "Nie połączono", Toast.LENGTH_LONG).show();
				break;
			case Result.RESULT_ALREADY_CONNECTED:
				Toast.makeText(mainActivity, "Już połączono", Toast.LENGTH_LONG).show();
				break;
			case Result.RESULT_UPDATE_STATUS:
				if(result.getData() instanceof RobotStatus)
				{
					update((RobotStatus) result.getData());
				}
				else System.out.println("Result is not instance of RobotStatus");
				break;
			}
		}
	};

	public static ActivityMain mainActivity;

	private Robot rbt;
	private MenuItem connectionItem;
	private ViewGroup layoutMain;
	private ViewGroup layoutStatus;
	private TextView textIp;
	private TextView textName;
	private TextView textBattery;
	private ArrayList<Control> controls;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActivityMain.mainActivity = this;
		rbt = new Robot();
		ProfilesManager.init();
		CommandsManager.init();
		ExpressionManager.init();
		layoutMain = (ViewGroup) findViewById(R.id.layout_main);
		layoutStatus = (ViewGroup) findViewById(R.id.layout_status);
		textIp = (TextView) findViewById(R.id.text_connected_ip);
		textName = (TextView) findViewById(R.id.text_connected_name);
		textBattery = (TextView) findViewById(R.id.text_connected_battery);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		layoutMain.removeAllViews();
		Profile profile = ProfilesManager.getSelectedProfile();
		if(profile != null)
		{
			controls = profile.getControls();
			addControls();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_main, menu);
		connectionItem = menu.findItem(R.id.item_connect);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_connect:
			if(rbt.ev3 == null) rbt.connect(this, listener);
			else rbt.disconnect(this, listener);
			break;
		case R.id.item_profiles:
			Intent intent = new Intent(this, ActivityProfiles.class);
			startActivity(intent);
			break;
		case R.id.item_stop_motors:
			rbt.stopAllMotors();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void update()
	{
		if(rbt.ev3 != null)
		{
			rbt.getStatus(listener, Result.RESULT_UPDATE_STATUS);
		}
		else
		{
			connectionItem.setTitle("Połącz");
			layoutStatus.setBackgroundColor(getResources().getColor(R.color.color_not_connected));
			textIp.setVisibility(View.GONE);
			textBattery.setVisibility(View.GONE);
			textName.setText(getText(R.string.text_not_connected));
		}
	}

	public void update(RobotStatus status)
	{
		connectionItem.setTitle("Rozłącz");
		layoutStatus.setBackgroundColor(getResources().getColor(R.color.color_connected));
		textIp.setText(status.getIp());
		textName.setText(status.getName());
		textBattery.setText(String.format(Locale.US, "%.2f", status.getBattery()));
		textIp.setVisibility(View.VISIBLE);
		textBattery.setVisibility(View.VISIBLE);
	}

	public void addControls()
	{
		layoutMain.removeAllViews();
		for(Control control : controls)
		{
			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(control.getBounds().width(), control.getBounds().height(), control.getBounds().left, control.getBounds().top);
			View view = control.makeButton(this, true);
			view.setLayoutParams(params);
			layoutMain.addView(view);
		}
	}
}