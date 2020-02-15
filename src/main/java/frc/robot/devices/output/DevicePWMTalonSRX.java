/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.output;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorPWM;

/**
 * Motor device for TalonSRX.
*/
public class DevicePWMTalonSRX extends MotorPWM {
  private PWMTalonSRX controller;

  public DevicePWMTalonSRX(int channel) {
    this.controller = new PWMTalonSRX(channel);
  }
  
  @Override
  public boolean isValidCommand(DeviceOutputCommand command) {
    return command instanceof GenericMotorPWM;
  }

  @Override
  public void run(DeviceOutputCommand command) {
    if (command instanceof GenericMotorPWM) {
      GenericMotorPWM pwmCommand = (GenericMotorPWM) command;
      controller.set(pwmCommand.setpoint);
    }
  }
}
