package frc.team5115.Subsystems;

import static frc.team5115.Constants.INTAKE_MOTOR_ID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    private TalonSRX intake;
    private double intakeSpeed = 1;
    public ColorSensorV3 colorSensor;
    public ColorSensorV3 colorSensor2;
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final I2C.Port i2cPort2 = I2C.Port.kOnboard;
    private int ballProximity = 1050;

    private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
    private final Color kRedTarget = new Color(0.561, 0.232, 0.114);



    public Intake(){
        intake = new TalonSRX(INTAKE_MOTOR_ID);
        colorSensor = new ColorSensorV3(i2cPort);
        colorSensor2 = new ColorSensorV3(i2cPort2);
    }

    public void forwardIntake(){
        intake.set(ControlMode.PercentOutput, intakeSpeed);


        //System.out.println("color sensor:  "+colorSensor.getProximity());
    }

    public void reverseIntake(){
        intake.set(ControlMode.PercentOutput, -intakeSpeed);
    }

    public void stop(){
        intake.set(ControlMode.PercentOutput, 0);
        System.out.println("color sensor off");
    }
    public void ballDetection2(){
        System.out.println(colorSensor.getProximity());
    }
    public boolean ballDetection(){
        if(colorSensor.getProximity()>ballProximity){
            return true;
        }
        else{
            return false;
        }
    }

    
}
