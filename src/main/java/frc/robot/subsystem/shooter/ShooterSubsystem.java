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
  private static final int MOTOR_RPM = 5700; 
  private static final double SHOOT_DEFAULT_SETPOINT = 1;


  //These methods should go somewhere else in better designed code, but we are in a rush.
  /**
   * Calculate the expected speed at the setpoint. 
   * @param shooterSubsystemModel shooter state
   * @return return speed in RPM
   */
  public double calculateSetpointSpeed(ShooterSubsystemModel shooterSubsystemModel) {
    switch (shooterSubsystemModel.shooterState) {
      case STOPPED:
        return 0.0;
      case SHOOT_DEFAULT:
        return ShooterSubsystem.SHOOT_DEFAULT_SETPOINT * ShooterSubsystem.MOTOR_RPM;
      default:
        break;
    }
    return 0.0;
  }

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
