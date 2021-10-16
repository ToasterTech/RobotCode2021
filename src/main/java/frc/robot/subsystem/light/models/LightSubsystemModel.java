/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.light.models;

import java.util.Objects;

/**
 * A simple model for Shooter Commands. 
 */
public class LightSubsystemModel extends LightModel {
  
  public enum LightState {
    ON,
    OFF
  }
  
  public final LightState lightState;

  public LightSubsystemModel(LightState lightState) {
    this.lightState = lightState;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof LightSubsystemModel)) {
      return false;
    }
    LightSubsystemModel otherVal = (LightSubsystemModel) other;
    return this.lightState.equals(otherVal.lightState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.lightState);
  }

  public String toString() {
    return "LightSubsystemModel(" + this.lightState + ")";
  }
}
