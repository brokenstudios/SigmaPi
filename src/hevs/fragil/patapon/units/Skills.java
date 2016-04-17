package hevs.fragil.patapon.units;
public class Skills {
	private int range;
	private int damage;
	private int cooldown;
	
	Skills(int range, int damage, int cooldown){
		this.setRange(range);
		this.setDamage(damage);
		this.setCooldown(cooldown);
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public float getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
}
