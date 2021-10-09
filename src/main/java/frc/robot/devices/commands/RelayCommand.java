package frc.robot.devices.commands;

import java.util.Objects;

import edu.wpi.first.wpilibj.Relay;

public class RelayCommand extends DeviceOutputCommand {
  public enum RelayState {
    ON(Relay.Value.kOn), OFF(Relay.Value.kOff);

    public final Relay.Value value;

    private RelayState(Relay.Value value) {
      this.value = value;
    }
  }

  public final String deviceId;
  public final RelayState state;

  public RelayCommand(String deviceId, RelayState state) {
    this.deviceId = deviceId;
    this.state = state;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RelayCommand)) {
      return false;
    }
    RelayCommand otherVal = (RelayCommand) other;
    return this.state.equals(otherVal.state) & this.deviceId.equals(otherVal.deviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.state, this.deviceId);
  }

  @Override
  public String getDeviceId() {
    return this.deviceId;
  }
  
}
