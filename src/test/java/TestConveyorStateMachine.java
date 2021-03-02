import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import frc.robot.controllers.ConveyorStateMachine;
import frc.robot.controllers.ConveyorStateMachine.ConveyorStateMachineInput;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel.ConveyorState;

import org.junit.Test;


public class TestConveyorStateMachine {
  @Test
  public void testSimpleConveyorStates() {
    ConveyorStateMachine stateMachine = new ConveyorStateMachine();
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertTrue(stateMachine.getLastInput().isEmpty());
  
    ConveyorStateMachineInput input = new ConveyorStateMachineInput(false, false, false, false, false);
    ConveyorSystemModel model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, false, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 0);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, false, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 1);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(false, false, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 1);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 1);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 1);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 1);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, false, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, false, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_NEW_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(false, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 2);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, false, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INCREMENT_BALL_COUNT);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 3);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

    input = new ConveyorStateMachineInput(true, true, true, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.INTAKE_EXISTING_BALL);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 3);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.INTAKE_SLOW));

    input = new ConveyorStateMachineInput(true, true, true, false, false);
    model = stateMachine.run(input);
    assertEquals(stateMachine.getCurrentState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getNextState(), ConveyorStateMachine.ConveyorControlStates.IDLE);
    assertEquals(stateMachine.getLastInput().get(), input);
    assertEquals(stateMachine.getBallCount(), 3);
    assertEquals(model, new ConveyorSystemModel(ConveyorState.STOPPED));

  }
}
