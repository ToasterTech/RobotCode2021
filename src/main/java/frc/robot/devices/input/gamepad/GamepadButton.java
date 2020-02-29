package frc.robot.devices.input.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;


public class GamepadButton implements GamepadInputComponent<Boolean> {

  private String name;
  private Joystick joystick;
  private int rawButton;

  public GamepadButton(Joystick joystick, int rawButton, String name) {
    this.rawButton = rawButton;
    this.joystick = joystick;
    this.name = name;
  }

  @Override
  public InputContainer<Boolean> getValue() {
    return new SimpleInputContainer<Boolean>(this.joystick.getRawButton(this.rawButton));
  }

  @Override
  public String getComponentName() {
    return this.name;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GamepadButton)) {
      return false;
    }
    GamepadButton otherValue = (GamepadButton) other;
    return (this.rawButton == otherValue.rawButton 
      && this.joystick.equals(otherValue.joystick) 
      && this.name.equals(otherValue.name));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.name,
      this.joystick,
      this.rawButton
    );
  }

}
