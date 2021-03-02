package frc.robot.controllers;

import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel.ConveyorState;

import java.util.Optional;

public class ConveyorStateMachine {
  public static class ConveyorStateMachineInput {
    public final boolean ballSensor1Trigger;
    public final boolean ballSensor2Trigger;
    public final boolean ballSensor3Trigger;
    public final boolean shooterAtSpeed;
    public final boolean shooterTrigger;

    public ConveyorStateMachineInput(boolean ballSensor1, boolean ballSensor2, boolean ballSensor3,
        boolean shooterAtSpeed, boolean shooterTrigger) {
      this.ballSensor1Trigger = ballSensor1;
      this.ballSensor2Trigger = ballSensor2;
      this.ballSensor3Trigger = ballSensor3;
      this.shooterAtSpeed = shooterAtSpeed;
      this.shooterTrigger = shooterTrigger;
    }
  }

  public enum ConveyorControlStates {
    IDLE, INCREMENT_BALL_COUNT, INTAKE_NEW_BALL, INTAKE_EXISTING_BALL
  }

  private ConveyorControlStates nextState;
  private ConveyorControlStates currentState;
  private Optional<ConveyorStateMachineInput> lastInput;
  private int ballCount;

  public ConveyorStateMachine() {
    this.currentState = ConveyorControlStates.IDLE;
    this.nextState = ConveyorControlStates.IDLE;
    this.lastInput = Optional.empty();
    this.ballCount = 0;
  }

  public ConveyorControlStates getCurrentState() {
    return this.currentState;
  }

  public ConveyorControlStates getNextState() {
    return this.nextState;
  }

  public int getBallCount() {
    return this.ballCount;
  }

  public Optional<ConveyorStateMachineInput> getLastInput() {
    return this.lastInput;
  }

  public ConveyorSystemModel runIdleState(ConveyorStateMachineInput input) {
    if (input.ballSensor1Trigger && !lastInput.orElse(input).ballSensor1Trigger) {
      this.nextState = ConveyorControlStates.INCREMENT_BALL_COUNT;
    }
    return new ConveyorSystemModel(ConveyorState.STOPPED);
  }


  public ConveyorSystemModel runIncrementState(ConveyorStateMachineInput input) {
    this.ballCount = ballCount + 1;
    if (input.ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.IDLE;
    } else if (input.ballSensor2Trigger) {
      this.nextState = ConveyorControlStates.INTAKE_EXISTING_BALL;
    } else {
      this.nextState = ConveyorControlStates.INTAKE_NEW_BALL;
    }
    return new ConveyorSystemModel(ConveyorState.STOPPED);
  }

  public ConveyorSystemModel runIntakeNewBallState(ConveyorStateMachineInput input) {
    if ((input.ballSensor2Trigger && !lastInput.orElse(input).ballSensor2Trigger) || input.ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.IDLE;
    }
    return new ConveyorSystemModel(ConveyorState.INTAKE_SLOW);
  }

  public ConveyorSystemModel runIntakeExistingBallState(ConveyorStateMachineInput input) {
    if (!input.ballSensor2Trigger && lastInput.orElse(input).ballSensor2Trigger) {
      this.nextState = ConveyorControlStates.INTAKE_NEW_BALL;
    } else if (input.ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.IDLE;
    }
    return new ConveyorSystemModel(ConveyorState.INTAKE_SLOW);
  }

  /**
   * Runs the conveyor state machine.
   */
  public ConveyorSystemModel run(ConveyorStateMachineInput input) {
    ConveyorSystemModel conveyorModel;
    this.currentState = this.nextState;
    switch (this.currentState) {
      case IDLE:
        conveyorModel = runIdleState(input);
        break;
      case INCREMENT_BALL_COUNT:
        conveyorModel = runIncrementState(input);
        break;
      case INTAKE_NEW_BALL:
        conveyorModel = runIntakeNewBallState(input);
        break;
      case INTAKE_EXISTING_BALL:
        conveyorModel = runIntakeExistingBallState(input);
        break;
      default:
        conveyorModel = new ConveyorSystemModel(ConveyorState.STOPPED);
        break;
    }
    this.lastInput = Optional.of(input);
    return conveyorModel;
  }
}
