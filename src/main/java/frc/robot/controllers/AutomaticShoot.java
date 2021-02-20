/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.models.RobotModel.RobotModelBuilder;
import frc.robot.subsystem.conveyor.models.ConveyorModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.Objects;

/**
 * A Control the robot in teleop.
 */
public class AutomaticShoot extends RobotStateController {
  private EncoderSpeedCheck defaultTargetVelocity;
  private ShooterSubsystemModel.ShooterState shooterState;

  public AutomaticShoot(EncoderSpeedCheck defaultTargetVelocity) {
    this.defaultTargetVelocity = defaultTargetVelocity;
    this.shooterState = ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT;
  }


  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    RobotModelBuilder model = new RobotModel.RobotModelBuilder();
    
    model.buildShooterModel(new ShooterSubsystemModel(this.shooterState));
    // TODO: Redo this with the new conveyor code
    if (this.defaultTargetVelocity.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue())) {
      model.buildConveyorModel(new ConveyorSystemModel());
    } else {
      model.buildConveyorModel(new ConveyorSystemModel());
    }

    return model.build();
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return Objects.hash(this.defaultTargetVelocity, this.getClass());
  }
}
