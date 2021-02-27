package frc.robot.subsystem.conveyor.models;


/**
 * Base class for models defining drive behavior.
 */
public class ConveyorSystemModel extends ConveyorModel {

  public enum ConveyorState {
    INTAKE_SLOW, INTAKE_FAST, STOPPED, OUTAKE
  }

public final ConveyorState conveyorState;

  public ConveyorSystemModel(ConveyorState state) {
    this.conveyorState = state;
  }

  public ConveyorSystemModel() {
    this.conveyorState = ConveyorState.STOPPED;
  }
  @Override
  public boolean equals(Object other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return 0;
  }

}
