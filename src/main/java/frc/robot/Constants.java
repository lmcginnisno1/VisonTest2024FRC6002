// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.ReplanningConfig;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import com.pathplanner.lib.util.PIDConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    public static final HolonomicPathFollowerConfig kHolonomicPathFollowerConfig = new HolonomicPathFollowerConfig(
      new PIDConstants(1.0, 0.0, 0.0), // Translation PID constants
      new PIDConstants(1.0, 0.0, 0.0), // Rotation PID constants
      kMaxSpeedMetersPerSecond,
      0.4, // Drive base radius in meters. Distance from robot center to furthest module.
      new ReplanningConfig() // Default path replanning config. See the API for the options here
    );

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(26.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(26.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    // public static final int kFrontLeftDrivingCanId = 2;
    // public static final int kFrontLeftTurningCanId = 1;
    // public static final int kFrontRightDrivingCanId = 4;
    // public static final int kFrontRightTurningCanId = 3;
    // public static final int kRearLeftDrivingCanId = 6;
    // public static final int kRearLeftTurningCanId = 5;
    // public static final int kRearRightDrivingCanId = 8;
    // public static final int kRearRightTurningCanId = 7;

    public static final int kFrontLeftDrivingCanId = 14;
    public static final int kRearLeftDrivingCanId = 15;
    public static final int kFrontRightDrivingCanId = 5;
    public static final int kRearRightDrivingCanId = 4;

    public static final int kFrontLeftTurningCanId = 13;
    public static final int kRearLeftTurningCanId = 16;
    public static final int kFrontRightTurningCanId = 6;
    public static final int kRearRightTurningCanId = 3;

    public static final boolean kGyroReversed = false;
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;//0.04
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1.5;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final double kDriveDeadband = 0.05;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 2;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
    public static final int kEncoderPPR = 42;
  }

  public static final class IntakeConstants {
    public static final int kGroundIntakeMotorCANId = 11;
    public static final int kIndexerMotorCANId = 13;

    public static final double kIntakeForward = 0.25;
    public static final double kIntakeOff = 0.0;
    public static final double kIntakeReverse = -0.25;

    public static final double kIndexerForward = 0.25;
    public static final double kIndexerOff = 0.0;
    public static final double kIndexerReverse = -0.25;

    public static final int kIndexerCurrentLimit = 10;
    public static final int kGroundIntakeCurrentLimit = 10;
  }

  public static final class ShooterConstants{
    public static final int kShooterCurrentLimit = 30;

    public static final int kBottomShooterMotorFollowerCANId = 21;
    public static final int kBottomShooterMotorMainCANId = 22;
    public static final int kTopShooterMotorCANId = 23;

    public static final double kShooterOn = 1000.0;//RPM
    public static final double kShooterOff = 0.0;
    public static final int kShooterToleranceRPS = 10;

    public static final double kTopShooterP = 0.0;
    public static final double kTopShooterI = 0.0;
    public static final double kTopShooterD = 0.0;
    public static final double kSTop = 0;
    public static final double kVTop = 0;

    public static final double kBottomShooterP = 0.0;
    public static final double kBottomShooterI = 0.0;
    public static final double kBottomShooterD = 0.0;
    public static final double kSBottom = 0;
    public static final double kVBottom = 0;

    public static final double kShooterMaxAcceleration = 500;
    public static final double kShooterMaxVelocity = 3000;
    public static final double kShooterMinOupt = -1.0;
    public static final double kShooterMaxOutput = 1.0;

    public static final double kBottomShooterVelocityFF = 10.0;

    public static final double kFeederFeed = 0.75;
    public static final double kFeederOff = 0.0;
  }

  public static final class ArmConstants{
    public static final int kShoulderMotorCANId = 15;
    public static final int kElbowMotorCANId = 16;

    public static final double kShoulderMotorP = 0.0;
    public static final double kShoulderMotorI = 0.0;
    public static final double kShoulderMotorD = 0.0;
    public static final double kShoulderMotorFF = 0.0;

    public static final double kElbowMotorP = 0.0;
    public static final double kElbowMotorI = 0.0;
    public static final double kElbowMotorD = 0.0;
    public static final double kElbowMotorFF = 0.0;

    public static final double kShoulderMaxVelocity = 1;
    public static final double kShoulderMaxAcceleration = 3;

    public static final double kElbowMaxVelocity = 1;
    public static final double kElbowMaxAcceleration = 3;
  }

  public static final class ClimberConstants{
    public static final int kClimberMotorCANId = 17;
    public static final int kClimberServoPWMPort = 0;

    public static final double kWinchMotorP = 0.0;
    public static final double kWinchMotorI = 0.0;
    public static final double kWinchMotorD = 0.0;
    public static final double kWinchMotorFF = 0.0;
    
    public static final double kWinchMaxVelocity = 1;
    public static final double kWinchMaxAcceleration = 3;

    public static final double kClimberServoHooked = 0.0;
    public static final double kClimberServoUnhooked = 0.5;
  }

  public static final class CameraConstants{
    public static final boolean kDriveCam = true;
    public static final boolean kLimelight = false;
    public static final int kDriveCamIndex = 2;
    public static final int kLimelightIndex = 1;

    public static final double APRILTAG_AMBIGUITY_THRESHOLD = 0.2;
    public static final double POSE_AMBIGUITY_SHIFTER = 0.2;
    public static final double POSE_AMBIGUITY_MULTIPLIER = 4;
    public static final double NOISY_DISTANCE_METERS = 6;
    public static final double DISTANCE_WEIGHT = 7;
    public static final int TAG_PRESENCE_WEIGHT = 10;

    public static final Transform3d SHOOTER_CAMERA_TO_ROBOT = new Transform3d(new Translation3d(0.29845/*camera distance forwards from center*/,
                                                                                            0.0/*camera distace laterally from center*/,
                                                                                            0.343/*camera distance vertically from ground*/),
                                                                          new Rotation3d(0.0/*leave at 0*/,
                                                                                        0.35,/*adjust if camera is mounted at an angle*/
                                                                                          0.0/*leave at 0*/));

    public static final Transform3d PICKUP_CAMERAN_TO_ROBOT = new Transform3d(new Translation3d(0.29845/*camera distance forwards from center*/,
                                                                                            0.0/*camera distace laterally from center*/,
                                                                                            0.343/*camera distance vertically from ground*/),
                                                                          new Rotation3d(0.0/*leave at 0*/,
                                                                                        0.35,/*adjust if camera is mounted at an angle*/
                                                                                          0.0/*leave at 0*/));
  }

  public static final class FieldConstants{
    public static final double kFieldLengthMeters = 16.4846;
    public static final double kFieldWidthMeters = 8.1026;
  }
}
