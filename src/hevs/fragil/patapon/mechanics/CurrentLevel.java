package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.Gdx;

/**
 * A not so stinky factory/singleton class
 * 
 * @author loicg
 *
 */
public class CurrentLevel {
	private static Level instance = null;

	// Prevents direct object instantiation
	private CurrentLevel() {
	}

	static public void setLevel(Level l) {
		instance = l;
	}

	static public Level getLevel() {
		try {
			if (instance == null)
				throw new IllegalStateException("Level has not been set before, you fool!");
		} catch (IllegalStateException e) {
			Gdx.app.error("CurrentLevel", e.getMessage());
			Gdx.app.exit();
		}
		
		return instance;
	}
}
