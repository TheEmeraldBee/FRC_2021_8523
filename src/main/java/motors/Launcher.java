package motors;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Launcher {

    private final Spark launcherLeft;
    private final Spark launcherRight;

    private double launcherSpeed;
    private final double launchSpeedIncrement;

    public Launcher(double speedIncrement) {
        launcherLeft = new Spark(1);
        launcherRight = new Spark(0);
        launcherLeft.setInverted(true);
        launcherSpeed = 0;
        launchSpeedIncrement = speedIncrement;
    }

    public void setLauncherSpeed(boolean button) {
        if (button && launcherSpeed < 1) {
            launcherSpeed += launchSpeedIncrement;
        }
        else if (!button && launcherSpeed > 0) {
            launcherSpeed -= launchSpeedIncrement;
        }
        launcherSpeed = MathUtil.clamp(launcherSpeed, 0, 1);
        launcherRight.set(launcherSpeed);
        launcherLeft.set(launcherSpeed);
    }
}
