package frc.team5115.Commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Intake;
import edu.wpi.first.wpilibj.Timer;

public class IntakeBall extends CommandBase{
    private final Intake intake;
    private final Timer timer;

    public IntakeBall(){
        intake = new Intake();
        timer = new Timer();
    }

    public void initialize() {
        timer.reset();
        intake.forwardIntake();
    }
    
    public boolean isFinished() {
        if(timer.get()>2){
            return true;
        }
        else{
        return false;
        }
      }

    
}
