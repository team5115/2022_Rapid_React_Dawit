package frc.team5115.Commands.Auto.NewAuto;
import frc.team5115.Commands.Intake.*;
import frc.team5115.Commands.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team5115.Commands.Auto.NewAuto.Adjust.AdjustDriveCommandGroup;
import frc.team5115.Commands.Auto.NewAuto.BallFinder.AdjustRobotToBallCommandGroup;
import frc.team5115.Subsystems.*;


public class AutoCommandGroup extends SequentialCommandGroup {
    Drivetrain drivetrain;
    Limelight limelight;
    Intake intake;
    Feeder feeder;
    Shooter shooter;

    public AutoCommandGroup(Drivetrain drivetrain, Limelight limelight, Intake intake, Feeder feeder, Shooter shooter){
        this.drivetrain = drivetrain;
        this.limelight = limelight;
        this.intake = intake;
        this.feeder = feeder;
        this.shooter = shooter;
        addCommands(
            //go to intake ball
            new AdjustRobotToBallCommandGroup(drivetrain, limelight, intake),
            new IntakeBall(),
            //asjust to shoot
            new AdjustDriveCommandGroup(drivetrain, limelight),
            new Shoot(intake, feeder, shooter)
        );
    }

}
