package hevs.fragil.patapon.accessories;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.EditorObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;

public abstract class TowerImage implements EditorObject{
	private int x;
	private int h;
	protected SpriteSheet basis1;
	protected SpriteSheet basis2;
	protected SpriteSheet head;
	
	public TowerImage(int x, int h){
		this.h = h;
		this.x = x;
		loadFiles();
	}

	@Override
	public void draw(GdxGraphics g) {
		for(int i = 0 ; i < h ; i++){
			if(i%2 == 0)
				basis1.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2), Param.FLOOR_DEPTH + i*20);
			else
				basis2.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2), Param.FLOOR_DEPTH + i*20);
		}
		head.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2), Param.FLOOR_DEPTH + h*20);
		
	}
	@Override
	public boolean isVisible(GdxGraphics g, float objectPos) {
		return true;
	}

	public abstract void loadFiles();
	
	@Override
	public void place(int x) {
		this.x = x;
	}

	@Override
	public int getZ() {
		return 3;
	}
	
}
