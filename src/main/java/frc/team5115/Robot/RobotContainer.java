package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Subsystems.*;
import static frc.team5115.Constants.*;
import frc.team5115.Commands.Auto.NewAuto.Updater;
import frc.team5115.Commands.Intake.ColorIntake;
import frc.team5115.Commands.Limelight.Update;
import frc.team5115.Commands.Shooter.AllShoot;
import frc.team5115.Commands.Stopeverything;
import frc.team5115.Commands.Auto.NewAuto.Adjust.AdjustDriveCommandGroup;
import frc.team5115.Commands.Auto.NewAuto.BallFinder.DriveForward;
import frc.team5115.Commands.Auto.NewAuto.AutoCommandGroup;
import frc.team5115.Commands.*; 
import frc.team5115.Commands.Shooter.DelayShootGroup;

public class RobotContainer {

    public Drivetrain drivetrain;
    public Intake intake;
    public Shooter shooter;
    public Camera camera;
    public Feeder feeder;
    public Climber climber;
    public final CurrentUpdate currentupdate = new CurrentUpdate();
    public final Joystick joy = new Joystick(0);
    public final Limelight limelight = new Limelight(); 

    public RobotContainer() {
      drivetrain = new Drivetrain();
      intake = new Intake();
      shooter = new Shooter();
      feeder = new Feeder();
      climber = new Climber();
      camera = new Camera();
      configureButtonBindings();
    }

    public void configureButtonBindings() {
      new JoystickButton( joy, INTAKE_BUTTON_ID).whileHeld(new InstantCommand(intake::forwardIntake)).whenReleased(new InstantCommand(intake::stop));
      new JoystickButton(joy, SHOOTER_BUTTON_ID).whileHeld(new AllShoot(intake,feeder,shooter)).whenReleased(new Stopeverything(intake, feeder, shooter));
      // new JoystickButton(joy, 4).whileHeld(new ColorIntake()).whenReleased(new InstantCommand(shooter::stop));
      //new JoystickButton(joy, 1).whileHeld(new InstantCommand(climber::forwardClimb)).whenReleased(new InstantCommand(climber::stop)); 
      //new JoystickButton(joy, 4).whileHeld(new InstantCommand(climber::reverseClimb)).whenReleased(new InstantCommand(climber::stop));
      new JoystickButton(joy, 3).whileHeld(new InstantCommand(intake::reverseIntake)).whenReleased(new InstantCommand(intake::stop));
      new JoystickButton(joy, 4).whileHeld(new DelayShootGroup(intake, feeder, shooter)).whenReleased(new Stopeverything(intake, feeder, shooter));
      //new JoystickButton(joy, 1).whenPressed(new InstantCommand(drivetrain::distanceDetectionAverage));
      //new JoystickButton(joy, NAVX_RESET_BUTTON).whenPressed(drivetrain::resetGyro);
     /* if(joy.getPOV()>44 && 136>joy.getPOV()){
        camera.moveServoUp();
    }

    if(joy.getPOV()>224 && 316>joy.getPOV()){
        camera.moveServoDown();
    }*/
    
    //new JoystickButton(joy, CAMERA_UP_ID).whileHeld(new InstantCommand(camera::moveServoUp));
    //new JoystickButton(joy, CAMERA_DOWN_ID).whileHeld(new InstantCommand(camera::moveServoDown));

    
    }

    public void configureAuto(){
    //  drivetrain.setDefaultCommand();
    }
    public void configureLimelight(){
      drivetrain.setDefaultCommand(new Update(limelight).perpetually());
    }

    public void setDriveDefault(){
      drivetrain.setDefaultCommand(new driveDefaultCommand(drivetrain, joy, intake).perpetually());
    }

    public void configurePDP(){
      currentupdate.setDefaultCommand(new Updater(currentupdate, 2).perpetually());
  }

   static class driveDefaultCommand extends CommandBase {
        Drivetrain drivetrain;
        Intake intake;
        Joystick joy;

        public driveDefaultCommand(Drivetrain drivetrain, Joystick joystick, Intake intake) {
          addRequirements(drivetrain);
          this.drivetrain = drivetrain;
          this.intake = intake;
          joy = joystick;
        }

        @Override
        public void execute() {
          drivetrain.FieldOrientedDrive(joy.getRawAxis(JOY_X_AXIS_ID), joy.getRawAxis(JOY_Y_AXIS_ID), joy.getRawAxis(JOY_Z_AXIS_ID));
          drivetrain.distanceDetectionRaw();
          intake.ballDetection2();
        }    
    }


    public void startTeleop(){
            System.out.println("Starting teleop");
            new Stopeverything(intake, feeder, shooter);

        }

}