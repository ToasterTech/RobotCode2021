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
public class DeviceCANSparkMax extends MotorCAN {
  private CANSparkMax controller;
  private CANPIDController pidController;
  private CANEncoder encoder;
  private boolean pidEnabled;
  private List<DeviceCANSparkMax> followers;
  private boolean reverse;

  private double gainP;
  private double gainI;
  private double gainD;
  private double zoneI;
  private double gainFF;

  /**
   * Default constructor.
   * @param channel can ID 
   * @param motorType motortype either brushed or brushless
   */
  public DeviceCANSparkMax(int channel, MotorType motorType, boolean reverse) {
    this.controller = new CANSparkMax(channel, motorType);
    this.controller.setInverted(reverse);
    this.reverse = reverse;
    this.encoder = controller.getEncoder(EncoderType.kHallSensor, 4096);
    this.controller.restoreFactoryDefaults();
  }

  public DeviceCANSparkMax(int channel, MotorType motorType) {
    this(channel, motorType, false);
  }

  public DeviceCANSparkMax(int channel, MotorType motorType, boolean reverse, List<DeviceCANSparkMax> followers) {
    this(channel, motorType, reverse);
    this.followers = followers;
    for (DeviceCANSparkMax follower: this.followers) {
      follower.follow(this);
    }
  }

  public DeviceCANSparkMax(int channel, MotorType motorType, List<DeviceCANSparkMax> followers) {
    this(channel, motorType, false, followers);
  }

  public EncoderVelocityInputCAN getEncoderVelocityInput() {
    return new EncoderVelocityInputCAN(this.encoder);
  }

  public void setupPID(double gainP, double gainI, double gainD,
                       double zoneI, double gainFF) {
    this.pidController = controller.getPIDController();
    this.pidEnabled = true;
    this.updatePIDGains(gainP, gainI, gainD,
                        zoneI, gainFF);
  }

  /**
   * Update PID gains based on what is used on the onboard CAN.
   * @param gainP propotional gain
   * @param gainI integral gain
   * @param gainD derivative gain
   * @param zoneI I zone 
   * @param gainFF gain FF
   */
  public void updatePIDGains(double gainP, double gainI, double gainD,
                             double zoneI, double gainFF) {
    this.gainP = gainP;
    this.gainI = gainI;
    this.gainD = gainD;
    this.zoneI = zoneI;
    this.gainFF = gainFF;

    this.pidController.setP(this.gainP);
    this.pidController.setI(this.gainI);
    this.pidController.setD(this.gainD);
    this.pidController.setIZone(this.zoneI);
    this.pidController.setFF(this.gainFF);
    this.pidController.setOutputRange(-1, 1);
    //TODO: Motor speed should be defined here and we should have better ways of looking that up.
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
        this.pidController.setReference(
            canCommand.setpoint * canCommand.maxRPM, 
            ControlType.kVelocity
        );
      }
    }
  }

  private CANSparkMax getController() {
    return this.controller;
  }

  @Override
  protected void follow(MotorCAN master) {
    DeviceCANSparkMax masterSparkMax = (DeviceCANSparkMax) master;
    this.controller.follow(masterSparkMax.getController(), this.reverse);
  }
}
