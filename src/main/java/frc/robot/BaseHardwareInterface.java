/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.input.DeviceInput;
import frc.robot.devices.output.DeviceOutput;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Add your docs here.
 */
public class BaseHardwareInterface {
  protected HashMap<String, DeviceOutput> outputMap;
  protected HashMap<String, DeviceInput<?>> inputMap;

  /**
   * Constructor to HardwareInterface. Setup the device mappings.
   */
  public BaseHardwareInterface() {
    this.outputMap = new HashMap<String, DeviceOutput>();
    this.inputMap = new HashMap<String, DeviceInput<?>>();
  }

  /**
   * Run the hardware interface to set device commands to the devices.
   * @param commands a list of device output commands.
   */
  public void run(List<DeviceOutputCommand> commands) {
    for (DeviceOutputCommand command: commands) {
      DeviceOutput output = this.outputMap.get(command.getDeviceId());
      if (output.isValidCommand(command)) {
        output.run(command);
      } else {
        throw new IllegalArgumentException(
          String.format("%s is not a valid DeviceOutputCommand for %s", command.getClass(), output.getClass())
        );
      }
    }
  }

  public HashMap<String, InputContainer<?>> getInputValueMap() {
    HashMap<String, InputContainer<?>> inputValueMap = new HashMap<String, InputContainer<?>>();
    for (Map.Entry<String, DeviceInput<?>> entry: this.inputMap.entrySet()) {
      inputValueMap.put(entry.getKey(), entry.getValue().getValue());
    }
    return inputValueMap;
  }
}
