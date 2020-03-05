/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.Objects;

/**
 * A Control the robot in teleop.
 */
public class TankDrive extends RobotStateController {
  private String leftJoystickName;
  private String rightJoystickName;
  
  public TankDrive(String leftJoystickName, String rightJoystickName) {
    this.leftJoystickName = leftJoystickName;
    this.rightJoystickName = rightJoystickName;
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    return new RobotModel.RobotModelBuilder()
                .buildDriveModel(new DifferentialDriveModel(
                  (double)inputMap.get(this.leftJoystickName).getValue(),
                  (double)inputMap.get(this.rightJoystickName).getValue()
                ))
                .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rightJoystickName, this.leftJoystickName, this.getClass());
  }
}
