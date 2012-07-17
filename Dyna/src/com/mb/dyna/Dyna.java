package com.mb.dyna;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mb.dyna.objects.Bomberman;
import com.mb.dyna.objects.WorldGrid;

public class Dyna implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Bomberman player;
	private WorldGrid world;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		camera.setToOrtho(false);
		batch = new SpriteBatch();
		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		player = new Bomberman();
		
		world = new WorldGrid();
	}

	@Override
	public void dispose() {
		batch.dispose();
		player.dispose();
		world.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		world.update(batch);
		player.update(batch, world);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
