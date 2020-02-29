/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input;

import com.revrobotics.CANEncoder;

import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Objects;

/**
 * Encoder From CAN Device.
 */
public class EncoderVelocityInputCAN implements DeviceInput<Double> {
  private CANEncoder encoder;

  public EncoderVelocityInputCAN(CANEncoder encoder) {
    this.encoder = encoder;
  }

  public InputContainer<Double> getValue() {
    return new SimpleInputContainer<Double>(this.encoder.getVelocity());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof EncoderVelocityInputCAN)) {
      return false;
    }
    EncoderVelocityInputCAN otherValue = (EncoderVelocityInputCAN) other;
    return this.encoder.equals(otherValue.encoder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.encoder
    );
  }

}
