import static org.junit.Assert.assertEquals;

import frc.robot.controllers.TankDrive;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestTankDriveLimit {
  private double targetValue;
  private double currentValue;
  private double maxDiff;
  private double expected;

  public TestTankDriveLimit(double targetValue, double currentValue, double maxDiff, double expected) {
    this.targetValue = targetValue;
    this.currentValue = currentValue;
    this.maxDiff = maxDiff;
    this.expected = expected;

  }

  @Parameters
  public static Collection<Object[]> data() {                                  
    return Arrays.asList(new Object[][] {
        {0.0, 1.0, .2, .8},
        {0.0, -1.0, .2, -.8},
        {1.0, -1.0, .2, -.8},
        {-1.0, 1.0, .2, .8},
    });
  }

  @Test
  public void testTeleopControllerV1() {
    TankDrive drive = new TankDrive("leftJoystickName", "rightJoystickName");
    double result = drive.limitChange(this.targetValue, this.currentValue, this.maxDiff);
    System.out.println(this.targetValue + "::" + this.currentValue + "::" + this.maxDiff + "::" + this.expected + "::" + result);
    assertEquals(this.expected, result, .001);
  }
}