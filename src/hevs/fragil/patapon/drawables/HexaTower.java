package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class HexaTower extends Tower {

	public HexaTower(int x, int h) {
		super(x, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadFiles() {
		basis1 = new SpriteSheet("data/images/tower_hexa_basis1.png", 1, 1, 1, false, PlayMode.NORMAL);
		basis2 = new SpriteSheet("data/images/tower_hexa_basis2.png", 1, 1, 1, false, PlayMode.NORMAL);
		head = new SpriteSheet("data/images/tower_hexa_head.png", 1, 1, 1, false, PlayMode.NORMAL);
	}

}
