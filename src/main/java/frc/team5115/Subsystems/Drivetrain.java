package frc.team5115.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team5115.Robot.RobotContainer;
import edu.wpi.first.wpilibj.Encoder; 
import static frc.team5115.Constants.kD;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;

import static frc.team5115.Constants.*;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Drivetrain extends SubsystemBase{

    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX backLeft;
    private TalonSRX backRight;

    private double frontLeftSpeed;
    private double frontRightSpeed;
    private double backLeftSpeed;
    private double backRightSpeed;

    private double rightSpd;
    private double leftSpd;
    private double autoDriveDistance = 10;

    private AHRS gyro;
    private Timer timer;
    private Encoder encoder;

    public double distanceFromHub;
    public double averageDistanceDetector1;
    public double distancefromrobot;
    public double AngleA;
    private AnalogInput distanceDetector1;
    private AnalogInput distanceDecector2;
    public boolean distanceDetector1Present; 
    public boolean distanceDecector2Present;
    public boolean ballFullyDetected;

    
    public Drivetrain() {
        frontLeft = new TalonSRX(FRONT_LEFT_MOTOR_ID);
        frontRight = new TalonSRX(FRONT_RIGHT_MOTOR_ID);
        backLeft = new TalonSRX(BACK_LEFT_MOTOR_ID);
        backRight = new TalonSRX(BACK_RIGHT_MOTOR_ID);

       // gyro= new AHRS(SPI.Port.kMXP);
        //encoder = new Encoder(1,1);
       // encoder.reset();
       // gyro.resetDisplacement();

        distanceDetector1 = new AnalogInput(0);
        distanceDetector1Present = false; 
        distanceDecector2Present = false;
        ballFullyDetected = false;

        averageDistanceDetector1 = 0;
        
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        backRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    }

    //-----start of drive methods-----
    public void stop() {
        plugAndChugDrive(0, 0, 0, 0);
    }

    public void TankDrive(double x, double y, double throttle) { 
        leftSpd = (x-y) * throttle;
        rightSpd = (x+y) * throttle;
        plugAndChugDrive(leftSpd, rightSpd, leftSpd, rightSpd);
    }

    public void MecanumSimpleDrive(double y, double x, double z) {
        frontLeftSpeed = (-x + y + z);
        backLeftSpeed = (-x + y - z);
        frontRightSpeed = (x +  y + z);
        backRightSpeed = (x + y - z);

        plugAndChugDrive(frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);
    }

    public void FieldOrientedDrive(double strafe, double fwd, double rotate){
        double x;
        double y;
        double pi = 3.1415926;
       // float gyro_degrees = gyro.getYaw();
        double gyro_degrees = 0.5123;
        double gyro_radians = gyro_degrees * pi/180; 

        x = strafe*Math.cos(gyro_radians) + fwd*Math.sin(gyro_radians);
        y = strafe*Math.sin(gyro_radians) - fwd*Math.cos(gyro_radians);

        frontLeftSpeed = (y + x + rotate);
        backLeftSpeed = (y - x + rotate);
        frontRightSpeed = (-y + x + rotate);
        backRightSpeed = (-y - x + rotate);

        plugAndChugDrive(frontLeftSpeed, frontRightSpeed, backLeftSpeed, backRightSpeed);
    }

    public void plugAndChugDrive(double frontleftspeed, double frontrightspeed, double backleftspeed, double backrightspeed){
        frontLeft.set(ControlMode.PercentOutput, frontleftspeed);
        frontRight.set(ControlMode.PercentOutput, frontrightspeed);
        backLeft.set(ControlMode.PercentOutput, backleftspeed);
        backRight.set(ControlMode.PercentOutput, backrightspeed);
    }

    public void autodrive(){
        System.out.println("STARTING AUTO DRIVE");
        plugAndChugDrive(0.3, 0.3, 0.3, 0.3);
    }

    public boolean autoDriveFinished(){
        if(encoder.getDistance()>autoDriveDistance){
            return true;
        }
        else{
            return false;
        }
    }
    //-----end of drive methods-------


    //-----start of detection methods-----
    public void distanceDetectionRaw(){
        averageDistanceDetector1 = distanceDetector1.getVoltage()*10000;
        System.out.println("untrasonic:    " + averageDistanceDetector1);
    }

    public void distanceDetectionAverage(){
        double n = 5000;
        double f = 0;

        for(int i = 0; i< n; i++){
            double j = distanceDetector1.getVoltage()*ULTRASONIC_UNIT_CONVERSION;
            f = f + j;
            
        }
        f = f/n;
        averageDistanceDetector1 = f;
        distancefromrobot = averageDistanceDetector1;

        if(distanceDetector1.getVoltage()<15000){
            distanceDetector1Present = true;
        }
        else{
            distanceDetector1Present = false;
        }
        if(distanceDecector2.getAccumulatorCount()<15000){
            distanceDecector2Present = true;
        }
        else{
            distanceDecector2Present = false;
        }
    }

    public void encoder(){
        Encoder codeMonkey = new Encoder(0,1);
        System.out.println(codeMonkey.getRate());
    }

    //-----end of detection methods-----


    //-----start of adjust methods-----
    public void adjustAngleToBall(){
        boolean a = false;
        if(distanceDetector1Present == true){
            ballFullyDetected = true;
        }
        if(distanceDetector1Present == false){
            timer.reset();
            if(timer.get()<2){
                if (distanceDecector2Present == false){
                    a = false;
                    plugAndChugDrive(-0.5, 0.5, 0, 0);
                }
                else{
                    a = true;
                    plugAndChugDrive( 0.5, -0.5, 0, 0);
                }
            }
            else{
                if(a == false){
                    plugAndChugDrive( 0.5, -0.5, 0, 0);

                }
                else{
                    plugAndChugDrive(-0.5, 0.5, 0, 0);   
                }
        }
            ballFullyDetected = false;
        }

    }
    // scan left 10 degrees, then left 
    // spit out wrong color
    // do timer to stop moving  in the case that the color sensor doesnt pick up

    public void AdjustDistanceToBall(){
            leftSpd = (distancefromrobot)*hD;
            rightSpd = (distancefromrobot)*hD;
            plugAndChugDrive(leftSpd, rightSpd, leftSpd, rightSpd);
    }

    public void StrafeToBall(){
        plugAndChugDrive(0.5, -0.5, -0.5, 0.5);
    }

    public void adjustAngle(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = networkTableInstance.getEntry("tx");
        NetworkTableEntry tv = networkTableInstance.getEntry("tv");
        double xAngle = -tx.getDouble(0); 
        double dectector = tv.getDouble(0);
        if(dectector == 1){
            if(xAngle > 0){
                rightSpd = xAngle*kD;
                leftSpd = -rightSpd;
                plugAndChugDrive(-rightSpd, -rightSpd, 0, 0);
            }
            else{
                leftSpd = -xAngle*kD;
                rightSpd = leftSpd;
                plugAndChugDrive(rightSpd, rightSpd, 0, 0);
            }
        }
        else{
            plugAndChugDrive(0.5, -0.5, 0, 0);
        }
    }
    
    public void adjustDistance(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = networkTableInstance.getEntry("ty");
        NetworkTableEntry tv = networkTableInstance.getEntry("ty");


        double yAngle = ty.getDouble(0); 

        distanceFromHub = (AUTO_HIGH_GOAL_HEIGHT - AUTO_CAMERA_HEIGHT) / tan(toRadians(yAngle + AUTO_CAMERA_ANGLE));
        if(tv.getDouble(0) >0.1 ){
        if(distanceFromHub> OPTIMAL_DISTANCE_TO_HUB){
            leftSpd = (distanceFromHub-OPTIMAL_DISTANCE_TO_HUB)*hD;
            rightSpd = (distanceFromHub - OPTIMAL_DISTANCE_TO_HUB)*hD;
        }
        else{
            leftSpd = (OPTIMAL_DISTANCE_TO_HUB - distanceFromHub)*hD;
            rightSpd = (OPTIMAL_DISTANCE_TO_HUB - distanceFromHub)*hD;
        }
    }
    else{
        leftSpd = -0.5;
        rightSpd = -0.5;
    }
        plugAndChugDrive(leftSpd, rightSpd, leftSpd, rightSpd);
    }
    //-----end of adjust methods-----
    
    
    //-----start of get methods-----
    public double getDistance(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry ty = networkTableInstance.getEntry("ty");
        double tt = ty.getDouble(0);
        distanceFromHub = (AUTO_HIGH_GOAL_HEIGHT - AUTO_CAMERA_HEIGHT) / tan(toRadians(tt + AUTO_CAMERA_ANGLE));
        return Math.abs(distanceFromHub - OPTIMAL_DISTANCE_TO_HUB);
    }
    public double getX(){
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = networkTableInstance.getEntry("tx");
        return tx.getDouble(0);
    }
    //-----end of get methods-----
    
    
}