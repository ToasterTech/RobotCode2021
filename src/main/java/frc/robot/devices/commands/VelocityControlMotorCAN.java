/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.commands;

import java.util.Objects;


/**
 * Represents a command to Velocity Control.
 */
public class VelocityControlMotorCAN extends DeviceOutputCommand {
  public final String deviceId;
  public final double setpoint;
  public final int maxRPM;

  /**
   * Set up gains and setpoing for velocity control.
   * @param deviceId device name
   * @param setpoint setpoint for velocity control
   * @param maxRPM max RPM of motor
   */
  public VelocityControlMotorCAN(String deviceId, double setpoint, int maxRPM) {
    this.deviceId = deviceId;
    this.setpoint = setpoint;
    this.maxRPM = maxRPM;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof VelocityControlMotorCAN)) {
      return false;
    }
    VelocityControlMotorCAN otherVal = (VelocityControlMotorCAN) other;
    return (this.setpoint == otherVal.setpoint
      && this.deviceId == otherVal.deviceId
      && this.maxRPM == otherVal.maxRPM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.setpoint, 
      this.deviceId,
      this.maxRPM
    );
  }

  @Override
  public String getDeviceId() {
    return this.deviceId;
  }
  
}
