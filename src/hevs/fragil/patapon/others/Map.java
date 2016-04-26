package hevs.fragil.patapon.others;

import java.util.Timer;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import hevs.fragil.patapon.drawables.Arrow;
import hevs.fragil.patapon.drawables.BlinkingBorder;
import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.music.RythmTimer;
import hevs.fragil.patapon.units.Archer;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Shield;
import hevs.fragil.patapon.units.Spearman;
import hevs.fragil.patapon.units.Unit;
import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.gdx2d.lib.physics.DebugRenderer;
import hevs.gdx2d.lib.physics.PhysicsWorld;

public class Map extends PortableApplication{
	private int width;
	private static Vector<Company> companies = new Vector<Company>();
	private static SoundSample heNote, sNote, soNote, yesNote;
	private static Vector<SoundSample> tracks = new Vector<SoundSample>();
	private static Vector<DrawableObject> flyingOjects = new Vector<DrawableObject>();
	private static SoundSample snap;
	private static BlinkingBorder f;
	private static Timer tempoTimer = new Timer();
	private static Timer actionTimer = new Timer();
	DebugRenderer debugRenderer;
	
	public float stateTime;

	public static void main(String[] args) {
		new Map(1000);
		getCompanies().add(randomCompany(4,3,3));
	}
	public Map(int w){
		this(w, 900);
	}
	public Map(int w, int h){
		super(1500, h);
		this.width = w;
	}
	public static int getNbTracks(){
		return tracks.size();
	}
	public static void nextTrack(){
		for (SoundSample track :  tracks)
			track.stop();
		tracks.elementAt(RythmTimer.soundEnable).loop();
	}
	public static void snapToggle(){
		if(RythmTimer.snapEnable)snap.loop();
		else snap.stop();
	}
	public static void add (Company c){
		getCompanies().add(c);
	}
	public static void add (DrawableObject o){
		flyingOjects.add(o);
	}
	@Override
	public void onDispose() {
		super.onDispose();
		heNote.dispose();
		sNote.dispose();
		soNote.dispose();
		yesNote.dispose();
		for (SoundSample track : tracks) {
			track.dispose();
		}
		snap.dispose();
	}
	@Override
	public void onInit() {
		setTitle("Test Map Patapons H-E-S! - by FraGil 2016");
		//Load the sound files
		heNote = new SoundSample("data/music/HE.wav");
		sNote = new SoundSample("data/music/S.wav");
		soNote = new SoundSample("data/music/SO.wav");
		yesNote = new SoundSample("data/music/YES.wav");

		snap = new SoundSample("data/music/loop2.wav");
		
		tracks.add(new SoundSample("data/music/loop1.wav"));
		tracks.add(new SoundSample("data/music/loop3.wav"));
		tracks.add(new SoundSample("data/music/loop4.wav"));
		tracks.add(new SoundSample("data/music/loop5.wav"));
		tracks.add(new SoundSample("data/music/loop6.wav"));
		
		tempoTimer.scheduleAtFixedRate(new RythmTimer(), 0, Param.MUSIC_BAR);
		actionTimer.scheduleAtFixedRate(new ActionTimer(), 0, Param.ACTIONS_BAR);

		//Load the image files
		Unit.setLegsSprite("data/images/legs64x42.png", 4, 1);
		for (Company c : getCompanies()) {
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					u.setBodySprite("data/images/bodies64x102.png", 5, 5);
					u.setEyeSprite("data/images/eyes64x54.png", 7, 1);
				}
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
		f = new BlinkingBorder();
        stateTime = 0f;   
        
		new PhysicsScreenBoundaries(this.getWindowWidth(), this.getWindowHeight()-Param.FLOOR_DEPTH);
		debugRenderer = new DebugRenderer();
	}
	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		if (keycode == Keys.NUM_1){
			heNote.play();
			getCompanies().elementAt(0).add(Sequence.add(new Note(Drum.HE)));
		}
		if (keycode == Keys.NUM_2){
			sNote.play();
			getCompanies().elementAt(0).add(Sequence.add(new Note(Drum.S)));		
		}
		if (keycode == Keys.NUM_3){
			soNote.play();
			getCompanies().elementAt(0).add(Sequence.add(new Note(Drum.SO)));		
		}
		if (keycode == Keys.NUM_4){
			yesNote.play();
			getCompanies().elementAt(0).add(Sequence.add(new Note(Drum.YES)));		
		}
		
		if (keycode == Keys.SPACE){
			heNote.setPitch(2);
			sNote.setPitch(2);
			soNote.setPitch(2);
			yesNote.setPitch(2);
		}
		if (keycode == Keys.ENTER){
			heNote.setPitch(1);
			sNote.setPitch(1);
			soNote.setPitch(1);
			yesNote.setPitch(1);	
		}
		
		if (keycode == Keys.A)
			RythmTimer.snapFlag = !RythmTimer.snapFlag;
		
		if (keycode == Keys.D)
			RythmTimer.soundFlag++ ;
		
		if (keycode == Keys.LEFT)
			getCompanies().firstElement().moveRelative(-10);
		
		if (keycode == Keys.RIGHT)
			getCompanies().firstElement().moveRelative(+10);
	}
	public void onGraphicRender(GdxGraphics g) {		
		//clear the screen
		g.clear(Param.BACKGROUND);
		
		//write help
		g.setColor(Color.BLACK);
		g.drawStringCentered(490f, "Touche A pour activer/désactiver les claps");
		g.drawStringCentered(470f, "Flèches pour bouger la companie");
		g.drawStringCentered(450f, "Touches 1 à 4 pour jouer les sons");
		g.drawStringCentered(430f, "Touche D pour changer de loop sonore");
		
		float fY = Param.FLOOR_DEPTH;
		
		//draw floor
		g.drawFilledRectangle(width/2, 0, width, fY, 0,Color.DARK_GRAY);
		
		for (Company c : getCompanies()) {
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					u.draw(g, stateTime);
				}
			}
		}
		for (DrawableObject o : flyingOjects) {
			o.draw(g);
		}
		
		//oh yeah
		g.drawSchoolLogoUpperRight();
		//draw the frame to show the rythm
		f.draw(g);
		PhysicsWorld.updatePhysics(Gdx.graphics.getRawDeltaTime());
        stateTime += Gdx.graphics.getDeltaTime();
		g.drawSchoolLogoUpperRight();
		g.drawFPS();
	}
	public static Vector<Company> getCompanies() {
		return companies;
	}
	/**
	 * @author Loïc Gillioz (lg)
	 * @param nb1 : number of archers
	 * @param nb2 : number of swordmans
	 * @param nb3 : number of shields
	 * @return a sample company that contains {@code nb1} archers,
	 * {@code nb2} swordmans and {@code nb3}shields.
	 */
	private static Company randomCompany(int nb1, int nb2, int nb3){
		Company comp = new Company("Patapons");
		
		for(int i = 0 ; i < 3; i++){
			comp.add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			comp.sections.elementAt(0).add(new Archer((int)(1+Math.random()*5), (int)(1+Math.random()*5)));
		}
		for(int i = 0 ; i < nb2; i++){
			comp.sections.elementAt(1).add(new Spearman((int)(1+Math.random()*5), (int)(1+Math.random()*5)));
		}
		for(int i = 0 ; i < nb3; i++){
			comp.sections.elementAt(2).add(new Shield((int)(1+Math.random()*5), (int)(1+Math.random()*5)));
		}
		
		int initialPos = comp.getWidth()/2 + 50;
		comp.moveAbsolute(initialPos);
		return comp;
	}
}
