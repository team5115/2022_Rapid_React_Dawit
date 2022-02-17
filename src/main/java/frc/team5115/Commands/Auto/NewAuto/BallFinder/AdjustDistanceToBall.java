package frc.team5115.Commands.Auto.NewAuto.BallFinder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team5115.Subsystems.Limelight.*;
import frc.team5115.Subsystems.Drivetrain.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Subsystems.*;
import static frc.team5115.Constants.*;
import frc.team5115.Robot.*;

public class AdjustDistanceToBall extends CommandBase{

    Drivetrain drivetrain;
    Intake intake;
    public AdjustDistanceToBall(Drivetrain drivetrain, Intake intake){
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        this.intake = intake;
    }


    @Override
        public void execute() {
            drivetrain.AdjustDistanceToBall();
        }
    @Override
        public boolean isFinished() {
            if(drivetrain.distancefromrobot > 50){
                return false;
            }
            else{
                return false;
            }
        }
}