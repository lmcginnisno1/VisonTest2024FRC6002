package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GlobalVariables extends SubsystemBase{

    private Pose2d m_robotPose = new Pose2d(0.0, 0.0, new Rotation2d(0.0));
    public double m_AngleToTarget = 0;
    boolean m_readyToShoot = false;

    public enum SourceSlot{
        LEFT,
        MIDDLE,
        RIGHT
    }

    private SourceSlot m_SourceSlotSelected = SourceSlot.MIDDLE;

    @Override
    public void periodic(){
        
    }

    public void setRobotPose(Pose2d p_robotPose){
        m_robotPose = p_robotPose;
    }

    public Pose2d getRobotPose(){
        return m_robotPose;
    }

    public void setAngleToTarget(double p_angle){
        m_AngleToTarget = p_angle;
    }

    public double getAngleToTarget(){
        return m_AngleToTarget;
    }

    public void SetSourceSlot(SourceSlot p_SourceSlot){
        m_SourceSlotSelected = p_SourceSlot;
    }

    public SourceSlot getSelectedSourceSlot(){
        return m_SourceSlotSelected;
    }

    public void setReadyToShoot(boolean p_readyToShoot){
        m_readyToShoot = p_readyToShoot;
    }

    public boolean ReadyToShoot(){
        return m_readyToShoot;
    }
}
