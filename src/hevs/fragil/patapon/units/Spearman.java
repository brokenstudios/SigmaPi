package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;

public class Spearman extends Unit {
	static double modLife = +0.2;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	static BitmapImage img ;
	
	public Spearman(){
//		Adaptation des aptitudes
		super();
		super.setLife(super.life*(1.0+modLife));
//		super.attack.add(modAttack);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+super.toString();
	}

	@Override
	public void draw(GdxGraphics g) {
		int y = Param.FLOOR_DEPTH + 21;
		g.drawPicture(super.position, y, img);
	}
	//Just for onInit method
	static public void setImgPath(String url){
		img = new BitmapImage(url);
	}
	public void attack(){
		
	}
}