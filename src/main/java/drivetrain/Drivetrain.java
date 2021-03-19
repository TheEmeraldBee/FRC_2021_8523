package drivetrain;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpiutil.math.MathUtil;

public class Drivetrain {

    private final PWMTalonSRX m_leftMotor;
    private final PWMTalonSRX m_rightMotor;

    private double speedFactor = 0.3;

    public Drivetrain() {
        // Define Motors
        m_leftMotor = new PWMTalonSRX(0);
        m_rightMotor = new PWMTalonSRX(3);
    }

    public void arcadeDrive(double lAxis, double twist) {

        double lMotorSpeed;
        double rMotorSpeed;

        // Custom Input Dead-zones
        double axisRange = 0.01;
        if (lAxis < axisRange && lAxis > -axisRange) { lAxis = 0; }
        if (twist < axisRange && twist > -axisRange) { twist = 0; }

        // Turning In Place
        if (lAxis == 0 && twist != 0) {
            lMotorSpeed = -twist*0.75;
            rMotorSpeed = twist*0.75;
        }
        // Turning Left
        else if (twist < 0){
            rMotorSpeed = lAxis;
            lMotorSpeed = lAxis + (Math.abs(twist) * 0.7);
        }
        // Turning Right
        else if (twist > 0){
            rMotorSpeed = lAxis + (Math.abs(twist) * 0.7);
            lMotorSpeed = lAxis;
        }
        // Straight
        else {
            rMotorSpeed = lAxis;
            lMotorSpeed = lAxis;
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
