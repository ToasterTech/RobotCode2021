/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.shooter.models;

import java.util.Objects;

/**
 * A simple model for Shooter Commands. 
 */
public class ShooterSubsystemModel extends ShooterModel {
  public enum ShooterState {
    STOPPED,
    SHOOT_DEFAULT
  }
  
  public final ShooterState shooterState;

  public ShooterSubsystemModel(ShooterState shooterState) {
    this.shooterState = shooterState;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ShooterSubsystemModel)) {
      return false;
    }
    ShooterSubsystemModel otherVal = (ShooterSubsystemModel) other;
    return this.shooterState == otherVal.shooterState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shooterState);
  }
}
