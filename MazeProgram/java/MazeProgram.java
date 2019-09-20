import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class MazeProgram extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	int x=30,y=210,stretch=10,numSteps=0,minSteps=147,dir=0;
	int row;
	int col;
	int setbacks = 0;
	boolean game = true;
	boolean right,left,up,end,hasKey,restart,doorOpen = false;
	String[][] maze = new String[25][104];
	Font font = new Font("Times New Roman",Font.PLAIN,18);
	public static Ceiling ceiling1;
	public static Ceiling ceiling2;
	public static Wall leftWall1;
	public static Wall leftWall2;
	public static Wall rightWall1;
	public static Wall rightWall2;
	public static Floor floor1;
	public static Floor floor2;
	URL resource = getClass().getResource("music.mp3");
	AudioClip clip = new AudioClip(resource.toString());
	URL resourceTwo = getClass().getResource("key.mp3");
	AudioClip clipTwo = new AudioClip(resourceTwo.toString());  
	URL resourceThree = getClass().getResource("trap.mp3");
	AudioClip clipThree = new AudioClip(resourceThree.toString());  

	public MazeProgram()
	{
		clip.play();
		setBoard();
		frame=new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}
	
	public void showMaze(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, 1650, 1080);
		for(int y=0;y<maze.length;y++) {
			for(int x=0;x<maze[0].length;x++) {
				if(maze[y][x].equals("0"))
				{
					g.setColor(Color.GRAY);
					g.fillRect(x*stretch, y*stretch, stretch, stretch);
					g.setColor(Color.BLACK);
					g.drawRect(x*stretch, y*stretch, stretch, stretch);
				}
				if(!doorOpen) {
					if(maze[y][x].equals("D")) {
						g.setColor(Color.BLUE);
						g.fillRect(x*stretch, y*stretch, stretch, stretch);
						g.setColor(Color.BLACK);
						g.drawRect(x*stretch, y*stretch, stretch, stretch);
					}
				}
				if(!hasKey) {
					if(maze[y][x].equals("K")) {
						g.setColor(Color.YELLOW);
						g.fillRect(x*stretch, y*stretch, stretch, stretch);
						g.setColor(Color.BLACK);
						g.drawRect(x*stretch, y*stretch, stretch, stretch);
					}
				}
				if(maze[y][x].equals("T")) {
					g.setColor(Color.BLACK);
					g.fillRect(x*stretch, y*stretch, stretch, stretch);
					g.setColor(Color.BLACK);
					g.drawRect(x*stretch, y*stretch, stretch, stretch);
				}
				if(maze[y][x].equals("E")) {
					g.setColor(Color.GREEN);
					g.fillRect(x*stretch, y*stretch, stretch, stretch);
					g.setColor(Color.BLACK);
					g.drawRect(x*stretch, y*stretch, stretch, stretch);
				}
			}
		}
	}
	
	public void setDir(boolean right, boolean left, int dir) {
		if(right) {
			if(dir == 360) {
				this.dir = 0;
			}
			this.dir+=90;
			this.right = false;
		}
		if(left) {
			if(dir == 0) {
				this.dir = 360;
			}
			this.dir-=90;
			this.left = false;
		}
	}
	
	public void move(boolean up, int dir, String[][] maze, int x, int y, int stretch, int numSteps) {
		if(up) {
			if(dir == 0 || dir == 360) {
				if(!hasKey) {
					if(maze[(y/stretch)-1][x/stretch].equals(" ")||maze[(y/stretch)-1][x/stretch].equals("0")||maze[(y/stretch)-1][x/stretch].equals("E")||maze[(y/stretch)-1][x/stretch].equals("T")||maze[(y/stretch)-1][x/stretch].equals("K")) {
						if(maze[(y/stretch)-1][x/stretch].equals(" ")||maze[(y/stretch)-1][x/stretch].equals("E")||maze[(y/stretch)-1][x/stretch].equals("T")||maze[(y/stretch)-1][x/stretch].equals("K")) {
							this.y-=stretch;
							this.numSteps++;
						}
					}
				}else {
					if(maze[(y/stretch)-1][x/stretch].equals(" ")||maze[(y/stretch)-1][x/stretch].equals("0")||maze[(y/stretch)-1][x/stretch].equals("E")||maze[(y/stretch)-1][x/stretch].equals("T")||maze[(y/stretch)-1][x/stretch].equals("K")||maze[(y/stretch)-1][x/stretch].equals("D")) {
						if(maze[(y/stretch)-1][x/stretch].equals(" ")||maze[(y/stretch)-1][x/stretch].equals("E")||maze[(y/stretch)-1][x/stretch].equals("T")||maze[(y/stretch)-1][x/stretch].equals("K")||maze[(y/stretch)-1][x/stretch].equals("D")) {
							this.y-=stretch;
							this.numSteps++;
						}
					}
				}
			}
			if(dir == 90) {
				if(!hasKey) {
					if(maze[y/stretch][(x/stretch)+1].equals(" ")||maze[y/stretch][(x/stretch)+1].equals("0")||maze[y/stretch][(x/stretch)+1].equals("E")||maze[y/stretch][(x/stretch)+1].equals("T")||maze[y/stretch][(x/stretch)+1].equals("K")) {
						if(maze[y/stretch][(x/stretch)+1].equals(" ")||maze[y/stretch][(x/stretch)+1].equals("E")||maze[y/stretch][(x/stretch)+1].equals("T")||maze[y/stretch][(x/stretch)+1].equals("K")) {
							this.x+=stretch;
							this.numSteps++;
						}
					}
				}else {
					if(maze[y/stretch][(x/stretch)+1].equals(" ")||maze[y/stretch][(x/stretch)+1].equals("0")||maze[y/stretch][(x/stretch)+1].equals("E")||maze[y/stretch][(x/stretch)+1].equals("T")||maze[y/stretch][(x/stretch)+1].equals("K")||maze[y/stretch][(x/stretch)+1].equals("D")) {
						if(maze[y/stretch][(x/stretch)+1].equals(" ")||maze[y/stretch][(x/stretch)+1].equals("E")||maze[y/stretch][(x/stretch)+1].equals("T")||maze[y/stretch][(x/stretch)+1].equals("K")||maze[y/stretch][(x/stretch)+1].equals("D")) {
							this.x+=stretch;
							this.numSteps++;
						}
					}
				}
			}
			if(dir == 180) {
				if(!hasKey) {
					if(maze[(y/stretch)+1][x/stretch].equals(" ")||maze[(y/stretch)+1][x/stretch].equals("0")||maze[(y/stretch)+1][x/stretch].equals("E")||maze[(y/stretch)+1][x/stretch].equals("T")||maze[(y/stretch)+1][x/stretch].equals("K")) {
						if(maze[(y/stretch)+1][x/stretch].equals(" ")||maze[(y/stretch)+1][x/stretch].equals("E")||maze[(y/stretch)+1][x/stretch].equals("T")||maze[(y/stretch)+1][x/stretch].equals("K")) {
							this.y+=stretch;
							this.numSteps++;
						}
					}
				}else {
					if(maze[(y/stretch)+1][x/stretch].equals(" ")||maze[(y/stretch)+1][x/stretch].equals("0")||maze[(y/stretch)+1][x/stretch].equals("E")||maze[(y/stretch)+1][x/stretch].equals("T")||maze[(y/stretch)+1][x/stretch].equals("K")||maze[(y/stretch)+1][x/stretch].equals("D")) {
						if(maze[(y/stretch)+1][x/stretch].equals(" ")||maze[(y/stretch)+1][x/stretch].equals("E")||maze[(y/stretch)+1][x/stretch].equals("T")||maze[(y/stretch)+1][x/stretch].equals("K")||maze[(y/stretch)+1][x/stretch].equals("D")) {
							this.y+=stretch;
							this.numSteps++;
						}
					}
				}
			}
			if(dir == 270) {
				if(!hasKey) {
					if(maze[y/stretch][(x/stretch)-1].equals(" ")||maze[y/stretch][(x/stretch)-1].equals("0")||maze[y/stretch][(x/stretch)-1].equals("E")||maze[y/stretch][(x/stretch)-1].equals("T")||maze[y/stretch][(x/stretch)-1].equals("K")) {
						if(maze[y/stretch][(x/stretch)-1].equals(" ")||maze[y/stretch][(x/stretch)-1].equals("E")||maze[y/stretch][(x/stretch)-1].equals("T")||maze[y/stretch][(x/stretch)-1].equals("K")) {
							this.x-=stretch;
							this.numSteps++;
						}
					}
				}else {
					if(maze[y/stretch][(x/stretch)-1].equals(" ")||maze[y/stretch][(x/stretch)-1].equals("0")||maze[y/stretch][(x/stretch)-1].equals("E")||maze[y/stretch][(x/stretch)-1].equals("T")||maze[y/stretch][(x/stretch)-1].equals("K")||maze[y/stretch][(x/stretch)-1].equals("D")) {
						if(maze[y/stretch][(x/stretch)-1].equals(" ")||maze[y/stretch][(x/stretch)-1].equals("E")||maze[y/stretch][(x/stretch)-1].equals("T")||maze[y/stretch][(x/stretch)-1].equals("K")||maze[y/stretch][(x/stretch)-1].equals("D")) {
							this.x-=stretch;
							this.numSteps++;
						}
					}
				}
			}
			this.up = false;
		}
	}
	
	public void checkEnd(String[][] maze, int x, int y, boolean end, int numSteps, int minSteps, Graphics g) {
		g.setFont(font);
		if(maze[y/stretch][x/stretch].equals("E")) {
			this.end = true;
			g.drawString("You have completed the maze!", 250, 300);
		}
	}
	
	public void showPlayer(Graphics g, int x, int y, int stretch) {
		g.setColor(Color.RED);
		g.fillRect(x,y,stretch,stretch);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, stretch, stretch);
	}
	
	public void showSteps(Graphics g, int numSteps, int minSteps) {
		g.setFont(font);
		g.drawString("Steps: "+numSteps, 25, 325);
	}
	
	public void showDir(Graphics g, int dir) {
		g.setFont(font);
		if(dir == 90) {
			g.drawString("Dir: East", 25, 350);
		}else if(dir == 180) {
			g.drawString("Dir: South", 25, 350);
		}
		else if(dir == 270) {
			g.drawString("Dir: West", 25, 350);
		}
		else {
			g.drawString("Dir: North", 25, 350);
		}
	}
	
	public void drawCeiling(Graphics g, Ceiling ceiling) {
		g.setColor(Color.GRAY);
		g.fillPolygon(ceiling.getPolygon());
		g.setColor(Color.BLACK);
		g.drawPolygon(ceiling.getPolygon());
	}
	
	public void drawWall(Graphics g, Wall wall, Color col) {
		g.setColor(col);
		g.fillPolygon(wall.getPolygon());
		g.setColor(Color.BLACK);
		g.drawPolygon(wall.getPolygon());
	}
	
	public void drawFloor(Graphics g, Floor floor) {
		g.setColor(Color.GRAY);
		g.fillPolygon(floor.getPolygon());
		g.setColor(Color.BLACK);
		g.drawPolygon(floor.getPolygon());
	}
	
	public void drawTunnel(Graphics g, Color col) {
		g.setColor(col);
		g.fillRect(ceiling2.getX()[3], ceiling2.getY()[3], 100, 200);
		g.setColor(Color.BLACK);
		g.drawRect(ceiling2.getX()[3], ceiling2.getY()[3], 100, 200);
	}
	
	public void checkTrap(String[][] maze, int x, int y) {
		if(maze[y/stretch][x/stretch].equals("T")) {
			this.setbacks++;
			clipThree.play();
			restart();
		}
	}
	
	public void checkKey(String[][] maze, int x, int y) {
		if(maze[y/stretch][x/stretch].equals("K")) {
			this.hasKey = true;
			clipTwo.play();
		}
	}
	
	public void restart() {
		clip.stop();
		x=30;
		y=210;
		numSteps=0;
		dir=0;
		row = y/stretch;
		col = x/stretch;
		right=false;
		left=false;
		up=false;
		end=false;
		hasKey=false;
		doorOpen=false;
		restart=false;
		game = true;
		clip.play();
	}
	
	public void paintComponent(Graphics g)
	{
		if(game) {
			super.paintComponent(g);
			showMaze(g);
			setDir(right,left,dir);
			move(up, dir, maze, x, y, stretch, numSteps);
			checkEnd(maze, x, y, end, numSteps, minSteps, g);
			showPlayer(g, x, y, stretch);
			showSteps(g,numSteps,minSteps);
			showDir(g,dir);
			checkTrap(maze, x, y);
			if(!hasKey) {
				checkKey(maze, x, y);
			}
			g.setFont(font);
			g.drawString("Press 'r' to go back to the beginning", 625, 325);
			g.drawString("Setbacks: "+setbacks, 625, 350);
			if(!hasKey) {
				g.drawString("Key? No", 625, 375);
			}else {
				g.drawString("Key? Yes", 625, 375);
			}
			if(maze[y/stretch][x/stretch].equals("D")) {
				doorOpen = true;
			}
			row = y/stretch;
			col = x/stretch;
			try {
				if(dir == 0 || dir == 360) {
					if(!maze[row-1][col].equals("0") || !maze[row-2][col].equals("0")) {
						if(maze[row-3][col].equals("0") || maze[row-2][col].equals("0") || maze[row-1][col].equals("0")) {
							drawTunnel(g,Color.GRAY);
						}
						if(maze[row-3][col].equals(" ") || maze[row-2][col].equals(" ") || maze[row-1][col].equals(" ")) {
							drawTunnel(g,Color.BLACK);
						}
						if(maze[row-2][col-1].equals("0")) {	
							drawWall(g,leftWall2,Color.GRAY);
						}
						if(maze[row-2][col-1].equals(" ")||maze[row-2][col-1].equals("K")) {
							drawWall(g,leftWall2,Color.GRAY);
							g.drawRect(leftWall2.getX()[0],leftWall2.getY()[1],100,200);
						}	
						if(maze[row-1][col-1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}
						if(maze[row-1][col-1].equals(" ")||maze[row-1][col-1].equals("K")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}
						if(maze[row-2][col+1].equals("0")) {	
							drawWall(g,rightWall2,Color.GRAY);
						}
						if(maze[row-2][col+1].equals(" ")||maze[row-2][col+1].equals("K")) {
							drawWall(g,rightWall2,Color.GRAY);
							g.drawRect(rightWall2.getX()[0],rightWall2.getY()[0],100,200);
						}	
						if(maze[row-1][col+1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}
						if(maze[row-1][col+1].equals(" ")||maze[row-1][col+1].equals("K")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}
						drawCeiling(g,ceiling1);
						drawCeiling(g,ceiling2);
						drawFloor(g,floor1);
						drawFloor(g,floor2);
						if(maze[row-2][col].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor2.getPolygon());
						}
						if(maze[row-1][col].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor1.getPolygon());
						}
					}
					if(maze[row-3][col].equals("0")) {
						drawTunnel(g,Color.GRAY);
					}
					if(maze[row-2][col].equals("0")) {
						if(maze[row-1][col+1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}
						if(maze[row-1][col+1].equals(" ")||maze[row-1][col+1].equals("K")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}
						if(maze[row-1][col-1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}
						if(maze[row-1][col-1].equals(" ")||maze[row-1][col-1].equals("K")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}
						g.setColor(Color.GRAY);
						g.fillRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
					}
					if(maze[row-1][col].equals("0")) {
						g.setColor(Color.GRAY);
						g.fillRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
					}else if(maze[row-1][col].equals("D")){
						if(!doorOpen) {
							g.setColor(Color.BLUE);
							g.fillRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
							g.setColor(Color.BLACK);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
						}
					}
				}
				if(dir == 90) {
					if(!maze[row][col+1].equals("0") || !maze[row][col+2].equals("0")) {
						if(maze[row][col+3].equals("0") || maze[row][col+2].equals("0") || maze[row][col+1].equals("0")) {
							drawTunnel(g,Color.GRAY);
						}
						if(maze[row][col+3].equals(" ") || maze[row][col+2].equals(" ") || maze[row][col+1].equals(" ")) {
							drawTunnel(g,Color.BLACK);
						}
						if(maze[row+1][col+2].equals("0")) {	
							drawWall(g,rightWall2,Color.GRAY);
						}else if(maze[row+1][col+2].equals(" ")||maze[row+1][col+2].equals("K")||maze[row+1][col+2].equals("T")) {
							drawWall(g,rightWall2,Color.GRAY);
							g.drawRect(rightWall2.getX()[0],rightWall2.getY()[0],100,200);
						}else if(maze[row+1][col+2].equals("D")) {
							drawWall(g,rightWall2,Color.BLUE);
						}
						if(maze[row+1][col+1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}else if(maze[row+1][col+1].equals(" ")||maze[row+1][col+1].equals("K")||maze[row+1][col+1].equals("T")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}else if(maze[row+1][col+1].equals("D")){
							drawWall(g,rightWall1,Color.BLUE);
						}
						if(maze[row-1][col+2].equals("0")) {	
							drawWall(g,leftWall2,Color.GRAY);
						}else if(maze[row-1][col+2].equals(" ")||maze[row-1][col+2].equals("K")||maze[row-1][col+2].equals("T")) {
							drawWall(g,leftWall2,Color.GRAY);
							g.drawRect(leftWall2.getX()[0],leftWall2.getY()[1],100,200);
						}else{
							drawWall(g,leftWall2,Color.BLUE);
						}
						if(maze[row-1][col+1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}else if(maze[row-1][col+1].equals(" ")||maze[row-1][col+1].equals("K")||maze[row-1][col+1].equals("T")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}else {
							drawWall(g,leftWall1,Color.BLUE);
						}
						drawCeiling(g,ceiling1);
						drawCeiling(g,ceiling2);
						drawFloor(g,floor1);
						drawFloor(g,floor2);
						if(maze[row][col+2].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor2.getPolygon());
						}
						if(maze[row][col+1].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor1.getPolygon());
						}
					}
					if(maze[row][col+3].equals("0")) {
						drawTunnel(g,Color.GRAY);
					}
					if(maze[row][col+2].equals("0")) {
						if(maze[row+1][col+1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}else if(maze[row+1][col+1].equals(" ")||maze[row+1][col+1].equals("K")||maze[row+1][col+1].equals("T")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}else {
							drawWall(g,rightWall1,Color.BLUE);
						}
						if(maze[row-1][col+1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}else if(maze[row-1][col+1].equals(" ")||maze[row-1][col+1].equals("K")||maze[row-1][col+1].equals("T")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}else {
							drawWall(g,leftWall1,Color.BLUE);
						}
						g.setColor(Color.GRAY);
						g.fillRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
					}
					if(maze[row][col+1].equals("0")) {
						g.setColor(Color.GRAY);
						g.fillRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
					}
				}
				if(dir == 180) {
					if(!maze[row+1][col].equals("0") || !maze[row+2][col].equals("0")) {
						if(maze[row+3][col].equals("0") || maze[row+2][col].equals("0") || maze[row+1][col].equals("0")) {
							drawTunnel(g,Color.GRAY);
						}
						if(maze[row+3][col].equals(" ") || maze[row+2][col].equals(" ") || maze[row+1][col].equals(" ")) {
							drawTunnel(g,Color.BLACK);
						}
						if(maze[row+2][col-1].equals("0")) {	
							drawWall(g,rightWall2,Color.GRAY);
						}
						if(maze[row+2][col-1].equals(" ")||maze[row+2][col-1].equals("K")) {
							drawWall(g,rightWall2,Color.GRAY);
							g.drawRect(rightWall2.getX()[0],rightWall2.getY()[0],100,200);
						}	
						if(maze[row+1][col-1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}
						if(maze[row+1][col-1].equals(" ")||maze[row+1][col-1].equals("K")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}
						if(maze[row+2][col+1].equals("0")) {	
							drawWall(g,leftWall2,Color.GRAY);
						}
						if(maze[row+2][col+1].equals(" ")||maze[row+2][col+1].equals("K")) {
							drawWall(g,leftWall2,Color.GRAY);
							g.drawRect(leftWall2.getX()[0],leftWall2.getY()[1],100,200);
						}	
						if(maze[row+1][col+1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}
						if(maze[row+1][col+1].equals(" ")||maze[row+1][col+1].equals("K")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}
						drawCeiling(g,ceiling1);
						drawCeiling(g,ceiling2);
						drawFloor(g,floor1);
						drawFloor(g,floor2);
						if(maze[row+2][col].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor2.getPolygon());
						}
						if(maze[row+1][col].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor1.getPolygon());
						}
					}
					if(maze[row+3][col].equals("0")) {
						drawTunnel(g,Color.GRAY);
					}
					if(maze[row+2][col].equals("0")){
						if(maze[row+1][col-1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}
						if(maze[row+1][col-1].equals(" ")||maze[row+1][col-1].equals("K")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}
						if(maze[row+1][col+1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}
						if(maze[row+1][col+1].equals(" ")||maze[row+1][col+1].equals("K")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}
						g.setColor(Color.GRAY);
						g.fillRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
					}
					if(maze[row+1][col].equals("0")){
						g.setColor(Color.GRAY);
						g.fillRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
					}
				}
				if(dir == 270) {
					if(!maze[row][col-1].equals("0") || !maze[row][col-2].equals("0")) {
						if(maze[row][col-3].equals("0") || maze[row][col-2].equals("0") || maze[row][col-1].equals("0")) {
							drawTunnel(g,Color.GRAY);
						}
						if(maze[row][col-3].equals(" ") || maze[row][col-2].equals(" ") || maze[row][col-1].equals(" ")) {
							drawTunnel(g,Color.BLACK);
						}
						if(maze[row+1][col-2].equals("0")) {	
							drawWall(g,leftWall2,Color.GRAY);
						}else if(maze[row+1][col-2].equals(" ")||maze[row+1][col-2].equals("K")||maze[row+1][col-2].equals("T")) {
							drawWall(g,leftWall2,Color.GRAY);
							g.drawRect(leftWall2.getX()[0],leftWall2.getY()[1],100,200);
						}else {
							drawWall(g,leftWall2,Color.BLUE);
						}
						if(maze[row+1][col-1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}else if(maze[row+1][col-1].equals(" ")||maze[row+1][col-1].equals("K")||maze[row+1][col-1].equals("T")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}else {
							drawWall(g,leftWall1,Color.BLUE);
						}
						if(maze[row-1][col-2].equals("0")) {	
							drawWall(g,rightWall2,Color.GRAY);
						}else if(maze[row-1][col-2].equals(" ")||maze[row-1][col-2].equals("K")||maze[row-1][col-2].equals("T")) {
							drawWall(g,rightWall2,Color.GRAY);
							g.drawRect(rightWall2.getX()[0],rightWall2.getY()[0],100,200);
						}else {
							drawWall(g,rightWall2,Color.BLUE);
						}
						if(maze[row-1][col-1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}else if(maze[row-1][col-1].equals(" ")||maze[row-1][col-1].equals("K")||maze[row-1][col-1].equals("T")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}else {
							drawWall(g,rightWall1,Color.BLUE);
						}
						drawCeiling(g,ceiling1);
						drawCeiling(g,ceiling2);
						drawFloor(g,floor1);
						drawFloor(g,floor2);
						if(maze[row][col-2].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor2.getPolygon());
						}
						if(maze[row][col-1].equals("T")) {
							g.setColor(Color.BLACK);
							g.fillPolygon(floor1.getPolygon());
						}
					}
					if(maze[row][col-3].equals("0")) {
						drawTunnel(g,Color.GRAY);
					}
					if(maze[row][col-2].equals("0")) {
						if(maze[row+1][col-1].equals("0")) {	
							drawWall(g,leftWall1,Color.GRAY);
						}else if(maze[row+1][col-1].equals(" ")||maze[row+1][col-1].equals("K")||maze[row+1][col-1].equals("T")) {
							drawWall(g,leftWall1,Color.GRAY);
							g.drawRect(leftWall1.getX()[0],leftWall1.getY()[1],100,250);
						}else {
							drawWall(g,leftWall1,Color.BLUE);
						}
						if(maze[row-1][col-1].equals("0")) {	
							drawWall(g,rightWall1,Color.GRAY);
						}else if(maze[row-1][col-1].equals(" ")||maze[row-1][col-1].equals("K")||maze[row-1][col-1].equals("T")) {
							drawWall(g,rightWall1,Color.GRAY);
							g.drawRect(rightWall1.getX()[0],rightWall1.getY()[0],100,250);
						}else {
							drawWall(g,rightWall1,Color.BLUE);
						}
						g.setColor(Color.GRAY);
						g.fillRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall2.getX()[0],leftWall2.getY()[0],200,250);
						if(!hasKey) {	
							if(maze[row][col-1].equals("K")) {
								g.setColor(Color.YELLOW);
								g.fillRect(325,575,50,10);
								g.setColor(Color.BLACK);
								g.drawRect(325,575,50,10);
							}
						}
					}
					if(maze[row][col-1].equals("0")) {
						g.setColor(Color.GRAY);
						g.fillRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
						g.setColor(Color.BLACK);
						g.drawRect(leftWall1.getX()[0],leftWall1.getY()[0],400,350);
					}
				}
			}catch(Exception e) {
				System.out.println("Index Out of Bounds: Not drawing 3D Maze");
			}
			if(restart) {
				restart();
			}
			repaint();
			if(end) {
				game = false;
			}
		}
	}
	public void setBoard()
	{
		File name = new File("Maze2.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			String[] line;
			int count = 0;
			while( (text=input.readLine())!= null)
			{
				line = text.split("");
				maze[count] = line;
				count++;
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

		setWalls();
	}

	public void setWalls()
	{
		//when you're ready for the 3D part
		int[] c1X={150,550,450,250};
		int[] c1Y={250,250,300,300};
		int[] c2X= {250,450,400,300};
		int[] c2Y={300,300,325,325};
		int[] lw1X = {150,250,250,150};
		int[] lw1Y = {250,300,550,600};
		int[] lw2X = {250,300,300,250};
		int[] lw2Y = {300,325,550,550};
		int[] rw1X = {450,550,550,450};
		int[] rw1Y = {300,250,600,550};
		int[] rw2X = {400,450,450,400};
		int[] rw2Y = {325,300,550,525};
		int[] f1X = {250,450,550,150};
		int[] f1Y = {550,550,600,600};
		int[] f2X = {300,400,450,250};
		int[] f2Y = {525,525,550,550};
		
		ceiling1 = new Ceiling(c1X,c1Y);
		ceiling2 = new Ceiling(c2X,c2Y);
		leftWall1 = new Wall(lw1X,lw1Y);
		leftWall2 = new Wall(lw2X,lw2Y);
		rightWall1 = new Wall(rw1X,rw1Y);
		rightWall2 = new Wall(rw2X,rw2Y);
		floor1 = new Floor(f1X,f1Y);
		floor2 = new Floor(f2X,f2Y);
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			restart = true;
		}
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{
		MazeProgram app=new MazeProgram();
	}
}