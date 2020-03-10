package frc.robot.util;

/**
 * Very simple calculation to determine if encoder speed is within acceptable range.
 */
public class EncoderSpeedCheck {
  private double deviation;
  private double targetValue;

  public EncoderSpeedCheck(double deviation, double targetValue) {
    this.deviation = deviation;
    this.targetValue = targetValue;
  }

  public boolean isEncoderAtSpeed(double encoderValue) {
    return (targetValue - deviation <= Math.abs(encoderValue)) && (Math.abs(encoderValue) <= targetValue + deviation);
  }
}
  