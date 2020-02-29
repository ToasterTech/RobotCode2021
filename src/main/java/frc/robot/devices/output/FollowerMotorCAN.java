package frc.robot.devices.output;

import com.revrobotics.CANSparkMax;


public class FollowerMotorCAN {
  private CANSparkMax controller;
  private boolean inverted;

  public FollowerMotorCAN(CANSparkMax controller, boolean inverted) {
    this.controller = controller;
    this.inverted = inverted;
  }
  
  public FollowerMotorCAN(CANSparkMax controller) {
    this(controller, false);
  }

  public void follow(CANSparkMax leader) {
    this.controller.follow(leader, this.inverted);
  }
}
