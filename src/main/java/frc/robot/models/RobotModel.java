/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.models;

import frc.robot.subsystem.conveyor.models.ConveyorModel;
import frc.robot.subsystem.drive.models.DriveModel;
import frc.robot.subsystem.hanger.models.HangerModel;
import frc.robot.subsystem.light.models.LightModel;
import frc.robot.subsystem.shooter.models.ShooterModel;

import java.util.Objects;
import java.util.Optional;

/**
 * A model for robot data.
 */
public class RobotModel extends Model {
  public final Optional<ConveyorModel> conveyorModel;
  public final Optional<ShooterModel> shooterModel;
  public final Optional<DriveModel> driveModel;
  public final Optional<HangerModel> hangerModel;
  public final Optional<LightModel> lightModel;

  public static class RobotModelBuilder {
    private Optional<ConveyorModel> conveyorModel = Optional.empty();
    private Optional<ShooterModel> shooterModel = Optional.empty();
    private Optional<DriveModel> driveModel = Optional.empty();
    private Optional<HangerModel> hangerModel = Optional.empty();
    private Optional<LightModel> lightModel = Optional.empty();

    public RobotModelBuilder buildConveyorModel(ConveyorModel model) {
      this.conveyorModel = Optional.of(model);
      return this;
    }

    public RobotModelBuilder buildShooterModel(ShooterModel model) {
      this.shooterModel = Optional.of(model);
      return this;
    }

    public RobotModelBuilder buildDriveModel(DriveModel model) {
      this.driveModel = Optional.of(model);
      return this;
    }

    public RobotModelBuilder buildHangerModel(HangerModel model) {
      this.hangerModel = Optional.of(model);
      return this;
    }

    public RobotModelBuilder buildLightModel(LightModel model) {
      this.lightModel = Optional.of(model);
      return this;
    }

    public RobotModel build() {
      return new RobotModel(this);
    }
  }

  public RobotModel(RobotModelBuilder builder) {
    this.conveyorModel = builder.conveyorModel;
    this.shooterModel = builder.shooterModel;
    this.driveModel = builder.driveModel;
    this.hangerModel = builder.hangerModel;
    this.lightModel = builder.lightModel;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RobotModel)) {
      return false;
    }
    RobotModel otherVal = (RobotModel) other;
    return (this.conveyorModel.equals(otherVal.conveyorModel) && this.shooterModel.equals(otherVal.shooterModel)
        && this.driveModel.equals(otherVal.driveModel) && this.hangerModel.equals(otherVal.hangerModel));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.conveyorModel, this.shooterModel, this.driveModel, this.hangerModel);
  }

  public String toString() {
    return "RobotModel(" + this.conveyorModel + "::" + this.shooterModel + "::" + this.driveModel + "::"
        + this.hangerModel + ")";
  }

}
