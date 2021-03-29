package motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Drivetrain {

    // Define Motors
    private final CANSparkMax leftMotor;
    private final CANSparkMax rightMotor;

    // Factor at which to multiply axis for speeds
    private double speedFactor = 0.3;

    private boolean backwards = false;

    private boolean lastSwapMove = false;

    public Drivetrain() {
        // Set Up Motors
        leftMotor = new CANSparkMax(2, MotorType.kBrushed);
        rightMotor = new CANSparkMax(1, MotorType.kBrushed);

        // Reset Motor Controller Settings
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();

        // Change the Speed Of Acceleration
        leftMotor.setOpenLoopRampRate(1);
        rightMotor.setOpenLoopRampRate(1);
    }

    public void arcadeDrive(double lAxis, double twist) {

        // Set Motor Speed
        double lMotorSpeed;
        double rMotorSpeed;

        // Custom Input Dead-zones
        double axisRange = 0.01;
        if (lAxis < axisRange && lAxis > -axisRange) { lAxis = 0; }
        if (twist < axisRange && twist > -axisRange) { twist = 0; }

        // Set Default Speed
        rMotorSpeed = lAxis;
        lMotorSpeed = lAxis;

        // Turning In Place
        if (lAxis == 0 && twist != 0) {
            lMotorSpeed = -twist * 0.75;
            rMotorSpeed = twist * 0.75;
        }

        // If Forward
        if (lAxis > 0) {
            // Turning Left
            if (twist > 0) {
                rMotorSpeed = lAxis - (Math.abs(twist) * 0.7);
            }
            // Turning Right
            else if (twist < 0) {
                lMotorSpeed = lAxis - (Math.abs(twist) * 0.7);
                rMotorSpeed = lAxis;
            }
        }

        // If Backward
        if (lAxis < 0) {
            // Turning Left
            if (twist > 0) {
                lMotorSpeed = lAxis;
                rMotorSpeed = lAxis + (Math.abs(twist) * 0.7);
            }
            // Turning Right
            else if (twist < 0) {
                lMotorSpeed = lAxis + (Math.abs(twist) * 0.7);
                rMotorSpeed = lAxis;
            }
        }

        // Speed Factors
        rMotorSpeed *= speedFactor;
        lMotorSpeed *= speedFactor;

        // Move Motors
        if (!backwards) {
            rightMotor.set(MathUtil.clamp(-rMotorSpeed, -1, 1));
            leftMotor.set(MathUtil.clamp(lMotorSpeed, -1, 1));
        }
        else {
            leftMotor.set(MathUtil.clamp(-rMotorSpeed, -1, 1));
            rightMotor.set(MathUtil.clamp(lMotorSpeed, -1, 1));
        }
    }

    public void setSpeed(double rSpeed, double lSpeed) {
        rightMotor.set(rSpeed);
        leftMotor.set(lSpeed);
    }

    public void swapDirection(boolean button) {
        if (button && !lastSwapMove) {
            backwards = !backwards;
        }
        lastSwapMove = button;
    }

    public void changeSpeed(int dPad) {
        double minSpeed = 0.3;
        double maxSpeed = 1;
        if (dPad == 0 && speedFactor < maxSpeed) {
            speedFactor = Math.round((speedFactor + 0.1) * 10.0) / 10.0;
            System.out.println(speedFactor);
        }
        else if (dPad == 180 && speedFactor > minSpeed) {
            speedFactor = Math.round((speedFactor - 0.1) * 10.0) / 10.0;
            System.out.println(speedFactor);
        }
    }
}
