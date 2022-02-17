package frc.team5115.Subsystems;
 
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team5115.Constants;
 
import static frc.team5115.Constants.*;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;
 
public class Limelight {
 
    NetworkTableEntry pipeline;
    public NetworkTableEntry tx;
    public NetworkTableEntry ty;
    public NetworkTableEntry tv;
    public NetworkTableEntry ta;
 
    public double xAngle;
    public double area;
    public double yAngle;
    public double t;
    public boolean target;
 
    int currentPipeline = 0;
    public Limelight() {
        NetworkTable networkTableInstance = NetworkTableInstance.getDefault().getTable("limelight");
        tx = networkTableInstance.getEntry("tx");
        ty = networkTableInstance.getEntry("ty");
        tv = networkTableInstance.getEntry("tv");
        ta = networkTableInstance.getEntry("ta");
        pipeline = networkTableInstance.getEntry("pipeline");
 
        xAngle = tx.getDouble(0);
        yAngle = ty.getDouble(0);
        area = ta.getDouble(0);
        t = tv.getDouble(0);
        if(t == 1){
            target = true;
        }
        else{
            target = false;
        }
 
    }
 
 
    public void setPipeline(int newPipe) {
 
            pipeline.setNumber(newPipe);
            currentPipeline = newPipe;
            System.out.println("Changed Pipeline to " + newPipe);
        }
 
    public void setPipeline(Constants.Pipeline pipeline) {
        setPipeline(pipeline.getPipelineNumber());

    }
    
}
 