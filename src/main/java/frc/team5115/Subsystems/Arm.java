package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;
import edu.wpi.first.wpilibj.DigitalInput;

//import java.io.*;
import java.lang.Thread;

import static frc.team5115.Constants.*;

public class Arm extends Thread{
    TalonSRX Talon1;
    double DCMOTORSPEED;
    DigitalInput ArmStopper;

    double idlespeed;



    public Arm(){
        DCMOTORSPEED = 1;
        ArmStopper = new DigitalInput(0);
        Talon1 = new TalonSRX(5);
        idlespeed = ARM_IDLE_SPEED;
    }

    public void Open(){
        for(int i = 0; i<210000; i++){
            Talon1.set(ControlMode.PercentOutput, DCMOTORSPEED);
        }
        Talon1.set(ControlMode.PercentOutput, ARM_IDLE_SPEED);

    }


    public void Close(){
        for(int t=0; t<200000; t++){
            idlespeed = idlespeed - (ARM_IDLE_SPEED/175000);
            Talon1.set(ControlMode.PercentOutput, idlespeed);
        }

    }     
}
