package pl.karol202.ev3controller.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import pl.karol202.ev3controller.command.CommandsList;
import pl.karol202.ev3controller.command.CommandsManager;
import pl.karol202.ev3controller.control.Control;
import pl.karol202.ev3controller.control.ControlButton;
import pl.karol202.ev3controller.ProfilesManager;
import pl.karol202.ev3controller.R;

public class ActivityEditControlButton extends ActivityEditControl implements View.OnClickListener
{
	private EditText editTextName;
	private Button buttonBounds;
	private Button buttonClickCommand;
	private Button buttonReleaseCommand;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(!(control instanceof ControlButton)) System.out.println("Control of control button edit activity isn't of its type.");
		setContentView(R.layout.activity_edit_control_button);
		editTextName = (EditText) findViewById(R.id.editText_control_button_name);
		buttonBounds = (Button) findViewById(R.id.button_control_button_bounds);
		buttonClickCommand = (Button) findViewById(R.id.button_control_button_click);
		buttonReleaseCommand = (Button) findViewById(R.id.button_control_button_release);
		editTextName.setText(control.getName());
		buttonBounds.setOnClickListener(this);
		buttonClickCommand.setOnClickListener(this);
		buttonReleaseCommand.setOnClickListener(this);
	}

	@Override
	public Control createNewControl(String name, Rect bounds)
	{
		ControlButton control = new ControlButton(name, bounds);
		control.setClickCommand(CommandsManager.lists.size());
		CommandsManager.lists.add(new CommandsList());
		control.setReleaseCommand(CommandsManager.lists.size());
		CommandsManager.lists.add(new CommandsList());
		CommandsManager.saveCommands();
		return control;
	}

	@Override
	protected void applyControl()
	{
		control.setName(editTextName.getText().toString());
		ProfilesManager.saveProfiles();
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.button_control_button_bounds:
			editBounds();
			break;
		case R.id.button_control_button_click:
			editCommands(((ControlButton) control).getClickCommand());
			break;
		case R.id.button_control_button_release:
			editCommands(((ControlButton) control).getReleaseCommand());
			break;
		}
	}
}