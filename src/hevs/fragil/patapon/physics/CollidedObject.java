package hevs.fragil.patapon.physics;

public interface CollidedObject {
	public int getCollisionGroup();
	public boolean applyDamage(float damage);
}
