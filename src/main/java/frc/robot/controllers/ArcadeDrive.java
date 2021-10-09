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
public class ArcadeDrive extends RobotStateController {
  private String throttleJoystickName;
  private String steeringJoystickName;
  private double currentValueLeft;
  private double currentValueRight;
  
  public ArcadeDrive(String throttleJoystickName, String steeringJoystickName) {
    this.throttleJoystickName = throttleJoystickName;
    this.steeringJoystickName = steeringJoystickName;
    this.currentValueLeft = 0.0;
    this.currentValueRight = 0.0;
  }

  public double limitChange(double target, double current, double maxDiff) {
    double diff = target - current;
    return current + ((Math.abs(diff) > maxDiff) ? maxDiff * Math.signum(diff) : diff);
  }


  public double checkBounds(double value) {
  if(value > 1) {
    return 1;
  } else if (value < -1) {
    return -1;
  }
  return value;
}

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    double joystickValueThrottle = (double)inputMap.get(this.throttleJoystickName).getValue();
    double steeringValueThrottle = (double)inputMap.get(this.steeringJoystickName).getValue();
    
    this.currentValueLeft = this.limitChange(checkBounds(joystickValueThrottle - steeringValueThrottle), this.currentValueLeft, 0.01);
    this.currentValueRight = this.limitChange(checkBounds(joystickValueThrottle + steeringValueThrottle), this.currentValueRight, 0.01);




    return new RobotModel.RobotModelBuilder()
                .buildDriveModel(new DifferentialDriveModel(
                  this.currentValueLeft,
                  this.currentValueRight
                ))
                .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.steeringJoystickName, this.throttleJoystickName, this.getClass());
  }
}
