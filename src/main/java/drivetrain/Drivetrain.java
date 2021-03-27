package drivetrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Drivetrain {

    private final CANSparkMax m_leftMotor;
    private final CANSparkMax m_rightMotor;

    private double speedFactor = 0.3;

    public Drivetrain() {
        // Define Motors
        m_leftMotor = new CANSparkMax(2, MotorType.kBrushed);
        m_rightMotor = new CANSparkMax(1, MotorType.kBrushed);
        m_leftMotor.restoreFactoryDefaults();
        m_rightMotor.restoreFactoryDefaults();
        m_leftMotor.setClosedLoopRampRate(2);
        m_rightMotor.setClosedLoopRampRate(2);
    }

    public void arcadeDrive(double lAxis, double twist) {

        double lMotorSpeed;
        double rMotorSpeed;

        // Custom Input Dead-zones
        double axisRange = 0.01;
        if (lAxis < axisRange && lAxis > -axisRange) { lAxis = 0; }
        if (twist < axisRange && twist > -axisRange) { twist = 0; }

        // Set Default Speed
        rMotorSpeed = lAxis;
        lMotorSpeed = lAxis;

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

        rMotorSpeed *= speedFactor;
        lMotorSpeed *= speedFactor;

        m_rightMotor.set(MathUtil.clamp(rMotorSpeed, -1, 1));
        m_leftMotor.set(MathUtil.clamp(-lMotorSpeed, -1, 1));
    }

    public void setSpeed(double rSpeed, double lSpeed) {
        m_rightMotor.set(rSpeed);
        m_leftMotor.set(lSpeed);
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
