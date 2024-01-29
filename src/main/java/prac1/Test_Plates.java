package prac1;

import java.util.Arrays;
import java.util.Random;

public class Test_Plates {
public static void main (String [] args ) {
		
		Object [] plates = {
			new Plate(1, "0241", "ACD"),
			new Plate(3, "0241", "ACD"),
			new Plate(3, "0241", "ACD"),
			new Plate(1, "0000", "ZZZ"),
			new Plate(1, "0000", "BKT"),
			new Plate(6, "1111", "AAA"),
			new Plate(4, "9090", "ABC"),
			new Plate(4, "9090", "TTD"),
			new Plate(4, "2315", "ZZZ"),
			new Plate(5, "3020", "JKP"),
			new Plate(5, "5099", "BTB"),
			new Plate(2, "3311", "PRQ")
		};
		
		// shuffle
		Random alea = new Random();
		plates = Arrays.stream(plates).sorted((a,b)-> 5-alea.nextInt(10)).toArray();
		
		// sort and print
		System.out.println("Plates sorted\n");
		Arrays.stream(plates).sorted().forEach(c-> System.out.println(c));
	}
}
