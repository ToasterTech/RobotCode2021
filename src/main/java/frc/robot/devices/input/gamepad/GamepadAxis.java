package frc.robot.devices.input.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;


public class GamepadAxis implements GamepadInputComponent<Double> {

  private String name;
  private Joystick joystick;
  private int rawAxis;

  public GamepadAxis(Joystick joystick, int rawAxis, String name) {
    this.rawAxis = rawAxis;
    this.joystick = joystick;
    this.name = name;
  }

  @Override
  public InputContainer<Double> getValue() {
    return new SimpleInputContainer<Double>(this.joystick.getRawAxis(this.rawAxis));
  }

  @Override
  public String getComponentName() {
    return this.name;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof GamepadAxis)) {
      return false;
    }
    GamepadAxis otherValue = (GamepadAxis) other;
    return (this.rawAxis == otherValue.rawAxis 
      && this.joystick.equals(otherValue.joystick) 
      && this.name.equals(otherValue.name));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.name,
      this.joystick,
      this.rawAxis
    );
  }
}
