package hevs.fragil.patapon.graphics;

import com.badlogic.gdx.graphics.Color;

import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Timer;
import java.util.Vector;

import com.badlogic.gdx.Input.Keys;

import hevs.fragil.patapon.others.Data;
import hevs.fragil.patapon.others.Note;
import hevs.fragil.patapon.others.Sequence;
import hevs.fragil.patapon.units.*;

public class Map extends PortableApplication {
	int width;
	boolean snapEnable = false;
	private Vector<Company> companies = new Vector<Company>();
	private Vector<SoundSample> notes = new Vector<SoundSample>();
	private Vector<SoundSample> tracks = new Vector<SoundSample>();
	private Sequence s = new Sequence();
	private SoundSample snap;
	private Frame f;
	private Timer timer = new Timer();
	
	public Map(int width){
		//TODO organiser l'ordre des sections (peut-être pas là)
		this.width = width;
	}
	public void add (Company c){
		companies.add(c);
	}
	@Override
	public void onDispose() {
		super.onDispose();
		for (SoundSample note : notes) {
			note.dispose();
		}
		for (SoundSample track : tracks) {
			track.dispose();
		}
		snap.dispose();
	}
	@Override
	public void onInit() {
		setTitle("Test Map Patapons H-E-S! - by FraGil 2016");
		//Load the sound files
		notes.add(new SoundSample("data/music/HE.wav"));
		notes.add(new SoundSample("data/music/S.wav"));
		notes.add(new SoundSample("data/music/SO.wav"));
		notes.add(new SoundSample("data/music/YES.wav"));
		snap = new SoundSample("data/music/loop2.wav");
		tracks.add(new SoundSample("data/music/loop1.wav"));
		tracks.add(new SoundSample("data/music/loop3.wav"));
		tracks.add(new SoundSample("data/music/loop4.wav"));
		tracks.add(new SoundSample("data/music/loop5.wav"));
		tracks.add(new SoundSample("data/music/loop6.wav"));
		Data.nbLoops = tracks.size();
		timer.schedule(new Tempo(), 0, Data.TEMPO_MS);

		//Load the image files
		Archer.setImgPath("data/images/Android_PI_48x48.png");
		Swordman.setImgPath("data/images/Android_PI_48x48.png");
		Shield.setImgPath("data/images/Android_PI_48x48.png");

		f = new Frame();
	}
	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);

		if (keycode == Keys.NUM_1){
			notes.elementAt(0).play();
			s.add(new Note(Data.HE));
		}
		if (keycode == Keys.NUM_2){
			notes.elementAt(1).play();
			s.add(new Note(Data.S));		
		}
		if (keycode == Keys.NUM_3){
			notes.elementAt(2).play();
			s.add(new Note(Data.SO));		
		}
		if (keycode == Keys.NUM_4){
			notes.elementAt(3).play();
			s.add(new Note(Data.YES));		
		}

		if (keycode == Keys.SPACE) {
			for (SoundSample note : notes) {
				note.setPitch(2);
			}
		}
		if (keycode == Keys.ENTER) {
			for (SoundSample note : notes) {
				note.setPitch(1);
			}
		}
		if (keycode == Keys.A) {//add snaps
			Data.snapFlag = !Data.snapFlag;
		}
		if (keycode == Keys.D) {//change background music
			Data.soundFlag++ ;
		}
		if (keycode == Keys.LEFT) {//change backgroud music
			companies.firstElement().moveRelative(-10);
		}
		if (keycode == Keys.RIGHT) {//change backgroud music
			companies.firstElement().moveRelative(+10);
		}
	}
	public void onGraphicRender(GdxGraphics g) {
//		change music when necessary
		if(Data.soundChange){
			for (SoundSample track : tracks)
				track.stop();
			tracks.elementAt(Data.soundEnable).loop();
			Data.soundChange = false;
			System.out.println("Music changed at " + System.currentTimeMillis()%500);
		}
		if(Data.snapChange){
			if(Data.snapEnable)snap.loop();
			else snap.stop();
			Data.snapChange = false;
			System.out.println("Snap changed at " + System.currentTimeMillis()%500);
		}
		
//		clear the screen
		g.clear(new Color(Data.backColorR, Data.backColorG, Data.backColorB,1));
		
//		write help
		g.drawStringCentered(490f, "Touche A pour activer/désactiver les claps");
		g.drawStringCentered(470f, "Flèches pour bouger la companie");
		g.drawStringCentered(450f, "Touches 1 à 4 pour jouer les sons");
		g.drawStringCentered(430f, "Touche D pour changer de loop sonore");
		
//		draw floor
		g.setColor(Color.DARK_GRAY);
		float floorY = Data.FLOOR;
		g.drawFilledRectangle(0, 0, width, floorY, 0);
		
//		draw centers
		float highPos = 10f;
		g.setColor(Color.BLACK);
		for (Company c : companies) {
			float compPos = c.globalPosition;
			g.drawLine(compPos, floorY, compPos, floorY+60f);
			g.drawLine(compPos, floorY+80f, compPos, floorY+120f);
			g.drawString(compPos, floorY + 140f, "Company " + c.name + " center",3);
			for (Section s : c.sections) {
				highPos *= -1;
				float secPos = s.globalPosition;
				g.drawLine(secPos, floorY, secPos, floorY+highPos+50f);
				g.drawString(secPos, floorY + highPos+50f + 15f, "Section " + s.name +" center",3);
			}
		}
		
		
//		draw units
		for(Company c : companies){
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					u.draw(g);
				}
			}
		}
		
//		oh yeah
		g.drawSchoolLogo();
		// Draw a rectangle to show the rythm
		f.draw(g);
	}
}
