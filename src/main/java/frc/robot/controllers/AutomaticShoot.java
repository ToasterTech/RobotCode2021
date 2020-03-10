/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.models.RobotModel.RobotModelBuilder;
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
  private ConveyorSystemModel.IntakeState intakeState;

  public AutomaticShoot(EncoderSpeedCheck defaultTargetVelocity) {
    this.defaultTargetVelocity = defaultTargetVelocity;
    this.shooterState = ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT;
    this.intakeState = ConveyorSystemModel.IntakeState.INTAKE;
  }

  public AutomaticShoot(EncoderSpeedCheck defaultTargetVelocity, ConveyorSystemModel.IntakeState intakeState) {
    this.defaultTargetVelocity = defaultTargetVelocity;
    this.shooterState = ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT;
    this.intakeState = intakeState;
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    RobotModelBuilder model = new RobotModel.RobotModelBuilder();
    
    model.buildShooterModel(new ShooterSubsystemModel(this.shooterState));

    if (this.defaultTargetVelocity.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue())) {
      model.buildConveyorModel(new ConveyorSystemModel(
          this.intakeState,
          ConveyorSystemModel.IntakePosition.UP,
          ConveyorSystemModel.ShooterBlockState.OPEN
      ));
    } else {
      model.buildConveyorModel(new ConveyorSystemModel(
          ConveyorSystemModel.IntakeState.STOPPED,
          ConveyorSystemModel.IntakePosition.UP,
          ConveyorSystemModel.ShooterBlockState.CLOSE
      ));
    }
    System.out.println(model);

    return model.build();
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return Objects.hash(this.defaultTargetVelocity, this.getClass());
  }
}
