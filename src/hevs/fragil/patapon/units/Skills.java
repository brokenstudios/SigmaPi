package hevs.fragil.patapon.units;
public class Skills {
	private int life;
	private int attack;
	private int range;
	private int defense;
	private float cooldown;
	
	Skills(int life, int attack, int range, int defense, float cooldown){
		this.setRange(range);
		this.setCooldown(cooldown);
		this.setRange(range);
		this.setLife(life);
		this.setDefense(defense);
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
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
