package hevs.fragil.patapon.mechanics;

import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.fragil.patapon.drawables.Frame;
import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.physics.Floor;
import hevs.fragil.patapon.physics.Projectile;
import hevs.fragil.patapon.physics.StickyInfo;

public class Level extends RenderingScreen {
	private Decor decor;
	private Floor floor;
	private Frame frame;
	private Sequence sequence;
	private SoundSample heNote, sNote, soNote, yesNote;
	private SoundSample snap, track;

	private boolean debugActive = false;
	// -2:stop, -1:stopped, 1:playing, 2:activate
	private int snapState = -1;
	private int trackState = 2;

	DebugRenderer debugRenderer;

	private Vector<Projectile> flyingOjects = new Vector<Projectile>();
	private Vector<StickyInfo> toJoin = new Vector<StickyInfo>();
	private Vector<PhysicsPolygon> toDisable = new Vector<PhysicsPolygon>();

	private float stateTime;
	public float sinceLastRythm;

	// A world with gravity pointing down. Must be called!
	World world = PhysicsWorld.getInstance();

	public Level() {
	}

	public void add(Projectile o) {
		flyingOjects.add(o);
	}

	@Override
	public void dispose() {
		super.dispose();
		heNote.dispose();
		sNote.dispose();
		soNote.dispose();
		yesNote.dispose();
		track.dispose();
		snap.dispose();
	}

	@Override
	public void onInit() {
		PhysicsWorld.getInstance();
		CurrentLevel.setLevel(this);
		
		decor = new Decor(Param.MAP_WIDTH, Param.WIN_HEIGHT, Param.Type3);
		PlayerCompany.getInstance().initRandomCompany(3, 3, 4);

		// Load the sound files
		heNote = new SoundSample("data/music/HE.wav");
		sNote = new SoundSample("data/music/S.wav");
		soNote = new SoundSample("data/music/SO.wav");
		yesNote = new SoundSample("data/music/YES.wav");
		snap = new SoundSample("data/music/loop2.wav");
		track = new SoundSample("data/music/loop1.wav");

		// Create a default map and the floor that belong
		frame = new Frame();
		floor = new Floor(decor.getWidth());
		sequence = new Sequence();
		sequence.loadSprites("data/images/drums102x102.png");
		
		debugRenderer = new DebugRenderer();
	}

	public void onKeyDown(int keycode) {
		if (keycode == Keys.NUM_1) {
			heNote.play();
			Action toDo = sequence.add(Drum.HE, sinceLastRythm);
			PlayerCompany.getInstance().setAction(toDo);
		}
		if (keycode == Keys.NUM_2) {
			sNote.play();
			PlayerCompany.getInstance().setAction(sequence.add(Drum.S, sinceLastRythm));
		}
		if (keycode == Keys.NUM_3) {
			soNote.play();
			PlayerCompany.getInstance().setAction(sequence.add(Drum.SO, sinceLastRythm));
		}
		if (keycode == Keys.NUM_4) {
			yesNote.play();
			PlayerCompany.getInstance().setAction(sequence.add(Drum.YES, sinceLastRythm));
		}
		if (keycode == Keys.D) {
			debugActive = !debugActive;
		}
		if (keycode == Keys.S) {
			switch (snapState) {
			case -1:
				snapState = 2;
				break;
			case 1:
				snapState = -2;
				break;
			}
		}
		if (keycode == Keys.T) {
			switch (trackState) {
			case -1:
				trackState = 2;
				break;
			case 1:
				trackState = -2;
				break;
			}
		}
		if(keycode == Keys.ESCAPE){
			dispose();
			System.exit(0);
		}
	}

	public void onGraphicRender(GdxGraphics g) {
		// clear the screen with the decor background
		g.clear(decor.getBackground());
		
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		// press d for debug
		if (debugActive)
			debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera().combined);

		// stick flying objects
		createJoints();
		
		// move objects
		stepProjectiles(g);
		rythm();
		action();
		sequence.step();
		
		//display help
		g.drawStringCentered(800, "Fever : " + sequence.getFever());
		g.drawStringCentered(780, "T to disable/enable track");
		g.drawStringCentered(760, "S to disable/enable snap");
		g.drawStringCentered(740, "D to disable/enable debug mode");
		
		//display objects
		floor.draw(g);
		decor.draw(g);
		frame.draw(g);
		sequence.draw(g);
		
		PlayerCompany.getInstance().draw(g, stateTime);

		stateTime += Gdx.graphics.getDeltaTime();
	}

	public void createWeldJoint(StickyInfo si) {
		toJoin.add(si);
	}

	public void disable(PhysicsPolygon p) {
		toDisable.add(p);
	}

	private void rythm() {
		sinceLastRythm += Gdx.graphics.getDeltaTime();
		
		if (sinceLastRythm >= 0.5) {
			// every 500ms			
			changeTrack();
			sinceLastRythm -= 0.5f;
			frame.toggle();
		}
	}
	private void changeTrack(){
		switch (trackState) {
		case -2:
			track.stop();
			trackState = -1;
			break;
		case 2:
			track.loop();
			trackState = 1;
			break;
		}
		switch (snapState) {
		case -2:
			snap.stop();
			snapState = -1;
			break;
		case 2:
			snap.loop();
			snapState = 1;
			break;
		}
	}
	private void action() {
		ActionTimer.run(PlayerCompany.getInstance().getHeroes(), sequence.getFever());
	}

	private void createJoints() {
		while (toJoin.size() > 0) {
			// get last element and delete it
			StickyInfo si = toJoin.remove(0);
			// create a new joint
			WeldJointDef wjd = new WeldJointDef();
			wjd.bodyA = si.bodyA;
			wjd.bodyB = si.bodyB;
			wjd.referenceAngle = si.bodyB.getAngle() - si.bodyA.getAngle();
			wjd.initialize(si.bodyA, si.bodyB, PhysicsConstants.coordPixelsToMeters(si.anchor));
			PhysicsWorld.getInstance().createJoint(wjd);
		}
	}

	private void stepProjectiles(GdxGraphics g) {
		// Should be used like that
		for (Iterator<Projectile> iter = flyingOjects.iterator(); iter.hasNext();) {
			Projectile projectile = iter.next();

			projectile.step(Gdx.graphics.getDeltaTime());
			projectile.draw(g);

			// If a ball is not visible anymore, it should be destroyed
			if (projectile.shouldBeDestroyed()) {
				// Mark the ball for deletion when possible
				projectile.destroy();
				// Remove the ball from the collection as well
				iter.remove();
			}
		}
	}
}
