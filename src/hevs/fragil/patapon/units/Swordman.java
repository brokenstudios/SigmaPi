package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Data;

public class Swordman extends Unit {
	static double modLife = +0.2;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	
	public Swordman(){
//		Adaptation des aptitudes
		super();
		super.id = Data.SWORDMAN;
		super.setLife(super.life*(1.0+modLife));
//		super.attack.add(modAttack);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+super.toString();
	}
}
