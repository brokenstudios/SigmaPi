package hevs.fragil.patapon.units;

public class Species {
	int spriteLine = 0;
	String name = "noname";
	public Species(){
		this(0);
	}
	public Species(int spriteLine){
		this.spriteLine = spriteLine;
	}
	public Species(int spriteLine, String name){
		this.spriteLine = spriteLine;
		this.name = name;
	}
}
