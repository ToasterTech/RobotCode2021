/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.shooter;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.shooter.models.ShooterModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;

import java.util.Arrays;
import java.util.List;


/**
 * Subsystem for transforming shooter models to motor commands.
 */
public class ShooterSubsystem extends RobotSubsystem<ShooterModel> {

  @Override
  public List<DeviceOutputCommand> run(ShooterModel input) {
    if (input instanceof ShooterSubsystemModel) {
      ShooterSubsystemModel shooterSubsystemModel = (ShooterSubsystemModel) input;
      switch (shooterSubsystemModel.shooterState) {
        case STOPPED:
          return Arrays.asList(
            new GenericMotorCAN("shooterMotor", 0.0)
          );
        case SHOOT_DEFAULT:
          return Arrays.asList(
            new VelocityControlMotorCAN(
              "shooterMotor", 
              -1,
              .000330,
              .000000,
              .00002,
              .000025,
              .000175,
              5700
            )
          );
        default:
          break;
      }
    }
    throw new IllegalArgumentException(
      String.format("%s is not a supported model for ShooterSystem", input.getClass())
    );
  }
}
