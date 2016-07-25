package com.palyrobotics.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Dashboard {
	private NetworkTable table;
	private DriverStation ds;
	private boolean checkingAlliance;
	
	public Dashboard(DriverStation driverstation) {
		table = NetworkTable.getTable("RobotTable");
		this.ds = driverstation;
		this.checkingAlliance = true;
	}
	
	/**
	 * Put the default values into the dashboard
	 */
	public void initDashboard() {
		table.putNumber("match-time", 90);
		table.putString("game-period", "DISABLED");
		table.putBoolean("brownout-status", false);
		table.putNumber("battery", 12.0);
		table.putString("alliance", "invalid");
		
	}
	
	/**
	 * Update a specific key on the NetworkTables 
	 * @param key to update
	 * @param doubleValue
	 * @param stringValue
	 * @param booleanValue
	 * @param type double, string, or boolean
	 */
	public void updateKey(String key, double doubleValue, String stringValue, boolean booleanValue, String type) {
		switch(type) {
		case "string":
			table.putString(key, stringValue);
			break;
		case "double":
			table.putNumber(key, doubleValue);
			break;
		case "boolean":
			table.putBoolean(key, booleanValue);
		}
	}
	
	/**
	 * Update the values of the dashboard
	 */
	public void updateDashboard() {
		if(checkingAlliance) {
			switch(ds.getAlliance()) {
			case Blue:
				table.putString("alliance", "blue");
				checkingAlliance = false;
				break;
			case Red:
				table.putString("alliance", "red");
				checkingAlliance = false;
				break;
			case Invalid:
				table.putString("alliance", "invalid");
			}
		}
		
		table.putNumber("match-time", ds.getMatchTime());
		table.putBoolean("brownout-status", ds.isBrownedOut());
		table.putNumber("battery", ds.getBatteryVoltage());
		if(ds.isAutonomous()) {
			table.putString("game-period", "Autonomous");
		} else if (ds.isDisabled()) {
			table.putString("game-period", "Disabled");
		} else if (ds.isOperatorControl()) {
			table.putString("game-period", "TeleOperated");
		} else if (ds.isTest()) {
			table.putString("game-period", "Test");
		} else {
			table.putString("game-period", "Unidentified");
		}
		
	}
}
