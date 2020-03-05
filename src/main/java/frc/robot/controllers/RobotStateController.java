/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.util.InputContainer;

import java.util.HashMap;

/**
 * A base class for robot controllers.
 */
public abstract class RobotStateController {
  public abstract RobotModel run(HashMap<String, InputContainer<?>> inputMap);

  public boolean equals(Object other) {
    return this.hashCode() == other.hashCode();
  }

  public abstract int hashCode();
}
