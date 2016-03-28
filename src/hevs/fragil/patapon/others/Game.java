package hevs.fragil.patapon.others;
import hevs.fragil.patapon.graphics.Map;
import hevs.fragil.patapon.units.*;

public class Game {
	public static void main(String[] args) {
		//Nouvelles unités
		Archer a1 = new Archer();
		Archer a2 = new Archer();
		Archer a3 = new Archer();
		Archer a4 = new Archer();
		
		Swordman b1 = new Swordman();
		Swordman b2 = new Swordman();
		Swordman b3 = new Swordman();
		
		Shield c1 = new Shield();
		Shield c2 = new Shield();
		Shield c3 = new Shield();
		
		Section sec1 = new Section("1");
		Section sec2 = new Section("2");
		Section sec3 = new Section("3");
		
		Company comp1 = new Company("1");
		
		sec1.add(a1);
		sec1.add(a2);
		sec1.add(a3);
		sec1.add(a4); 
		
		sec2.add(b1);
		sec2.add(b2);
		sec2.add(b3);
		
		sec3.add(c1);
		sec3.add(c2);
		sec3.add(c3);
		
		comp1.add(sec1);
		comp1.add(sec2);
		comp1.add(sec3);
		
		System.out.println(comp1);
		
		Map map1 = new Map(1000);
		map1.add(comp1);

		int initialPos = comp1.getWidth()/2+50;
		comp1.moveAbsolute(initialPos);
		map1.update();
		System.out.println(map1);
	}
}
