package hevs.fragil.patapon.mechanics;

import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.fragil.patapon.drawables.Clouds;
import hevs.fragil.patapon.drawables.Scenery;
import hevs.fragil.patapon.drawables.Frame;
import hevs.fragil.patapon.drawables.Mountains;
import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.physics.Floor;
import hevs.fragil.patapon.physics.Fragment;
import hevs.fragil.patapon.physics.Projectile;
import hevs.fragil.patapon.physics.StickyInfo;
import hevs.fragil.patapon.physics.Tower;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.State;
import hevs.fragil.patapon.units.Unit;

/**
 * Level class to instantiate a new Level. This contains its own 
 * Scenery and other elements particular to this level.
 * For instance, the level is always the same.
 * TODO This class should be able to read files and creates itself in function.
 */
public class Level extends RenderingScreen {
	private Scenery scenery;
	private Floor floor;
	private Frame frame;
	private Sequence sequence;
	private SoundSample heNote, sNote, soNote, yesNote;
	private SoundSample snap, track;
	private Company enemies = new Company();

	private boolean debugActive = false;
	private MusicFlag snapState = MusicFlag.STOPPED;
	private MusicFlag trackState = MusicFlag.TOPLAY;

	DebugRenderer debugRenderer;

	private Vector<Projectile> projectiles = new Vector<Projectile>();
	private Vector<StickyInfo> toJoin = new Vector<StickyInfo>();
	private Vector<PhysicsPolygon> toDisable = new Vector<PhysicsPolygon>();
	private Vector<DrawableObject> toKill = new Vector<DrawableObject>();

	private float stateTime;
	public float sinceLastRythm;

	private Vector3 camera;

	// A world with gravity pointing down. Must be called!
	World world = PhysicsWorld.getInstance();

	public Level() {
	}

	public void add(Projectile o) {
		projectiles.add(o);
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

		scenery = new Scenery(Param.MAP_WIDTH, Param.CAM_HEIGHT, Param.BACKGROUND);
		Mountains.loadFiles();
		Clouds.loadFiles();

		enemies.initEnnemies(2,1,3);

		// Load the sound files
		heNote = new SoundSample("data/music/HE.wav");
		sNote = new SoundSample("data/music/S.wav");
		soNote = new SoundSample("data/music/SO.wav");
		yesNote = new SoundSample("data/music/YES.wav");
		snap = new SoundSample("data/music/loop2.wav");
		track = new SoundSample("data/music/loop1.wav");

		// Create a default map and the floor that belong
		frame = new Frame();
		floor = new Floor(scenery.getWidth());
		sequence = new Sequence();
		Sequence.loadSprites("data/images/drums102x102.png");
		SequenceTimer.loadFiles();

		debugRenderer = new DebugRenderer();
	}

	public void onKeyDown(int keycode) {
		if (keycode == Keys.NUM_1) {
			heNote.play();
			State toDo = sequence.add(Drum.HE, sinceLastRythm);
			PlayerCompany.getCompany().setAction(toDo);
		}
		if (keycode == Keys.NUM_2) {
			sNote.play();
			PlayerCompany.getCompany().setAction(sequence.add(Drum.S, sinceLastRythm));
		}
		if (keycode == Keys.NUM_3) {
			soNote.play();
			PlayerCompany.getCompany().setAction(sequence.add(Drum.SO, sinceLastRythm));
		}
		if (keycode == Keys.NUM_4) {
			yesNote.play();
			PlayerCompany.getCompany().setAction(sequence.add(Drum.YES, sinceLastRythm));
		}
		if (keycode == Keys.D) {
			debugActive = !debugActive;
		}
		if (keycode == Keys.S) {
			switch (snapState) {
			case STOPPED:
				snapState = MusicFlag.TOPLAY;
				break;
			case PLAYING:
				snapState = MusicFlag.TOSTOP;
				break;
			default:
				break;
			}
		}
		if (keycode == Keys.T) {
			switch (trackState) {
			case STOPPED:
				trackState = MusicFlag.TOPLAY;
				break;
			case PLAYING:
				trackState = MusicFlag.TOSTOP;
				break;
			default:
				break;
			}
		}
		if (keycode == Keys.ESCAPE) {
			dispose();
			System.exit(0);
		}
		/* Some manual commands for debug or easy demo */
		// Enble these to play without taking care of rythms
		if (keycode == Keys.A) {
			PlayerCompany.getCompany().setAction(State.ATTACK);
		}
		if (keycode == Keys.M) {
			PlayerCompany.getCompany().setAction(State.WALK);
		}
		if (keycode == Keys.R) {
			PlayerCompany.getCompany().setAction(State.RETREAT);
		}
		// Enable these to navigate easily in scenery with camera
		if (keycode == Keys.LEFT) {
			scenery.addManualOffset(-500);
		}
		if (keycode == Keys.RIGHT) {
			scenery.addManualOffset(500);
		}
		if (keycode == Keys.CONTROL_RIGHT){
			scenery.centerCamera();
		}
	}

	public void onGraphicRender(GdxGraphics g) {
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		
		// process camera position inside map limits
		camera = scenery.cameraProcess(PlayerCompany.getCompany(), enemies);

		// apply camera position
		//TODO play with scale to play with zoom :D enjoy your pain
		g.moveCamera(camera.x, 0, Param.MAP_WIDTH, Param.MAP_HEIGHT);
		
		if (debugActive) {
			g.clear();
			debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera().combined);
		} 
		else {
			// clear the screen with the decor background
			g.clear(scenery.getBackground());
		}
		
		// stick flying objects
		createJoints();

		// update objects
		stepProjectiles(g);
		stepFragments();
		rythm();
		action();
		sequence.step();
		killUnits();
		destroyObjects();		
		
		if(!debugActive){
			// display help
			g.drawStringCentered(800, "Fever : " + sequence.getFever());
			g.drawStringCentered(780, "T to disable/enable track");
			g.drawStringCentered(760, "S to disable/enable snap");
			g.drawStringCentered(740, "D to disable/enable debug mode");
			g.drawStringCentered(720, "Num(1, 2, 3, 4) = Note(He, S, So, Yes)");
			g.drawStringCentered(700, "Use A, M, R for Attack, Walk, Retreat and arrows to move camera");
			
			// display objects
			floor.draw(g);
			scenery.draw(g);
			frame.draw(g);
			sequence.draw(g);
			PlayerCompany.getCompany().draw(g);
			enemies.draw(g);
			drawProjectiles(g);
		}
		stateTime += Gdx.graphics.getDeltaTime();
	}

	private void stepFragments() {
		Vector<DrawableObject> toDestroy = new Vector<DrawableObject>();
		
		for (DrawableObject d : scenery.toDraw) {
			if(d instanceof Fragment){
				if(((Fragment)d).step()){
					toDestroy.add(d);
				}
			}
			
		}
		
		for (DrawableObject d : toDestroy) {
			((Fragment)d).destroy();
			scenery.toDraw.remove(d);
		}
		
		toDestroy.removeAllElements();
		
	}

	private void destroyObjects() {
		Vector<DrawableObject> toDestroy = new Vector<DrawableObject>();
		Vector<DrawableObject> newFragments = new Vector<DrawableObject>();
		for (DrawableObject d : scenery.toDraw) {
			int h = 0;
			int x = 0;
			if(d instanceof Tower){
				if(((Tower)d).isExploded()){
					x = ((Tower)d).getPos();
					h = ((Tower)d).getHeight();
					((Tower)d).destroy();
					toDestroy.add(d);
				}
				//create h/20 lines of 5 bricks
				for(int i = 0 ; i < h ; i++){
					for(int j = 0 ; j < 5 ; j++){
						newFragments.add(new Fragment(x - 50 + j*20, Param.FLOOR_DEPTH + i*20, 20, 20));
					}
				}
			}
		}
		for (DrawableObject d : toDestroy) {
			scenery.toDraw.remove(d);
		}
		scenery.toDraw.addAll(newFragments);
		toDestroy.removeAllElements();
	}

	private void killUnits() {
		//Remove heroes
		Company c = PlayerCompany.getCompany();
		for (Section s : c.sections) {
			for (Unit u : s.units) {
				if (u.isDead()) {
					toKill.add(u);
				}
			}
			//remove the section if all of its units are killed
			if(toKill.containsAll(s.units)){
				toKill.add(s);
			}
		}
		//remove every object to kill
		for (Object o : toKill) {
			if(o instanceof Unit){
				((Unit) o).destroyBox();
				for (Section s : c.sections) {
					if (s.units.contains(o)) {
						s.units.remove(o);
					}
				}
			}
			else if(o instanceof Section){
				c.remove((Section) o);
			}
		}
		toKill.removeAllElements();

		//Do the same to remove enemies
		c = enemies;
		for (Section s : c.sections) {
			for (Unit u : s.units) {
				if (u.isDead()) {
					toKill.add(u);
				}
			}
			if(toKill.containsAll(s.units)){
				toKill.add(s);
			}
		}
		for (Object o : toKill) {
			if(o instanceof Unit){
				((Unit) o).destroyBox();
				for (Section s : c.sections) {
					if (s.units.contains(o)) {
						s.units.remove(o);
					}
				}
			}
			else if(o instanceof Section){
				c.remove((Section) o);
			}
		}
		toKill.removeAllElements();
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

	private void changeTrack() {
		switch (trackState) {
		case TOSTOP:
			track.stop();
			trackState = MusicFlag.STOPPED;
			break;
		case TOPLAY:
			track.loop();
			trackState = MusicFlag.PLAYING;
			break;
		default:
			break;
		}
		switch (snapState) {
		case TOSTOP:
			snap.stop();
			snapState = MusicFlag.STOPPED;
			break;
		case TOPLAY:
			snap.loop();
			snapState = MusicFlag.PLAYING;
			break;
		default:
			break;
		}
	}

	private void action() {
		SequenceTimer.run(PlayerCompany.getCompany(), sequence.getFever());
		EnemiesTimer.run(enemies);
	}

	public Company getEnemies() {
		return enemies;
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
		for (Iterator<Projectile> iter = projectiles.iterator(); iter.hasNext();) {
			Projectile projectile = iter.next();

			projectile.step(Gdx.graphics.getDeltaTime());

			// If a ball is not visible anymore, it should be destroyed
			if (projectile.shouldBeDestroyed()) {
				// Mark the ball for deletion when possible
				projectile.destroy();
				// Remove the ball from the collection as well
				iter.remove();
			}
		}
	}
	private void drawProjectiles(GdxGraphics g){
		for (Iterator<Projectile> iter = projectiles.iterator(); iter.hasNext();) {
			Projectile projectile = iter.next();

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

	public float getStateTime() {
		return stateTime;
	}

	public Scenery getDecor() {
		return scenery;
	}
}
