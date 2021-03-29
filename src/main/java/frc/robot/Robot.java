// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import motors.Drivetrain;
import Aim.Target;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import motors.IntakeHopper;
import motors.Launcher;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  // Prepare Controller, Timer, and Drive-Train
  private final XboxController XboxController = new XboxController(0);
  private int pov = 0;
  private final Timer timer = new Timer();
  private Drivetrain drivetrain;
  private Target limelightTargeting;
  private Launcher launcher;
  private IntakeHopper intakeHopper;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    // Setup Robot
    drivetrain = new Drivetrain();
    limelightTargeting = new Target();
    launcher = new Launcher(0.05);
    intakeHopper = new IntakeHopper(0.7, 0.05);

    CameraServer.getInstance().startAutomaticCapture().setResolution(160, 120);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    timer.reset();
    timer.start();
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

    drivetrain.swapDirection(XboxController.getRawButton(5));

    drivetrain.arcadeDrive(XboxController.getRawAxis(1),  XboxController.getRawAxis(2));

    launcher.setLauncherSpeed(XboxController.getAButton());
    intakeHopper.setSpeed(XboxController.getBButton(), XboxController.getYButton(), XboxController.getXButton());

    int currentPov = XboxController.getPOV();
    if (currentPov != pov) {
      pov = currentPov;
      drivetrain.changeSpeed(XboxController.getPOV());

    }

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    if (XboxController.getBButton()) { limelightTargeting.aimTarget(drivetrain); }
  }
}
