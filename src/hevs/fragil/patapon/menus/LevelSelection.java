package hevs.fragil.patapon.menus;

import java.util.LinkedList;

import com.badlogic.gdx.Input.Keys;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Param;

public class LevelSelection extends RenderingScreen{
	private int itemSelected = 0;
	private BitmapImage selection;
	
	private static LinkedList<MapInfo> list = new LinkedList<MapInfo>();
	
	@Override
	public void onInit() {
		readFiles();
		list.clear();
		list.add(new MapInfo());
		list.add(new MapInfo());
		list.add(new MapInfo());
		list.add(new MapInfo());
		list.add(new MapInfo());
		selection = new BitmapImage("images/selection.png");
	}

	private void readFiles() {
		
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.BACKGROUND);
		g.drawStringCentered(850, "LEVEL SELECTION", Param.large);
		
		int listHeight = list.size() * 100;
		int start = (Param.CAM_HEIGHT - listHeight) / 2;
		for (int i = 0; i < list.size(); i++) {
			int posY = start + i * 100;
			
			if(i == itemSelected)
				g.drawPicture(600, posY-40, selection);
			
			g.drawStringCentered(posY, list.get(i).getName(), Param.medium);
		}
	}
	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		switch (keycode) {
		case Keys.UP: 		if(itemSelected < list.size()-1){
								itemSelected ++;
							}
							break;
		case Keys.DOWN: 	if(itemSelected > 0){
								itemSelected --;
							}
							break;
		}
	}
}
