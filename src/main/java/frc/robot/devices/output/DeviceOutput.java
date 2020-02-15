/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.output;

import frc.robot.devices.commands.DeviceOutputCommand;

/**
 * Code to actually output commands to hardware devices.
 */
public abstract class DeviceOutput {
  /**
   * Checks if the given command is valid for this device output.
   * @param command input command
   */
  public abstract boolean isValidCommand(DeviceOutputCommand command);

  /**
   * Sets the device to the value specified by command.
   * @param command input command
   */
  public abstract void run(DeviceOutputCommand command);
}
