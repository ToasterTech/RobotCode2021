/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.devices.output;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;

import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.GenericMotorCAN;
import frc.robot.devices.commands.VelocityControlMotorCAN;
import frc.robot.devices.input.EncoderVelocityInputCAN;

import java.util.List;

/**
 * Motor device for SparkMax.
*/
public class DeviceCANSparkMax extends MotorPWM {
  private CANSparkMax controller;
  private CANPIDController pidController;
  private CANEncoder encoder;
  private boolean pidEnabled;
  private List<FollowerMotorCAN> followers;

  /**
   * Default constructor.
   * @param channel can ID 
   * @param motorType motortype either brushed or brushless
   * @param setupPID setup PID for this motor
   */
  public DeviceCANSparkMax(int channel, MotorType motorType, boolean setupPID) {
    this.controller = new CANSparkMax(channel, motorType);
    this.encoder = controller.getEncoder(EncoderType.kHallSensor, 4096);
    this.controller.restoreFactoryDefaults();
    if (setupPID) {
      this.setupPID();
    }
  }

  public DeviceCANSparkMax(int channel, MotorType motorType, boolean setupPID, List<FollowerMotorCAN> followers) {
    this(channel, motorType, setupPID);
    this.followers = followers;
    for (FollowerMotorCAN follower: this.followers) {
      follower.follow(this.controller);
    }
  }

  public EncoderVelocityInputCAN getEncoderVelocityInput() {
    return new EncoderVelocityInputCAN(this.encoder);
  }

  public void setupPID() {
    this.pidController = controller.getPIDController();
    this.pidEnabled = true;
  }
  
  @Override
  public boolean isValidCommand(DeviceOutputCommand command) {
    return (command instanceof GenericMotorCAN 
        || (command instanceof VelocityControlMotorCAN && this.pidEnabled));
  }

  @Override
  public void run(DeviceOutputCommand command) {
    if (command instanceof GenericMotorCAN) {
      GenericMotorCAN canCommand = (GenericMotorCAN) command;
      controller.set(canCommand.setpoint);
    }
    if (command instanceof VelocityControlMotorCAN) {
      if (this.pidEnabled) {
        VelocityControlMotorCAN canCommand = (VelocityControlMotorCAN) command;
        this.pidController.setP(canCommand.gainP);
        this.pidController.setI(canCommand.gainI);
        this.pidController.setD(canCommand.gainD);
        this.pidController.setIZone(canCommand.zoneI);
        this.pidController.setFF(canCommand.gainFF);
        this.pidController.setOutputRange(-1, 1);

        this.pidController.setReference(
            canCommand.setpoint * canCommand.maxRPM, 
            ControlType.kVelocity
        );
      }
    }
  }
}
