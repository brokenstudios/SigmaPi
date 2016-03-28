package hevs.fragil.patapon.units;

import hevs.fragil.patapon.others.Data;

public class Shield extends Unit {
	static double modLife = +1.0;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	
	public Shield(){
//		Adaptation des aptitudes
		super();
		super.id = Data.SHIELD;
		super.setLife(super.life*(1.0+modLife));
//		super.attack = super.attack.add(modAttack);
	}
	
	public String toString(){
		return this.getClass().getSimpleName()+super.toString();
	}
}