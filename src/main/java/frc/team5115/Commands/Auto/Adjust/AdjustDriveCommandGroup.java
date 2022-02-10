package frc.team5115.Commands.Auto.Adjust;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team5115.Subsystems.Limelight.*;
import frc.team5115.Subsystems.Drivetrain.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.team5115.Commands.Auto.DriveForward;
import frc.team5115.Subsystems.*;
import static frc.team5115.Constants.*;
import frc.team5115.Robot.*;
import frc.team5115.Commands.*;

public class AdjustDriveCommandGroup extends SequentialCommandGroup {
        Limelight limelight;
        Drivetrain drivetrain;

    

    public AdjustDriveCommandGroup(Drivetrain drivetrain, Limelight limelight){

        this.drivetrain = drivetrain;
        this.limelight = limelight;
        addCommands(
        //Adjusts Angle
        new AdjustAngle(drivetrain, limelight),

       //new AdjustDistance(drivetrain, limelight),

        new Stop(drivetrain));
        
}
}
