/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.util.Objects;

/**
 * A container for inputs.
 */
public class SimpleInputContainer<T> implements InputContainer<T>  {
  private T value;

  public SimpleInputContainer(T value) {
    this.value = value;
  }

  public T getValue() {
    return this.value;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof SimpleInputContainer<?>)) {
      return false;
    }
    SimpleInputContainer<?> otherValue = (SimpleInputContainer<?>) other;
    return this.value.equals(otherValue.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }
}
