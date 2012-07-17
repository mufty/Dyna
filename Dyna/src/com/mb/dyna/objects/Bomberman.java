package com.mb.dyna.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mb.dyna.controls.Controller;

public class Bomberman {
	private Texture walkWhiteLeftTexture;
	private Texture walkWhiteRightTexture;
	private Texture walkWhiteUpTexture;
	private Texture walkWhiteDownTexture;
	private Rectangle bomberman;
	private Controller input;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	private Animation walkDownAnimation;
	private Animation walkUpAnimation;
	private TextureRegion[] walkLeftFrames;
	private TextureRegion[] walkRightFrames;
	private TextureRegion[] walkDownFrames;
	private TextureRegion[] walkUpFrames;
	private TextureRegion currentFrame;
	private float deltaTime;
	private boolean collidesUp;
	private boolean collidesDown;
	
	public Bomberman() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		input = new Controller();
		Gdx.input.setInputProcessor(input);
		
		walkWhiteLeftTexture = new Texture(Gdx.files.internal("data/bomberman_walk-left.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkWhiteLeftTexture, walkWhiteLeftTexture.getWidth() / 3, walkWhiteLeftTexture.getHeight() / 1);
		walkLeftFrames = new TextureRegion[3];
		int index = 0;
        for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 3; j++) {
                	walkLeftFrames[index++] = tmp[i][j];
                }
        }
		walkLeftAnimation = new Animation(0.15f, walkLeftFrames);
		
		walkWhiteRightTexture = new Texture(Gdx.files.internal("data/bomberman_walk-right.png"));
		tmp = TextureRegion.split(walkWhiteRightTexture, walkWhiteRightTexture.getWidth() / 3, walkWhiteRightTexture.getHeight() / 1);
		walkRightFrames = new TextureRegion[3];
		index = 0;
        for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 3; j++) {
                	walkRightFrames[index++] = tmp[i][j];
                }
        }
		walkRightAnimation = new Animation(0.15f, walkRightFrames);
		
		walkWhiteDownTexture = new Texture(Gdx.files.internal("data/bomberman_walk-down.png"));
		tmp = TextureRegion.split(walkWhiteDownTexture, walkWhiteDownTexture.getWidth() / 3, walkWhiteDownTexture.getHeight() / 1);
		walkDownFrames = new TextureRegion[3];
		index = 0;
        for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 3; j++) {
                	walkDownFrames[index++] = tmp[i][j];
                }
        }
		walkDownAnimation = new Animation(0.15f, walkDownFrames);
		
		walkWhiteUpTexture = new Texture(Gdx.files.internal("data/bomberman_walk-up.png"));
		tmp = TextureRegion.split(walkWhiteUpTexture, walkWhiteUpTexture.getWidth() / 3, walkWhiteUpTexture.getHeight() / 1);
		walkUpFrames = new TextureRegion[3];
		index = 0;
        for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 3; j++) {
                	walkUpFrames[index++] = tmp[i][j];
                }
        }
		walkUpAnimation = new Animation(0.15f, walkUpFrames);
		
		bomberman = new Rectangle();
		bomberman.x = w / 2;
		bomberman.y = h / 2;
		bomberman.width = 21;
		bomberman.height = 32;
		
		deltaTime = 0f;
	}

	public void dispose() {
		walkWhiteLeftTexture.dispose();
		walkWhiteRightTexture.dispose();
		walkWhiteDownTexture.dispose();
		walkWhiteUpTexture.dispose();
	}

	public void update(SpriteBatch batch, WorldGrid world) {
		deltaTime += Gdx.graphics.getDeltaTime();
		if(input.getLastState() == 2){
			currentFrame = walkDownAnimation.getKeyFrame(0, true);
		} else if(input.getLastState() == 3){ 
			currentFrame = walkRightAnimation.getKeyFrame(0, true);
		} else if(input.getLastState() == 4){ 
			currentFrame = walkUpAnimation.getKeyFrame(0, true);
		} else {
			currentFrame = walkLeftAnimation.getKeyFrame(0, true);
		}
		
		//checkCollision(world);
		
		if(input.isDown() && isFreeToMoveDown(world)){
			bomberman.y = bomberman.y - 1;
			currentFrame = walkDownAnimation.getKeyFrame(deltaTime, true);
		}
		
		if(input.isUp() && isFreeToMoveUp(world)){
			bomberman.y = bomberman.y + 1;
			currentFrame = walkUpAnimation.getKeyFrame(deltaTime, true);
		}
		
		if(input.isLeft() && isFreeToMoveLeft(world)){
			bomberman.x = bomberman.x - 1;
			currentFrame = walkLeftAnimation.getKeyFrame(deltaTime, true);
		}
		
		if(input.isRight() && isFreeToMoveRight(world)){
			bomberman.x = bomberman.x + 1;
			currentFrame = walkRightAnimation.getKeyFrame(deltaTime, true);
		}
		
		batch.draw(currentFrame, bomberman.x, bomberman.y);
	}

	/*private void checkCollision(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman)){
				if(input.isDown()){
					this.collidesDown = true;
				}
			}
		}
	}*/

	private boolean isFreeToMove(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman)){
				return false;
			}
		}
		return true;
	}
	
	private boolean isFreeToMoveDown(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman) && (r.y <= bomberman.y + r.height || !collidesDown)){
				this.collidesUp = true;
				this.collidesDown = false;
				return false;
			}
		}
		collidesUp = false;
		collidesDown = false;
		return true;
	}
	
	private boolean isFreeToMoveUp(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman) && (r.y >= bomberman.y + bomberman.height || !collidesUp)){
				this.collidesUp = false;
				this.collidesDown = true;
				return false;
			}
		}
		collidesUp = false;
		collidesDown = false;
		return true;
	}
	
	private boolean isFreeToMoveLeft(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman) && r.x < bomberman.x){
				return false;
			}
		}
		return true;
	}
	
	private boolean isFreeToMoveRight(WorldGrid world) {
		for(Rectangle r : world.getSolidBodies()){
			if(r.overlaps(bomberman) && r.x < bomberman.x){
				return false;
			}
		}
		return true;
	}
}
