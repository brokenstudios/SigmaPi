package hevs.fragil.patapon.units;
public class FightFactor {
	private int range;
	private int damage;
	private float cooldown;
	
	FightFactor(int range, int damage, float cooldown){
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
	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
}
