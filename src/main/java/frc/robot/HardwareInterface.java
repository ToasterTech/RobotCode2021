/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.output.DeviceCANSparkMax;
import frc.robot.devices.output.DeviceOutput;
import frc.robot.devices.output.DevicePWMTalonSRX;
import java.util.HashMap;
import java.util.List;


/**
 * Add your docs here.
 */
public class HardwareInterface {
  private HashMap<String, DeviceOutput> deviceMap; 

  /**
   * Constructor to HardwareInterface. Setup the device mappings. 
   */
  public HardwareInterface() {
    this.deviceMap = new HashMap<String, DeviceOutput>();
    // TODO: Make these addressed correctly
    this.deviceMap.put("leftMotor1", new DevicePWMTalonSRX(1));
    this.deviceMap.put("leftMotor2", new DevicePWMTalonSRX(2));
    this.deviceMap.put("rightMotor1", new DevicePWMTalonSRX(3));
    this.deviceMap.put("rightMotor2", new DevicePWMTalonSRX(4));

    this.deviceMap.put("shooterMotor", new DeviceCANSparkMax(1, MotorType.kBrushless, true));
  }

  /**
   * Run the hardware interface to set device commands to the devices.
   * @param commands a list of device output commands.
   */
  public void run(List<DeviceOutputCommand> commands) {
    for (DeviceOutputCommand command: commands) {
      DeviceOutput output = this.deviceMap.get(command.getDeviceId());
      if (output.isValidCommand(command)) {
        output.run(command);
      } else {
        throw new IllegalArgumentException(
          String.format("%s is not a valid DeviceOutputCommand for %s", command.getClass(), output.getClass())
        );
      }
    }

  }

}
