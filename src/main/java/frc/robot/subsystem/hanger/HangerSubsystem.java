/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.hanger;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.SolenoidCommand;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.hanger.models.HangerModel;
import frc.robot.subsystem.hanger.models.HangerSystemModel;

import java.util.Arrays;
import java.util.List;

/**
 * Subsystem for transforming hanger models to motor commands.
 */
public class HangerSubsystem extends RobotSubsystem<HangerModel> {
  @Override
  public List<DeviceOutputCommand> run(HangerModel input) {
    DeviceOutputCommand motorCommand;
    DeviceOutputCommand breakCommand;

    if (input instanceof HangerSystemModel) {
      HangerSystemModel conveyorSystemModel = (HangerSystemModel) input;
      if (conveyorSystemModel.hangerState == HangerSystemModel.HangerState.RAISE) {
        motorCommand = new GenericMotorCAN("hangerMotor", -.6);
        breakCommand = new SolenoidCommand("hangerLock", SolenoidCommand.SolenoidState.OPEN);
      } else if (conveyorSystemModel.hangerState == HangerSystemModel.HangerState.LOWER) {
        motorCommand = new GenericMotorCAN("hangerMotor", .6);
        breakCommand = new SolenoidCommand("hangerLock", SolenoidCommand.SolenoidState.OPEN);
      } else if (conveyorSystemModel.hangerState == HangerSystemModel.HangerState.LOWER_FAST) {
        motorCommand = new GenericMotorCAN("hangerMotor", 1.0);
        breakCommand = new SolenoidCommand("hangerLock", SolenoidCommand.SolenoidState.OPEN);
      } else {
        motorCommand = new GenericMotorCAN("hangerMotor", 0.0);
        breakCommand = new SolenoidCommand("hangerLock", SolenoidCommand.SolenoidState.CLOSE);
      }
    } else {
      breakCommand = new SolenoidCommand("hangerLock", SolenoidCommand.SolenoidState.CLOSE);
      motorCommand = new GenericMotorCAN("conveyorMotor", 0.0);
    }
    return Arrays.asList(
      motorCommand,
      breakCommand
    );  
  }
}
