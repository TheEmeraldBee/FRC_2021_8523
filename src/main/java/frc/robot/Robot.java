// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import drivetrain.Drivetrain;
import Aim.Target;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // Prepare Controller, Timer, and Drive-Train
  private final XboxController m_XboxController = new XboxController(0);
  private int pov = 0;
  private final Timer m_timer = new Timer();
  private Drivetrain drivetrain;
  private Target limelightTargeting;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Setup Robot
    drivetrain = new Drivetrain();
//    limelightTargeting = new Target();

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
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {

    drivetrain.arcadeDrive(m_XboxController.getRawAxis(1),  m_XboxController.getRawAxis(2));

    int currentPov = m_XboxController.getPOV();
    if (currentPov != pov) {
      pov = currentPov;
      drivetrain.changeSpeed(m_XboxController.getPOV());

    }

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
//    if (m_XboxController.getBButton()) { limelightTargeting.aim(drivetrain); }
  }
}
