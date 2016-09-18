package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Blocks {
	private int x;
	private int y;
	private double dy;
	private double gravity;
	private int width;
	private int height;
	private boolean right;
	
	private Random r;
	private int randomNum;
	
	public Blocks(){	
		r = new Random();
		randomNum = r.nextInt(2);
		
		gravity = 4;
		width = 150;
		height = 50;
		
		y = -height;
		
		if(randomNum == 0){
			right = true;
		}
		if(randomNum == 1){
			right = false;
		}
		
		if(right){
			x = GamePanel.WIDTH - width + 20;
		}else if(right == false){
			x = 0 - 20;
		}
		
		Constants.RIGHT = right;
	}
	
	public boolean update(){
		y += gravity;

		if(x < - width || x > GamePanel.WIDTH + width ||
			y < - height || y > GamePanel.HEIGHT + height){
			return true;
		}
		return false;	
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(x, y, width, height, 20, 20);
	}
}
