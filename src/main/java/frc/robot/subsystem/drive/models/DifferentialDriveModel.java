/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem.drive.models;

import java.util.Objects;

/**
 * A simple model for Differential Drivetrains. 
 */
public class DifferentialDriveModel extends DriveModel {
  public final double left;
  public final double right;

  public DifferentialDriveModel(double left, double right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof DifferentialDriveModel)) {
      return false;
    }
    DifferentialDriveModel otherVal = (DifferentialDriveModel)other;
    return this.left == otherVal.left && this.right == otherVal.right;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.left, this.right);
  }

  public String toString() {
    return "DifferentialDriveModel(" + left + "::" + right + ")";
  }
}
