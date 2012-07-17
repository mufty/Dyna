package com.mb.dyna.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class WorldGrid {
	private int width = 13;
	private int height = 11;
	private int gridSize = 32;
	private Surface[][] grid;
	private Surface grassTexture;
	private Surface stoneTexture;
	private Surface woodTexture;
	private int maxStones = 32;
	private List<Rectangle> solidBodies;
	
	public WorldGrid() {
		grid = new Surface[width][height];
		grassTexture = new Surface(Gdx.files.internal("data/grass.png"));
		stoneTexture = new Surface(Gdx.files.internal("data/stone.png"));
		stoneTexture.setType(Surface.TYPE_SOLID);
		woodTexture = new Surface(Gdx.files.internal("data/wood.png"));
		woodTexture.setType(Surface.TYPE_SOLID);
		
		for(int i = 0; i < height; i++){
			for(int x = 0; x < width; x++){
				if(i == 0 || x == 0 || x == width - 1 || i == height - 1){
					grid[x][i] = grassTexture;
				}
				
				if(x % 2 != 0 && (i != 0 && i != height - 1) && (i % 2 != 0)){
					grid[x][i] = stoneTexture;
				}
			}
		}
		
		int rndCount = 0;
		Random generator = new Random();
		while(rndCount < maxStones){
			int x = generator.nextInt(width);
			int y = generator.nextInt(height);
			
			if(x % 2 == 0 && y % 2 == 0){
				grid[x][y] = woodTexture;
				rndCount++;
			}
		}
		
		for(int i = 0; i < height; i++){
			for(int x = 0; x < width; x++){
				if(grid[x][i] == null)
					grid[x][i] = grassTexture;
			}
		}
		
		//gen solid bodies
		setSolidBodies(new ArrayList<Rectangle>());
		for(int i = 0; i < height; i++){
			for(int x = 0; x < width; x++){
				if(grid[x][i] != null){
					if(grid[x][i].getType().equals(Surface.TYPE_SOLID)){
						Rectangle r = new Rectangle(gridSize * x, gridSize * i, gridSize, gridSize);
						getSolidBodies().add(r);
					}
				}
			}
		}
	}

	public void update(SpriteBatch batch) {
		for(int i = 0; i < height; i++){
			for(int x = 0; x < width; x++){
				if(grid[x][i] != null){
					batch.draw(grid[x][i], gridSize * x, gridSize * i);
				}
			}
		}
	}
	
	public void dispose(){
		for(int i = 0; i < height; i++){
			for(int x = 0; x < width; x++){
				grid[x][i].dispose();
			}
		}
	}

	public void setSolidBodies(List<Rectangle> solidBodies) {
		this.solidBodies = solidBodies;
	}

	public List<Rectangle> getSolidBodies() {
		return solidBodies;
	}
}
