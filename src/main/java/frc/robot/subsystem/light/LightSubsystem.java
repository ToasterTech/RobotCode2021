/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.light;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.RelayCommand;
import frc.robot.devices.commands.RelayCommand.RelayState;
import frc.robot.subsystem.RobotSubsystem;
import frc.robot.subsystem.light.models.LightModel;
import frc.robot.subsystem.light.models.LightSubsystemModel;

import java.util.Arrays;
import java.util.List;


/**
 * Subsystem for transforming shooter models to motor commands.
 */
public class LightSubsystem extends RobotSubsystem<LightModel> {


  @Override
  public List<DeviceOutputCommand> run(LightModel input) {
    if (input instanceof LightSubsystemModel) {
      LightSubsystemModel lightModel = (LightSubsystemModel) input;
      switch (lightModel.lightState) {
        case ON:
          return Arrays.asList(new RelayCommand("lightRelay", RelayState.ON));
        case OFF:
          return Arrays.asList(new RelayCommand("lightRelay", RelayState.OFF));
        default:
          break;
      }
    }
    throw new IllegalArgumentException(
      String.format("%s is not a supported model for LightSystem", input.getClass())
    );
  }
}
