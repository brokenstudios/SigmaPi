package hevs.fragil.patapon.units;
public class Skills {
	private int life;
	private int attack;
	private int range;
	private int cooldown;
	
	Skills(int life, int attack, int range, int cooldown){
		this.setRange(range);
		this.setCooldown(cooldown);
		this.setRange(range);
		this.setCooldown(cooldown);
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
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

}
