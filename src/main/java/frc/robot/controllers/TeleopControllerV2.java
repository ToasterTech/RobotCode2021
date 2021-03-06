/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import frc.robot.controllers.ConveyorStateMachine.ConveyorStateMachineInput;
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
  private TankDrive tankDrive;
  private Toggle joystickToggle;
  private ConveyorStateMachine conveyorStateMachine;
  private EncoderSpeedCheck defaultTargetVelocity;

  public TeleopControllerV2(EncoderSpeedCheck defaultTargetVelocity, ConveyorStateMachine conveyorStateMachine) {
    this.tankDrive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
    this.joystickToggle = new Toggle();
    this.conveyorStateMachine = conveyorStateMachine;
    this.defaultTargetVelocity = defaultTargetVelocity;
  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {    
    RobotModel autoShooterModel;
    ShooterSubsystemModel.ShooterState shooterState;
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
                .buildShooterModel(new ShooterSubsystemModel(shooterState))
                .buildHangerModel(new HangerSystemModel(hangerState))
                .buildDriveModel(this.tankDrive.run(inputMap).driveModel.get())
                .buildConveyorModel(
                  conveyorStateMachine.run(new ConveyorStateMachineInput(
                    (double)inputMap.get("conveyorSonarFront").getValue() > 2,
                    (double)inputMap.get("conveyorSonarMiddle").getValue() > 2,
                    (double)inputMap.get("conveyorSonarTop").getValue() > 2,
                    this.defaultTargetVelocity.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()),
                    (boolean)inputMap.get("driveRightShoulder").getValue()
                  )
                )
              )
              .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tankDrive, this.getClass());
  }
}
