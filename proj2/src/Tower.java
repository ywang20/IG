import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tower {
	//instance variables
		private final int towerWidth = 250;
		private final int towerHeight = 400;
		Image image = new Image("towerImage.png", towerWidth, towerHeight, false, false);
		private double x,y;//location
		private Label imageLabel = new Label("", new ImageView(image));
		
		public Tower(double x, double y){
			this.x = x;
			this.y = y;
			imageLabel.setLayoutX(x-towerWidth/2);
			imageLabel.setLayoutY(y-towerHeight/2);
		}
		
		public Label getImage(){
			return imageLabel;
		}
	
}
