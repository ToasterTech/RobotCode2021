/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.commands;

import java.util.Objects;


/**
 * Represents a command to set the percent of a CAN motor.
 */
public class GenericMotorCAN extends DeviceOutputCommand {
  public final String deviceId;
  public final double setpoint;

  public GenericMotorCAN(String deviceId, double setpoint) {
    this.deviceId = deviceId;
    this.setpoint = setpoint;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GenericMotorCAN)) {
      return false;
    }
    GenericMotorCAN otherVal = (GenericMotorCAN) other;
    return this.setpoint == otherVal.setpoint && this.deviceId.equals(otherVal.deviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.setpoint, this.deviceId);
  }

  @Override
  public String getDeviceId() {
    return this.deviceId;
  }
  
}
