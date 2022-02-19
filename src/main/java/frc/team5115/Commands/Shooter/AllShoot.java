package frc.team5115.Commands.Shooter;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Intake;
import frc.team5115.Subsystems.*;
import edu.wpi.first.wpilibj.Timer;

public class AllShoot extends CommandBase{
    public Intake intake;
    public Feeder feeder;
    public Shooter shooter;
    public Timer timer;
    
    public AllShoot(Intake a, Feeder b, Shooter c){
       intake = a;
       feeder = b;
       shooter = c;
    }

    public void initialize() {}


    public void execute() {
        shooter.forwardShoot();
        feeder.forwardFeeder();
        intake.forwardIntake();
    }

    public boolean isFinished() {
        return false;
    }
    
}
