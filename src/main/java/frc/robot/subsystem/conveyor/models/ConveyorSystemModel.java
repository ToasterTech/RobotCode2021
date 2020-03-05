/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.conveyor.models;

import java.util.Objects;

/**
 * A simple model for the conveyor system on the 2020 robot. 
 */
public class ConveyorSystemModel extends ConveyorModel {
  public enum IntakeState {
    INTAKE, OUTTAKE, STOPPED
  }

  public enum IntakePosition {
    UP, DOWN
  }

  public enum ShooterBlockState {
    OPEN, CLOSE
  }


  public final IntakeState intakeState; 
  public final IntakePosition intakePosition;
  public final ShooterBlockState shooterBlockState;

  public ConveyorSystemModel(IntakeState intakeState, IntakePosition intakePosition, ShooterBlockState shooterBlockState) {
    this.intakeState = intakeState;
    this.intakePosition = intakePosition;
    this.shooterBlockState = shooterBlockState;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ConveyorSystemModel)) {
      return false;
    }
    ConveyorSystemModel otherVal = (ConveyorSystemModel)other;
    return this.intakeState.equals(otherVal.intakeState)  && this.intakePosition.equals(otherVal.intakePosition) && this.shooterBlockState.equals(otherVal.shooterBlockState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.intakeState, this.intakePosition, this.shooterBlockState);
  }

  public String toString() {
    return "ConveyorSystemModel(" + intakeState + "::" + intakePosition + "::" + shooterBlockState + ")";
  }
}
