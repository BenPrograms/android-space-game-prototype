package ben.jeckpackassasin.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	private int x;
	private int y;
	private boolean right;
	private int width;
	private int height;
	
	private boolean fireRight; 
	private boolean fireLeft;
	private boolean normal;
	private long fireTimer;
	private long fireDelay;
	private long particleTime;
	private long particleDelay;
	
	public static ArrayList<Particle> particles;
	
	BufferedImage rightImage = null;
	
	private Color color = new Color(255, 0, 0);
	
	public Player(){
		particles = new ArrayList<Particle>();
		
		try {
		    rightImage = ImageIO.read(new File("res/playerRight.png"));
		} catch (IOException e) {
			System.out.println("player did not load");
		}
		
		width = 35;
		height = 67;
		
		x = GamePanel.WIDTH / 2 - 10;
		y = GamePanel.HEIGHT / 2;
		
		fireRight = false;
		fireLeft = false;
		normal = true;
		particleTime = System.nanoTime();
		particleDelay = 20;
		fireTimer = System.nanoTime();
		fireDelay = 250;
	}
	
	public void rightFire(boolean b){
		fireRight = b;
	}
	public void leftFire(boolean b){
		fireLeft = b;
	}
	
	public void update(){
		particleTime++;
		
		if(particleTime >= particleDelay){
			particles.add(new Particle(x));
		}
		
		for(int i = 0; i < particles.size(); i++){
			boolean remove = particles.get(i).update();
			
			if(remove){
				particles.remove(i);
				i--;
			}
		}
		
		if(fireRight){
			normal = true;
			
			long elapsed = (System.nanoTime() - fireTimer) / 1000000;
			if(elapsed > fireDelay){
				GamePanel.bullets.add(new Bullet(x + width / 2, y + height / 2 - 1, true));
				fireTimer = System.nanoTime();
			}
		}
		if(fireLeft){
			normal = false;
			
			long elapsed = (System.nanoTime() - fireTimer) / 1000000;
			if(elapsed > fireDelay){
				GamePanel.bullets.add(new Bullet(x + width / 2, y + height / 2 - 1, false));
				fireTimer = System.nanoTime();
			}
		}
	}
	
	public void draw(Graphics2D g){
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).draw(g);
		}
		
		if(normal){
			g.drawImage(rightImage, x, y, width, height, null);
		}
		if(normal == false){
			g.drawImage(rightImage, x + width - 10, y, -width, height, null);
		}
	}
}
