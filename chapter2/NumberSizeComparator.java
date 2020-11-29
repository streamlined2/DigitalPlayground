package chapter2;

import java.util.Comparator;

public class NumberSizeComparator<T extends Number> implements Comparator<T> {

	@Override
	public int compare(T a, T b) {
		if(a.length()>b.length()) return 1;
		else if(a.length()<b.length()) return -1;
		else return 0;
	}

	
}
