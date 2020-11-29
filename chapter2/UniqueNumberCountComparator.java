package chapter2;

import java.util.Comparator;

public class UniqueNumberCountComparator<T extends Number> implements Comparator<T> {

	@Override
	public int compare(T a, T b) {
		if(a.getDigitsCount()>b.getDigitsCount()) return 1;
		else if(a.getDigitsCount()<b.getDigitsCount()) return -1;
		else return 0;
	}

}
