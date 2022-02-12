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
 


public class Camera extends SubsystemBase {
    Servo servo;

    public Camera(){
        servo = new Servo(1);
        servo.setAngle(100);

    
    }

    public void startServo(){
        servo.setAngle(10);
        System.out.print("ha");
        } 
    
    public void stopServo(){
        servo.setAngle(100);
    }
}
