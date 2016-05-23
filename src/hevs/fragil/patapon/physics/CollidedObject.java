package hevs.fragil.patapon.physics;

public interface CollidedObject {
	public int getCollisionGroup();
	public void applyDamage(float damage);
}
