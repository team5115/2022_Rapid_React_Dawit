package frc.team5115.Subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
 


public class Camera extends SubsystemBase {
    Servo servo;
    int degree;

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

    public void moveServoUp(){
        servo.setAngle(degree + 1);
    }

    public void moveServoDown(){
        servo.setAngle(degree - 1);
    }
}
