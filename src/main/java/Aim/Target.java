package Aim;

import motors.Drivetrain;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Target {
    NetworkTable table;
    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry tv;

    public void aimTarget(Drivetrain drivetrain) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        tv = table.getEntry("tv");
        double targetX = tx.getDouble(0.0);
        double targetY = ty.getDouble(0.0);
        double targetV = tv.getDouble(0.0);

        // 10 Degrees Waterford
        // 7.5" Waterford
        // 29" to center of tape

        if (targetV == 1) {

            double distance = (29 - 7.5) / (Math.tan(Math.toRadians(10 + targetY)));
            distance = Math.round((distance) * 10.0) / 10.0;

            System.out.println(distance);

            if (targetX < -5) {
                drivetrain.setSpeed(0.3, -0.3);
            } else if (targetX > 5) {
                drivetrain.setSpeed(-0.3, 0.3);
            } else {
                if (distance > 60) {
                    drivetrain.setSpeed(-0.3, -0.3);
                } else if (distance < 80) {
                    drivetrain.setSpeed(0.3, 0.3);
                }
            }
        }
    }

    public void findBall() {
        CameraServer.getInstance().startAutomaticCapture().setResolution(160, 120);
        CvSink cvSink = CameraServer.getInstance().getVideo();
    }
}
