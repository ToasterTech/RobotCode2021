/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.devices.input.DeviceInput;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

//TODO: The wildcard type parameter is nasty and leads to unchecked exceptions, which are fine 
// because we actually know what they are, but we should avoid anyway.
//TODO: This should be in a base class.
/**
 * Joystick.
 */
public class JoystickInput implements DeviceInput<HashMap<String, InputContainer<?>>> {
  private Joystick joystick;
  private HashMap<String, GamepadInputComponent<?>> joystickMap;
  private String name;

  /**
   * Create a playstation controller.
   * 
   * @param joystick joystick reference
   */
  public JoystickInput(String name, Joystick joystick) {
    this.joystick = joystick;
    this.joystickMap = new HashMap<String, GamepadInputComponent<?>>();
    this.name = name;

    // TODO: These mappings are most certainly wrong.
    this.addGamepadComponent("joystick", new GamepadAxis(this.joystick, 0, "AxisX"));
    this.addGamepadComponent("joystick", new GamepadAxis(this.joystick, 1, "AxisY"));
    this.addGamepadComponent("joystick", new GamepadAxis(this.joystick, 2, "Twist"));

    this.addGamepadComponent("joystick", new GamepadButton(this.joystick, 0, "Trigger"));
    this.addGamepadComponent("joystick", new GamepadButton(this.joystick, 1, "TopLeftButton"));
    this.addGamepadComponent("joystick", new GamepadButton(this.joystick, 2, "TopRightButton"));
    this.addGamepadComponent("joystick", new GamepadButton(this.joystick, 3, "TopUpperButton"));
    this.addGamepadComponent("joystick", new GamepadButton(this.joystick, 4, "TopLowerButton"));

    this.addGamepadComponent("base", new GamepadButton(this.joystick, 5, "LeftUpperButton"));
    this.addGamepadComponent("base", new GamepadButton(this.joystick, 6, "LeftLowerButton"));
    this.addGamepadComponent("base", new GamepadButton(this.joystick, 7, "RightUpperButton"));
    this.addGamepadComponent("base", new GamepadButton(this.joystick, 8, "RightLowerButton"));

  }

  public void addGamepadComponent(String prefix, GamepadInputComponent<?> component) {
    this.joystickMap.put(prefix + component.getComponentName(), component);
  }

  public InputContainer<HashMap<String, InputContainer<?>>> getValue() {
    HashMap<String, InputContainer<?>> inputValueMap = new HashMap<String, InputContainer<?>>();
    for (Map.Entry<String, GamepadInputComponent<?>> entry : this.joystickMap.entrySet()) {
      inputValueMap.put(entry.getKey(), entry.getValue().getValue());
    }
    return new SimpleInputContainer<HashMap<String, InputContainer<?>>>(inputValueMap);
  }

  public HashMap<String, GamepadInputComponent<?>> getDeviceMap() {
    HashMap<String, GamepadInputComponent<?>> namedMap = new HashMap<String, GamepadInputComponent<?>>();
    for (Map.Entry<String, GamepadInputComponent<?>> entry : this.joystickMap.entrySet()) {
      namedMap.put(this.name + StringUtils.capitalize(entry.getKey()), entry.getValue());
    }
    return namedMap;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof JoystickInput)) {
      return false;
    }
    JoystickInput otherValue = (JoystickInput) other;
    return (this.joystickMap.equals(otherValue.joystickMap)
      && this.joystick.equals(otherValue.joystick) 
      && this.name.equals(otherValue.name));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.name,
      this.joystick,
      this.joystickMap
    );
  }

}
