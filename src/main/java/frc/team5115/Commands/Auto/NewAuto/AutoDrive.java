package frc.team5115.Commands.Auto.NewAuto;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.Drivetrain;

public class AutoDrive extends CommandBase {
    private final Drivetrain drivetrain;

    public AutoDrive(){
        drivetrain = new Drivetrain();
    }
    
    public void initialize() {
        drivetrain.autodrive(); 
    }
    
    public boolean isFinished() {
        return drivetrain.autoDriveFinished();
      }
}
