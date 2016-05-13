package hevs.fragil.patapon.mechanics;

import hevs.fragil.patapon.physics.Arrow;
import hevs.fragil.patapon.units.Archer;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Shield;
import hevs.fragil.patapon.units.Spearman;
import hevs.fragil.patapon.units.Unit;

public class PlayerData {
	//TODO set all game variables
	private Company heroes = new Company();
	
	public PlayerData(){
		heroes = randomCompany(4,3,3);
		//Load the image files
		Unit.setLegsSprite("data/images/legs64x42.png", 4, 1);
		for (Section s : heroes.sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/bodies64x102.png", 5, 5);
				u.setEyeSprite("data/images/eyes64x54.png", 7, 1);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
	}
	
	/**
	 * @author Loïc Gillioz (lg)
	 * @param nb1 : number of archers
	 * @param nb2 : number of swordmans
	 * @param nb3 : number of shields
	 * @return a sample company that contains {@code nb1} archers,
	 * {@code nb2} swordmans and {@code nb3}shields.
	 */
	private Company randomCompany(int nb1, int nb2, int nb3){
		Company comp = new Company("Patapons");
		
		for(int i = 0 ; i < 3; i++){
			comp.add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			comp.sections.elementAt(0).add(new Archer());
		}
		for(int i = 0 ; i < nb2; i++){
			comp.sections.elementAt(1).add(new Spearman());
		}
		for(int i = 0 ; i < nb3; i++){
			comp.sections.elementAt(2).add(new Shield());
		}
		
		int initialPos = comp.getWidth()/2 + 50;
		comp.moveAbsolute(initialPos);
		return comp;
	}

	public void addAction(Action action) {
		heroes.add(action);
	}

	public Company getHeroes() {
		return heroes;
	}
}
