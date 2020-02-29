package pl.karol202.ev3controller.control;

import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.roundrules.RoundRules;
import pl.karol202.ev3controller.activity.ActivityEditControl;
import pl.karol202.ev3controller.activity.ActivityEditControlButton;
import pl.karol202.ev3controller.roundrules.RulesButton;

public enum ControlType
{
	BUTTON(Control.TYPE_BUTTON, R.string.name_control_button, R.drawable.arrow_down, ActivityEditControlButton.class, new RulesButton());

	private final int typeCode;
	private final int name;
	private final int image;
	private final Class<? extends ActivityEditControl> editActivity;
	private final RoundRules roundRules;

	ControlType(int typeCode, int name, int image, Class<? extends ActivityEditControl> editActivity, RoundRules roundRules)
	{
		this.typeCode = typeCode;
		this.name = name;
		this.image = image;
		this.editActivity = editActivity;
		this.roundRules = roundRules;
	}

	public int getTypeCode()
	{
		return typeCode;
	}

	public int getName()
	{
		return name;
	}

	public int getImage()
	{
		return image;
	}

	public Class<? extends ActivityEditControl> getEditActivity()
	{
		return editActivity;
	}

	public RoundRules getRoundRules()
	{
		return roundRules;
	}
}
