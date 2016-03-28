package hevs.fragil.patapon.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import hevs.gdx2d.components.audio.SoundSample;
import hevs.gdx2d.demos.scrolling.objects.Pipe;
import hevs.gdx2d.demos.scrolling.objects.Sky;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.interfaces.DrawableObject;

import java.util.Vector;

import com.badlogic.gdx.Input.Keys;

import hevs.fragil.patapon.others.Data;
import hevs.fragil.patapon.others.Timing;
import hevs.fragil.patapon.units.*;

public class Map extends PortableApplication {
	int width;
	int[] pixels;
	static int height = 30;
	private Vector<Company> companies = new Vector<Company>();
	long musicID;
	boolean snapEnable = false;
	private Vector<SoundSample> notes = new Vector<SoundSample>();
	private Vector<SoundSample> loops = new Vector<SoundSample>();
	private SoundSample snap;
	
	
	public Map(int width){
		//TODO organiser l'ordre des sections (peut-être pas là)
		this.width = width;
		pixels = new int[width];
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
		for (SoundSample loop : loops) {
			loop.dispose();
		}
	}
	@Override
	public void onInit() {
		setTitle("Test Map Patapons H-E-S! - by FraGil 2016");
		// Load the sound files
		notes.add(new SoundSample("data/music/HE.wav"));
		notes.add(new SoundSample("data/music/S.wav"));
		notes.add(new SoundSample("data/music/SO.wav"));
		notes.add(new SoundSample("data/music/YES.wav"));
		snap = new SoundSample("data/music/loop2.wav");
		loops.add(new SoundSample("data/music/loop1.wav"));
		loops.add(new SoundSample("data/music/loop3.wav"));
		loops.add(new SoundSample("data/music/loop4.wav"));
		loops.add(new SoundSample("data/music/loop5.wav"));
		loops.add(new SoundSample("data/music/loop6.wav"));
		loops.add(new SoundSample("data/music/loop7.wav"));
		loops.add(new SoundSample("data/music/loop8.wav"));
		snap.loop();
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
			notes.elementAt(0).setPitch(2);
			notes.elementAt(1).setPitch(2);
			notes.elementAt(2).setPitch(2);
			notes.elementAt(3).setPitch(2);
		}
		if (keycode == Keys.ENTER) {
			notes.elementAt(0).setPitch(1);
			notes.elementAt(1).setPitch(1);
			notes.elementAt(2).setPitch(1);
			notes.elementAt(3).setPitch(1);
		}
		if (keycode == Keys.A) {//add snaps
			if(snapEnable)snap.mofidyPlayingVolument(0, 0);
			else snap.mofidyPlayingVolument(1, 0);
			snapEnable = !snapEnable;
		}
		if (keycode == Keys.D) {//change background music
			
		}
		if (keycode == Keys.LEFT) {//change backgroud music
			companies.firstElement().moveRelative(-10);
			update();
		}
		if (keycode == Keys.RIGHT) {//change backgroud music
			companies.firstElement().moveRelative(+10);
			update();
		}
	}
	public void onGraphicRender(GdxGraphics g) {
//		clear the screen
		g.clear(Color.WHITE);
		DrawableObject sky = new Sky();
		sky.draw(g);
//		write help
		g.drawStringCentered(490f, "Touche A pour activer/désactiver les claps");
		g.drawStringCentered(470f, "Flèches pour bouger la companie");
		g.drawStringCentered(450f, "Touches 1 à 4 pour jouer les sons");
		g.drawStringCentered(430f, "Touche D pour changer de loop sonore");
//		draw floor
		g.setColor(Color.NAVY);
		float floorY = height-10f;
		g.drawFilledRectangle(0, 0, width, floorY, 0);
//		draw limits
		float x = companies.firstElement().globalPosition-companies.firstElement().getWidth()/2f;
		g.setColor(Color.BLACK);
		g.drawLine(x, 10, x, 50);
		x = companies.firstElement().globalPosition+companies.firstElement().getWidth()/2f;
		g.drawLine(x, 10, x, 50);
//		draw centers
		float textPos = 30f;
		g.setColor(Color.BLACK);
		for (Company company : companies) {
			float compPos = company.globalPosition;
			g.drawLine(compPos, floorY, compPos, floorY+20f);
			g.drawString(compPos, floorY + 35f, "Company " + company.name + " center",3);
			for (Section section : company.sections) {
				textPos += 20f;
				float secPos = section.globalPosition;
				g.drawLine(secPos, floorY, secPos, floorY+textPos);
				g.drawString(secPos, floorY + textPos + 15f, "Section " + section.name +" center",3);
			}
		}
//		draw patapons
		for (int i = 0; i < pixels.length; i++) {
			switch(pixels [i]){
			case Data.ARCHER : 		g.drawFilledCircle(i, height-15f, 5, Color.YELLOW);
									break;
			case Data.SWORDMAN : 	g.drawFilledCircle(i, height-15f, 5, Color.ORANGE);
									break;
			case Data.SHIELD : 		g.drawFilledCircle(i, height-15f, 5, Color.RED);
									break;
			}
		}
	}
	public void update(){
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = Data.NOTHING;
		}
		for(Company c : companies){
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					pixels[u.position] = u.id;
				}
			}
		}
	}
	public String toString(){
		String s = "";
		for (int i = 0; i < pixels.length; i++) {
			switch(pixels [i]){
			case Data.NOTHING : 	s+="_";
									break;
			case Data.ARCHER : 		s+="A";
									break;
			case Data.SHIELD : 		s+="D";
									break;
			case Data.SWORDMAN : 	s+="S";
									break;
			default : 				s+="☺";
									break;
			}
		}
		return s;
	}
}
