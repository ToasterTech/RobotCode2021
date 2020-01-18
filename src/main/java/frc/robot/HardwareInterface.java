/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.PWMTalonSRX;

/**
 * Add your docs here.
 */
public class HardwareInterface {
  private PWMTalonSRX motor1 = new PWMTalonSRX(1);
  private PWMTalonSRX motor2 = new PWMTalonSRX(2);
  private PWMTalonSRX motor3 = new PWMTalonSRX(3);
  private PWMTalonSRX motor4 = new PWMTalonSRX(4);

  /**
   * Sets motor motorName to motorValue.
   * @param motorName Name of the motor.
   * @param motorValue Value to set the motor to.
   */
  public void setMotor(String motorName, double motorValue) {
    if (motorName.equals("motor1")) {
      this.motor1.set(motorValue);
    }
    if (motorName.equals("motor2")) {
      this.motor2.set(motorValue);
    }
    if (motorName.equals("motor3")) {
      this.motor3.set(motorValue);
    }
    if (motorName.equals("motor4")) {
      this.motor4.set(motorValue);
    }
  }

}
