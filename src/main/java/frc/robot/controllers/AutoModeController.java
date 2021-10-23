/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.controllers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.ConveyorStateMachine.ConveyorStateMachineInput;
import frc.robot.models.RobotModel;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel.ShooterState;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;



/**
 * A Control the robot in teleop, based on intial testing.
 */
public class AutoModeController extends RobotStateController {
  private TankDrive tankDrive;
  private AutomodeState autoState;
  private Optional<Double> targetEndTime;
  private EncoderSpeedCheck encoderSpeedCheckAuto;
  private ConveyorStateMachine conveyorStateMachine;

  private static final double WAIT_TIME = 0.0;
  private static final double SHOOT_TIME = 5.0 * 1000;
  private static final double DRIVE_TIME = 1.1 * 1000;


  public enum AutomodeState {
    WAIT_TO_SHOOT, SHOOT, DRIVE_BACK, STOP
  }

  public AutoModeController(EncoderSpeedCheck encoderSpeedCheckAuto, ConveyorStateMachine conveyorStateMachine) {
    this.tankDrive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
    this.autoState = AutomodeState.WAIT_TO_SHOOT;
    this.targetEndTime = Optional.empty();
    this.encoderSpeedCheckAuto = encoderSpeedCheckAuto;
    this.conveyorStateMachine = conveyorStateMachine;
  }

  private RobotModel waitToShoot(double currentTime, HashMap<String, InputContainer<?>> inputMap) {
    if (!targetEndTime.isPresent()) {
      targetEndTime = Optional.of(currentTime + WAIT_TIME);
    }
    if (this.encoderSpeedCheckAuto.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue())) {
      this.autoState = AutomodeState.SHOOT;
      targetEndTime = Optional.empty();
    }
    return new RobotModel.RobotModelBuilder().buildShooterModel(new ShooterSubsystemModel(ShooterState.SHOOT_DEFAULT))
    .buildConveyorModel(this.conveyorStateMachine
        .run(new ConveyorStateMachineInput((double) inputMap.get("conveyorSonarFront").getValue() > 1.5,
              (double) inputMap.get("conveyorSonarMiddle").getValue() > 1.5,
              (double) inputMap.get("conveyorSonarTop").getValue() > 1.5,
              (double) inputMap.get("conveyorSonarIntakeCheck").getValue() > 1.5,
              this.encoderSpeedCheckAuto.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()),
              false)))
    .build();
  }

  private RobotModel shoot(double currentTime, HashMap<String, InputContainer<?>> inputMap) {
    if (!targetEndTime.isPresent()) {
      targetEndTime = Optional.of(currentTime + SHOOT_TIME);
    }
    SmartDashboard.putNumber("targetTime", (double)targetEndTime.get());
    if (currentTime > this.targetEndTime.get()) {
      this.autoState = AutomodeState.DRIVE_BACK;
      // this.autoState = AutomodeState.STOP;
      targetEndTime = Optional.empty();
    }
    return new RobotModel.RobotModelBuilder().buildShooterModel(new ShooterSubsystemModel(ShooterState.SHOOT_DEFAULT))
      .buildConveyorModel(this.conveyorStateMachine
          .run(new ConveyorStateMachineInput((double) inputMap.get("conveyorSonarFront").getValue() > 1.5,
                (double) inputMap.get("conveyorSonarMiddle").getValue() > 1.5,
                (double) inputMap.get("conveyorSonarTop").getValue() > 1.5,
                (double) inputMap.get("conveyorSonarIntakeCheck").getValue() > 1.5,
                this.encoderSpeedCheckAuto.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()),
                true)))
      .build();
  }

  private RobotModel driveBack(double currentTime) {
    if (!targetEndTime.isPresent()) {
      targetEndTime = Optional.of(currentTime + DRIVE_TIME);
    }
    if (currentTime > this.targetEndTime.get()) {
      this.autoState = AutomodeState.STOP;
      targetEndTime = Optional.empty();
    }
    return new RobotModel.RobotModelBuilder()
                          .buildDriveModel(new DifferentialDriveModel(.6, .6))
                          .build();
  }

  @Override

  public RobotModel run(HashMap<String, InputContainer<?>> inputMap) {
    SmartDashboard.putBoolean("ConveyorFrontTirggered", (double) inputMap.get("conveyorSonarFront").getValue() > 1.5);
    SmartDashboard.putBoolean("ConveyorMiddleTirggered", (double) inputMap.get("conveyorSonarMiddle").getValue() > 1.5);
    SmartDashboard.putBoolean("ConveyorTopTirggered", (double) inputMap.get("conveyorSonarTop").getValue() > 1.5);
    SmartDashboard.putBoolean("ShooterAtSpeed",
        this.encoderSpeedCheckAuto.isEncoderAtSpeed((double) inputMap.get("shooterEncoderVelocity").getValue()));
    SmartDashboard.putBoolean("ShooterTriggered", (boolean) inputMap.get("driverRightShoulder").getValue());
    SmartDashboard.putNumber("BallCount", conveyorStateMachine.getBallCount());

    SmartDashboard.putNumber("ConveyorFrontValue", (double) inputMap.get("conveyorSonarFront").getValue());
    SmartDashboard.putNumber("ConveyorMiddleValue", (double) inputMap.get("conveyorSonarMiddle").getValue());
    SmartDashboard.putNumber("ConveyorTopValue", (double) inputMap.get("conveyorSonarTop").getValue());
    switch (this.autoState) {
      case WAIT_TO_SHOOT: return this.waitToShoot((double) inputMap.get("currentTime").getValue(), inputMap);
      case SHOOT: return this.shoot((double) inputMap.get("currentTime").getValue(), inputMap);
      case DRIVE_BACK: return this.driveBack((double) inputMap.get("currentTime").getValue());
      case STOP: return new RobotModel.RobotModelBuilder().buildDriveModel(new DifferentialDriveModel(0,0)).build();
      default: new RobotModel.RobotModelBuilder().build();
    }
    return new RobotModel.RobotModelBuilder().build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tankDrive, this.getClass());
  }
}
