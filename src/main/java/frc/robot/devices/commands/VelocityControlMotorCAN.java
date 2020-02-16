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
  public final double gainP;
  public final double gainI;
  public final double gainD;
  public final double zoneI;
  public final double gainFF;
  public final int maxRPM;

  /**
   * Set up gains and setpoing for velocity control.
   * @param deviceId device name
   * @param setpoint setpoint for velocity control
   * @param gainP propotional gain
   * @param gainI integral gain
   * @param gainD differential gain
   * @param zoneI integral gain zone
   * @param gainFF feed forward gain
   * @param maxRPM max RPM of motor
   */
  public VelocityControlMotorCAN(String deviceId, double setpoint,
                                 double gainP, double gainI, double gainD,
                                 double zoneI, double gainFF, int maxRPM) {
    this.deviceId = deviceId;
    this.setpoint = setpoint;
    this.gainP = gainP;
    this.gainI = gainI;
    this.gainD = gainD;
    this.zoneI = zoneI;
    this.gainFF = gainFF;
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
      && this.gainP == otherVal.gainP
      && this.gainI == otherVal.gainI
      && this.gainD == otherVal.gainD
      && this.zoneI == otherVal.zoneI
      && this.gainFF == otherVal.gainFF);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.setpoint, 
      this.deviceId,
      this.gainP,
      this.gainI,
      this.gainD,
      this.zoneI,
      this.gainFF
    );
  }

  @Override
  public String getDeviceId() {
    return this.deviceId;
  }
  
}
