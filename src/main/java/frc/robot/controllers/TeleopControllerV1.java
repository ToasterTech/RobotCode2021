/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.Objects;

/**
 * A Control the robot in teleop, based on intial testing.
 */
public class TeleopControllerV1 extends RobotStateController {
  private TankDrive tankDrive;

  public TeleopControllerV1() {
    this.tankDrive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {    
    ShooterSubsystemModel.ShooterState shooterState;
    if ((boolean)inputMap.get("driverLeftShoulder").getValue()) {
      shooterState = ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT;
    } else {
      shooterState = ShooterSubsystemModel.ShooterState.STOPPED;
    }

    ConveyorSystemModel.IntakeState intakeState;
    if ((boolean)inputMap.get("driverRightShoulder").getValue()) {
      intakeState = ConveyorSystemModel.IntakeState.INTAKE;
    } else if ((boolean)inputMap.get("driverXButton").getValue()) {
      intakeState = ConveyorSystemModel.IntakeState.OUTTAKE;
    } else {
      intakeState = ConveyorSystemModel.IntakeState.STOPPED;
    }

    ConveyorSystemModel.IntakePosition intakePosition;
    if ((double)inputMap.get("driverRightTrigger").getValue() > .5) {
      intakePosition = ConveyorSystemModel.IntakePosition.DOWN;
    }  else {
      intakePosition = ConveyorSystemModel.IntakePosition.UP;
    }

    ConveyorSystemModel.ShooterBlockState blockState;
    if ((double)inputMap.get("driverLeftTrigger").getValue() > .5) {
      blockState = ConveyorSystemModel.ShooterBlockState.OPEN;
    } else {
      blockState = ConveyorSystemModel.ShooterBlockState.CLOSE;
    }
    HangerSystemModel.HangerState hangerState;
    if ((boolean)inputMap.get("driverYButton").getValue()) {
      hangerState = HangerSystemModel.HangerState.RAISE;
    } else if ((boolean)inputMap.get("driverAButton").getValue()) {
      hangerState = HangerSystemModel.HangerState.LOWER;
    } else {
      hangerState = HangerSystemModel.HangerState.STOPPED;
    }

    return new RobotModel.RobotModelBuilder()
                .buildShooterModel(new ShooterSubsystemModel(shooterState))
                .buildConveyorModel(new ConveyorSystemModel(
                  intakeState,
                  intakePosition,
                  blockState
                ))
                .buildHangerModel(new HangerSystemModel(hangerState))
                .buildDriveModel(this.tankDrive.run(inputMap).driveModel.get())
                .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tankDrive, this.getClass());
  }
}
