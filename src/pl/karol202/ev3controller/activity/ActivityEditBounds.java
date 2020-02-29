package pl.karol202.ev3controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsoluteLayout;
import pl.karol202.ev3controller.*;
import pl.karol202.ev3controller.control.Control;

import java.util.ArrayList;

public class ActivityEditBounds extends Activity
{
	public static final int RESULT_BOUNDS_EDITED = 1;

	private final BoundsEditor.BoundsChangeListener boundsListener = new BoundsEditor.BoundsChangeListener()
	{
		@Override
		public void boundsChanged(Rect newBounds)
		{
			bounds = newBounds;
			AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(bounds.width(), bounds.height(), bounds.left, bounds.top);
			view.setLayoutParams(params);
			layoutEditBounds.invalidate();
		}
	};

	private ViewGroup layoutEditBounds;
	private View view;
	private Rect bounds;
	private ArrayList<Rect> otherBounds;
	private BoundsEditor boundsEditor;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_bounds);
		layoutEditBounds = (ViewGroup) findViewById(R.id.layout_edit_bounds);
		int profileId  = getIntent().getIntExtra("profileId", -1);
		int controlId = getIntent().getIntExtra("controlId", -1);
		if(profileId == -1) System.out.println("Profile id haven't been sent to bounds edit activity.");
		if(controlId == -1) System.out.println("Control id haven't been sent to bounds edit activity.");
		Profile profile = ProfilesManager.profiles.get(profileId);
		Control editedControl = profile.getControls().get(controlId);
		otherBounds = new ArrayList<Rect>();
		for(int i = 0; i < profile.getControls().size(); i++)
		{
			if(i != controlId)
			{
				addControl(profile.getControls().get(i), false);
				otherBounds.add(profile.getControls().get(i).getBounds());
			}
		}
		bounds = editedControl.getBounds();
		boundsEditor = new BoundsEditor(editedControl, otherBounds, boundsListener);
		view = addControl(editedControl, true);
		layoutEditBounds.setOnTouchListener(boundsEditor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_edit_bounds, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.item_apply_bounds:
			Intent result = new Intent();
			result.putExtra("bounds", bounds);
			setResult(RESULT_BOUNDS_EDITED, result);
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public View addControl(Control control, boolean edited)
	{
		AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(control.getBounds().width(), control.getBounds().height(), control.getBounds().left, control.getBounds().top);
		View view = edited ? control.makeButton(this, bounds, otherBounds) : control.makeButton(this, false);
		view.setClickable(false);
		view.setLayoutParams(params);
		layoutEditBounds.addView(view);
		return view;
	}
}