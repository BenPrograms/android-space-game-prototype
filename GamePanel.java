package ben.jeckpackassasin.com;
import javax.swing.JPanel;
import java.awt. *;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage. *;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	//Fields
	public static int WIDTH = 400;
	public static int HEIGHT = 600;
	private Thread thread;
	private boolean running;
	private int FPS = 30;
	private double averageFPS;
	
	private long blockTimer;
	private long blockDelay;
	
	private long starTimer;
	private long starDelay;
	
	private BufferedImage image;
	private Graphics2D g;
	private Player player;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Blocks> blocks;
	public static ArrayList<Enemy> enemy;
	public static ArrayList<Star> stars;
	
	private boolean debug = true;
	
	private Color background = new Color(0,0,25);
	
	//Constructor
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		
		blockDelay = 2000;
		blockTimer = System.nanoTime();
		
		starDelay = 250;
		starTimer = System.nanoTime();
		
		player = new Player();
		bullets = new ArrayList<Bullet>();
		blocks = new ArrayList<Blocks>();
		enemy = new ArrayList<Enemy>();
		stars = new ArrayList<Star>();
	}
	
	//Functions
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run(){
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		long totalTime = 0;
		
		//GameLoop
		while(running){
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			try{
				Thread.sleep(waitTime);
			}catch(Exception e){
				e.printStackTrace();
			}
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	public void gameUpdate(){
		starTimer++;
		if(starTimer >= starDelay){
			GamePanel.stars.add(new Star());
		}
		
		long elapsed = (System.nanoTime() - blockTimer) / 1000000;
		if(elapsed > blockDelay){
			GamePanel.blocks.add(new Blocks());
			GamePanel.enemy.add(new Enemy(Constants.RIGHT));
			blockTimer = System.nanoTime();
		}
		
		for(int i = 0; i < stars.size(); i++){
			boolean remove = stars.get(i).update();
			if(remove){
				stars.remove(i);
				i--;
			}
		}
		
		player.update();
	
		for(int i = 0; i < bullets.size(); i++){
			boolean remove = bullets.get(i).update();
			
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < blocks.size(); i ++){
			boolean remove = blocks.get(i).update();
			if(remove){
				blocks.remove(i);
				i--;
			}
		}
		
		//collision detection
		for(int i = 0; i < enemy.size(); i++){
			for(int g = 0; g < bullets.size(); g++){
				if(bullets.get(g).getY() + bullets.get(g).getHeight() >= enemy.get(i).getY()){
					if(bullets.get(g).getY() <= enemy.get(i).getY() + enemy.get(i).getWidth()){
						if(bullets.get(g).getX() <= enemy.get(i).getX() + enemy.get(i).getWidth() 
								&& bullets.get(g).getX() + bullets.get(g).getWidth() >= enemy.get(i).getX()){
							bullets.remove(g);
							enemy.remove(i);
							
							Constants.SCORE++;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < enemy.size(); i ++){
			boolean remove = enemy.get(i).update();
			if(enemy.get(i).getY() >= GamePanel.HEIGHT + enemy.get(i).getHeight()){
				Constants.SCORE = 0;
				enemy.remove(i);
			}
			if(remove){
				enemy.remove(i);
				i--;
			}
		}
	}
	
	public void gameRender(){
		g.setColor(background);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0; i < stars.size(); i++){
			stars.get(i).draw(g);
		}
		
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
		
		player.draw(g);
		
		for(int i = 0; i < blocks.size(); i ++){
			blocks.get(i).draw(g);
		}
		
		for(int i = 0; i < enemy.size(); i ++){
			enemy.get(i).draw(g);
		}
		
		if(debug = true){
			g.setColor(Color.WHITE);
			g.drawString("FPS: " + averageFPS, 20, 20);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("" + Constants.SCORE, GamePanel.WIDTH / 2, 30);
	}
	
	public void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	public void keyPressed(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_RIGHT){
			player.rightFire(true);
		}
		if(keyCode == KeyEvent.VK_LEFT){
			player.leftFire(true);
		}
	}
	
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_RIGHT){
			player.rightFire(false);
		}
		if(keyCode == KeyEvent.VK_LEFT){
			player.leftFire(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
