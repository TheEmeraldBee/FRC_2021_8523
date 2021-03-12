// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // Prepare Controller, Timer, and Drive-Train
  private DifferentialDrive m_robotDrive;
  private final XboxController m_XboxController = new XboxController(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Define Motors
    PWMTalonSRX m_leftMotor = new PWMTalonSRX(3);
    PWMTalonSRX m_rightMotor = new PWMTalonSRX(0);

    // Make Robot Drive Train
    m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

    // Set up the usb camera
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(160, 120);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    // Gets the Axis of the right and left sticks
    int RControllerAxis = 3;
    double RAxis = m_XboxController.getRawAxis(RControllerAxis);
    int LControllerAxis = 1;
    double LAxis = m_XboxController.getRawAxis(LControllerAxis);
    int TwistAxis = 2;
    double Twist = m_XboxController.getRawAxis(TwistAxis);

    RAxis /= 1.1;
    LAxis /= 1;
    Twist /= 1.5;

    // Custom Input Dead-zones
    double axisRange = 0.1;
    if (LAxis < axisRange && LAxis > -axisRange) { LAxis = 0; }
    if (RAxis < axisRange && RAxis > -axisRange) { RAxis = 0; }
    if (Twist < axisRange && Twist > -axisRange) { Twist = 0; }

    m_robotDrive.arcadeDrive(LAxis, Twist);

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
