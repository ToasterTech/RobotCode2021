/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;

/**
 * Encoder From CAN Device.
 */
public class LimitSwitch implements DeviceInput<Boolean> {
  private DigitalInput limitSwitch;

  public LimitSwitch(int channel) {
    this.limitSwitch = new DigitalInput(channel);
  }

  public LimitSwitch(DigitalInput digitalInput) {
    this.limitSwitch = digitalInput;
  }

  public InputContainer<Boolean> getValue() {
    return new SimpleInputContainer<Boolean>(this.limitSwitch.get());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof LimitSwitch)) {
      return false;
    }
    LimitSwitch otherValue = (LimitSwitch) other;
    return this.limitSwitch.equals(otherValue.limitSwitch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.limitSwitch
    );
  }

}
