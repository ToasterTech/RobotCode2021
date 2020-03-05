package frc.robot.devices.input.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;


public class GamepadPOV implements GamepadInputComponent<Integer> {

  private String name;
  private Joystick joystick;
  private int povIndex;

  public GamepadPOV(Joystick joystick, int povIndex, String name) {
    this.povIndex = povIndex;
    this.joystick = joystick;
    this.name = name;
  }

  @Override
  public InputContainer<Integer> getValue() {
    return new SimpleInputContainer<Integer>(this.joystick.getPOV(this.povIndex));
  }

  @Override
  public String getComponentName() {
    return this.name;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GamepadPOV)) {
      return false;
    }
    GamepadPOV otherValue = (GamepadPOV) other;
    return (this.povIndex == otherValue.povIndex 
      && this.joystick.equals(otherValue.joystick) 
      && this.name.equals(otherValue.name));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.name,
      this.joystick,
      this.povIndex
    );
  }
}
