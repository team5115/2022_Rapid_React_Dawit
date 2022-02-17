package frc.team5115.Commands.Auto.NewAuto.BallFinder;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Subsystems.*;
import frc.team5115.Commands.Auto.NewAuto.Adjust.Stop;
import frc.team5115.Commands.Auto.NewAuto.BallFinder.AdjustRobotToBallCommandGroup;


public class AdjustRobotToBallCommandGroup extends SequentialCommandGroup {
        Drivetrain drivetrain;
        Limelight limelight;
        Intake intake;
    public AdjustRobotToBallCommandGroup(Drivetrain drivetrain, Limelight limelight, Intake intake){
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.intake = intake;
        addCommands(
            new AdjustAngleToBall(drivetrain),
            new AdjustDistanceToBall(drivetrain, intake),
            new StrafeToBall(drivetrain),
            new DriveForward(drivetrain, intake),
            new Stop(drivetrain))
        ;

    }
}
