package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Data;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Archer extends Unit {
	static double modLife = -0.2;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	
	public Archer(){
//		Adaptation des aptitudes
		super();
		super.id = Data.ARCHER;
		super.setLife(super.life*(1.0+modLife));
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
