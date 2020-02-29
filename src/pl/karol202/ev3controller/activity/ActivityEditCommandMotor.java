package pl.karol202.ev3controller.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.Command;
import pl.karol202.ev3controller.command.CommandMotor;
import pl.karol202.ev3controller.command.CommandsManager;

public class ActivityEditCommandMotor extends ActivityEditCommand implements CompoundButton.OnCheckedChangeListener
{
	private CommandMotor commandMotor;

    private Spinner spinnerPort;
    private EditText editTextSpeed;
    private ToggleButton toggleButtonLimit;
    private ToggleButton toggleButtonDirection;
    private EditText editTextLimit;
	private TextView textLimitDegrees;
    private ToggleButton toggleButtonRelation;
    private Switch switchBrake;
    private Switch switchImmediately;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!(command instanceof CommandMotor)) System.out.println("Command of command edit activity isn't of its type");
		commandMotor = (CommandMotor) command;
        setContentView(R.layout.activity_edit_command_motor);
        spinnerPort = (Spinner) findViewById(R.id.spinner_motor_port);
        editTextSpeed = (EditText) findViewById(R.id.edit_motor_speed);
        toggleButtonLimit = (ToggleButton) findViewById(R.id.toggle_motor_limit);
        toggleButtonDirection = (ToggleButton) findViewById(R.id.toggle_motor_direction);
        editTextLimit = (EditText) findViewById(R.id.edit_motor_limit);
		textLimitDegrees = (TextView) findViewById(R.id.text_motor_limit_degrees);
        toggleButtonRelation = (ToggleButton) findViewById(R.id.toggle_motor_relation);
        switchBrake = (Switch) findViewById(R.id.switch_motor_brake);
        switchImmediately = (Switch) findViewById(R.id.switch_motor_immediately);
        spinnerPort.setSelection(commandMotor.getMotorIndex());
        editTextSpeed.setText(Integer.toString(commandMotor.getSpeed() * ((commandMotor.getSpeed() < 0) ? -1 : 1)));
        toggleButtonLimit.setOnCheckedChangeListener(this);
        toggleButtonDirection.setChecked(commandMotor.getSpeed() < 0);
        if(commandMotor.getLimit() == -1)
		{
			toggleButtonLimit.setChecked(true);
			editTextLimit.setEnabled(false);
			textLimitDegrees.setEnabled(false);
			toggleButtonRelation.setEnabled(false);
			switchImmediately.setChecked(true);
			switchImmediately.setEnabled(false);
        }
        else
        {
            toggleButtonLimit.setChecked(false);
            editTextLimit.setText(Integer.toString(commandMotor.getLimit()));
			toggleButtonRelation.setChecked(!commandMotor.isRelativeToCurrent());
	        switchImmediately.setChecked(commandMotor.isImmediately());
        }
		switchBrake.setChecked(commandMotor.isBrake());
    }

    @Override
    public Command createNewCommand()
    {
        CommandMotor command = new CommandMotor();
        //TODO Add expressions to command.
        return command;
    }

    @Override
    protected void applyCommand()
    {
        String speedValue = editTextSpeed.getText().toString();
        String limitValue = editTextLimit.getText().toString();
        commandMotor.setMotorIndex(spinnerPort.getSelectedItemPosition());
		commandMotor.setSpeed(!speedValue.equals("") ? (Integer.parseInt(speedValue) * (toggleButtonDirection.isChecked() ? -1 : 1)) : 0);
		commandMotor.setLimit(toggleButtonLimit.isChecked() ? -1 : (!limitValue.equals("") ? Integer.parseInt(limitValue) : 0));
        commandMotor.setRelativeToCurrent(!toggleButtonRelation.isChecked());
		commandMotor.setBrake(switchBrake.isChecked());
		commandMotor.setImmediately(toggleButtonLimit.isChecked() || switchImmediately.isChecked());
		CommandsManager.saveCommands();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
    {
        editTextLimit.setEnabled(!isChecked);
		textLimitDegrees.setEnabled(!isChecked);
        toggleButtonRelation.setEnabled(!isChecked);
        if(isChecked)
        {
	        switchImmediately.setChecked(true);
	        switchImmediately.setEnabled(false);
        }
	    else switchImmediately.setEnabled(true);
    }
}