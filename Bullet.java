package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
	//Fields
	private int x;
	private int y;
	private int r;
	private int rad;
	private int speed;
	private int gravity;
	private int dx;
	private int dy;
	private Color color;
	private boolean right;
	private int width;
	private int height;
	
	//Constructor
	public Bullet(int x, int y, boolean right){
		this.right = right;
		this.x = x;
		this.y = y;
	
		r = 3;
		height = r * 2;
		width = r * 2;
		speed = 15;
		gravity = 1;
		dx = speed;
		color = Color.WHITE;
	}
	//Functions
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	
	public boolean update(){
		
		gravity += 1; 
		dy = gravity;
		
		if(right){
			x += dx;
		}else if(right == false){
			x -= dx;
		}
		
		y += dy;
		
		if(x < -r || x > GamePanel.WIDTH + r ||
			y < -r || y > GamePanel.HEIGHT + r){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
	}
}
