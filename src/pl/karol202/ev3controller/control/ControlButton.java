package pl.karol202.ev3controller.control;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import pl.karol202.ev3controller.command.CommandWorker;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.activity.ActivityEditControl;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.roundrules.RoundRules;

import java.util.ArrayList;

public class ControlButton extends Control implements OnTouchListener
{
	private int clickCommands;
	private int releaseCommands;

	public ControlButton()
	{
		super();
	}

	public ControlButton(String name, Rect bounds)
	{
		super(name, bounds);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN) CommandWorker.executeCommandsList(clickCommands);
		if(event.getAction() == MotionEvent.ACTION_UP) CommandWorker.executeCommandsList(releaseCommands);
		return v.onTouchEvent(event);
	}

	@Override
	public View makeButton(Activity activity, boolean enabled)
	{
		Button button = new Button(activity);
		button.setText(getName());
		if(enabled) button.setOnTouchListener(this);
		return button;
	}

	@Override
	public View makeButton(Activity activity, final Rect bounds, final ArrayList<Rect> otherBounds)
	{
		Button button = new Button(activity)
		{
			@Override
			protected void onDraw(Canvas canvas)
			{
				super.onDraw(canvas);
				getRoundRules().onDraw(canvas, bounds, otherBounds);
			}
		};
		button.setText(getName());
		return button;
	}

	@Override
	public void loadProperties(int profile, int controlId)
	{
		SharedPreferences prefs = ProfilesManager.prefs;
		String header = getHeader(profile, controlId);
		clickCommands = prefs.getInt(header + "click", -1);
		releaseCommands = prefs.getInt(header + "release", -1);
	}

	@Override
	public void saveControl(int profile, int controlId)
	{
		super.saveControl(profile, controlId);
		SharedPreferences.Editor editor = ProfilesManager.editor;
		String header = getHeader(profile, controlId);
		editor.putInt(header + "click", clickCommands);
		editor.putInt(header + "release", releaseCommands);
	}

	@Override
	public void removeControl(int profile, int controlId)
	{
		super.removeControl(profile, controlId);
		SharedPreferences.Editor editor = ProfilesManager.editor;
		String header = getHeader(profile, controlId);
		editor.remove(header + "click");
		editor.remove(header + "release");

		CommandsManager.removeCommandsList(clickCommands);
		CommandsManager.removeCommandsList(releaseCommands);
	}

	@Override
	protected void readFromParcel(Parcel in)
	{
		super.readFromParcel(in);
		clickCommands = in.readInt();
		releaseCommands = in.readInt();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		super.writeToParcel(dest, flags);
		dest.writeInt(clickCommands);
		dest.writeInt(releaseCommands);
	}

	@Override
	public int getTypeCode()
	{
		return ControlType.BUTTON.getTypeCode();
	}

	@Override
	public int getIcon()
	{
		return ControlType.BUTTON.getImage();
	}

	@Override
	public Class<? extends ActivityEditControl> getEditActivity()
	{
		return ControlType.BUTTON.getEditActivity();
	}

	@Override
	public RoundRules getRoundRules()
	{
		return ControlType.BUTTON.getRoundRules();
	}

	public int getClickCommand()
	{
		return clickCommands;
	}

	public void setClickCommand(int clickCommand)
	{
		this.clickCommands = clickCommand;
	}

	public int getReleaseCommand()
	{
		return releaseCommands;
	}

	public void setReleaseCommand(int releaseCommand)
	{
		this.releaseCommands = releaseCommand;
	}
}
