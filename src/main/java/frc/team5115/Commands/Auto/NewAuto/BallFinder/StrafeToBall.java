package frc.team5115.Commands.Auto.NewAuto.BallFinder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.*;
import edu.wpi.first.wpilibj.Timer;



public class StrafeToBall extends CommandBase{
    Drivetrain drivetrain;
    Timer timer;
    public StrafeToBall(Drivetrain drivetrain){
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
        timer.reset();
    }

    @Override
        public void execute() {
            drivetrain.StrafeToBall();
        }

    @Override
    public boolean isFinished() {
        if(timer.get()>2){
            return false;
        }        
        return true;
        }
    
}
