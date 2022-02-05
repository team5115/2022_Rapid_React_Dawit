package frc.team5115.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.Auto.DriveForward;
import frc.team5115.Commands.Auto.NewAuto.Adjust.AdjustDriveCommandGroup;
import frc.team5115.Commands.Auto.NewAuto.AutoCommandGroup;
import frc.team5115.Commands.Limelight.Update;
import frc.team5115.Subsystems.*;
import static frc.team5115.Constants.*;
import frc.team5115.Robot.*;

public class RobotContainer {

    public Drivetrain drivetrain;
    public final Shooter shooter = new Shooter();
    public final Intake intake = new Intake();
    public final Climber climber = new Climber();
    //public final Feeder feeder = new Feeder();
    public final DriveForward DriveForward = new DriveForward(drivetrain);
    public final Joystick joy = new Joystick(0);
    public final Limelight limelight = new Limelight(); 
   // public final Mecanum cart = new Mecanum();
  

    public RobotContainer() {
        drivetrain = new Drivetrain(this);
        configureButtonBindings();

    }     

    private void configureButtonBindings() {

        //new JoystickButton(joy, DC_MOTOR_BB2_ID).whenPressed(new InstantCommand(arm::Open));
        //new JoystickButton(joy, DC_MOTOR_BB1_ID).whenPressed(new InstantCommand(arm::Close));

        new JoystickButton(joy, SHOOTER_BUTTON_ID).whenHeld(new AdjustDriveCommandGroup(drivetrain, limelight));
        new JoystickButton(joy, CLIMBER_UP_BUTTON_ID).whenPressed(new InstantCommand(climber::startServo));

        //new JoystickButton(joy, SHOOTER_BUTTON_ID).whenHeld(new InstantCommand(shooter::shoot)).whenReleased(new InstantCommand(shooter::stopShoot));
        //new JoystickButton(joy, WINCH_BUTTON_ID).whenHeld(new InstantCommand(climber::StartWinch)).whenReleased(new InstantCommand(climber::StopClimb));
        //new JoystickButton(joy, INTAKE_BUTTON_ID).whenHeld(new InstantCommand(intake::driverIntake).alongWith(new InstantCommand(feeder::moveCells))).whenReleased(new InstantCommand(intake::stopIntake).alongWith(new InstantCommand(feeder::stopCells)));
        //new JoystickButton(joy, INTAKE_REVERSE_ID).whenHeld(new InstantCommand(intake::spitout).alongWith(new InstantCommand(feeder::spit))).whenReleased(new InstantCommand(intake::stopIntake).alongWith(new InstantCommand(feeder::stopCells)));
       
        //cart.setDefaultCommand(new mecanumDefaultCommand(cart, joy).perpetually());
    }
    public void configureDrive(){
        drivetrain.setDefaultCommand(new driveDefaultCommand(drivetrain, joy).perpetually());
        System.out.println("drivetrain default");


    }

    public void configureLimelight(){
        drivetrain.setDefaultCommand(new Update(limelight).perpetually());
    }

   static class driveDefaultCommand extends CommandBase {

        Drivetrain drivetrain;
        Joystick joystick;

        public driveDefaultCommand(Drivetrain drivetrain, Joystick joystick) {
            addRequirements(drivetrain);
            this.drivetrain = drivetrain;
            this.joystick = joystick;
        }

        @Override
        public void execute() {
           //drivetrain.MecanumSimpleDrive(joystick.getRawAxis(JOY_X_AXIS_ID), joystick.getRawAxis(JOY_Y_AXIS_ID), joystick.getRawAxis(JOY_Z_AXIS_ID));
           //drivetrain.DistanceDetectionRaw();
           drivetrain.DistanceDetectionAverage();
        }
    }
/*
    static class mecanumDefaultCommand extends CommandBase {

        Mecanum cart;
        Joystick joystick;
        double gyroangle = 0;

        public mecanumDefaultCommand(Mecanum cart, Joystick joystick) {
            addRequirements(cart);
            this.cart = cart;
            this.joystick = joystick;
        }

        @Override
        public void execute() {
           cart.simpleDrive(joystick.getRawAxis(XBOX_X_AXIS_ID), joystick.getRawAxis(XBOX_Y_AXIS_ID), joystick.getRawAxis(XBOX_Z_AXIS_ID));
            //drivetrain.drive(-.5, -.5, 1);
        }
    }
    */

    public void getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        drivetrain.autodrive();
    }
    public void startTeleop(){
            System.out.println("Starting teleop");
        }

}