/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorPWM;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.drive.models.DriveModel;

import java.util.Arrays;
import java.util.List;


/**
 * Subsystem for transforming drive models to motor commands.
 */
public class DriveSubsystem extends RobotSubsystem<DriveModel> {
  @Override
  public List<DeviceOutputCommand> run(DriveModel input) {
    if (input instanceof DifferentialDriveModel) {
      DifferentialDriveModel differentialDriveModel = (DifferentialDriveModel) input;
      return Arrays.asList(
        new GenericMotorPWM("leftMotor1", differentialDriveModel.left),
        new GenericMotorPWM("leftMotor2", differentialDriveModel.left),
        new GenericMotorPWM("rightMotor1", differentialDriveModel.right),
        new GenericMotorPWM("rightMotor2", differentialDriveModel.right)
      );
    }
    throw new IllegalArgumentException(
      String.format("%s is not a supported model for DriveSubsystem", input.getClass())
    );
  }
}
