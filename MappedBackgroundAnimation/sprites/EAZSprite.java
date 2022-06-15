import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class EAZSprite implements DisplayableSprite, MovableSprite, CollidingSprite {
	
	Image imageStill = null;
	private double centerX = 0.0;
    private double centerY = 0.0;
    private double height = 40.0;
    private double width = 40.0;
    private boolean dispose = false;
    private double velocityX = 200;
    private double velocityY = 200;
    private String proximityMessage = "";
    private int score = 0;
    private boolean exit = false;
    
    public EAZSprite(double d, double f) {
    	if(imageStill == null) {
    		try {
    			imageStill = ImageIO.read(new File("res/EAZ/Chimpy.png"));
    		}
    		catch(IOException e) {
    			System.out.println(e.toString());
    		}
    	}
    }

	@Override
	public void setCenterX(double centerX) {
		this.centerX = centerX;
		
	}

	@Override
	public void setCenterY(double centerY) {
		// TODO Auto-generated method stub
		this.centerY = centerY;
	}

	@Override
	public void setVelocityX(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		this.velocityX = pixelsPerSecond;
	}

	@Override
	public void setVelocityY(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		this.velocityY = pixelsPerSecond;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return imageStill;
	}

	@Override
	public boolean getVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double getMinX() {
		// TODO Auto-generated method stub
		return centerX - (width / 2);
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return centerX + (width / 2);
	}

	@Override
	public double getMinY() {
		// TODO Auto-generated method stub
		return centerY - (width / 2);
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return centerY + (width / 2);
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double getCenterX() {
		// TODO Auto-generated method stub
		return centerX;
	}

	@Override
	public double getCenterY() {
		// TODO Auto-generated method stub
		return centerY;
	}

	@Override
	public boolean getDispose() {
		// TODO Auto-generated method stub
		return dispose;
	}

	@Override
	public void setDispose(boolean dispose) {
		// TODO Auto-generated method stub
		this.dispose = dispose;
	}
	
	public static boolean inside(double leftA, double topA, double rightA, double bottomA, double leftB, double topB, double rightB, double bottomB) {
		boolean insideX = ((leftB <= leftA) && (rightA <= rightB));
		boolean insideY = ((topB <= topA) && (bottomA <= bottomB));
		if (insideX && insideY) {
			return true;
		}
		else {
			return false;	    	
		}
	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		// TODO Auto-generated method stub
        double deltaX = actual_delta_time * velocityX * 0.001;
        double deltaY = actual_delta_time * velocityY * 0.001;
        
//      boolean proximityOfOtherSprite = checkCollisionWithOtherSprite(universe.getSprites(), deltaX, deltaY);
//		boolean collidingCoin = checkCollisionWithCoins(universe.getSprites(), deltaX, deltaY);
//		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);
//		boolean collidingBarrierY = checkCollisionWithBarrier(universe.getSprites(), 0, deltaY);
//		boolean collidingExitSprite = checkCollisionWithExitSprite(universe.getSprites(), deltaX, deltaY);
		
//		if(proximityOfOtherSprite) {
//			proximityMessage = "You're a bit close to me!";;
//		}else {
//			proximityMessage = "";
//		}
//		
//		if(collidingCoin) {
//			score += 100;
//		}
//		
//		if(collidingBarrierX) {
//			velocityX = 0;
//		}
//		
//		if(collidingBarrierY) {
//			velocityY = 0;
//		}
//		
//		if(collidingExitSprite) {
//			exit = true;
//		}
		
		this.centerY += actual_delta_time * 0.001 * velocityY; //Same deltaY as before, have to recalculate to properly account for wall collisions
		this.centerX += actual_delta_time * 0.001 * velocityX; //Same deltaX as before, have to recalculate to properly account for wall collisions
		
	}

	private boolean checkCollisionWithExitSprite(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean colliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.inside(this.getMinX() + deltaX, this.getMinY() + deltaY, this.getMaxX()  + deltaX, this.getMaxY() + deltaY, sprite.getMinX(), sprite.getMinY(), sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
					}
				}
			}
		return colliding;
	}

	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean colliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if(CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, this.getMaxX() + deltaX, this.getMaxY() + deltaY, sprite.getMinX(), sprite.getMinY(), sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;
				}
			}
		}
		return colliding;
	}

	private boolean checkCollisionWithCoins(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean colliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if(CollisionDetection.covers(this.getMinX() + deltaX, this.getMinY() + deltaY, this.getMaxX() + deltaX, this.getMaxY() + deltaY, sprite.getMinX(), sprite.getMinY(), sprite.getMaxX(), sprite.getMaxY())) {
					sprite.setDispose(true);
					colliding = true;
					break;
				}
			}
		}
		return colliding;
	}

	private boolean checkCollisionWithOtherSprite(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean colliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				double distanceX = this.centerX - sprite.getCenterX();
				double distanceY = this.centerY - sprite.getCenterY();
				double radialDistance = Math.sqrt((distanceY * distanceY) + (distanceX * distanceX));
				if (radialDistance <= 100) {
					colliding = true;
				}
				else {
					colliding = false;
				}
			}
		}
		return colliding;
	}

	@Override
	public long getScore() {
		return score;
	}

	@Override
	public String getProximityMessage() {
		return proximityMessage;
	}

	@Override
	public boolean getIsAtExit() {
		return exit;
	}
}
