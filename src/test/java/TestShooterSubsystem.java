import static org.junit.Assert.assertEquals;

import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.devices.output.DeviceOutput;
import frc.robot.subsystem.shooter.ShooterSubsystem;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class TestShooterSubsystem {
  private ShooterSubsystemModel shooterModel;
  private List<DeviceOutput> expectedOutputs;
  private ShooterSubsystem shooterSubsystem;

  public TestShooterSubsystem(ShooterSubsystemModel shooterModel, 
                              List<DeviceOutput> expectedOutputs) {
    this.shooterModel = shooterModel;
    this.expectedOutputs = expectedOutputs;
    this.shooterSubsystem = new ShooterSubsystem();
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT), 
          Arrays.asList(
            new VelocityControlMotorCAN(
              "shooterMotor", 
              -1,
              5700
            )
          )
        },
        {
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED),  
          Arrays.asList(
            new GenericMotorCAN("shooterMotor", 0.0)
          )
        },
    });

  }

  @Test
  public void testSubsystemRun() {
    assertEquals(this.shooterSubsystem.run(this.shooterModel), this.expectedOutputs);
  }
}