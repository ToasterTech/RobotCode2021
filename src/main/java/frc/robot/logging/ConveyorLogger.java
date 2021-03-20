package frc.robot.logging;

import frc.robot.controllers.ConveyorStateMachine;
import frc.robot.controllers.ConveyorStateMachine.ConveyorStateMachineInput;
import frc.robot.util.InputContainer;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ConveyorLogger {
  private class LogFormat extends Formatter {

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    @Override
    public String format(final LogRecord record) {
      return String.format("%1$s::%2$s::%3$s\n", new SimpleDateFormat(PATTERN).format(new Date(record.getMillis())),
          record.getLevel().getName(), formatMessage(record));
    }
  }

  private Logger logger;
  private Handler fh;
  private LogFormat formatter;

  /**
   * Constructor.
   * 
   * @param path the path to log file
   */
  public ConveyorLogger(String path) {
    logger = Logger.getLogger("Conveyor");
    logger.setUseParentHandlers(false);
    formatter = new LogFormat();
    try {
      fh = new FileHandler(path);
      fh.setFormatter(formatter);
      logger.addHandler(fh);
    } catch (SecurityException | IOException e) {
      fh = new ConsoleHandler();
      fh.setFormatter(formatter);
      logger.addHandler(fh);  
      e.printStackTrace();
    }
  }

  public ConveyorLogger() {
    logger = Logger.getLogger("Conveyor");
    logger.setUseParentHandlers(false);
    formatter = new LogFormat();
    fh = new ConsoleHandler();
    fh.setFormatter(formatter);
    logger.addHandler(fh);
  }
  
  /**
   * Log the state of the conveyor.
   * @param inputMap input map
   * @param conveyorStateMachine the conveyor to log
   */
  public void log(HashMap<String, InputContainer<?>> inputMap, ConveyorStateMachine conveyorStateMachine) {
    logger.info(String.format(
        "input::sensor_front:%1$1.2f::sensor_middle:%2$1.2f::sensor_top:%3$1.2f::shooter_speed:%4$4.2f::shoot_pressed:%5$b",
        (double) inputMap.get("conveyorSonarFront").getValue(), (double) inputMap.get("conveyorSonarMiddle").getValue(),
        (double) inputMap.get("conveyorSonarTop").getValue(),
        (double) inputMap.get("shooterEncoderVelocity").getValue(),
        (boolean) inputMap.get("driverRightShoulder").getValue()));
    ConveyorStateMachineInput input = conveyorStateMachine.getLastInput().orElse(new ConveyorStateMachineInput(false, false, false, false, false));
    logger.info(String.format(
        "conveyor::current_state:%1$s::next_state:%2$s::sensor_1:%3$b::sensor_2:%4$b::sensor_3:%5$b::shooter_at_speed:%6$b::shooter_trigger:%7$b",
        conveyorStateMachine.getCurrentState().toString(),
        conveyorStateMachine.getNextState().toString(),
        input.ballSensor1Trigger,
        input.ballSensor2Trigger,
        input.ballSensor3Trigger,
        input.shooterAtSpeed,
        input.shooterTrigger
    ));
  }
}
