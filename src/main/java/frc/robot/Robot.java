/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorPWM;
import frc.robot.devices.commands.SolenoidCommand;
import frc.robot.devices.commands.SolenoidCommand.SolenoidState;
import frc.robot.subsystem.conveyor.ConveyorSubsystem;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.drive.DriveSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.hanger.HangerSubsystem;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.shooter.ShooterSubsystem;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.InputContainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
  private HangerSubsystem hangerSubystem;
  private HardwareInterface hardwareInterface;


  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    this.driveSubsystem = new DriveSubsystem();
    this.shooterSubsystem = new ShooterSubsystem();
    this.conveyorSystem = new ConveyorSubsystem();
    this.hangerSubystem = new HangerSubsystem();

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
    List<DeviceOutputCommand> hangerCommands;

    HashMap<String, InputContainer<?>> inputMap = hardwareInterface.getInputValueMap();

    // This is temporary we need a better way of addressing this
    if ((boolean)inputMap.get("driverLeftShoulder").getValue()) {
      shooterCommands = this.shooterSubsystem.run(
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT)
      );
    } else {
      shooterCommands = this.shooterSubsystem.run(
          new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED)
      );
    }

    if ((boolean)inputMap.get("driverYButton").getValue()) {
      hangerCommands = this.hangerSubystem.run(new HangerSystemModel(HangerSystemModel.HangerState.RAISE));
    } else if ((boolean)inputMap.get("driverAButton").getValue()) {
      hangerCommands = this.hangerSubystem.run(new HangerSystemModel(HangerSystemModel.HangerState.LOWER));
    } else {
      hangerCommands = this.hangerSubystem.run(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED));
    }

    conveyorCommands = this.conveyorSystem.run(
        new ConveyorSystemModel(
          ((boolean)inputMap.get("driverRightShoulder").getValue()) ? ConveyorSystemModel.IntakeState.INTAKE : (
            ((boolean)inputMap.get("driverXButton").getValue()) ? ConveyorSystemModel.IntakeState.OUTTAKE : ConveyorSystemModel.IntakeState.STOPPED),
          ((double)inputMap.get("driverRightTrigger").getValue() > .5) ? ConveyorSystemModel.IntakePosition.DOWN : ConveyorSystemModel.IntakePosition.UP,
          ((double)inputMap.get("driverLeftTrigger").getValue() > .5) ? ConveyorSystemModel.ShooterBlockState.OPEN : ConveyorSystemModel.ShooterBlockState.CLOSE
        )
    );

    List<DeviceOutputCommand> driveMotorCommands = this.driveSubsystem.run(
        new DifferentialDriveModel(
          (double)inputMap.get("driverLeftAxisY").getValue() * 0.5,
          (double)inputMap.get("driverRightAxisY").getValue() * 0.5
        )
    );
    hardwareInterface.run(
        //Don't worry about this code, I know it is confusing, but it does make sense
        Stream.of(
            hangerCommands,
            driveMotorCommands,
            shooterCommands, 
            conveyorCommands)
          .flatMap(Collection::stream)
          .collect(Collectors.toList())
    );

    SmartDashboard.putBoolean("RightShoulder", (boolean)inputMap.get("driverRightShoulder").getValue());
    SmartDashboard.putBoolean("LeftShoulder", (boolean)inputMap.get("driverLeftShoulder").getValue());
    SmartDashboard.putNumber("LeftTrigger", (double)inputMap.get("driverLeftTrigger").getValue());
    SmartDashboard.putNumber("RightTrigger", (double)inputMap.get("driverRightTrigger").getValue());
    SmartDashboard.putNumber("ShooterSpeed", (double)inputMap.get("shooterEncoderVelocity").getValue());
    SmartDashboard.putNumber("ConveyorSpeed", (double)inputMap.get("conveyorEncoderVelocity").getValue());
    SmartDashboard.putNumber("currentTime", (double)inputMap.get("currentTime").getValue());
    SmartDashboard.putBoolean("hangerSwitch", (boolean)inputMap.get("hangerSwitch").getValue());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
