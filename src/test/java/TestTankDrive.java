import static org.junit.Assert.assertEquals;

import frc.robot.controllers.TankDrive;
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
public class TestTankDrive {
  private HashMap<String, InputContainer<?>> inputMap1;
  private HashMap<String, InputContainer<?>> inputMap2;

  private RobotModel expectedModel1;  
  private RobotModel expectedModel2;  

  /**
   * Setup tank drive.
   * @param inputMap1 inputMap1
   * @param inputMap2 inputMap2
   * @param expectedModel1 expectedModel1
   * @param expectedModel2 expectedModel2
   */
  public TestTankDrive(HashMap<String, InputContainer<?>> inputMap1, HashMap<String, InputContainer<?>> inputMap2, RobotModel expectedModel1, RobotModel expectedModel2) {
    this.expectedModel1 = expectedModel1;
    this.expectedModel2 = expectedModel2;

    this.inputMap1 = inputMap1;
    this.inputMap2 = inputMap2;

  }

  @Parameters
  public static Collection<Object[]> data() {    
    final HashMap<String, InputContainer<?>> test1_1 = new HashMap<String, InputContainer<?>>();                         
    test1_1.put("driverLeftAxisY", new SimpleInputContainer<Double>(1.0));
    test1_1.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    final HashMap<String, InputContainer<?>> test1_2 = new HashMap<String, InputContainer<?>>();                         
    test1_2.put("driverLeftAxisY", new SimpleInputContainer<Double>(1.0));
    test1_2.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));

    final RobotModel model1_1 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(.01, .01))
                                      .build();
    final RobotModel model1_2 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(.02, .02))
                                      .build();

    final HashMap<String, InputContainer<?>> test2_1 = new HashMap<String, InputContainer<?>>();                         
    test2_1.put("driverLeftAxisY", new SimpleInputContainer<Double>(1.0));
    test2_1.put("driverRightAxisY", new SimpleInputContainer<Double>(1.0));
    final HashMap<String, InputContainer<?>> test2_2 = new HashMap<String, InputContainer<?>>();                         
    test2_2.put("driverLeftAxisY", new SimpleInputContainer<Double>(-1.0));
    test2_2.put("driverRightAxisY", new SimpleInputContainer<Double>(-1.0));

    final RobotModel model2_1 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(.01, .01))
                                      .build();
    final RobotModel model2_2 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(0.0, 0.0))
                                      .build();

    final HashMap<String, InputContainer<?>> test3_1 = new HashMap<String, InputContainer<?>>();                         
    test3_1.put("driverLeftAxisY", new SimpleInputContainer<Double>(.25));
    test3_1.put("driverRightAxisY", new SimpleInputContainer<Double>(.25));
    final HashMap<String, InputContainer<?>> test3_2 = new HashMap<String, InputContainer<?>>();                         
    test3_2.put("driverLeftAxisY", new SimpleInputContainer<Double>(.25));
    test3_2.put("driverRightAxisY", new SimpleInputContainer<Double>(.25));

    final RobotModel model3_1 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(.01, .01))
                                      .build();
    final RobotModel model3_2 = new RobotModel.RobotModelBuilder()
                                      .buildDriveModel(new DifferentialDriveModel(0.02, 0.02))
                                      .build();
                                  
    return Arrays.asList(new Object[][] {
        {test1_1, test1_2, model1_1, model1_2},
        {test2_1, test2_2, model2_1, model2_2},
        {test3_1, test3_2, model3_1, model3_2},
    });
  }

  @Test
  public void testTankDrive() {
    
    TankDrive drive = new TankDrive("driverLeftAxisY", "driverRightAxisY");
    RobotModel results1 = drive.run(this.inputMap1);
    System.out.println(results1);
    System.out.println(this.expectedModel1);
    assertEquals(this.expectedModel1, results1);

    RobotModel results2 = drive.run(this.inputMap2);
    System.out.println(results2);
    System.out.println(this.expectedModel2);
    assertEquals(this.expectedModel2, results2);
  }
}