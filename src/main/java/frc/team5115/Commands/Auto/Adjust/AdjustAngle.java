package frc.team5115.Commands.Auto.Adjust;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team5115.Subsystems.Limelight.*;
import frc.team5115.Subsystems.Drivetrain.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.Auto.DriveForward;
import frc.team5115.Subsystems.*;
import static frc.team5115.Constants.*;
import frc.team5115.Robot.*;

public class AdjustAngle extends CommandBase {
    Drivetrain drivetrain;
    Limelight limelight;
      

    public AdjustAngle(Drivetrain drivetrain, Limelight limelight) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        this.limelight = limelight;

    }

    @Override
        public void execute() {
            drivetrain.AdjustAngle();
        }
    @Override
        public boolean isFinished() {
            if(Math.abs(drivetrain.getX()) < 0.01){
                return true;
            }
            else{
                return false;
            }
        }
}