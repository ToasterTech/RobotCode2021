/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.output;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.SolenoidCommand;

/**
 * Motor device for TalonSRX.
*/
public class DeviceSolenoid extends DeviceOutput {
  private Solenoid solenoid;

  public DeviceSolenoid(int channel) {
    this.solenoid = new Solenoid(channel);
  }
  
  @Override
  public boolean isValidCommand(DeviceOutputCommand command) {
    return command instanceof SolenoidCommand;
  }

  @Override
  public void run(DeviceOutputCommand command) {
    if (command instanceof SolenoidCommand) {
      SolenoidCommand solenoidCommand = (SolenoidCommand) command;
      this.solenoid.set(solenoidCommand.state == SolenoidCommand.SolenoidState.OPEN);
    }
  }
}
