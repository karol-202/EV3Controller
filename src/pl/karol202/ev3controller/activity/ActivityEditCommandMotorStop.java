package pl.karol202.ev3controller.activity;

import android.os.Bundle;
import android.widget.*;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.command.Command;
import pl.karol202.ev3controller.command.CommandMotor;
import pl.karol202.ev3controller.command.CommandMotorStop;
import pl.karol202.ev3controller.command.CommandsManager;

public class ActivityEditCommandMotorStop extends ActivityEditCommand
{
	private CommandMotorStop commandMotor;

    private Spinner spinnerPort;
    private Switch switchLock;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(!(command instanceof CommandMotorStop)) System.out.println("Command of command edit activity isn't of its type");
		commandMotor = (CommandMotorStop) command;
        setContentView(R.layout.activity_edit_command_motor_stop);
        spinnerPort = (Spinner) findViewById(R.id.spinner_motor_port);
        switchLock = (Switch) findViewById(R.id.switch_motor_lock);
        spinnerPort.setSelection(commandMotor.getMotorIndex());
        switchLock.setChecked(commandMotor.isLock());
    }

    @Override
    public Command createNewCommand()
    {
        CommandMotorStop command = new CommandMotorStop();
        //TODO Add expressions to command.
        return command;
    }

    @Override
    protected void applyCommand()
    {
        commandMotor.setMotorIndex(spinnerPort.getSelectedItemPosition());
		commandMotor.setLock(switchLock.isChecked());
		CommandsManager.saveCommands();
    }
}