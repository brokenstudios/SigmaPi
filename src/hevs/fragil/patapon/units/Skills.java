package hevs.fragil.patapon.units;
public class Skills {
	private int life;
	private int attack;
	private int rangeMax;
	private int rangeMin;
	private int defense;
	private float cooldown;
	
	Skills(int life, int attack, int rangeMax, int rangeMin, int defense, float cooldown){
		this.setRange(rangeMax, rangeMin);
		this.setCooldown(cooldown);
		this.setLife(life);
		this.setDefense(defense);
		this.setAttack(attack);
	}
	public int getRange() {
		return (rangeMax-rangeMin)/2;
	}
	public int getRangeMax() {
		return rangeMax;
	}
	public int getRangeMin() {
		return rangeMin;
	}
	public void setRange(int rangeMax, int rangeMin) {
		this.rangeMax = rangeMax;
		this.rangeMin = rangeMin;
	}
	public void setRangeMax(int rangeMax){
		this.rangeMax = rangeMax;
	}
	public void setRangeMin(int rangeMin){
		this.rangeMin = rangeMin;
	}
	public float getCooldown() {
		return cooldown;
	}
	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public void setDefense(int defense){
		this.defense = defense;
	}
	public int getDefense() {
		return defense;
	}
}
