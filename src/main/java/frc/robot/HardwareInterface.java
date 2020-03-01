/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.input.DeviceInput;
import frc.robot.devices.input.gamepad.GamepadInput;
import frc.robot.devices.output.DeviceCANSparkMax;
import frc.robot.devices.output.DeviceOutput;
import frc.robot.devices.output.DevicePWMTalonSRX;
import frc.robot.devices.output.DeviceSolenoid;
import frc.robot.devices.output.FollowerMotorCAN;
import frc.robot.util.InputContainer;

import java.util.Arrays;
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

    this.outputMap.put("intakeStop", new DeviceSolenoid(0));
    this.outputMap.put("intakeDrop", new DeviceSolenoid(2));

    DeviceCANSparkMax shooterMotor = new DeviceCANSparkMax(2, MotorType.kBrushless, true, Arrays.asList(
        new FollowerMotorCAN(new CANSparkMax(1, MotorType.kBrushless), true)
    ));

    this.outputMap.put("shooterMotor", shooterMotor);
    this.inputMap.put("shooterEncoderVelocity", shooterMotor.getEncoderVelocityInput());

    DeviceCANSparkMax conveyorMotor = new DeviceCANSparkMax(3, MotorType.kBrushless, true);
    
    this.outputMap.put("conveyorMotor", conveyorMotor);
    this.inputMap.put("conveyorEncoderVelocity", conveyorMotor.getEncoderVelocityInput());

    GamepadInput gamepad = new GamepadInput("driver", new Joystick(1));
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
