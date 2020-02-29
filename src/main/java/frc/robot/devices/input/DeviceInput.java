/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.input;

import frc.robot.util.InputContainer;

/**
 * Represents a device to get inputs from.
 */
public interface DeviceInput<T> {
  public InputContainer<T> getValue();
}
