/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.intake;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.SolenoidCommand;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.intake.models.IntakeModel;
import frc.robot.subsystem.intake.models.IntakeSystemModel;

import java.util.Arrays;
import java.util.List;

/**
 * Subsystem for transforming intake models to motor commands.
 */
public class IntakeSubsytem extends RobotSubsystem<IntakeModel> {
  @Override
  public List<DeviceOutputCommand> run(IntakeModel input) {
    DeviceOutputCommand motorCommand;
    DeviceOutputCommand intakeArmCommand;

    if (input instanceof IntakeSystemModel) {
      IntakeSystemModel conveyorSystemModel = (IntakeSystemModel) input;
      if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.STOPPED) {
        motorCommand = new GenericMotorCAN("intakeMotor", 0.0);
      } else if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.INTAKE) {
        motorCommand = new VelocityControlMotorCAN(
          "intakeMotor", 
          -.5,
          5700
        );
      } else if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.OUTTAKE) {
        motorCommand = new VelocityControlMotorCAN(
          "intakeMotor", 
          .32,
          5700
        );
      } else {
        motorCommand = new GenericMotorCAN("intakeMotor", 0.0);
      }

      if (conveyorSystemModel.intakePosition == IntakeSystemModel.IntakePosition.UP) {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      } else if (conveyorSystemModel.intakePosition == IntakeSystemModel.IntakePosition.DOWN) {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.OPEN);
      } else {
        intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      }
    } else {
      intakeArmCommand = new SolenoidCommand("intakeDrop", SolenoidCommand.SolenoidState.CLOSE);
      motorCommand = new GenericMotorCAN("intakeMotor", 0.0);
    }
    return Arrays.asList(
      motorCommand,
      intakeArmCommand
    );  
  }
}
