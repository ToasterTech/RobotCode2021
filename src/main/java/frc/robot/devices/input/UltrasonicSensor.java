/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;

public class UltrasonicSensor implements DeviceInput<Integer> {
  private AnalogInput ultrasonicSensor;
  
  public UltrasonicSensor(int channel) {
    this.ultrasonicSensor = new AnalogInput(channel);
  }

  public UltrasonicSensor(AnalogInput analogInput) {
    this.ultrasonicSensor = analogInput;
  }

  public InputContainer<Integer> getValue() {
    return new SimpleInputContainer<Integer>(this.ultrasonicSensor.getValue());
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof UltrasonicSensor)) {
      return false;
    }
    UltrasonicSensor otherValue = (UltrasonicSensor) other;
    return this.ultrasonicSensor.equals(otherValue.ultrasonicSensor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.ultrasonicSensor);
  }
}
