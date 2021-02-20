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
  private double currentValueLeft;
  private double currentValueRight;
  
  public TankDrive(String leftJoystickName, String rightJoystickName) {
    this.leftJoystickName = leftJoystickName;
    this.rightJoystickName = rightJoystickName;
    this.currentValueLeft = 0.0;
    this.currentValueRight = 0.0;
  }

  public double limitChange(double target, double current, double maxDiff) {
    double diff = target - current;
    return current + ((Math.abs(diff) > maxDiff) ? maxDiff * Math.signum(diff) : diff);
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    this.currentValueLeft = this.limitChange((double)inputMap.get(this.leftJoystickName).getValue(), this.currentValueLeft, 0.01);
    this.currentValueRight = this.limitChange((double)inputMap.get(this.rightJoystickName).getValue(), this.currentValueRight, 0.01);

    return new RobotModel.RobotModelBuilder()
                .buildDriveModel(new DifferentialDriveModel(
                  this.currentValueLeft,
                  this.currentValueRight
                ))
                .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rightJoystickName, this.leftJoystickName, this.getClass());
  }
}
