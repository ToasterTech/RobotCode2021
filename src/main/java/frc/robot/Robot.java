/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.AutoModeController;
import frc.robot.controllers.ConveyorStateMachine;
import frc.robot.controllers.RobotStateController;
import frc.robot.controllers.TeleopControllerV2;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.logging.ConveyorLogger;
import frc.robot.models.RobotModel;
import frc.robot.subsystem.conveyor.ConveyorSubsystem;
import frc.robot.subsystem.conveyor.models.ConveyorSystemModel;
import frc.robot.subsystem.drive.DriveSubsystem;
import frc.robot.subsystem.drive.models.DifferentialDriveModel;
import frc.robot.subsystem.hanger.HangerSubsystem;
import frc.robot.subsystem.hanger.models.HangerSystemModel;
import frc.robot.subsystem.intake.IntakeSubsytem;
import frc.robot.subsystem.intake.models.IntakeSystemModel;
import frc.robot.subsystem.intake.models.IntakeSystemModel.IntakePosition;
import frc.robot.subsystem.intake.models.IntakeSystemModel.IntakeState;
import frc.robot.subsystem.shooter.ShooterSubsystem;
import frc.robot.subsystem.shooter.models.ShooterSubsystemModel;
import frc.robot.util.EncoderSpeedCheck;
import frc.robot.util.InputContainer;

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
  private ConveyorSubsystem conveyorSubsystem;
  private HangerSubsystem hangerSubystem;
  private IntakeSubsytem intakeSubsytem;
  private HardwareInterface hardwareInterface;
  private RobotStateController controller;
  private ConveyorStateMachine conveyorStateMachine;
  private ConveyorLogger conveyorLogger;
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    this.driveSubsystem = new DriveSubsystem();
    this.shooterSubsystem = new ShooterSubsystem();
    this.conveyorSubsystem = new ConveyorSubsystem();
    this.hangerSubystem = new HangerSubsystem();
    this.intakeSubsytem = new IntakeSubsytem();
    this.hardwareInterface = new HardwareInterface();
    this.conveyorStateMachine = new ConveyorStateMachine();
    this.conveyorLogger = new ConveyorLogger("/home/lvuser/conveyor.log");

    SmartDashboard.putBoolean("operatorJoystickTopLeftButton", (boolean)false);
    SmartDashboard.putBoolean("operatorJoystickTopRightButton", (boolean)false);
    SmartDashboard.putBoolean("operatorJoystickTrigger", (boolean)false);
    SmartDashboard.putBoolean("operatorBaseRightUpperButton", (boolean)false);
    SmartDashboard.putBoolean("operatorBaseLeftUpperButton", (boolean)false);
    SmartDashboard.putBoolean("operatorBaseLeftLowerButton", (boolean)false);


    SmartDashboard.putNumber("ShooterSpeed", (double)0.0);
    SmartDashboard.putNumber("ConveyorSpeed", (double)0.0);
    SmartDashboard.putNumber("currentTime", (double)0.0);
    SmartDashboard.putBoolean("hangerSwitch", (boolean)false);

  }

  @Override
  public void autonomousInit() {
    this.controller = new AutoModeController(new EncoderSpeedCheck(230, 5300));

  }

  @Override
  public void autonomousPeriodic() {
    HashMap<String, InputContainer<?>> inputMap = hardwareInterface.getInputValueMap();
    RobotModel model = controller.run(inputMap);

    List<DeviceOutputCommand> shooterCommands = shooterSubsystem.run(model.shooterModel.orElse(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED)));
    List<DeviceOutputCommand> conveyorCommands = conveyorSubsystem.run(model.conveyorModel.orElse(
        new ConveyorSystemModel()
    ));
    List<DeviceOutputCommand> hangerCommands = hangerSubystem.run(model.hangerModel.orElse(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED)));
    List<DeviceOutputCommand> driveMotorCommands = driveSubsystem.run(model.driveModel.orElse(new DifferentialDriveModel(0.0, 0.0)));


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

    SmartDashboard.putBoolean("operatorJoystickTopLeftButton", (boolean)inputMap.get("operatorJoystickTopLeftButton").getValue());
    SmartDashboard.putBoolean("operatorJoystickTopRightButton", (boolean)inputMap.get("operatorJoystickTopRightButton").getValue());
    SmartDashboard.putBoolean("operatorJoystickTrigger", (boolean)inputMap.get("operatorJoystickTrigger").getValue());
    SmartDashboard.putBoolean("operatorBaseRightUpperButton", (boolean)inputMap.get("operatorBaseRightUpperButton").getValue());
    SmartDashboard.putBoolean("operatorBaseLeftUpperButton", (boolean)inputMap.get("operatorBaseLeftUpperButton").getValue());
    SmartDashboard.putBoolean("operatorBaseLeftLowerButton", (boolean)inputMap.get("operatorBaseLeftLowerButton").getValue());


    SmartDashboard.putNumber("ShooterSpeed", (double)inputMap.get("shooterEncoderVelocity").getValue());
    SmartDashboard.putNumber("ConveyorSpeed", (double)inputMap.get("conveyorEncoderVelocity").getValue());
    SmartDashboard.putNumber("currentTime", (double)inputMap.get("currentTime").getValue());
    SmartDashboard.putBoolean("hangerSwitch", (boolean)inputMap.get("hangerSwitch").getValue());
  }

  @Override
  public void teleopInit() {
    this.controller = new TeleopControllerV2(
      new EncoderSpeedCheck(270, 5600),
      this.conveyorStateMachine 
      );//this.shooterSubsystem.calculateSetpointSpeed(ShooterSubsystemModel.ShooterState.SHOOT_DEFAULT)));
  }

  @Override
  public void teleopPeriodic() {
    HashMap<String, InputContainer<?>> inputMap = hardwareInterface.getInputValueMap();
    RobotModel model = controller.run(inputMap);

    List<DeviceOutputCommand> shooterCommands = shooterSubsystem.run(model.shooterModel.orElse(new ShooterSubsystemModel(ShooterSubsystemModel.ShooterState.STOPPED)));
    List<DeviceOutputCommand> conveyorCommands = conveyorSubsystem.run(model.conveyorModel.orElse(
        new ConveyorSystemModel()
    ));
    List<DeviceOutputCommand> hangerCommands = hangerSubystem.run(model.hangerModel.orElse(new HangerSystemModel(HangerSystemModel.HangerState.STOPPED)));
    List<DeviceOutputCommand> driveMotorCommands = driveSubsystem.run(model.driveModel.orElse(new DifferentialDriveModel(0.0, 0.0)));
    List<DeviceOutputCommand> intakeCommands = intakeSubsytem.run(model.intakeModel.orElse(new IntakeSystemModel(IntakeState.STOPPED, IntakePosition.UP)));

    hardwareInterface.run(
        //Don't worry about this code, I know it is confusing, but it does make sense
        Stream.of(
            hangerCommands,
            driveMotorCommands,
            shooterCommands, 
            intakeCommands,
            conveyorCommands)
          .flatMap(Collection::stream)
          .collect(Collectors.toList())
    );
    SmartDashboard.putBoolean("operatorJoystickTopLeftButton", (boolean)inputMap.get("operatorJoystickTopLeftButton").getValue());
    SmartDashboard.putBoolean("operatorJoystickTopRightButton", (boolean)inputMap.get("operatorJoystickTopRightButton").getValue());
    SmartDashboard.putBoolean("operatorJoystickTrigger", (boolean)inputMap.get("operatorJoystickTrigger").getValue());
    SmartDashboard.putBoolean("operatorBaseRightUpperButton", (boolean)inputMap.get("operatorBaseRightUpperButton").getValue());
    SmartDashboard.putBoolean("operatorBaseLeftUpperButton", (boolean)inputMap.get("operatorBaseLeftUpperButton").getValue());
    SmartDashboard.putBoolean("operatorBaseLeftLowerButton", (boolean)inputMap.get("operatorBaseLeftLowerButton").getValue());

    SmartDashboard.putNumber("ShooterSpeed", (double)inputMap.get("shooterEncoderVelocity").getValue());
    SmartDashboard.putNumber("ConveyorSpeed", (double)inputMap.get("conveyorEncoderVelocity").getValue());
    SmartDashboard.putNumber("currentTime", (double)inputMap.get("currentTime").getValue());
    SmartDashboard.putBoolean("hangerSwitch", (boolean)inputMap.get("hangerSwitch").getValue());
    
    this.conveyorLogger.log(inputMap, conveyorStateMachine);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
