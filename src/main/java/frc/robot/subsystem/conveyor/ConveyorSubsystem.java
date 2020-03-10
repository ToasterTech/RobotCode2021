/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.conveyor;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.SolenoidCommand;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.conveyor.models.ConveyorModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import java.util.Arrays;
import java.util.List;

/**
 * Subsystem for transforming intake models to motor commands.
 */
public class ConveyorSubsystem extends RobotSubsystem<ConveyorModel> {
  @Override
  public List<DeviceOutputCommand> run(ConveyorModel input) {
    DeviceOutputCommand motorCommand;
    DeviceOutputCommand intakeArmCommand;
    DeviceOutputCommand shooterBlockerCommand;

    if (input instanceof ConveyorSystemModel) {
      ConveyorSystemModel conveyorSystemModel = (ConveyorSystemModel) input;
      if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.STOPPED) {
        motorCommand = new GenericMotorCAN("conveyorMotor", 0.0);
      } else if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.INTAKE) {
        motorCommand = new VelocityControlMotorCAN(
          "conveyorMotor", 
          -.5,
          5700
        );
      } else if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.OUTTAKE) {
        motorCommand = new VelocityControlMotorCAN(
          "conveyorMotor", 
          .32,
          5700
        );
      } else {
        motorCommand = new GenericMotorCAN("conveyorMotor", 0.0);
      }

      if (conveyorSystemModel.intakePosition == ConveyorSystemModel.IntakePosition.UP) {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      } else if (conveyorSystemModel.intakePosition == ConveyorSystemModel.IntakePosition.DOWN) {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.OPEN);
      } else {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      }

      if (conveyorSystemModel.shooterBlockState == ConveyorSystemModel.ShooterBlockState.OPEN) {
        shooterBlockerCommand = new SolenoidCommand("intakeStop", SolenoidCommand.SolenoidState.OPEN);
      } else if (conveyorSystemModel.shooterBlockState == ConveyorSystemModel.ShooterBlockState.CLOSE) {
        shooterBlockerCommand = new SolenoidCommand("intakeStop", SolenoidCommand.SolenoidState.CLOSE);
      } else {
        shooterBlockerCommand = new SolenoidCommand("intakeStop", SolenoidCommand.SolenoidState.CLOSE);
      }

    } else {
      intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      shooterBlockerCommand = new SolenoidCommand("intakeStop", SolenoidCommand.SolenoidState.CLOSE);
      motorCommand = new GenericMotorCAN("conveyorMotor", 0.0);
    }
    return Arrays.asList(
      motorCommand,
      intakeArmCommand,
      shooterBlockerCommand
    );  
  }
}
