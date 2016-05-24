package hevs.fragil.patapon.music;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;

public class Note implements DrawableObject {
	// delay between tempo and note
	private Drum drum;
	private static int feverScore = 0;
	private static float remainingPause = 0;
	// margins of acceptance
	public boolean outOfRythm = false;
	private static SpriteSheet drums;

	public Note(Drum d, float dt) {
		this.drum = d;
		// to have values in ms
		dt *= 1000;
		
		if (dt < 1) {
			juge(0);
			System.out.println(" ms ! WOW ! LIKE A BOSS !");
		} else if (dt < Param.MUSIC_BAR / 2) {
			juge(dt);
			System.out.println(" ms too late");
		} else if (dt > Param.MUSIC_BAR / 2) {
			juge(Param.MUSIC_BAR - dt);
			System.out.println(" ms too early");
		}
		
	}

	// return a value depending of the user rythm precision
	public void juge(float delay) {
		// bonus score if well done
		System.out.print(getDrum().toString() + " ");
		if (delay < Param.PERFECT) {
			System.out.print("PERFECT : " + Math.round(delay));
			feverScore += 15;
		} else if (delay < Param.EXCELLENT) {
			System.out.print("EXCELLENT : " + Math.round(delay));
			feverScore += 10;
		} else if (delay < Param.GOOD) {
			System.out.print("GOOD : " + Math.round(delay));
			feverScore += 5;
		} else if (delay < Param.PASS) {
			System.out.print("PASS : " + Math.round(delay));
			feverScore += 1;
		} else {
			System.out.print("Follow the rythm !");
			clearFever();
		}
	}

	@Override
	public void draw(GdxGraphics g) {
		drums.drawFrame(getDrum().ordinal(), Param.WIN_WIDTH / 2, 600);
	}

	public void draw(GdxGraphics g, int x) {
		drums.drawFrame(getDrum().ordinal(), x, 600);
	}

	public Drum getDrum() {
		return drum;
	}

	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public static void loadSprites(String url) {
		drums = new SpriteSheet(url, 1, 4, 0.2f);
	}

	/**
	 * @return value between 0 and 100
	 */
	public static int getFever() {
		return Math.min(feverScore, 100);
	}

	public static void clearFever() {
		feverScore = 0;
	}

	public static void startForbiddenTime() {
		remainingPause = 2;
	}

	public static void updateForbiddenTime(float dt) {
		if (remainingPause > 0) {
			remainingPause -= dt;
		} else {
			remainingPause = 0;
		}
	}
}
