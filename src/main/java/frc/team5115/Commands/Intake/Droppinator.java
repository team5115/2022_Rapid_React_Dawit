package frc.team5115.Commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Intake;

public class Droppinator extends CommandBase {
    private Intake intake;

    public Droppinator(){
        intake = new Intake();
    }
    
    public void initialize() {
    }
    
    public boolean isFinished() {
        return intake.ballDetection();
      }
}