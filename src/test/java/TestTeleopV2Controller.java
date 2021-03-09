import static org.junit.Assert.assertEquals;

import frc.robot.controllers.ConveyorStateMachine;
import frc.robot.controllers.TeleopControllerV2;
import frc.robot.models.RobotModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel.ConveyorState;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestTeleopV2Controller {
  private HashMap<String, InputContainer<?>> inputMap;
  private RobotModel expectedModel;

  public TestTeleopV2Controller(HashMap<String, InputContainer<?>> inputMap, RobotModel expectedModel) {
    this.expectedModel = expectedModel;
    this.inputMap = inputMap;
  }

  @Parameters
  public static Collection<Object[]> data() {
    final HashMap<String, InputContainer<?>> test1 = new HashMap<String, InputContainer<?>>();
    test1.put("driverLeftAxisY", new SimpleInputContainer<Double>(0.0));
    test1.put("driverRightAxisY", new SimpleInputContainer<Double>(0.0));
    test1.put("driverRightTrigger", new SimpleInputContainer<Double>(0.0));
    test1.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));
    test1.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(0.0));

    test1.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(false));
    test1.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));
    test1.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test1.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test1.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    test1.put("operatorJoystickTopLeftButton", new SimpleInputContainer<Boolean>(false));
    test1.put("operatorJoystickTopRightButton", new SimpleInputContainer<Boolean>(false));
    test1.put("operatorJoystickTrigger", new SimpleInputContainer<Boolean>(false));
    test1.put("operatorBaseRightUpperButton", new SimpleInputContainer<Boolean>(false));
    test1.put("operatorBaseLeftUpperButton", new SimpleInputContainer<Boolean>(false));
    test1.put("operatorBaseLeftLowerButton", new SimpleInputContainer<Boolean>(false));
    
    test1.put("conveyorSonarFront", new SimpleInputContainer<Double>(0.0));
    test1.put("conveyorSonarMiddle", new SimpleInputContainer<Double>(0.0));
    test1.put("conveyorSonarTop", new SimpleInputContainer<Double>(0.0));

    final RobotModel model1 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildDriveModel(new DifferentialDriveModel(0.0, 0.0))
                                      .buildConveyorModel(new ConveyorSystemModel(ConveyorState.STOPPED))
                                      .build();

    final HashMap<String, InputContainer<?>> test2 = new HashMap<String, InputContainer<?>>();
    test2.put("driverLeftAxisY", new SimpleInputContainer<Double>(0.0));
    test2.put("driverRightAxisY", new SimpleInputContainer<Double>(0.0));
    test2.put("driverRightTrigger", new SimpleInputContainer<Double>(1.0));
    test2.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));
    test2.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(3000.0));

    test2.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(true));
    test2.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));
    test2.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test2.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test2.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    test2.put("operatorJoystickTopLeftButton", new SimpleInputContainer<Boolean>(false));
    test2.put("operatorJoystickTopRightButton", new SimpleInputContainer<Boolean>(false));
    test2.put("operatorJoystickTrigger", new SimpleInputContainer<Boolean>(false));
    test2.put("operatorBaseRightUpperButton", new SimpleInputContainer<Boolean>(false));
    test2.put("operatorBaseLeftUpperButton", new SimpleInputContainer<Boolean>(false));
    test2.put("operatorBaseLeftLowerButton", new SimpleInputContainer<Boolean>(false));

    test2.put("conveyorSonarFront", new SimpleInputContainer<Double>(0.0));
    test2.put("conveyorSonarMiddle", new SimpleInputContainer<Double>(0.0));
    test2.put("conveyorSonarTop", new SimpleInputContainer<Double>(0.0));

    final RobotModel model2 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildDriveModel(new DifferentialDriveModel(0.0, 0.0))
                                      .buildConveyorModel(new ConveyorSystemModel(ConveyorState.STOPPED))
                                      .build();


    final HashMap<String, InputContainer<?>> test3 = new HashMap<String, InputContainer<?>>();
    test3.put("driverLeftAxisY", new SimpleInputContainer<Double>(0.0));
    test3.put("driverRightAxisY", new SimpleInputContainer<Double>(0.0));
    test3.put("driverRightTrigger", new SimpleInputContainer<Double>(1.0));
    test3.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));
    test3.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5600.0));

    test3.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(true));
    test3.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));
    test3.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test3.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test3.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    test3.put("operatorJoystickTopLeftButton", new SimpleInputContainer<Boolean>(false));
    test3.put("operatorJoystickTopRightButton", new SimpleInputContainer<Boolean>(false));
    test3.put("operatorJoystickTrigger", new SimpleInputContainer<Boolean>(false));
    test3.put("operatorBaseRightUpperButton", new SimpleInputContainer<Boolean>(false));
    test3.put("operatorBaseLeftUpperButton", new SimpleInputContainer<Boolean>(false));
    test3.put("operatorBaseLeftLowerButton", new SimpleInputContainer<Boolean>(false));

    test3.put("conveyorSonarFront", new SimpleInputContainer<Double>(0.0));
    test3.put("conveyorSonarMiddle", new SimpleInputContainer<Double>(0.0));
    test3.put("conveyorSonarTop", new SimpleInputContainer<Double>(0.0));


    final RobotModel model3 = new RobotModel.RobotModelBuilder()
                                  .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED))
                                  .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                  .buildDriveModel(new DifferentialDriveModel(0.0, 0.0))
                                  .buildConveyorModel(new ConveyorSystemModel(ConveyorState.STOPPED))
                                  .build();


    final HashMap<String, InputContainer<?>> test4 = new HashMap<String, InputContainer<?>>();
    test4.put("driverLeftAxisY", new SimpleInputContainer<Double>(0.0));
    test4.put("driverRightAxisY", new SimpleInputContainer<Double>(0.0));
    test4.put("driverRightTrigger", new SimpleInputContainer<Double>(1.0));
    test4.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));
    test4.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5600.0));

    test4.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(false));
    test4.put("driverRightShoulder", new SimpleInputContainer<Boolean>(true));
    test4.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test4.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test4.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    test4.put("operatorJoystickTopLeftButton", new SimpleInputContainer<Boolean>(false));
    test4.put("operatorJoystickTopRightButton", new SimpleInputContainer<Boolean>(false));
    test4.put("operatorJoystickTrigger", new SimpleInputContainer<Boolean>(false));
    test4.put("operatorBaseRightUpperButton", new SimpleInputContainer<Boolean>(false));
    test4.put("operatorBaseLeftUpperButton", new SimpleInputContainer<Boolean>(false));
    test4.put("operatorBaseLeftLowerButton", new SimpleInputContainer<Boolean>(false));

    test4.put("conveyorSonarFront", new SimpleInputContainer<Double>(0.0));
    test4.put("conveyorSonarMiddle", new SimpleInputContainer<Double>(0.0));
    test4.put("conveyorSonarTop", new SimpleInputContainer<Double>(0.0));

    final RobotModel model4 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildDriveModel(new DifferentialDriveModel(0.0, 0.0))
                                      .buildConveyorModel(new ConveyorSystemModel(ConveyorState.STOPPED))
                                      .build();
                                  
    return Arrays.asList(new Object[][] {
        {test1, model1},
        {test2, model2},
        {test3, model3},
        {test4, model4},

    });
  }

  @Test
  public void testTeleopControllerV1() {
    TeleopControllerV2 teleopControllerV1 = new TeleopControllerV2(new EncoderSpeedCheck(200, 5700), new ConveyorStateMachine());
    RobotModel results = teleopControllerV1.run(this.inputMap);
    System.out.println(results);
    System.out.println(this.expectedModel);

    assertEquals(this.expectedModel, results);
  }
}