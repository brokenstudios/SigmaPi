package hevs.fragil.patapon.units;

import java.util.Vector;

import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;

public class Section {
	public String name = "";
	int globalLife;
	int width;
	public int globalPosition;
	
	public Vector<Unit> units = new Vector<Unit>();
	public Section(){
		this(0,"noname");
	}
	public Section(String name){
		this(0,name);
	}
	public Section(int pos){
		this(pos, "noname");
	}
	public Section(int pos, String name){
		this.globalPosition = pos;
		this.name = name;   
	}
	public int getWidth(){
		return units.size()*Param.UNIT_WIDTH;
	}
	public void move(int newPos){
		int width = getWidth();
		double margin = newPos - width/2.0 + Param.UNIT_WIDTH/2.0;
		if(margin > 0){
			globalPosition = newPos;
			double tempPos = margin;
			for (Unit unit : units) {
				unit.setPosition((int)tempPos);
				tempPos += Param.UNIT_WIDTH;
			}
		}		
	}
	public String toString(){
		String t = "  Start of Section \n";
		t += "   This section contains : \n";
		for (Unit unit : units) {
			t += "     "+unit.toString()+"\n";
		}
		t += "  End of Section";
		return t;
	}
	public void add(Unit u){
		units.addElement(u);
		width += Param.UNIT_WIDTH;
	}
	public void remove(Unit u){
		units.remove(u);
		width -= Param.UNIT_WIDTH;
	}
	public void draw(GdxGraphics g, float stateTime) {
		for (Unit unit : units) {
			unit.draw(g, stateTime);
		}
	}
}
