package frc.team5115.Commands.Auto.NewAuto;
import frc.team5115.Constants.*;
import frc.team5115.Subsystems.*;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class Updater extends CommandBase{
    PowerDistribution PDP;
    CurrentUpdate currentupdate;
    int Port;
    
        public Updater(CurrentUpdate x, int PortNumber){
            currentupdate = x;
            PDP = currentupdate.PDP;
            Port = PortNumber;
        }
    @Override
        public void execute(){
            System.out.println(currentupdate.returnMotorCurrent(PDP, Port));

        }
    @Override
        public boolean isFinished() {
            if(currentupdate.returnMotorCurrent(PDP, Port)>10){
                return true;
            }
            else{
                return false;
            }

        }
        
        



}
