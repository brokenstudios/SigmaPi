package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Data;
import hevs.gdx2d.lib.GdxGraphics;

public class Shield extends Unit {
	static double modLife = +1.0;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	
	public Shield(){
//		Adaptation des aptitudes
		super();
		super.id = Data.SHIELD;
		super.setLife(super.life*(1.0+modLife));
		super.setImgPath("data/images/brick.png");
//		super.attack = super.attack.add(modAttack);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+super.toString();
	}

	@Override
	public void draw(GdxGraphics g) {
		// TODO Auto-generated method stub
		
	}
}