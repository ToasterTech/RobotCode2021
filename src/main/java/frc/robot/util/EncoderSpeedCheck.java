package frc.robot.util;

/**
 * Very simple calculation to determine if encoder speed is within acceptable range.
 */
public class EncoderSpeedCheck {
  private double deviation;

  public EncoderSpeedCheck(double deviation) {
    this.deviation = deviation;
  }

  public boolean isEncoderAtSpeed(double encoderValue, double targetValue) {
    return (targetValue - deviation <= encoderValue) && (encoderValue <= targetValue + deviation);
  }
}
  