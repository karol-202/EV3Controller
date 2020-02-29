package pl.karol202.ev3controller.control;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.command.Command;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.roundrules.RoundRules;
import pl.karol202.ev3controller.activity.ActivityEditControl;

import java.util.ArrayList;

public abstract class Control implements Parcelable
{
	public static final int TYPE_BUTTON = 0;

	private String name;
	private Rect bounds;

	public Control() {}

	public Control(String name, Rect rect)
	{
		this.name = name;
		this.bounds = rect;
	}

	public abstract View makeButton(Activity activity, boolean enabled);

	public abstract View makeButton(Activity activity, Rect bounds, ArrayList<Rect> otherBounds);

	public abstract void loadProperties(int profile, int controlId);

	public abstract int getTypeCode();

	public abstract int getIcon();

	public abstract Class<? extends ActivityEditControl> getEditActivity();

	public abstract RoundRules getRoundRules();

	public static Control loadControl(int profile, int controlId)
	{
		SharedPreferences prefs = ProfilesManager.prefs;
		String header = getHeader(profile, controlId);
		int type = prefs.getInt(header + "type", -1);
		Rect bounds = new Rect();
		bounds.left = prefs.getInt(header + "bounds" + 0, 0);
		bounds.top = prefs.getInt(header + "bounds" + 1, 0);
		bounds.right = prefs.getInt(header + "bounds" + 2, 0);
		bounds.bottom = prefs.getInt(header + "bounds" + 3, 0);
		String name = prefs.getString(header + "name", "Bez nazwy");
		Control control;
		switch(type)
		{
		case TYPE_BUTTON:
			control = new ControlButton(name, bounds);
			break;
		default:
			control = null;
			System.out.println("No control type set");
		}
		control.loadProperties(profile, controlId);
		return control;
	}

	public void saveControl(int profile, int controlId)
	{
		SharedPreferences.Editor editor = ProfilesManager.editor;
		String header = getHeader(profile, controlId);
		editor.putInt(header + "type", getTypeCode());
		editor.putInt(header + "bounds" + 0, bounds.left);
		editor.putInt(header + "bounds" + 1, bounds.top);
		editor.putInt(header + "bounds" + 2, bounds.right);
		editor.putInt(header + "bounds" + 3, bounds.bottom);
		editor.putString(header + "name", name);
	}

	public void removeControl(int profile, int controlId)
	{
		SharedPreferences.Editor editor = ProfilesManager.editor;
		String header = getHeader(profile, controlId);
		editor.remove(header + "type");
		editor.remove(header + "bounds" + 0);
		editor.remove(header + "bounds" + 1);
		editor.remove(header + "bounds" + 2);
		editor.remove(header + "bounds" + 3);
		editor.remove(header + "name");
	}

	public static String getHeader(int i, int j)
	{
		return "profile" + i + "control" + j;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Rect getBounds()
	{
		return bounds;
	}

	public void setBounds(Rect bounds)
	{
		this.bounds = bounds;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<Control> CREATOR = new Creator<Control>()
	{
		public Control createFromParcel(Parcel in)
		{
			Control control;
			int type = in.readInt();
			switch(type)
			{
			case TYPE_BUTTON:
				control = new ControlButton();
				break;
			default:
				control = null;
			}
			control.readFromParcel(in);
			return control;
		}

		public Control[] newArray(int size)
		{
			return new Control[size];
		}
	};

	protected void readFromParcel(Parcel in)
	{
		name = in.readString();
		bounds = in.readParcelable(null);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(getTypeCode());
		dest.writeString(name);
		dest.writeParcelable(bounds, flags);
	}
}
