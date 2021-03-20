import static org.junit.Assert.assertEquals;

import frc.robot.controllers.ConveyorStateMachine;
import frc.robot.logging.ConveyorLogger;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.HashMap;

import org.junit.Test;


public class TestConveyorLogger {
  @Test
  public void testLoger() {
    ConveyorLogger logger = new ConveyorLogger();
    final HashMap<String, InputContainer<?>> inputMap = new HashMap<String, InputContainer<?>>();   
    ConveyorStateMachine stateMachine = new ConveyorStateMachine();    
                      
    inputMap.put("conveyorSonarFront", new SimpleInputContainer<Double>(0.5));
    inputMap.put("conveyorSonarMiddle", new SimpleInputContainer<Double>(0.4));
    inputMap.put("conveyorSonarTop", new SimpleInputContainer<Double>(1.5));
    inputMap.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5600.0));
    inputMap.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));

    logger.log(inputMap, stateMachine);
  }
}