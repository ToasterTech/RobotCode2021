/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;


/**
 * Tooggle based on input trigger.
 */
public class Toggle {
  private boolean on;
  private boolean lastTrigger;

  public Toggle() {
    this.on = false;
    this.lastTrigger = false;
  }

  public boolean run(boolean trigger) {
    if (trigger && !lastTrigger) {
      this.on = ! this.on;
    }
    this.lastTrigger = trigger;
    return this.on;
  }
}
