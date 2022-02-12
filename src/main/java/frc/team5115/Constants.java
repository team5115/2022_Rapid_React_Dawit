
package frc.team5115;

public class Constants{
   
    //autonomous stuff
    public static final StartingConfiguration startingConfiguration = StartingConfiguration.Middle;
    public static final double startY = 120;
    public static final int startingAngle = -45; //90 is looking away from the driver stations.

    public static final double AUTO_MAX_THROTTLE = 0.3;
    public static final double AUTO_CAMERA_HEIGHT = 25; //units: centimeters.
    public static final double AUTO_CAMERA_ANGLE = 60; //units: degrees.
    public static final double AUTO_SHOOTIN_DISTANCE = 120; //units: inches. todome update
    public static final double AUTO_HIGH_GOAL_HEIGHT = 272; //units: centimeters (temporary).
    public static final double HUB_DISTANCE = 200; //units: centimeters.

    //motor ids
    public static final byte FRONT_LEFT_MOTOR_ID = 1;
    public static final byte FRONT_RIGHT_MOTOR_ID = 2;
    public static final byte BACK_LEFT_MOTOR_ID = 3;
    public static final byte BACK_RIGHT_MOTOR_ID = 4;
    public static final double DEAD_BAND = .02;

    public static final int INTAKE_MOTOR_ID = 5;
    public static final int SHOOTER_MOTOR_ID = 7;
    public static final int SCISSOR_MOTOR_ID = 9;
    public static final int WINCH_MOTOR_ID = 11;
    public static final int FEEDER_MOTOR_ID = 6;
    public static final int ACCELERATOR_MOTOR_ID = 8;

    public static final byte INTAKE_BUTTON_ID = 1;
    public static final byte INTAKE_REVERSE_ID = 2;
    public static final int DC_MOTOR_BB_ID = 5;
    public static final int DC_MOTOR_BB2_ID = 6;
    public static  final byte WINCH_BUTTON_ID = 2;
    public static final byte SCISSORS_DOWN_BUTTON_ID = 3;
    public static final byte CLIMBER_UP_BUTTON_ID = 4;
    public static final byte AUTO_TURN_AND_MOVE_BUTTON_ID = 5;
    public static final byte SHOOTER_BUTTON_ID = 1;
    public static final byte AUTO_BALL_TARCKING = 7;
    public static final byte AUTO_TURN_BUTTON_ID = 8;
    public static final byte WINCH_RELEASE_BUTTON_ID = 2;



    //Speed constants for subsystems.
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public static final Gains kGains_Velocity = new Gains( 0.25, 0.001, 20, 1023.0/7200.0,  300,  1.00);
    public static final double FEEDER_STORE_SPEED = -0.4;
    public static final double FEEDER_FLUSH_SPEED = -0.8;
    public static final double INTAKE_INHALE_SPEED = -0.3;
    public static final double  ARM_IDLE_SPEED = 0.15;
    public static final double kD = 0.1;
    public static final double hD = 0.044;
    public static final double bA = 10;
    public static final double MaxArea = 0.1;
    public static final double kA = 0.1;
    public static final double ULTRASONIC_UNIT_CONVERSION = (10000/45.927);
    public static final double D1 = 20; //units centimeter, distance from one ultrasonic to the other
    //(5.0/10240);
    

    //X-Box
    public static byte JOY_Y_AXIS_ID = 1;
    public static byte JOY_X_AXIS_ID = 4;
    public static byte JOY_Z_AXIS_ID = 0; 
    public static byte Joy_THROTTLE_AXIS = 3;

    public static float JOY_X_DEADZONE = 0.075f;

    //information on where we start
    public enum StartingConfiguration {
        Right, Middle, Left;
        public double getX() {
            switch (this) {
                case Right:
                    return 50;
                case Middle:
                    return 100;
                case Left:
                    return 200;
            }
            return 0;
        }
    }

    public enum Pipeline {
        DriveCamera, Balls, GreenLedMode;
        public int getPipelineNumber() {
            switch(this) {
                case DriveCamera:
                    return 0;
                case Balls:
                    return 3;
                case GreenLedMode:
                    return 2;
            }
            return -1;
        }
    }
}
