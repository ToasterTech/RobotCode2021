/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.conveyor;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
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
    if (input instanceof ConveyorSystemModel) {
      ConveyorSystemModel conveyorSystemModel = (ConveyorSystemModel) input;
      if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.STOPPED) {
        return Arrays.asList(
          new GenericMotorCAN("conveyorMotor", 0.0)
        );
      }
      if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.INTAKE) {
        return Arrays.asList(
          new GenericMotorCAN("conveyorMotor", 1)
        );
      }
      if (conveyorSystemModel.intakeState == ConveyorSystemModel.IntakeState.OUTTAKE) {
        return Arrays.asList(
          new GenericMotorCAN("conveyorMotor", -1)
        );
      }
    }
    return null;
  }
}
