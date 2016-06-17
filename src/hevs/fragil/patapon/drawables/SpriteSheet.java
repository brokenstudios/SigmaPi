package hevs.fragil.patapon.drawables;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * SpriteSheet management class to provide easy drawing and initialization
 * @author Loïc Gillioz
 */
public class SpriteSheet {
	Animation animation;
	Texture sheet;
	Sprite[] sprites;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	
	float frameDuration;
	float preAnimDelay = 0f;
	private boolean flipped = false;
	
	/**
	 * Initialize a new Spritesheet
	 * @author Loïc Gillioz
	 * @param url : the image location
	 * @param cols : number of cols in your spritesheet image
	 * @param rows : number of rows in your spritesheet image
	 * @param frameDuration : duration of each frame of the animation
	 * @param flipped : true if flipped
	 * @param looping : true if looping animation
	 */
	public SpriteSheet(String url, int cols, int rows, float frameDuration, boolean flipped, PlayMode playMode){
		this.flipped = flipped;
		this.frameDuration = frameDuration;
		
		sheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
        sprites = new Sprite[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sprites[index++] = new Sprite(tmp[i][j]);
            }
        }

        animation = new Animation(frameDuration, sprites);
        spriteBatch = new SpriteBatch();
        
        switch(playMode){
        case LOOP 			:	animation.setPlayMode(PlayMode.LOOP);
								break;
        case LOOP_PINGPONG 	:	animation.setPlayMode(PlayMode.LOOP);
								break;
		case LOOP_RANDOM 	:	animation.setPlayMode(PlayMode.LOOP);
								break;
		case LOOP_REVERSED 	:	animation.setPlayMode(PlayMode.LOOP);
								break;
		case NORMAL 		:	animation.setPlayMode(PlayMode.LOOP);
								break;
		case REVERSED 		:	animation.setPlayMode(PlayMode.LOOP);
								break;
        }
	}
	/**
	 * Draws a frame at given position
	 * @param frameIndex : index of the frame in the spritesheet (left to right, up to down)
	 * @param posX : x location in pixels
	 * @param posY : y location in pixels
	 */
	public void drawFrame(int frameIndex, int posX, int posY){
		spriteBatch.begin();
		Sprite tmp = sprites[frameIndex];
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX, posY);
		}
		tmp.setRotation(0);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	/**
	 * Draws a special walk animation for unit bodies, simulates a walk effect by applying rotations
	 * @param walkIndex : index of the walk animation from the legs
	 * @param spriteNumber : frame to draw (in the body spritesheet)
	 * @param posX : draw location in x 
	 * @param posY : draw location in y
	 * @param originY : y rotation center
	 */
	public void drawWalkAnimation(int walkIndex, int spriteNumber, float posX, float posY){
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		float angle = 0f;
		switch(walkIndex){
			case 0 : 	angle = -3;  
						posY -= 3;
						break;
			case 1 : 	angle = 0;
						break;
			case 2 : 	angle = 3;
						posY -= 3;
						break;
			case 3 : 	angle = 0;
						break;
		}	
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32-96, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX-96, posY);
		}
		tmp.setRotation(angle);
//		if(flipped && tmp.isFlipX() == false)
//			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	/**
	 * Draws a rotated frame with a given angle
	 * @param spriteNumber : index of the frame in the spritesheet table
	 * @param angle : rotation angle applied in radians
	 * @param posX : draw location in x 
	 * @param posY : draw location in y
	 */
	public void drawRotatedFrame(int spriteNumber, float angle, float posX, float posY){
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		Vector2 offset = new Vector2();
//		offset.set(Math.cos(angle), Math.sin(angle));
		if(flipped){
			tmp.setPosition(posX-32-96, posY);
			tmp.setOrigin(96+32, 58);

		}
		else {
			tmp.setPosition(posX-96, posY);
			tmp.setOrigin(96, 58);
		}
		tmp.setRotation((float)Math.toDegrees(angle));
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	public int drawAllFrames(float time, float posX, float posY){
		currentFrame = animation.getKeyFrame(time, true);
		TextureRegion[] a = animation.getKeyFrames();
		int index = java.util.Arrays.asList(a).indexOf(currentFrame);
		spriteBatch.begin();
		Sprite tmp = sprites[index];
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32-96, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX-96, posY);
		}
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
		return index;
	}
	public int drawAllFrames(float time, Vector2 pos){
		return drawAllFrames(time, pos.x, pos.y);
	}
	public int drawFrames(float time, int startIndex, int nbFrames, float posX, float posY){
		currentFrame = animation.getKeyFrame(time, true);
		TextureRegion[] a = animation.getKeyFrames();
		int index = java.util.Arrays.asList(a).indexOf(currentFrame);
		index = index % nbFrames;
		index += startIndex;
		spriteBatch.begin();
		Sprite tmp = sprites[index];
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32-96, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX-96, posY);
		}
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
		return index;
	}
	public int drawFrames(float stateTime, int startIndex, int nbFrames, Vector2 pos) {
		return drawFrames(stateTime, startIndex, nbFrames, pos.x, pos.y);
	}
	public void drawFrameAlpha(int frameIndex, int posX, int posY, float alpha) {
		spriteBatch.begin();
		Sprite tmp = sprites[frameIndex];
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32-96, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX-96, posY);
		}
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch, alpha);
		spriteBatch.end();
	}
	public void drawRotatedFrameAlpha(int spriteNumber, float angle, float posX, float posY, float alpha) {
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		if(flipped){
			tmp.setOrigin(96+32, 58);
			tmp.setPosition(posX-32-96, posY);

		}
		else {
			tmp.setOrigin(96, 58);
			tmp.setPosition(posX-96, posY);
		}
		//rotate the sprite around the left down corner
		tmp.setRotation((float)Math.toDegrees(angle));
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
//		//location of the left down corner depends of offsets :
//		float offsetX = 32 - tmp.getWidth()/2;
//		float offsetY = tmp.getHeight()/2 - 38;
//		Vector2 offset = new Vector2(offsetX, offsetY);
//		Vector2 pos = new Vector2(posX, posY);
//		offset.rotateRad(angle);
//		pos.add(offset);
		tmp.draw(spriteBatch, alpha);
		spriteBatch.end();
	}
	public float getFrameDuration() {
		return frameDuration;
	}
	public float getPreAnimDelay() {
		return preAnimDelay;
	}
	public boolean finished(float stateTime) {
		return animation.isAnimationFinished(stateTime);
	}
	public void drawRotatedFrameAlpha(int spriteNumber, float angle, Vector2 pos, float alpha){
		drawRotatedFrameAlpha(spriteNumber, angle, pos.x, pos.y, alpha);
	}
}
