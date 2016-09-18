package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Star {
	private int y;
	private int r;
	private int gravity;
	private int dy;
	private int opacity;
	
	private Color color;
	private Random speed;
	private Random x;
	private Random ys;
	
	private int randomX;
	private int randomSpeed;
	private int randomSize;
	
	BufferedImage star = null;
	
	public Star(){
		opacity = 100;
		r = 4;
		gravity = 1;
		
		try {
		    star = ImageIO.read(new File("res/star.png"));
		} catch (IOException e) {
			System.out.println("star did not load");
		}
		
		ys = new Random();
		randomSize = ys.nextInt(((40) - (3)) + 1) + (3);
		
		speed = new Random();
		randomSpeed = speed.nextInt((2 + 1) - 1) + 1;
		
		x = new Random();
		randomX = x.nextInt(((GamePanel.WIDTH) - (0)) + 1) + 0;
		
		y = 0 - randomSize;
	}
	
	public boolean update(){
		y += randomSpeed;
		if(randomX < -r || randomX > GamePanel.WIDTH + r ||
				y < -r || y > GamePanel.HEIGHT + r){
				return true;
			}
			return false;
	}
	
	public void draw(Graphics2D g){
		color = new Color(255, 255, 255, 100);
		g.setColor(color);
		g.drawImage(star, randomX, y, randomSize, randomSize, null);
	}
}
