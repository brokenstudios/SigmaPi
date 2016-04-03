package hevs.fragil.patapon.graphics;

import com.badlogic.gdx.graphics.Color;

import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Timer;
import java.util.Vector;

import com.badlogic.gdx.Input.Keys;

import hevs.fragil.patapon.music.Drum;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.music.Sequence;
import hevs.fragil.patapon.music.Tempo;
import hevs.fragil.patapon.others.Data;
import hevs.fragil.patapon.units.*;

public class Map extends PortableApplication {
	private int width;
	private static Vector<Company> companies = new Vector<Company>();
	private static Vector<SoundSample> notes = new Vector<SoundSample>();
	private static Vector<SoundSample> tracks = new Vector<SoundSample>();
	private static Sequence s = new Sequence();
	private static SoundSample snap;
	private static Frame f;
	private static Timer timer = new Timer();
	public static Color backColor = new Color(	1f-((float)Math.random()*0.5f), 
												1f-((float)Math.random()*0.5f), 
												1f-((float)Math.random()*0.5f), 1);
	public Map(int w){
		super(w, 500);
		this.width = w;
	}
	public static int getNbTracks(){
		return tracks.size();
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
		timer.schedule(new Tempo(), 0, Data.BAR);

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
			s.add(new Note(Drum.HE));
		}
		if (keycode == Keys.NUM_2){
			notes.elementAt(1).play();
			s.add(new Note(Drum.S));		
		}
		if (keycode == Keys.NUM_3){
			notes.elementAt(2).play();
			s.add(new Note(Drum.SO));		
		}
		if (keycode == Keys.NUM_4){
			notes.elementAt(3).play();
			s.add(new Note(Drum.YES));		
		}

		if (keycode == Keys.SPACE)
			for (SoundSample note : notes)
				note.setPitch(2);
		if (keycode == Keys.ENTER)
			for (SoundSample note : notes) 
				note.setPitch(1);
		
		if (keycode == Keys.A)
			Data.snapFlag = !Data.snapFlag;
		if (keycode == Keys.D)
			Data.soundFlag++ ;
		if (keycode == Keys.LEFT)
			companies.firstElement().moveRelative(-10);
		if (keycode == Keys.RIGHT)
			companies.firstElement().moveRelative(+10);
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
		g.clear(backColor);
		
//		write help
		g.drawStringCentered(490f, "Touche A pour activer/désactiver les claps");
		g.drawStringCentered(470f, "Flèches pour bouger la companie");
		g.drawStringCentered(450f, "Touches 1 à 4 pour jouer les sons");
		g.drawStringCentered(430f, "Touche D pour changer de loop sonore");
		
//		draw floor
		g.setColor(Color.DARK_GRAY);
		float fY = Data.FLOOR_DEPTH;
		g.drawFilledRectangle(width/2, 0, width, fY, 0);
		
//		draw centers
		float highPos = -10f;
		g.setColor(Color.BLACK);
		
		for (Company c : companies) {
			float cGP = c.globalPosition;
			//vertical line on the company center
			g.drawLine(cGP, fY + 60f, cGP, fY + 160f);
			g.drawString(cGP, fY + 180f,"Company " + c.name, 3);
			
			for (Section s : c.sections) {
				//1 time up, 1 time down
				highPos *= -1;
				
				float sGP = s.globalPosition;
				//vertical line on the section center
				g.drawLine(sGP, fY + 50f, sGP, fY+highPos+120f);
				g.drawString(sGP, fY + highPos + 135f, "Section " + s.name, 3);
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
		g.drawSchoolLogoUpperRight();
//		draw a rectangle to show the rythm
		f.draw(g);
	}
}
