	import javafx.scene.media.*;            
	import javafx.scene.layout.*; 
	import javafx.scene.control.Label;
	import javafx.scene.image.*;
	
public class Disk {
	//instance variables
	private int diskWidth;//depending on the value of the disk
	private final int diskHeight = 32;
	private int value;//0-6
	Image image;
	private double x,y;//location
	private Label imageLabel;
	
	public Disk(int value, double x, double y){
		this.value = value;
		diskWidth = (value+1)*32;
		this.x = x;
		this.y = y;
		image = new Image("diskImage.jpg", diskWidth, diskHeight, true, false);
		imageLabel = new Label("", new ImageView(image));
		imageLabel.setLayoutX(x-diskWidth/2);
		imageLabel.setLayoutY(y-diskHeight/2);
	}
	public Label getImage(){
		return imageLabel;
	}
	
	public void moveTo (double newX, double newY) {
		if (newX < diskWidth/2) {//reset x
			newX = diskWidth/2;
		} else if (newX > IG.WIDTH - diskWidth/2) {
			newX = IG.WIDTH - diskWidth/2;
		}

		if (newY < diskHeight/2) {// reset y
			newY = diskHeight/2;
		} else if (newY > IG.HEIGHT - diskHeight/2) {
			newY = IG.HEIGHT - diskHeight/2;
		}

		imageLabel.setTranslateX(newX - (imageLabel.getLayoutX() + diskWidth/2));
		imageLabel.setTranslateY(newY - (imageLabel.getLayoutY() + diskHeight/2));
	}
	
}
