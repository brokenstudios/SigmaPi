package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Data;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;

public class Shield extends Unit {
	static double modLife = +1.0;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	static BitmapImage img ;
	
	public Shield(){
//		Adaptation des aptitudes
		super();
		super.setLife(super.life*(1.0+modLife));
//		super.attack = super.attack.add(modAttack);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+super.toString();
	}

	@Override
	public void draw(GdxGraphics g) {
		int y = Data.FLOOR_DEPTH + 11;
		g.drawPicture(super.position, y, img);
	}
	//Just for onInit method
	static public void setImgPath(String url){
		img = new BitmapImage(url);
	}
}