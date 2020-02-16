/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.subsystem.drive.DriveSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private DriveSubsystem driveSubsystem;
  private HardwareInterface hardwareInterface;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    this.driveSubsystem = new DriveSubsystem();
    this.hardwareInterface = new HardwareInterface();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    List<DeviceOutputCommand> driveMotorCommands = this.driveSubsystem.run(
        new DifferentialDriveModel(0, 0)
    );
    List<DeviceOutputCommand> shooterCommands = Arrays.asList(
        new VelocityControlMotorCAN(
          "shooterMotor", 
          .1,
          .000330,
          .000000,
          .00002,
          .000025,
          .000175,
          5700
        )
    );
    hardwareInterface.run(
        // Join lists in java, ugh
        Stream.concat(
            driveMotorCommands.stream(), 
            shooterCommands.stream()
        ).collect(Collectors.toList())
    );
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
