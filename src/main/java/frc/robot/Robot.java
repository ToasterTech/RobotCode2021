/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.subsystem.conveyor.ConveyorSubsystem;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.drive.DriveSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.shooter.ShooterSubsystem;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;

import java.util.Collection;
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
  private ShooterSubsystem shooterSubsystem;
  private ConveyorSubsystem conveyorSystem;

  private HardwareInterface hardwareInterface;
  private Joystick controller = new Joystick(0);


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    this.driveSubsystem = new DriveSubsystem();
    this.shooterSubsystem = new ShooterSubsystem();
    this.conveyorSystem = new ConveyorSubsystem();
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
    List<DeviceOutputCommand> shooterCommands;
    List<DeviceOutputCommand> conveyorCommands;

    List<DeviceOutputCommand> driveMotorCommands = this.driveSubsystem.run(
        new DifferentialDriveModel(controller.getRawAxis(0), controller.getRawAxis(2))
    );
    // These buttons may be wrong, need to investigate.
    if (controller.getRawButton(0)) {
      shooterCommands = this.shooterSubsystem.run(
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT)
      );
    } else {
      shooterCommands = this.shooterSubsystem.run(
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED)
      );
    }
    // These buttons may be wrong, need to investigate.
    if (controller.getRawButton(1)) {
      conveyorCommands = this.conveyorSystem.run(
          new ConveyorSystemModel(ConveyorSystemModel.IntakeState.INTAKE)
      );
    } else {
      conveyorCommands = this.conveyorSystem.run(
        new ConveyorSystemModel(ConveyorSystemModel.IntakeState.STOPPED)
      );
    }
    hardwareInterface.run(
        //Don't worry about this code, I know it is confusing, but it does make sense
        Stream.of(shooterCommands, conveyorCommands, driveMotorCommands)
          .flatMap(Collection::stream)
          .collect(Collectors.toList())
    );
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
