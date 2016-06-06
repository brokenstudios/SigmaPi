package hevs.fragil.patapon.mechanics;

import hevs.fragil.patapon.units.Company;

/**
 * Singleton pattern for player data, only one company
 * that is stored in a single file
 */
public class PlayerCompany {
	//TODO get saved company if exist
	private Company heroes ;
	private static PlayerCompany instance = null;
	
	private PlayerCompany(){
		heroes = new Company("heroes");
		heroes.initRandomHeroes(1, 0, 0);
	}

	public void setAction(State action) {
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
