package frc.team5115.Subsystems;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class CurrentUpdate extends SubsystemBase{
    public PowerDistribution PDP;

    public CurrentUpdate(){
        PDP = new PowerDistribution();

    }
    

    public double returnMotorCurrent(PowerDistribution pdp,int portNumber){
        PDP = pdp;
        double motorCurrent = PDP.getCurrent(portNumber);
        //System.out.println(m);
        return motorCurrent;
    }

    
}
