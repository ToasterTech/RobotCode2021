/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;


/**
 * Current time in milliseconds.
 */
public class CurrentTime implements DeviceInput<Double> {

  public CurrentTime() {

  }

  public InputContainer<Double> getValue() {
    return new SimpleInputContainer<Double>(Timer.getFPGATimestamp() * 1000);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof CurrentTime)) {
      return true;
    }
    return false;
  }
}
