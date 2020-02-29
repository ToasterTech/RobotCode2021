/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.input.DeviceInput;
import frc.robot.devices.input.gamepad.GamepadInput;
import frc.robot.devices.output.DeviceCANSparkMax;
import frc.robot.devices.output.DeviceOutput;
import frc.robot.devices.output.DevicePWMTalonSRX;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Add your docs here.
 */
public class HardwareInterface extends BaseHardwareInterface {
  /**
   * Constructor to HardwareInterface. Setup the device mappings. 
   */
  public HardwareInterface() {
    super();

    // TODO: Make these addressed correctly
    this.outputMap.put("leftMotor1", new DevicePWMTalonSRX(1));
    this.outputMap.put("leftMotor2", new DevicePWMTalonSRX(2));
    this.outputMap.put("rightMotor1", new DevicePWMTalonSRX(3));
    this.outputMap.put("rightMotor2", new DevicePWMTalonSRX(4));

    DeviceCANSparkMax shooterMotor = new DeviceCANSparkMax(2, MotorType.kBrushless, true);
    DeviceCANSparkMax shooterFollower = new DeviceCANSparkMax(1, MotorType.kBrushless, false);

    this.outputMap.put("shooterMotor", shooterMotor);
    this.inputMap.put("shooterEncoderVelocity", shooterMotor.getEncoderVelocityInput());

    this.outputMap.put("conveyorMotor", new DeviceCANSparkMax(3, MotorType.kBrushless, false));

    GamepadInput gamepad = new GamepadInput("driverJoystick", new Joystick(1));
    this.inputMap.put("driverGamepad", gamepad);
    this.inputMap.putAll(gamepad.getDeviceMap());
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
