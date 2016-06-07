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
import hevs.fragil.patapon.drawables.Frame;
import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.physics.Floor;
import hevs.fragil.patapon.physics.Projectile;
import hevs.fragil.patapon.physics.StickyInfo;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public class Level extends RenderingScreen {
	private Decor decor;
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

	private Vector<Projectile> flyingOjects = new Vector<Projectile>();
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

		decor = new Decor(Param.MAP_WIDTH, Param.CAM_HEIGHT, Param.BACKGROUND);

		enemies.initEnnemies(2, 3, 4);

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
		Sequence.loadSprites("data/images/drums102x102.png");
		SequenceTimer.loadFiles();

		debugRenderer = new DebugRenderer();
	}

	public void onKeyDown(int keycode) {
		if (keycode == Keys.NUM_1) {
			heNote.play();
			State toDo = sequence.add(Drum.HE, sinceLastRythm);
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
		if (keycode == Keys.A) {
			PlayerCompany.getInstance().setAction(State.ATTACK);
		}
		if (keycode == Keys.M) {
			PlayerCompany.getInstance().setAction(State.WALK);
		}
		if (keycode == Keys.R) {
			PlayerCompany.getInstance().setAction(State.RETREAT);
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
		// Display camera and company informations (only for debug)
		if (keycode == Keys.C) {
			System.out.println("Camera pos = " + camera.x);
			System.out.println("Company pos = " + PlayerCompany.getInstance().getHeroes().getPosition());
		}
		if (keycode == Keys.ESCAPE) {
			dispose();
			System.exit(0);
		}
	}

	public void onGraphicRender(GdxGraphics g) {
		// process camera position inside map limits
		if(enemies.isEmpty())
			camera = decor.cameraProcess(PlayerCompany.getInstance().getHeroes());			
		else
			camera = decor.cameraProcess(PlayerCompany.getInstance().getHeroes(), enemies);

		// apply camera position
		//TODO play with scale tp play with zoom :D enjoy your pain
//		g.zoom(camera.z);
		g.moveCamera(camera.x, 0, Param.MAP_WIDTH, Param.MAP_HEIGHT);
		
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		
		if (debugActive) {
			g.clear();
			debugRenderer.render(PhysicsWorld.getInstance(), g.getCamera().combined);
			
			// stick flying objects
			createJoints();

			// update objects
			stepProjectiles(g);
			rythm();
			action();
			sequence.step();
			killUnits();
		} else {
			// clear the screen with the decor background
			g.clear(decor.getBackground());

			// stick flying objects
			createJoints();

			// update objects
			stepProjectiles(g);
			rythm();
			action();
			sequence.step();
			killUnits();
			PlayerCompany.getInstance().getHeroes().aiMove();
			enemies.aiMove();

			// display help
			g.drawStringCentered(800, "Fever : " + sequence.getFever());
			g.drawStringCentered(780, "T to disable/enable track");
			g.drawStringCentered(760, "S to disable/enable snap");
			g.drawStringCentered(740, "D to disable/enable debug mode");

			// display objects
			floor.draw(g);
			decor.draw(g);
			frame.draw(g);
			sequence.draw(g);
			PlayerCompany.getInstance().getHeroes().draw(g);
			enemies.draw(g);
		}
		stateTime += Gdx.graphics.getDeltaTime();
	}

	private void killUnits() {
		// remove heroes
		Company c = PlayerCompany.getInstance().getHeroes();
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

		// remove enemies
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
		SequenceTimer.run(PlayerCompany.getInstance().getHeroes(), sequence.getFever());
	}

	public Company getEnnemies() {
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

	public float getStateTime() {
		return stateTime;
	}
}
