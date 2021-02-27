import static org.junit.Assert.assertEquals;

import frc.robot.controllers.AutomaticShoot;
import frc.robot.models.RobotModel;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;
import frc.robot.util.SimpleInputContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestAutoShootController {
  private HashMap<String, InputContainer<?>> inputMap;
  private RobotModel expectedModel;  

  public TestAutoShootController(HashMap<String, InputContainer<?>> inputMap, RobotModel expectedModel) {
    this.expectedModel = expectedModel;
    this.inputMap = inputMap;
  }

  @Parameters
  public static Collection<Object[]> data() {
    final HashMap<String, InputContainer<?>> test1 = new HashMap<String, InputContainer<?>>();
    test1.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5700.0));

    final RobotModel model1 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .build();

    final HashMap<String, InputContainer<?>> test2 = new HashMap<String, InputContainer<?>>();
    test2.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(0.0));

    final RobotModel model2 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .build();

    final HashMap<String, InputContainer<?>> test3 = new HashMap<String, InputContainer<?>>();
    test3.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5600.0));

    final RobotModel model3 = new RobotModel.RobotModelBuilder()
                                      .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                      .build();

    final HashMap<String, InputContainer<?>> test4 = new HashMap<String, InputContainer<?>>();
    test4.put("shooterEncoderVelocity", new SimpleInputContainer<Double>(5499.0));

    final RobotModel model4 = new RobotModel.RobotModelBuilder()
                                  .buildShooterModel(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT))
                                  .build();

                                  
    return Arrays.asList(new Object[][] {
        {test1, model1},
        {test2, model2},
        {test3, model3},
        {test4, model4},
    });
  }

  @Ignore
  @Test
  public void testAutoShootController() {
    AutomaticShoot autoShoot = new AutomaticShoot(new EncoderSpeedCheck(200, 5700));
    RobotModel results = autoShoot.run(this.inputMap);
    System.out.println(results);
    System.out.println(this.expectedModel);

    assertEquals(this.expectedModel, results);
  }
}