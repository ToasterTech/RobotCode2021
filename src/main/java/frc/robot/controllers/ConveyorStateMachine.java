package frc.robot.controllers;

import java.util.Optional;

import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel.ConveyorState;

public class ConveyorStateMachine {
    public class ConveyorStateMachineInput {
        public final boolean ballSensor1Trigger;
        public final boolean ballSensor2Trigger;
        public final boolean ballSensor3Trigger; 
        public final boolean shooterAtSpeed;
        public final boolean shooterTriggered;

        public ConveyorStateMachineInput(boolean ballsensor1, boolean ballsensor2, boolean ballsensor3, boolean shooterAtSpeed, boolean shooterTriggered) {
            this.ballSensor1Trigger = ballsensor1;
            this.ballSensor2Trigger = ballsensor2;
            this.ballSensor3Trigger = ballsensor3;
            this.shooterAtSpeed = shooterAtSpeed;
            this.shooterTriggered = shooterTriggered;
        }

    }

    public enum ConveyorControlStates{
        IDLE, INCREMENT_BALL_COUNT, INTAKE_NEW_BALL, INTAKE_EXISTING_BALL
    }

    private ConveyorControlStates currentState;
    private Optional<ConveyorStateMachineInput> lastInput;
    private int ballCount;

    public ConveyorStateMachine() {
        this.currentState = ConveyorControlStates.IDLE;
        this.lastInput = Optional.empty();
        this.ballCount = 0;
    }

    public ConveyorControlStates getCurrentState(){
        return this.currentState;
    }


    public ConveyorSystemModel runIdleState(ConveyorStateMachineInput input) {
        if(input.ballSensor1Trigger && lastInput.orElse(input).ballSensor1Trigger) {
            this.currentState = ConveyorControlStates.INCREMENT_BALL_COUNT;
        }
        return new ConveyorSystemModel(ConveyorState.STOPPED);

    }

    public ConveyorSystemModel runIncrementState(ConveyorStateMachineInput input) {
        this.ballCount = ballCount + 1;
        if(input.ballSensor3Trigger) {
            this.currentState = ConveyorControlStates.IDLE;
        }else if(input.ballSensor2Trigger) {
            this.currentState = ConveyorControlStates.INTAKE_EXISTING_BALL;
        }else{
            this.currentState = ConveyorControlStates.INTAKE_NEW_BALL;
        }
             return new ConveyorSystemModel(ConveyorState.STOPPED);

    }

    public ConveyorSystemModel runIntakeNewBallState(ConveyorStateMachineInput input) {
            if((input.ballSensor2Trigger && lastInput.orElse(input).ballSensor2Trigger) || input.ballSensor3Trigger) {
                this.currentState = ConveyorControlStates.IDLE;
            }
             return new ConveyorSystemModel(ConveyorState.STOPPED);

    }

    public ConveyorSystemModel runIntakeExistingBallState(ConveyorStateMachineInput input) {
        if(!input.ballSensor2Trigger && lastInput.orElse(input).ballSensor2Trigger) {
            this.currentState = ConveyorControlStates.INTAKE_NEW_BALL;
        }else if(input.ballSensor2Trigger) {
            this.currentState = ConveyorControlStates.IDLE;
        }
         return new ConveyorSystemModel(ConveyorState.STOPPED);
    }

    public ConveyorSystemModel run(ConveyorStateMachineInput input) {
        ConveyorSystemModel conveyorState = new ConveyorSystemModel(ConveyorState.STOPPED);
        switch(this.currentState) {
            case IDLE: conveyorState=runIdleState(input);break;
            case INCREMENT_BALL_COUNT: conveyorState=runIncrementState(input);break;
            case INTAKE_NEW_BALL: conveyorState=runIntakeNewBallState(input);break;
            case INTAKE_EXISTING_BALL: conveyorState=runIntakeExistingBallState(input);break;
        }
        this.lastInput = Optional.of(input);
        return conveyorState;
    }
}