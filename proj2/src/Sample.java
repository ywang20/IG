/******************************************************************************\
* Copyright (C) 2012-2016 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import java.util.concurrent.TimeUnit;

class SampleListener extends Listener {
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        System.out.println("Frame id: " + frame.id()
                         + ", timestamp: " + frame.timestamp()
                         + ", hands: " + frame.hands().count()
                         + ", fingers: " + frame.fingers().count());

        //Get hands
        for(com.leapmotion.leap.Hand hand : frame.hands()) {
            String handType = hand.isLeft() ? "Left hand" : "Right hand";
            System.out.println("  " + handType + ", id: " + hand.id()
                             + ", palm position: " + hand.palmPosition());
//
//            // Get the hand's normal vector and direction
//            Vector normal = hand.palmNormal();
//            Vector direction = hand.direction();
//
//            // Calculate the hand's pitch, roll, and yaw angles
//            System.out.println("  pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
//                             + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
//                             + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");
//
//            // Get arm bone
//            Arm arm = hand.arm();
//            System.out.println("  Arm direction: " + arm.direction()
//                             + ", wrist position: " + arm.wristPosition()
//                             + ", elbow position: " + arm.elbowPosition());

            // Get fingers
//            for (Finger finger : hand.fingers()) {
//                System.out.println("    " + finger.type() + ", id: " + finger.id()
//                                 + ", length: " + finger.length()
//                                 + "mm, width: " + finger.width() + "mm");
//
//                //Get Bones
//                for(Bone.Type boneType : Bone.Type.values()) {
//                    Bone bone = finger.bone(boneType);
//                    System.out.println("      " + bone.type()
//                                     + " bone, start: " + bone.prevJoint()
//                                     + ", end: " + bone.nextJoint()
//                                     + ", direction: " + bone.direction());
//                }
//            }
//            System.out.println(hand.pointables().count());
//            for (Finger f : hand.fingers())
//            	System.out.println(f.type() + f.toString());
            
            if (isPoint(hand, 70.0f))
            	System.out.println("pointed");
            else if (isPinch(hand, 80.0f))
            	System.out.println("pinched");
            else 
            	System.out.println("open");
            
        }

        if (!frame.hands().isEmpty()) {
            System.out.println();
        }
        
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
    
    private boolean isPinch(Hand hand, float radius)
    {
		int pinches = 0;
		Vector thumbpos = hand.fingers().get(0).tipPosition();
		System.out.println(thumbpos);
		for (int x = 1; x < 5; x++) {
			System.out.println(hand.fingers().get(x).tipPosition());
			if (distance(thumbpos, hand.pointables().get(x).tipPosition()) < radius)
				pinches++;
		}
		
		return pinches > 2;
    }
    
    private boolean isPoint(com.leapmotion.leap.Hand hand, float radius)
    {
    	int inrange = 0; 
    	
    	Vector indexpos = hand.fingers().get(1).tipPosition();
    	System.out.println(indexpos);
    	for (Finger finger : hand.fingers())
    		if (distance(indexpos, finger.tipPosition()) < radius)
    			inrange++;
    	return inrange == 1;
    }
    
    private double distance(Vector t, Vector f)
    {
    	return Math.sqrt(Math.pow((t.getX()-f.getX()), 2) 
    			+ Math.pow((t.getY()-f.getY()), 2)
    			+ Math.pow((t.getZ()-f.getZ()), 2));
    }
}

class Sample {
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }
}
