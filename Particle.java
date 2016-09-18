package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
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
	private int randomYS;
	
	public Particle(int newX){
		opacity = 100;
		r = 4;
		y = GamePanel.HEIGHT / 2 + 55;
		gravity = 1;
		
		ys = new Random();
		randomYS = ys.nextInt((1 - (-1)) + 1) + (-1);
		
		speed = new Random();
		randomSpeed = speed.nextInt((10 + 4) - 1) + 4;
		
		x = new Random();
		randomX = x.nextInt(((newX + 20) - (newX + 5)) + 1) + newX + 5;
	}
	
	public boolean update(){
		y += randomSpeed;
		randomX += randomYS;
		if(opacity >= 4){
			opacity-=4;
		}
		if(randomX < -r || randomX > GamePanel.WIDTH + r ||
				y < -r || y > GamePanel.HEIGHT + r){
				return true;
			}
			return false;
	}
	
	public void draw(Graphics2D g){
		color = new Color(255, 0, 0, opacity);
		g.setColor(color);
		g.fillOval((int) (randomX - r), (int) (y - r), 2 * r, 2 * r);
	}
}
