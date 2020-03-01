import static org.junit.Assert.assertEquals;

import frc.robot.util.EncoderSpeedCheck;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestEncoderSpeedCheck {

  private EncoderSpeedCheck speedChecker;
  private boolean withinRange;
  private double encoderValue;
  private double targetValue;

  public TestEncoderSpeedCheck(double range, double encoderValue, double targetValue, boolean result) {
    this.speedChecker = new EncoderSpeedCheck(range);
    this.encoderValue = encoderValue;
    this.withinRange = result;
    this.targetValue = targetValue;
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {10, 500, 500, true},
        {10, 500, 510, true},
        {10, 500, 505, true},
        {10, 500, 515, false},
        {10, 500, 490, true},
        {10, 500, 485, false},
        {10, 500, 489.9, false},
        {10, 500, 510.1, false},
    });
  }

  @Test
  public void testEncoderSpeedCheck() {
    assertEquals(this.withinRange, this.speedChecker.isEncoderAtSpeed(this.encoderValue, this.targetValue));
  }
}