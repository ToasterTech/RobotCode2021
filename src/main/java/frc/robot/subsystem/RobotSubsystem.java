/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.models.Model;
import java.util.List;

/**
 * A robot subystem to generate device output commands.
 */
public abstract class RobotSubsystem<InputModelT extends Model> {
  public abstract List<DeviceOutputCommand> run(InputModelT input);
}
