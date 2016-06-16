package hevs.fragil.patapon.units;
public class Skills {
	private int life;
	private int attack;
	private int rangeMax;
	private int rangeMin;
	private int defense;
	private float cooldown;
	private int level = 1;
	
	/**
	 * Allow to instantiate some skills
	 * @param level
	 * @param life
	 * @param attack
	 * @param rangeMax
	 * @param rangeMin
	 * @param defense
	 * @param cooldown
	 */
	Skills(int level, int life, int attack, int rangeMin, int rangeMax, int defense, float cooldown){
		this.setRange(rangeMin, rangeMax);
		this.setCooldown(cooldown);
		this.setLife(life);
		this.setDefense(defense);
		this.setAttack(attack);
		this.setLevel(level);
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
	public void setRange(int rangeMin, int rangeMax) {
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
