/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.hanger.models;

import java.util.Objects;

/**
 * A simple model for the hanger system on the 2020 robot. 
 */
public class HangerSystemModel extends HangerModel {
  public enum HangerState {
    RAISE, LOWER, LOWER_FAST, STOPPED
  }

  public final HangerState hangerState;

  public HangerSystemModel(HangerState hangerState) {
    this.hangerState = hangerState;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HangerSystemModel)) {
      return false;
    }
    HangerSystemModel otherVal = (HangerSystemModel) other;
    return this.hangerState == otherVal.hangerState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.hangerState);
  }
}
