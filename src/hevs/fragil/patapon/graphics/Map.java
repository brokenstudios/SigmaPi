package hevs.fragil.patapon.graphics;

import com.badlogic.gdx.graphics.Color;
import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import java.util.Vector;
import com.badlogic.gdx.Input.Keys;
import hevs.fragil.patapon.others.Data;
import hevs.fragil.patapon.others.Timing;
import hevs.fragil.patapon.units.*;

public class Map extends PortableApplication {
	int width;
	boolean snapEnable = false;
	private Vector<Company> companies = new Vector<Company>();
	private Vector<SoundSample> notes = new Vector<SoundSample>();
	private SoundSample snap, track;
	
	
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
		snap.dispose();
		track.dispose();
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
		track = new SoundSample("data/music/loop1.wav");
		//Load the image files
		Archer.setImgPath("data/images/Android_PI_48x48.png");
		Swordman.setImgPath("data/images/Android_PI_48x48.png");
		Shield.setImgPath("data/images/Android_PI_48x48.png");
	}
	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);

		if (keycode == Keys.NUM_1){
			notes.elementAt(0).play();
			Timing.saveTime();
		}
		if (keycode == Keys.NUM_2){
			notes.elementAt(1).play();
			Timing.saveTime();
		}
		if (keycode == Keys.NUM_3){
			notes.elementAt(2).play();
			Timing.saveTime();
		}
		if (keycode == Keys.NUM_4){
			notes.elementAt(3).play();
			Timing.saveTime();
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
			if(snapEnable)snap.stop();
			else snap.loop();
			snapEnable = !snapEnable;
		}
		if (keycode == Keys.D) {//change background music
		}
		if (keycode == Keys.LEFT) {//change backgroud music
			companies.firstElement().moveRelative(-10);
		}
		if (keycode == Keys.RIGHT) {//change backgroud music
			companies.firstElement().moveRelative(+10);
		}
	}
	public void onGraphicRender(GdxGraphics g) {
//		clear the screen
		g.clear(Color.GRAY);
		
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
	}
}
