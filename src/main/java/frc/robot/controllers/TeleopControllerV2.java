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
import frc.robot.subsystem.intake.models.IntakeSystemModel;
import frc.robot.subsystem.intake.models.IntakeSystemModel.IntakePosition;
import frc.robot.subsystem.intake.models.IntakeSystemModel.IntakeState;

import java.util.HashMap;
import java.util.Objects;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A Control the robot in teleop, based on intial testing.
 */
public class TeleopControllerV2 extends RobotStateController {
  private TankDrive tankDrive;
  private Toggle joystickToggle;
  private ConveyorStateMachine conveyorStateMachine;
  private EncoderSpeedCheck defaultTargetVelocity;
  private IntakePosition intakePosition;

  public TeleopControllerV2(EncoderSpeedCheck defaultTargetVelocity, ConveyorStateMachine conveyorStateMachine) {
    this.tankDrive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
    this.joystickToggle = new Toggle();
    this.conveyorStateMachine = conveyorStateMachine;
    this.defaultTargetVelocity = defaultTargetVelocity;
    this.intakePosition = IntakePosition.UP;

    this.conveyorStateMachine = conveyorStateMachine;

  }

  @Override
  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {    
    ShooterSubsystemModel.ShooterState shooterState;
    IntakeState intakeState;
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

if(joystickToggle.run((boolean)inputMap.get("driverXButton").getValue())) { 
  intakePosition = IntakePosition.DOWN; 
  } else {
    intakePosition = IntakePosition.UP;
  }

  if((boolean)inputMap.get("driverLeftShoulder").getValue()) {
    intakeState = IntakeState.INTAKE;
  } else {
    intakeState = IntakeState.STOPPED;
  }

  SmartDashboard.putBoolean("ConveyorFrontTirggered", (double) inputMap.get("conveyorSonarFront").getValue() > 1.1);
  SmartDashboard.putBoolean("ConveyorMiddleTirggered", (double) inputMap.get("conveyorSonarMiddle").getValue() > 2);
  SmartDashboard.putBoolean("ConveyorTopTirggered", (double) inputMap.get("conveyorSonarTop").getValue() > 2);
  SmartDashboard.putBoolean("ShooterAtSpeed", this.defaultTargetVelocity.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()));
  SmartDashboard.putBoolean("ShooterTriggered",  (boolean)inputMap.get("driverRightShoulder").getValue());


  SmartDashboard.putNumber("ConveyorFrontValue", (double)inputMap.get("conveyorSonarFront").getValue());
  SmartDashboard.putNumber("ConveyorMiddleValue", (double)inputMap.get("conveyorSonarMiddle").getValue());
  SmartDashboard.putNumber("ConveyorTopValue", (double)inputMap.get("conveyorSonarTop").getValue());

    return new RobotModel.RobotModelBuilder()
                .buildShooterModel(new ShooterSubsystemModel(shooterState))
                .buildHangerModel(new HangerSystemModel(hangerState))
                .buildDriveModel(this.tankDrive.run(inputMap).driveModel.get())
                .buildConveyorModel(
                  conveyorStateMachine.run(new ConveyorStateMachineInput(
                    (double)inputMap.get("conveyorSonarFront").getValue() > 1.5,
                    (double)inputMap.get("conveyorSonarMiddle").getValue() > 2,
                    (double)inputMap.get("conveyorSonarTop").getValue() > 2,
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
    return Objects.hash(this.tankDrive, this.getClass());
  }
}
