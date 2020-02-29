package pl.karol202.ev3controller;

public class RobotStatus
{
	private String ip;
	private String name;
	private float battery;

	public RobotStatus(String ip, String name, float battery)
	{
		this.ip = ip;
		this.name = name;
		this.battery = battery;
	}

	public String getIp()
	{
		return ip;
	}

	public String getName()
	{
		return name;
	}

	public float getBattery()
	{
		return battery;
	}
}
