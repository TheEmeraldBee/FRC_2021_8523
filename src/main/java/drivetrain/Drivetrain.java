package drivetrain;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.XboxController;

public class Drivetrain {
    private PWMTalonSRX m_leftMotor;
    private PWMTalonSRX m_rightMotor;

    private final double axisRange = 0.1;

    private double speedFactor = 0.25;
    private final double minSpeed = 0.25;
    private final double maxSpeed = 1;

    public Drivetrain() {
        // Define Motors
        m_leftMotor = new PWMTalonSRX(0);
        m_rightMotor = new PWMTalonSRX(3);
    }

    public void arcadeDrive(double lAxis, double twist) {

        double lMotorSpeed;
        double rMotorSpeed;

        // Custom Input Dead-zones
        if (lAxis < axisRange && lAxis > -axisRange) { lAxis = 0; }
        if (twist < axisRange && twist > -axisRange) { twist = 0; }

        // Turning In Place
        if (lAxis == 0) {
            lMotorSpeed = -twist*0.75;
            rMotorSpeed = twist*0.75;
        }
        // Turning Left
        else if (twist < 0){
            rMotorSpeed = lAxis;
            lMotorSpeed = lAxis * (Math.abs(twist) * 0.9);
        }
        // Turning Right
        else if (twist > 0){
            rMotorSpeed = lAxis * (Math.abs(twist) * 0.9);
            lMotorSpeed = lAxis;
        }
        // Straight
        else {
            rMotorSpeed = lAxis;
            lMotorSpeed = lAxis;
        }

        rMotorSpeed *= speedFactor;
        lMotorSpeed *= speedFactor;

        m_rightMotor.set(rMotorSpeed/1.1);
        m_leftMotor.set(-lMotorSpeed);
    }

    public void changeSpeed(int dPad) {
        if (dPad == 0 && speedFactor < maxSpeed) {
            speedFactor += 0.25;
        }
        else if (dPad == 180 && speedFactor > minSpeed) {
            speedFactor -= 0.25;
        }
    }
}
