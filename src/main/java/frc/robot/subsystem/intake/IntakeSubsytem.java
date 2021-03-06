/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.intake;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorPWM;
import frc.robot.devices.commands.SolenoidCommand;
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
    DeviceOutputCommand intakeArmLeftCommand;
    DeviceOutputCommand intakeArmRightCommand;

    if (input instanceof IntakeSystemModel) {
      IntakeSystemModel conveyorSystemModel = (IntakeSystemModel) input;
      if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.STOPPED) {
        motorCommand = new GenericMotorPWM("intakeMotor", 0.0);
      } else if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.INTAKE) {
        motorCommand = new GenericMotorPWM("intakeMotor", -1.0);
      } else if (conveyorSystemModel.intakeState == IntakeSystemModel.IntakeState.OUTTAKE) {
        motorCommand = new GenericMotorPWM("intakeMotor", 0.0);
      } else {
        motorCommand = new GenericMotorPWM("intakeMotor", 0.0);
      }

      if (conveyorSystemModel.intakePosition == IntakeSystemModel.IntakePosition.UP) {
        intakeArmLeftCommand = new SolenoidCommand("intakeDropLeft", SolenoidCommand.SolenoidState.CLOSE);
        intakeArmRightCommand = new SolenoidCommand("intakeDropRight", SolenoidCommand.SolenoidState.CLOSE);
      } else if (conveyorSystemModel.intakePosition == IntakeSystemModel.IntakePosition.DOWN) {
        intakeArmLeftCommand = new SolenoidCommand("intakeDropLeft", SolenoidCommand.SolenoidState.OPEN);
        intakeArmRightCommand = new SolenoidCommand("intakeDropRight", SolenoidCommand.SolenoidState.OPEN);
      } else {
        intakeArmLeftCommand = new SolenoidCommand("intakeDropLeft", SolenoidCommand.SolenoidState.CLOSE);
        intakeArmRightCommand = new SolenoidCommand("intakeDropRight", SolenoidCommand.SolenoidState.CLOSE);
      }
    } else {
      intakeArmLeftCommand = new SolenoidCommand("intakeDropLeft", SolenoidCommand.SolenoidState.CLOSE);
      intakeArmRightCommand = new SolenoidCommand("intakeDropRight", SolenoidCommand.SolenoidState.CLOSE);
      motorCommand = new GenericMotorPWM("intakeMotor", 0.0);
    }
    return Arrays.asList(
      motorCommand,
      intakeArmLeftCommand,
      intakeArmRightCommand
    );  
  }
}
