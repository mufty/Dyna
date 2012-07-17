package com.mb.dyna.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class Controller implements InputProcessor {
	private boolean down;
	private boolean up;
	private boolean left;
	private boolean right;
	private int lastState = 1;
	private int initTouchX;
	private int initTouchY;

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == 20){
			setDown(true);
		} else if (keycode == 19){
			setUp(true);
		} else if (keycode == 22){
			setRight(true);
		}  else if (keycode == 21){
			setLeft(true);
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == 20){
			setDown(false);
		} else if (keycode == 19){
			setUp(false);
		} else if (keycode == 22){
			setRight(false);
		}  else if (keycode == 21){
			setLeft(false);
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		initTouchX = x;
		initTouchY = y;
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		setRight(false);
		setLeft(false);
		setDown(false);
		setUp(false);
		initTouchX = 0;
		initTouchY = 0; 
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if(initTouchY == 0 && initTouchX == 0)
			return false;
		
		setRight(false);
		setLeft(false);
		setDown(false);
		setUp(false);
		
		if(initTouchX < x - 30){
			setRight(true);
			setLeft(false);
		}
		
		if(initTouchX > x + 30){
			setLeft(true);
			setRight(false);
		}

		if(initTouchY < y - 30){
			setUp(false);
			setDown(true);
		}
			
		if(initTouchY > y + 30){
			setDown(false);
			setUp(true);
		}
		
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDown(boolean down) {
		if(!down)
			setLastState(2);
		this.down = down;
	}

	public boolean isDown() {
		return down;
	}

	public void setUp(boolean up) {
		if(!up)
			setLastState(4);
		this.up = up;
	}

	public boolean isUp() {
		return up;
	}

	public void setLeft(boolean left) {
		if(!left)
			setLastState(1);
		this.left = left;
	}

	public boolean isLeft() {
		return left;
	}

	public void setRight(boolean right) {
		if(!right)
			setLastState(3);
		this.right = right;
	}

	public boolean isRight() {
		return right;
	}

	public void setLastState(int lastState) {
		this.lastState = lastState;
	}

	public int getLastState() {
		return lastState;
	}

}
