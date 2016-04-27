package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class StickyInfo{
    public Body bodyA;
    public Body bodyB;
    public Vector2 anchor;
    public StickyInfo(Body bodyA, Body bodyB, Vector2 anchor){
        this.bodyA = bodyA;
        this.bodyB = bodyB;
        this.anchor = anchor;
    }
};