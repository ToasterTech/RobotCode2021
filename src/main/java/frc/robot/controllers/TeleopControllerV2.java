/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.models.RobotModel;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;
import frc.robot.util.Toggle;

import java.util.HashMap;
import java.util.Objects;

/**
 * A Control the robot in teleop, based on intial testing.
 */
public class TeleopControllerV2 extends RobotStateController {
  private TankDrive drive;
  private Toggle joystickToggle;

  public TeleopControllerV2(EncoderSpeedCheck defaultTargetVelocity, ConveyorStateMachine conveyorStateMachine) {
    this.drive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
    this.joystickToggle = new Toggle();
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {    
    RobotModel autoShooterModel;
    ShooterSubsystemModel.ShooterState shooterState;
    // Auto Shooting
    if ((boolean)inputMap.get("driverLeftShoulder").getValue()) {
      autoShooterModel = autoShooterController.run(inputMap);
    } else {
      autoShooterModel = new RobotModel.RobotModelBuilder().build();
    }
    // Manual Shooting
    if ((boolean)inputMap.get("driverRightShoulder").getValue()) {
      shooterState = ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT;
    } else {
      shooterState = ShooterSubsystemModel.ShooterState.STOPPED;
    }

    // Hanger requires two buttons to be pushed to prevent accidental triggering.
    HangerSystemModel.HangerState hangerState;
    if ((boolean)inputMap.get("operatorBaseRightUpperButton").getValue()) {
      if ((boolean)inputMap.get("operatorBaseLeftUpperButton").getValue()) {
        hangerState = HangerSystemModel.HangerState.RAISE;
      } else if ((boolean)inputMap.get("operatorBaseLeftLowerButton").getValue()) {
        hangerState = HangerSystemModel.HangerState.LOWER;
      } else {
        hangerState = HangerSystemModel.HangerState.STOPPED;
      }  
    } else {
      hangerState = HangerSystemModel.HangerState.STOPPED;
    }

    return new RobotModel.RobotModelBuilder()
                .buildShooterModel(autoShooterModel.shooterModel.orElse(new ShooterSubsystemModel(shooterState)))
                .buildHangerModel(new HangerSystemModel(hangerState))
                .buildDriveModel(this.drive.run(inputMap).driveModel.get())
                .buildConveyorModel(
                  conveyorStateMachine.run(new ConveyorStateMachineInput(
                    (double)inputMap.get("conveyorSonarFront").getValue() > 1.5,
                    (double)inputMap.get("conveyorSonarMiddle").getValue() > 1.5,
                    (double)inputMap.get("conveyorSonarTop").getValue() > 1.5,
                    (double)inputMap.get("conveyorSonarIntakeCheck").getValue() > 1.5,
                    this.defaultTargetVelocity.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()),
                    (boolean)inputMap.get("driverRightShoulder").getValue()
                  )
                )
              )
              .buildIntakeModel(new IntakeSystemModel(intakeState, intakePosition))
              .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.drive, this.getClass());
  }
}
