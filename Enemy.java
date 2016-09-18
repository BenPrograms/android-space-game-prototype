package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Enemy {
	private int x;
	private int y;
	private double gravity;
	private int width;
	private int height;
	
	private boolean right;
	
	private Color color;
	private Random r;
	private int type;
	
	public Enemy(boolean right){
		this.right = right;
		
		r = new Random();
		type = r.nextInt(5);
		
		gravity = 4;
		
		if(type == 0){
			width = 45;
			height = 25;
			
			color = Color.WHITE;
		}
		if(type == 1){
			width = 30;
			height = 30;
			
			color = Color.CYAN;
		}
		if(type == 2){
			width = 60;
			height = 10;
			
			color = Color.ORANGE;
		}
		if(type == 3){
			width = 40;
			height = 30;
			
			color = Color.YELLOW;
		}
		if(type == 4){
			width = 50;
			height = 40;
			
			color = Color.BLUE;
		}
		if(right){
			x = GamePanel.WIDTH - (width * 2);
			y = -50 - height;
		}else if(right == false){
			x = 0 + (width);
			y = -50 - height;
		}
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
		y += gravity;
		
		if(x < -1000 || x > GamePanel.WIDTH + 1000 ||
			y < -1000 || y > GamePanel.HEIGHT + 1000){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}
