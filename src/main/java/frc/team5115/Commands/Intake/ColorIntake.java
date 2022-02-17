package frc.team5115.Commands.Intake;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class ColorIntake extends ParallelRaceGroup
{
    public ColorIntake(){
        addCommands(
            new IntakeBall(),
            new Droppinator());
    }
}