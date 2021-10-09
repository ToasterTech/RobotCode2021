package frc.robot.devices.output;

import edu.wpi.first.wpilibj.Relay;
import frc.robot.devices.commands.DeviceOutputCommand;
import frc.robot.devices.commands.RelayCommand;

public abstract class SpikeRelay extends DeviceOutput {
  public enum Direction {
    Forward(Relay.Direction.kForward), Backward(Relay.Direction.kReverse);

    public final Relay.Direction value;

    private Direction(Relay.Direction value) {
      this.value = value;
    }
  }
  
  private Relay controller;
  private Direction direction;

  public SpikeRelay(int channel, Direction direction) {
    this.direction = direction;
    this.controller = new Relay(channel, this.direction.value);
  }

  @Override
  public boolean isValidCommand(DeviceOutputCommand command) {
    return (command instanceof RelayCommand);
  }

  @Override
  public void run(DeviceOutputCommand command) {
    if (command instanceof RelayCommand) {
      RelayCommand relayCommand = (RelayCommand) command;
      controller.set(relayCommand.state.value);
    }
  }
}