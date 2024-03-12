package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.ShooterConstants;

public class SUB_BottomShooter extends PIDSubsystem{
    final CANSparkMax m_BottomShooterMotorMain;
    final CANSparkMax m_BottomShooterMotorFollower;
    final RelativeEncoder m_BottomShooterEncoder;
    double setpoint;

    private SimpleMotorFeedforward m_BottomShooterFeedforward =
      new SimpleMotorFeedforward(
          ShooterConstants.kSBottom, ShooterConstants.kVBottom);

    public SUB_BottomShooter(){
        super(new PIDController(ShooterConstants.kBottomShooterP, ShooterConstants.kBottomShooterI, ShooterConstants.kBottomShooterD));
        getController().setTolerance(ShooterConstants.kShooterToleranceRPS);
        setSetpoint(0);
        m_BottomShooterMotorMain = new CANSparkMax(ShooterConstants.kBottomShooterMotorMainCANId, MotorType.kBrushless);
        m_BottomShooterEncoder = m_BottomShooterMotorMain.getEncoder();
        m_BottomShooterMotorMain.setIdleMode(IdleMode.kCoast);
        m_BottomShooterMotorMain.setSmartCurrentLimit(ShooterConstants.kShooterCurrentLimit);

        m_BottomShooterMotorFollower = new CANSparkMax(ShooterConstants.kBottomShooterMotorFollowerCANId, MotorType.kBrushless);
        m_BottomShooterMotorFollower.setSmartCurrentLimit(ShooterConstants.kShooterCurrentLimit);
        m_BottomShooterMotorFollower.setIdleMode(IdleMode.kCoast);  
        m_BottomShooterMotorFollower.follow(m_BottomShooterMotorMain);

        m_BottomShooterMotorMain.burnFlash();
        m_BottomShooterMotorFollower.burnFlash();
        setSetpoint(0);
        m_BottomShooterEncoder.setPosition(0);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        m_BottomShooterMotorMain.setVoltage(output + m_BottomShooterFeedforward.calculate(setpoint));
    }

    @Override
    protected double getMeasurement() {
        return (double)m_BottomShooterEncoder.getVelocity();
    }

    public void setVoltage(double p_voltage){
        m_BottomShooterMotorMain.setVoltage(p_voltage);
    }

    public boolean atSetpoint() {
        return m_controller.atSetpoint();
    }

    public void telemetry(){
        SmartDashboard.putNumber("Bottom shooter motor velocity", m_BottomShooterEncoder.getVelocity());
        SmartDashboard.putNumber("Bottom shooter commanded velocity", 0);
        setSetpoint(SmartDashboard.getNumber("Bottom shooter commanded velocity", getSetpoint()));
        SmartDashboard.putNumber("Bottom Shooter P", m_controller.getP());
        SmartDashboard.putNumber("Bottom Shooter I", m_controller.getI());
        SmartDashboard.putNumber("Bottom Shooter D", m_controller.getD());
        SmartDashboard.putNumber("Bottom shooter kS", m_BottomShooterFeedforward.ks);
        SmartDashboard.putNumber("Bottom shooter kV", m_BottomShooterFeedforward.kv);
        
        m_controller.setPID(SmartDashboard.getNumber("Bottom Shooter P", m_controller.getP()),
                            SmartDashboard.getNumber("Bottom Shooter I", m_controller.getI()),
                            SmartDashboard.getNumber("Bottom Shooter D", m_controller.getD())
        );

        m_BottomShooterFeedforward = new SimpleMotorFeedforward(SmartDashboard.getNumber("Bottom shooter kS", 0),
            SmartDashboard.getNumber("Bottom shooter kV", 0));
    }

    @Override
    public void periodic(){
        // super.periodic();
        setpoint += 0.001;
        m_BottomShooterMotorMain.setVoltage(setpoint);
        if(m_BottomShooterEncoder.getPosition() > 0){
            SmartDashboard.putNumber("bottom shooter kS", setpoint - 0.001);
            setpoint = 0;
            m_BottomShooterMotorMain.setVoltage(setpoint);
        }
        telemetry();
    }
}
