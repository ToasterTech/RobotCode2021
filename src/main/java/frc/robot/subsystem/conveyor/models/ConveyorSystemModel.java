package frc.robot.subsystem.conveyor.models;

import java.util.Objects;

/**
 * Base class for models defining drive behavior.
 */
public class ConveyorSystemModel extends ConveyorModel {

  public enum ConveyorState {
    INTAKE_SLOW, INTAKE_FAST, STOPPED, OUTAKE
  }

  public final ConveyorState conveyorState;

  public ConveyorSystemModel() {
    this.conveyorState = ConveyorState.STOPPED;
  }

  public ConveyorSystemModel(ConveyorState state) {
    this.conveyorState = state;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ConveyorSystemModel)) {
      return false;
    }
    ConveyorSystemModel otherVal = (ConveyorSystemModel)other;
    return this.conveyorState.equals(otherVal.conveyorState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.conveyorState);
  }

  public String toString() {
    return "ConveyorStateModel(" + this.conveyorState + ")";
  }

}
