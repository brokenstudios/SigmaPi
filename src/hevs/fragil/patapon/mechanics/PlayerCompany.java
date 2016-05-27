package hevs.fragil.patapon.mechanics;

import hevs.fragil.patapon.physics.Arrow;
import hevs.fragil.patapon.physics.Spear;
import hevs.fragil.patapon.units.Archer;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Shield;
import hevs.fragil.patapon.units.Spearman;
import hevs.fragil.patapon.units.Unit;

/**
 * Singleton pattern for player data, only one company
 * that is stored in a single file
 */
public class PlayerCompany {
	//TODO set all game variables
	private Company heroes ;
	private static PlayerCompany instance = null;
	
	private PlayerCompany(){
	}
	
	/**
	 * @param nb1 : number of archers
	 * @param nb2 : number of swordmans
	 * @param nb3 : number of shields
	 * @return a sample company that contains {@code nb1} archers,
	 * {@code nb2} swordmans and {@code nb3}shields.
	 */
	public void initRandomHeroes(int nb1, int nb2, int nb3){
		heroes = new Company("Sigmapis");
		
		for(int i = 0 ; i < 3; i++){
			heroes.add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			heroes.sections.elementAt(0).add(new Archer());
		}
		for(int i = 0 ; i < nb2; i++){
			heroes.sections.elementAt(1).add(new Spearman());
		}
		for(int i = 0 ; i < nb3; i++){
			heroes.sections.elementAt(2).add(new Shield());
		}
		
		int initialPos = heroes.getWidth()/2 + 50;
		heroes.moveAbsolute(initialPos, 100);
		
		//Load the image files
		for (Section s : heroes.sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/bodies64x102.png", 5, 5);
				u.setEyeSprite("data/images/eyes64x54.png", 7, 1);
				u.setLegsSprite("data/images/legs64x42.png", 4, 1);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
		Spear.setImgPath("data/images/fleche.png");
	}

	public void setAction(Action action) {
		heroes.setAction(action);
	}

	public Company getHeroes() {
		return heroes;
	}
	public static PlayerCompany getInstance() {
		//TODO create new player company on new game 
		if (instance == null) {
			instance = new PlayerCompany();
		}
		return instance;
	}
}
