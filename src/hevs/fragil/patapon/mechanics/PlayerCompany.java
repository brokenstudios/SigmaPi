package hevs.fragil.patapon.mechanics;

import hevs.fragil.patapon.units.Company;

/**
 * Singleton pattern for player data, only one company
 * that is stored in a single file
 */
public class PlayerCompany {
	//TODO get saved company if exist
	private static Company instance ;
	
	private PlayerCompany(){
	}
	public static Company getCompany() {
		//TODO create new player company on new game 
		//For instance, the player company is randomly initialized
		if (instance == null) {
			instance = new Company();
			instance.initRandomHeroes(2, 0, 0);
		}
		return instance;
	}
}
