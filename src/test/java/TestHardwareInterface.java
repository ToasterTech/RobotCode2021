import static org.junit.Assert.assertEquals;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.BaseHardwareInterface;
import frc.robot.devices.input.LimitSwitch;
import frc.robot.devices.input.gamepad.GamepadInput;
import frc.robot.util.InputContainer;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;



public class TestHardwareInterface {

  private class HardwareInterfaceMock extends BaseHardwareInterface {
  
    public HardwareInterfaceMock(LimitSwitch testLimitSwitch) {
      super();
      this.inputMap.put("test", testLimitSwitch);
    }
  
    public HardwareInterfaceMock(GamepadInput testGamepad) {
      super();
      this.inputMap.put("driverGamepad", testGamepad);
      this.inputMap.putAll(testGamepad.getDeviceMap());
    }

  }


  @Test
  public void testInputMap() {
    // Test to make sure the input map is being generated correctly.
    DigitalInput testDigitalInput = Mockito.mock(DigitalInput.class);
    Mockito.doReturn(true).when(testDigitalInput).get();

    LimitSwitch limitSwitch = new LimitSwitch(testDigitalInput);

    HardwareInterfaceMock testHW = new HardwareInterfaceMock(limitSwitch);

    // Because of type of erasure we have to be careful here
    assert (boolean)(testHW.getInputValueMap().get("test").getValue());
  }

  @Test
  public void testGamepad() {
    // Test to make sure the input map is being generated correctly.
    Joystick testJoystick = Mockito.mock(Joystick.class);
    Mockito.doReturn(true).when(testJoystick).getRawButton(6);
    Mockito.doReturn(false).when(testJoystick).getRawButton(5);

    Mockito.when(testJoystick.getRawAxis(0)).thenReturn(1.0);
    Mockito.when(testJoystick.getRawAxis(1)).thenReturn(-1.0);
    Mockito.when(testJoystick.getRawAxis(4)).thenReturn(.5);
    Mockito.when(testJoystick.getRawAxis(5)).thenReturn(-.5);

    GamepadInput gamepadTest = new GamepadInput("driver", testJoystick);

    HardwareInterfaceMock testHW = new HardwareInterfaceMock(gamepadTest);

    // Because of type of erasure we have to be careful here
    HashMap<String,InputContainer<?>> inputValueMap = (
        (HashMap<String,InputContainer<?>>) testHW.getInputValueMap().get("driverGamepad").getValue()
    );
    for (Map.Entry<String, InputContainer<?>> entry: inputValueMap.entrySet()) {
      assertEquals(entry.getValue(), gamepadTest.getValue().getValue().get(entry.getKey()));
    }
    Boolean driveLeftTrigger = (boolean)testHW.getInputValueMap().get("driverLeftShoulder").getValue();
    Boolean driveRightTrigger = (boolean)testHW.getInputValueMap().get("driverRightShoulder").getValue();

    final double leftJoyX = (double)testHW.getInputValueMap().get("driverLeftAxisX").getValue();
    final double leftJoyY = (double)testHW.getInputValueMap().get("driverLeftAxisY").getValue();
    final double rightJoyX = (double)testHW.getInputValueMap().get("driverRightAxisX").getValue();
    final double rightJoyY = (double)testHW.getInputValueMap().get("driverRightAxisY").getValue();

    assertEquals(true, driveLeftTrigger);
    assertEquals(false, driveRightTrigger);
    assertEquals(1.0, leftJoyX, 0.0001);
    assertEquals(-1.0, leftJoyY, 0.0001);
    assertEquals(.5, rightJoyX, 0.0001);
    assertEquals(-.5, rightJoyY, 0.0001);

  }

}