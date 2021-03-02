import frc.robot.controllers.ConveyorStateMachine;
import jdk.internal.jline.internal.TestAccessible;

public class TestConveyorStateMachine {
    @Test
    public void testSimpleConveyorSetup(){
        ConveyorStateMachine staetMachine = new ConveyorStateMachine();
        assert stateMachine.getCurrentState() == ConveyorStateMachine.ConveyorControlStates.Idle;    }
}
