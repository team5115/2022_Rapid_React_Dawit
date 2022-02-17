package frc.team5115.Commands.Auto.NewAuto.BallFinder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team5115.Subsystems.*;

public class DriveForward extends CommandBase {
    Drivetrain drivetrain;
    Intake intake;

    public DriveForward(Drivetrain drivetrain, Intake intake) {
        this.drivetrain = drivetrain;
        this.intake = intake;
    }

    @Override
    public void execute() {
        drivetrain.autodrive();
        System.out.println("STARTING AUTO CODE");
    }

    @Override
    public boolean isFinished() {
        return intake.ballDetection();
    }
}
