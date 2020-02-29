package frc.robot.devices.input.gamepad;

import frc.robot.devices.input.DeviceInput;

public interface GamepadInputComponent<T> extends DeviceInput<T> {
  public String getComponentName();
}
