/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.intake.models;

import java.util.Objects;

/**
 * A simple model for the conveyor system on the 2020 robot. 
 */
public class IntakeSystemModel extends IntakeModel {
  public enum IntakeState {
    INTAKE, OUTTAKE, STOPPED
  }

  public enum IntakePosition {
    UP, DOWN
  }

  public final IntakeState intakeState; 
  public final IntakePosition intakePosition;

  public IntakeSystemModel(IntakeState intakeState, IntakePosition intakePosition) {
    this.intakeState = intakeState;
    this.intakePosition = intakePosition;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof IntakeSystemModel)) {
      return false;
    }
    IntakeSystemModel otherVal = (IntakeSystemModel)other;
    return this.intakeState.equals(otherVal.intakeState)  && this.intakePosition.equals(otherVal.intakePosition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.intakeState, this.intakePosition);
  }

  public String toString() {
    return "IntakeSystemModel(" + intakeState + "::" + intakePosition + ")";
  }
}
