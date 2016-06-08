package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.Gdx;

/**
 * Singleton pattern containing the current level
 * Level alterable by the {@code setLevel(Level l)} method.
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
