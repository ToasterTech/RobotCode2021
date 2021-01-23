/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.output;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;

import java.util.List;

/**
 * Motor device for SparkMax.
*/
public class DeviceCANTalonFX extends MotorCAN {
  private TalonFX controller;
  private List<DeviceCANTalonFX> followers;
  private boolean invert;

  /**
   * Default constructor.
   * @param channel can ID 
   * @param invert should the motor be inverted 
   */
  public DeviceCANTalonFX(int channel, boolean invert) {
    this.controller = new TalonFX(channel);
    // Default config
    TalonFXConfiguration configs = new TalonFXConfiguration();
    this.controller.configAllSettings(configs);

    this.controller.setInverted(invert);
    this.invert = invert;
  }

  public DeviceCANTalonFX(int channel) {
    this(channel, false);
  }


  public DeviceCANTalonFX(int channel, boolean invert, List<DeviceCANTalonFX> followers) {
    this(channel, invert);
    this.followers = followers;
    for (DeviceCANTalonFX follower: this.followers) {
      follower.follow(this);
    }
  }

  public DeviceCANTalonFX(int channel, List<DeviceCANTalonFX> followers) {
    this(channel, false, followers);
  }
  
  @Override
  public boolean isValidCommand(DeviceOutputCommand command) {
    return (command instanceof GenericMotorCAN);
    // TODO: Velocity commands not implemented yet
    // || (command instanceof VelocityControlMotorCAN && this.pidEnabled));
  }

  @Override
  public void run(DeviceOutputCommand command) {
    if (command instanceof GenericMotorCAN) {
      GenericMotorCAN canCommand = (GenericMotorCAN) command;
      controller.set(TalonFXControlMode.PercentOutput, canCommand.setpoint);
    }
    // TODO: Implement velocity control
    // if (command instanceof VelocityControlMotorCAN) {
    //   if (this.pidEnabled) {
    //     VelocityControlMotorCAN canCommand = (VelocityControlMotorCAN) command;
    //     this.pidController.setReference(
    //         canCommand.setpoint * canCommand.maxRPM, 
    //         ControlType.kVelocity
    //     );
    //   }
    // }
  }

  private TalonFX getController() {
    return this.controller;
  }

  @Override
  protected void follow(MotorCAN master) {
    DeviceCANTalonFX masterSparkMax = (DeviceCANTalonFX) master;
    this.controller.follow(masterSparkMax.getController());
    if (this.invert) {
      // Set this the follower to oppose master if it is inverted
      this.controller.setInverted(InvertType.OpposeMaster);
    }
  }
}
