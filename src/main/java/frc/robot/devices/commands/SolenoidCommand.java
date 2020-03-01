/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.commands;

import java.util.Objects;


/**
 * Represents a command to.
 */
public class SolenoidCommand extends DeviceOutputCommand {
  public enum SolenoidState {
    OPEN,
    CLOSE
  }

  public final String deviceId;
  public final SolenoidState state;

  public SolenoidCommand(String deviceId, SolenoidState state) {
    this.deviceId = deviceId;
    this.state = state;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SolenoidCommand)) {
      return false;
    }
    SolenoidCommand otherVal = (SolenoidCommand) other;
    return this.state == otherVal.state & this.deviceId == otherVal.deviceId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.state, this.deviceId);
  }

  @Override
  public String getDeviceId() {
    return this.deviceId;
  }
  
}
