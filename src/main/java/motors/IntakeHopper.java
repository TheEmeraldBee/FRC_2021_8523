package motors;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpiutil.math.MathUtil;

public class IntakeHopper {
    private final Spark motor;
    private final double maxSpeed;
    private final double speedIncrement;
    private double speed = 0;

    public IntakeHopper(double max, double increment) {

        maxSpeed = max;
        speedIncrement = increment;

        motor = new Spark(2);
    }

    public void setSpeed(boolean forwardButton, boolean backButton, boolean stopButton) {
        if (stopButton) {
            speed = 0;
        }
        else if (forwardButton && speed < maxSpeed) {
            speed += speedIncrement;
        }
        else if (backButton && speed > -maxSpeed) {
            speed -= speedIncrement;
        }

        speed = MathUtil.clamp(speed, -maxSpeed, maxSpeed);
        motor.set(speed);

    }
}
