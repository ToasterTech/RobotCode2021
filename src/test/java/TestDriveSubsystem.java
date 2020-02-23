import static org.junit.Assert.assertEquals;

import frc.robot.devices.commands.GenericMotorPWM;
import frc.robot.subsystem.drive.DriveSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestDriveSubsystem {

  /**
   * Build expected motor outputs for a differential drive robot.
   * 
   * @param left  left side of the drive train
   * @param right right side of the drive train
   * @return
   */
  public static List<GenericMotorPWM> buildExpectedMotorOutputs(double left, double right) {
    return Arrays.asList(
      new GenericMotorPWM("leftMotor1", left),
      new GenericMotorPWM("leftMotor2", left),
      new GenericMotorPWM("rightMotor1", right),
      new GenericMotorPWM("rightMotor2", right)

    );
  }

  private DifferentialDriveModel driveModel;
  private List<GenericMotorPWM> expectedOutputs;
  private DriveSubsystem driveSubsystem;

  public TestDriveSubsystem(DifferentialDriveModel driveModel, 
                            List<GenericMotorPWM> expectedOutputs) {
    this.driveModel = driveModel;
    this.expectedOutputs = expectedOutputs;
    this.driveSubsystem = new DriveSubsystem();
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {new DifferentialDriveModel(0, 0), buildExpectedMotorOutputs(0, 0)},
        {new DifferentialDriveModel(1, 1),  buildExpectedMotorOutputs(1, 1)},
        {new DifferentialDriveModel(-1, -1), buildExpectedMotorOutputs(-1, -1)},
        {new DifferentialDriveModel(-1, 1), buildExpectedMotorOutputs(-1, 1)},
        {new DifferentialDriveModel(1, -1), buildExpectedMotorOutputs(1, -1)} 
    });

  }

  @Test
  public void testSubsystemRun() {
    assertEquals(this.driveSubsystem.run(this.driveModel), this.expectedOutputs);
  }
}