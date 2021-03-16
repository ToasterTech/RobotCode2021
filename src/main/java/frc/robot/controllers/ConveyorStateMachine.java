package frc.robot.controllers;

import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel.ConveyorState;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    //Start States
    INIT, 
    //Intake States
    IDLE, INCREMENT_BALL_COUNT, INTAKE_NEW_BALL, INTAKE_EXISTING_BALL, 
    //Conveyor States
    SHOOTER_DECISION, WAIT_FOR_SHOOTER, SHOOT, DECREMENT_BALL_COUNT, PREPARE_NEXT_BALL_FOR_SHOOT
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

  public ConveyorSystemModel runInit(ConveyorStateMachineInput input) {
    if (input.ballSensor1Trigger) {
      this.nextState = ConveyorControlStates.INCREMENT_BALL_COUNT;
    } else { 
      this.nextState = ConveyorControlStates.IDLE;
    }
    return new ConveyorSystemModel(ConveyorState.STOPPED);

  }

  public ConveyorSystemModel runIdleState(ConveyorStateMachineInput input) {
    if (input.ballSensor1Trigger && !lastInput.orElse(input).ballSensor1Trigger) {
      this.nextState = ConveyorControlStates.INCREMENT_BALL_COUNT;
    } else if (input.shooterTrigger) {
      this.nextState = ConveyorControlStates.SHOOTER_DECISION;
    } else {
      this.nextState = ConveyorControlStates.IDLE;
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

  public ConveyorSystemModel runShooterDecision(ConveyorStateMachineInput input) {
    if (input.ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.WAIT_FOR_SHOOTER;
    } else {
      this.nextState = ConveyorControlStates.PREPARE_NEXT_BALL_FOR_SHOOT;
    }
    return new ConveyorSystemModel(ConveyorState.STOPPED);
  }

  public ConveyorSystemModel runWaitForShoot(ConveyorStateMachineInput input) {
    if (input.shooterAtSpeed) {
      this.nextState = ConveyorControlStates.SHOOT;
    } else {
      this.nextState = ConveyorControlStates.WAIT_FOR_SHOOTER;
    }
    return new ConveyorSystemModel(ConveyorState.STOPPED);
  }

  public ConveyorSystemModel runShoot(ConveyorStateMachineInput input) {
    if (!input.ballSensor3Trigger && lastInput.orElse(input).ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.DECREMENT_BALL_COUNT;
    } else {
      this.nextState = ConveyorControlStates.SHOOT;
    }
    return new ConveyorSystemModel(ConveyorState.INTAKE_FAST);
  }

  public ConveyorSystemModel runDecrementBallCount(ConveyorStateMachineInput input) {
    this.ballCount = this.ballCount - 1;
    this.nextState = ConveyorControlStates.PREPARE_NEXT_BALL_FOR_SHOOT;
    return new ConveyorSystemModel(ConveyorState.INTAKE_FAST);
  }

  public ConveyorSystemModel runPrepareNextBall(ConveyorStateMachineInput input) {
    if (input.ballSensor3Trigger && !lastInput.orElse(input).ballSensor3Trigger) {
      this.nextState = ConveyorControlStates.IDLE;
    } else if (input.ballSensor1Trigger && !lastInput.orElse(input).ballSensor1Trigger) {
      this.nextState = ConveyorControlStates.INCREMENT_BALL_COUNT;
    } else if (!input.shooterTrigger) {
      this.nextState = ConveyorControlStates.IDLE;
    }else {
      this.nextState = ConveyorControlStates.PREPARE_NEXT_BALL_FOR_SHOOT;
    }
  
    return new ConveyorSystemModel(ConveyorState.INTAKE_SLOW);
  } 


  /**
   * Runs the conveyor state machine.
   */
  public ConveyorSystemModel run(ConveyorStateMachineInput input) {
    ConveyorSystemModel conveyorModel;
    this.currentState = this.nextState;
    SmartDashboard.putString("ConveyorState", this.currentState.toString());
    switch (this.currentState) {
      case INIT:
        conveyorModel = runInit(input);
        break;
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
      case SHOOTER_DECISION:
        conveyorModel = runShooterDecision(input);
        break;
      case WAIT_FOR_SHOOTER:
        conveyorModel = runWaitForShoot(input);
        break;
      case SHOOT:
        conveyorModel = runShoot(input);
        break;
      case DECREMENT_BALL_COUNT:
        conveyorModel = runDecrementBallCount(input);
        break;
      case PREPARE_NEXT_BALL_FOR_SHOOT:
        conveyorModel = runPrepareNextBall(input);
        break;
      default:
        conveyorModel = new ConveyorSystemModel(ConveyorState.STOPPED);
        break;
    }
    this.lastInput = Optional.of(input);
    return conveyorModel;
  }
}
