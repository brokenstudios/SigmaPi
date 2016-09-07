package hevs.fragil.patapon.accessories;

import java.util.Comparator;

public class ZComparator implements Comparator<ZObject>{
	/* 1 Sigmapis
	 * 2 Floor
	 * 3 Towers
	 * 4 Trees
	 * 5 Mountains
	 * 6 Clouds
	 * ...
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	 public int compare(ZObject object1, ZObject object2) {
	     /*	1 before 2 	: <0
		 * 	1 equals 2 	: 0
		 * 	1 after 2 	: >0 */
		return object1.getZ() - object2.getZ();
	 }
	 
}
