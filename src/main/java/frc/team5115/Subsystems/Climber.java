package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.team5115.Constants.*;

//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Servo;

public class Climber {
  //private PWMSparkMax climberA;
  //private PWMSparkMax climberB;
  private double climbSpeed = .3;
  private Servo servo;
 // private CANSparkMax climber;

    public Climber(){
   //climberA = new PWMSparkMax(CLIMBER_A_MOTOR_ID);
   //climberB = new PWMSparkMax(10);
   //climber = new CANSparkMax(10, MotorType.kBrushless);
  
    }

    public void forwardClimb(){
     //climberA.set(climbSpeed);
     //climberB.set(climbSpeed);
     System.out.println("starting climb");
     //climber.set(0.25);
    }

    public void reverseClimb(){
       //climberA.set(-climbSpeed);
       //climberB.set(-climbSpeed);
       
    }

    public void stop(){
       //climberA.set(0);
       //climberB.set(0);

    }
}
