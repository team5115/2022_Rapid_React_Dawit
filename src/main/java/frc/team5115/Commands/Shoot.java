package frc.team5115.Commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Intake;
import frc.team5115.Subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class Shoot extends CommandBase{
    public Intake intake;
    public Feeder feeder;
    public Shooter shooter;
    public Timer timer;
    
    public Shoot(Intake a, Feeder b, Shooter c){
       intake = a;
       feeder = b;
       shooter = c;
    }

    public void initialize() {
        timer.reset();
    }

    public void execute() {
        if(timer.get()<2){
            shooter.forwardShoot();
        }
        if(timer.get()<4){
            shooter.forwardShoot();
            feeder.forwardFeeder();
        }
        else{
            shooter.forwardShoot();
            feeder.forwardFeeder();
            intake.forwardIntake();
        }
    }

    public boolean isFinished() {
        return false;
    }
    
}
