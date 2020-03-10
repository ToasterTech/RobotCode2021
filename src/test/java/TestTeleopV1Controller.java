import static org.junit.Assert.assertEquals;

import frc.robot.controllers.TeleopControllerV1;
import frc.robot.models.RobotModel;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
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
public class TestTeleopV1Controller {
  private HashMap<String, InputContainer<?>> inputMap;
  private RobotModel expectedModel;  

  public TestTeleopV1Controller(HashMap<String, InputContainer<?>> inputMap, RobotModel expectedModel) {
    this.expectedModel = expectedModel;
    this.inputMap = inputMap;
  }

  @Parameters
  public static Collection<Object[]> data() {
    final HashMap<String, InputContainer<?>> test1 = new HashMap<String, InputContainer<?>>();
    test1.put("driverLeftAxisY", new SimpleInputContainer<Double>(1.0));
    test1.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    test1.put("driverRightTrigger", new SimpleInputContainer<Double>(1.0));
    test1.put("driverLeftTrigger", new SimpleInputContainer<Double>(1.0));

    test1.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(true));
    test1.put("driverRightShoulder", new SimpleInputContainer<Boolean>(true));
    test1.put("driverXButton", new SimpleInputContainer<Boolean>(true));
    test1.put("driverYButton", new SimpleInputContainer<Boolean>(true));
    test1.put("driverAButton", new SimpleInputContainer<Boolean>(false));
    final RobotModel model1 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.RAISE))
                                      .buildConveyorModel(
                                        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.INTAKE, ConveyorSystemModel.IntakePosition.DOWN, ConveyorSystemModel.ShooterBlockState.OPEN))
                                      .buildDriveModel(new DifferentialDriveModel(.4, .4))
                                      .build();

    final HashMap<String, InputContainer<?>> test2 = new HashMap<String, InputContainer<?>>();
    test2.put("driverLeftAxisY", new SimpleInputContainer<Double>(1.0));
    test2.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    test2.put("driverRightTrigger", new SimpleInputContainer<Double>(1.0));
    test2.put("driverLeftTrigger", new SimpleInputContainer<Double>(1.0));

    test2.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(true));
    test2.put("driverRightShoulder", new SimpleInputContainer<Boolean>(true));
    test2.put("driverXButton", new SimpleInputContainer<Boolean>(true));
    test2.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test2.put("driverAButton", new SimpleInputContainer<Boolean>(true));

    final RobotModel model2 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.LOWER))
                                      .buildConveyorModel(
                                        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.INTAKE, ConveyorSystemModel.IntakePosition.DOWN, ConveyorSystemModel.ShooterBlockState.OPEN))
                                      .buildDriveModel(new DifferentialDriveModel(.4, .4))
                                      .build();

    final HashMap<String, InputContainer<?>> test3 = new HashMap<String, InputContainer<?>>();
    test3.put("driverLeftAxisY", new SimpleInputContainer<Double>(-1.0));
    test3.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    test3.put("driverRightTrigger", new SimpleInputContainer<Double>(0.0));
    test3.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));

    test3.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(true));
    test3.put("driverRightShoulder", new SimpleInputContainer<Boolean>(true));
    test3.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test3.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test3.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    final RobotModel model3 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildConveyorModel(
                                        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.INTAKE, ConveyorSystemModel.IntakePosition.UP, ConveyorSystemModel.ShooterBlockState.CLOSE))
                                      .buildDriveModel(new DifferentialDriveModel(-.4, .4))
                                      .build();

    final HashMap<String, InputContainer<?>> test4 = new HashMap<String, InputContainer<?>>();
    test4.put("driverLeftAxisY", new SimpleInputContainer<Double>(-1.0));
    test4.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    test4.put("driverRightTrigger", new SimpleInputContainer<Double>(0.0));
    test4.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));

    test4.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(false));
    test4.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));
    test4.put("driverXButton", new SimpleInputContainer<Boolean>(false));
    test4.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test4.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    final RobotModel model4 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildConveyorModel(
                                        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.STOPPED, ConveyorSystemModel.IntakePosition.UP, ConveyorSystemModel.ShooterBlockState.CLOSE))
                                      .buildDriveModel(new DifferentialDriveModel(-.4, .4))
                                      .build();

    final HashMap<String, InputContainer<?>> test5 = new HashMap<String, InputContainer<?>>();
    test5.put("driverLeftAxisY", new SimpleInputContainer<Double>(-1.0));
    test5.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    test5.put("driverRightTrigger", new SimpleInputContainer<Double>(0.0));
    test5.put("driverLeftTrigger", new SimpleInputContainer<Double>(0.0));

    test5.put("driverLeftShoulder", new SimpleInputContainer<Boolean>(false));
    test5.put("driverRightShoulder", new SimpleInputContainer<Boolean>(false));
    test5.put("driverXButton", new SimpleInputContainer<Boolean>(true));
    test5.put("driverYButton", new SimpleInputContainer<Boolean>(false));
    test5.put("driverAButton", new SimpleInputContainer<Boolean>(false));

    final RobotModel model5 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED))
                                      .buildHangerModel(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED))
                                      .buildConveyorModel(
                                        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.OUTTAKE, ConveyorSystemModel.IntakePosition.UP, ConveyorSystemModel.ShooterBlockState.CLOSE))
                                      .buildDriveModel(new DifferentialDriveModel(-.4, .4))
                                      .build();
                                  
    return Arrays.asList(new Object[][] {
        {test1, model1},
        {test2, model2},
        {test3, model3},
        {test4, model4},
        {test5, model5}
    });
  }

  @Test
  public void testTeleopControllerV1() {
    TeleopControllerV1 teleopControllerV1 = new TeleopControllerV1();
    RobotModel results = teleopControllerV1.run(this.inputMap);
    System.out.println(results);
    System.out.println(this.expectedModel);

    assertEquals(this.expectedModel, results);
  }
}