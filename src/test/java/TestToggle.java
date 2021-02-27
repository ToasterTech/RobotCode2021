import static org.junit.Assert.assertEquals;

import frc.robot.util.Toggle;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestToggle {

  private boolean trigger1;
  private boolean trigger2;
  private boolean trigger3;
  private boolean result1;
  private boolean result2;
  private boolean result3;

  /**
   * Create test case.
   * @param trigger1 first trigger
   * @param result1 result1
   * @param trigger2 trigger2
   * @param result2 result2
   * @param trigger3 trigger3
   * @param result3 result3
   */
  public TestToggle(boolean trigger1, boolean result1, boolean trigger2, boolean result2, boolean trigger3, boolean result3) {
    this.trigger1 = trigger1;
    this.trigger2 = trigger2;
    this.trigger3 = trigger3;
    this.result1 = result1;
    this.result2 = result2;
    this.result3 = result3;

  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {true, true, false, true, false, true},
        {false, false, true, true, true, true},
        {true, true, true, true, false, true},
        {true, true, false, true, true, false},

    });
  }

  @Test
  public void testToggle() {
    Toggle toggle = new Toggle();
    boolean toggleRes = toggle.run(this.trigger1);
    assertEquals(toggleRes, result1);
    System.out.println(toggleRes + "::" + result1);
    toggleRes = toggle.run(this.trigger2);
    assertEquals(toggle.run(this.trigger2), result2);
    System.out.println(toggleRes + "::" + result2);
    toggleRes = toggle.run(this.trigger3);
    assertEquals(toggle.run(this.trigger3), result3);
    System.out.println(toggleRes + "::" + result3);
  }
}