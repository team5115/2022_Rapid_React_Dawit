package frc.team5115.Subsystems;
 
import static frc.team5115.Constants.*;
import static frc.team5115.Constants.kD;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
 
public class Drivetrain extends SubsystemBase{
 
    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;
    private AnalogInput DistanceDetector1;
    private AnalogInput DistanceDetector2;
 
   
    //mecanum speed values 
    private double frontLeftSpeed;
    private double frontRightSpeed;
    private double backLeftSpeed;
    private double backRightSpeed;
 
    //tank speed values 
    private double rightSpd;
    private double leftSpd;

    public double d;

    //ultrasonic detector 
    public double AverageDistanceDetector1;
    public double AverageDistanceDetector2;

    public double distancefromrobot;
    public double AngleA;
 
    public Drivetrain(RobotContainer x) {
        frontLeft = new TalonSRX(FRONT_LEFT_MOTOR_ID);
        frontRight = new TalonSRX(FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(BACK_RIGHT_MOTOR_ID);

        DistanceDetector1 = new AnalogInput(0);
        DistanceDetector2 = new AnalogInput(1);
        
        AverageDistanceDetector1 = 0;
        AverageDistanceDetector2 = 0;


        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }
 
    public void stop() {
        tankDrive(0, 0, 0);
    }

    //gets raw values from ultrasonics 
    public void distanceDetectionRaw(){
        AverageDistanceDetector1 = DistanceDetector1.getVoltage()*10000;
        AverageDistanceDetector2 = DistanceDetector2.getVoltage()*10000;

        System.out.println("ultrasonic:    " + AverageDistanceDetector1);

        distanceAndAngleFromRobot();
    }

    //averages values from ultrasonics
    public void distanceDetectionAverage(){
        double n = 5000;
        double f = 0;
        double q = 0;

        for(int i = 0; i< n; i++){
            double j = DistanceDetector1.getVoltage()*ULTRASONIC_UNIT_CONVERSION;
            f = f + j;
            double k = DistanceDetector2.getVoltage()*ULTRASONIC_UNIT_CONVERSION;
            q = q + k;
        }

        f = f/n;
        q = q/n;

        AverageDistanceDetector1 = f;
        AverageDistanceDetector2 = q;

        System.out.println("ultrasonic:    " + AverageDistanceDetector1);

        distanceAndAngleFromRobot();

    }


    public void adjustAngle(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = networkTableInstance.getEntry("tx");
        NetworkTableEntry tv = networkTableInstance.getEntry("tv");


        double xangle = -tx.getDouble(0); 
        double dectector = tv.getDouble(0);
        if(dectector == 1){
            if(xangle > 0){
                rightSpd = xangle*kD;
                leftSpd = -rightSpd;
                frontLeft.set(ControlMode.PercentOutput, -rightSpd);
                frontRight.set(ControlMode.PercentOutput, -rightSpd);
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);
            }
            else{
                leftSpd = -xangle*kD;
                rightSpd = leftSpd;
                frontLeft.set(ControlMode.PercentOutput,  rightSpd);
                frontRight.set(ControlMode.PercentOutput, rightSpd);
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);  
            }
        }
    }

    public void distanceAndAngleFromRobot(){
        distancefromrobot = ((AverageDistanceDetector1*AverageDistanceDetector1)/2+D1*D1/4+(AverageDistanceDetector2*AverageDistanceDetector2)/2);
        double CosOfA = (((distancefromrobot*distancefromrobot)+(D1*D1)/4+-(AverageDistanceDetector2*AverageDistanceDetector2))/(distancefromrobot*D1));
        AngleA = Math.acos(CosOfA);
    }

    public void adjustAngleToBall(){
        if(AngleA > 0){
            rightSpd = AngleA*kD;
            leftSpd = -rightSpd;
            frontLeft.set(ControlMode.PercentOutput, -rightSpd);
            frontRight.set(ControlMode.PercentOutput, -rightSpd);
            backLeft.set(ControlMode.PercentOutput, 0);
            backRight.set(ControlMode.PercentOutput, 0);
        }
        else{
            leftSpd = -AngleA*kD;
            rightSpd = leftSpd;
            frontLeft.set(ControlMode.PercentOutput,  rightSpd);
            frontRight.set(ControlMode.PercentOutput, rightSpd);
            backLeft.set(ControlMode.PercentOutput, 0);
            backRight.set(ControlMode.PercentOutput, 0);  
        }
    }

    public void adjustDistanceToBall(){
        leftSpd = (distancefromrobot)*hD;
        rightSpd = (distancefromrobot)*hD;
        setTankSpd();
    }

    public void adjustDistanceToHub(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = networkTableInstance.getEntry("ty");
        NetworkTableEntry tv = networkTableInstance.getEntry("tv");

        double yangle = ty.getDouble(0); 
        double dectector = tv.getDouble(0);

        if(dectector == 1){
            d = (AUTO_HIGH_GOAL_HEIGHT - AUTO_CAMERA_HEIGHT) / tan(toRadians(yangle + AUTO_CAMERA_ANGLE));

        if(d> HUB_DISTANCE){
            leftSpd = (d-HUB_DISTANCE)*hD;
            rightSpd = (d - HUB_DISTANCE)*hD;
        }
        else{
            leftSpd = (HUB_DISTANCE - d)*hD;
            rightSpd = (HUB_DISTANCE - d)*hD;
        }
        setTankSpd();
        }
    }
   

    public void adjust(double tx, double ty, boolean tv){
        if(tv == true){
            while(Math.abs(tx)>0.01){
                leftSpd = -tx*kD;
                rightSpd = tx*kD;
                frontLeft.set(ControlMode.PercentOutput, leftSpd);
                frontRight.set(ControlMode.PercentOutput, rightSpd);
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);
            }
            while(Math.abs(d) - HUB_DISTANCE > 0.01){
                d = (AUTO_HIGH_GOAL_HEIGHT - AUTO_CAMERA_HEIGHT) / tan(toRadians(ty + AUTO_CAMERA_ANGLE));
                if(d> HUB_DISTANCE){
                    leftSpd = (d-HUB_DISTANCE)*hD;
                    rightSpd = (d - HUB_DISTANCE)*hD;
                }
                else{
                    leftSpd = (HUB_DISTANCE - d)*hD;
                    rightSpd = (HUB_DISTANCE - d)*hD;
                }
                setTankSpd();
            }

            System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
            System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);

            setTankSpd();
        }
    }
   
 
    public String tankDrive(double x, double y, double throttle) {
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        leftSpd = (x-y) * throttle;
        rightSpd = (x+y) * throttle;
        System.out.println("Setting Right Pair to :" + (int) rightSpd * 100);
        System.out.println("Setting Left Pair to :" + (int) leftSpd * 100);
 
        setTankSpd();
 
        return "name";
    }
    public void mecanumSimpleDrive(double y, double x, double z) {
        frontLeftSpeed = (-x + y + z);
        backLeftSpeed = (-x + y - z);
        frontRightSpeed = (x +  y + z);
        backRightSpeed = (x + y - z);
        
        /*frontLeftSpeed = (-y + x + z);
        backLeftSpeed = (-y + x - z);
        frontRightSpeed = (y +  x + z);
        backRightSpeed = (y + x - z);*/
       
        frontLeft.set(ControlMode.PercentOutput, frontLeftSpeed);
        frontRight.set(ControlMode.PercentOutput, frontRightSpeed);
        backLeft.set(ControlMode.PercentOutput, backLeftSpeed);
        backRight.set(ControlMode.PercentOutput, backRightSpeed);
    }
 
    public void autodrive(){
        System.out.println("STARTING AUTO DRIVE");
        frontLeft.set(ControlMode.PercentOutput, 0.5);
        frontRight.set(ControlMode.PercentOutput, 0.5);
        backLeft.set(ControlMode.PercentOutput, 0.5);
        backRight.set(ControlMode.PercentOutput, 0.5);
    }

    public double getX(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = networkTableInstance.getEntry("tx");


        return tx.getDouble(0);
    }
   
    public double getY(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = networkTableInstance.getEntry("ty");

        return ty.getDouble(0);
    }

    public void ballAdjustAngle(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = networkTableInstance.getEntry("tx");
        NetworkTableEntry tv = networkTableInstance.getEntry("tv");

        double xangle = -tx.getDouble(0); 
        double dectector = tv.getDouble(0);
        if(dectector == 1){
            if(xangle > 0){
                rightSpd = (xangle)*kD;
                leftSpd = -rightSpd;
                frontLeft.set(ControlMode.PercentOutput, rightSpd);
                frontRight.set(ControlMode.PercentOutput, -rightSpd);
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);
            }
            else{
                leftSpd = -(xangle)*kD;
                rightSpd = leftSpd;
                frontLeft.set(ControlMode.PercentOutput, -rightSpd);
                frontRight.set(ControlMode.PercentOutput, rightSpd);
                backLeft.set(ControlMode.PercentOutput, 0);
                backRight.set(ControlMode.PercentOutput, 0);  
            }
        }   
    }

    public void ballAdjustDistance(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = networkTableInstance.getEntry("ty");
        NetworkTableEntry tv = networkTableInstance.getEntry("tv");
        NetworkTableEntry ta = networkTableInstance.getEntry("ta");

        double yangle = ty.getDouble(0); 
        double dectector = tv.getDouble(0);
        double area = ta.getDouble(0);  //necessary?
        if(dectector == 1){
        
  
            d = (AUTO_HIGH_GOAL_HEIGHT - AUTO_CAMERA_HEIGHT) / tan(toRadians(yangle + AUTO_CAMERA_ANGLE));
            setTankSpd();
        }
        setTankSpd();
    }

    public void setTankSpd()
    {
        frontLeft.set(ControlMode.PercentOutput, leftSpd);
        frontRight.set(ControlMode.PercentOutput, rightSpd);
        backLeft.set(ControlMode.PercentOutput, leftSpd);
        backRight.set(ControlMode.PercentOutput, rightSpd);
    }

    public void adjustAngle(double angle){
        angleAdjusted = angle;
        if(angleAdjusted > 0){
            rightSpd = (angleAdjusted)*kD;
            leftSpd = -rightSpd;
            frontLeft.set(ControlMode.PercentOutput, -rightSpd);
            frontRight.set(ControlMode.PercentOutput, -rightSpd);
            backLeft.set(ControlMode.PercentOutput, 0);
            backRight.set(ControlMode.PercentOutput, 0);
        }
        else{
            leftSpd = -(angleAdjusted)*kD;
            rightSpd = leftSpd;
            frontLeft.set(ControlMode.PercentOutput, rightSpd);
            frontRight.set(ControlMode.PercentOutput, rightSpd);
            backLeft.set(ControlMode.PercentOutput, 0);
            backRight.set(ControlMode.PercentOutput, 0);
        }
}
