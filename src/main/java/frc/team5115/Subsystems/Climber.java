package frc.team5115.Subsystems;

import edu.wpi.first.wpilibj.Servo;
import static frc.team5115.Constants.*;
import static frc.team5115.Constants.kD;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
 


public class Climber extends SubsystemBase {
    Servo servo;

    public Climber(){
        servo = new Servo(0);
        servo.setAngle(100);

    
    }

    public void startServo(){
        servo.setAngle(10);
        } 
    
    public void stopServo(){
        servo.setAngle(100);
    }
}
