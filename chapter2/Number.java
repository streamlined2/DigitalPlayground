package chapter2;

import java.util.Arrays;

public class Number implements Comparable<Number> {
	
	public enum Digit {
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);
		
		private final int value;
		
		Digit(final int value){
			this.value=value;
		}
		
		public static Digit valueOf(char character) {
			return valueOf(character-'0');
		}
		
		public static Digit valueOf(int value) {
			if(value<0 || value>=Digit.values().length) throw new RuntimeException("digit 0 through 9 must be passed as parameter");
			return Digit.values()[value];
		}
		
		@Override public String toString() {
			return String.valueOf(value);
		}
	
	};
	
	private final Digit[] digits;
	
	public final int length() {
		return digits.length;
	}

	public Number(int unsigned) {
		if(unsigned<0) throw new RuntimeException("only positive numbers allowed");
		String string=String.valueOf(unsigned); 
		digits=new Digit[string.length()];
		for(int k=0;k<length();k++) {
			digits[k]=Digit.valueOf(string.charAt(k));
		}
	
	}
	
	@Override public boolean equals(Object o) {
		if(!(o instanceof Number)) return false;
		return Arrays.equals(this.digits, ((Number)o).digits);
	}
	
	@Override public int compareTo(Number o) {
		if(this.length()>o.length()) return 1;
		else if(this.length()<o.length()) return -1;
		else return Arrays.compare(this.digits, o.digits);
	}

	@Override public String toString() {
		final StringBuilder sb=new StringBuilder();
		Arrays.asList(digits).forEach(d->sb.append(d));
		return sb.toString();//Arrays.toString(digits);
	}
	
	private int[] getFrequencyMap() {
		int freqMap[]=new int[Digit.values().length];
		for(Digit digit:digits) {
			freqMap[digit.value]++;
		}
		return freqMap;
	}
	
	public int getDigitsCount() {
		int count=0;
		for(int freq:getFrequencyMap()) { if(freq>0) count++; };
		return count;
	}
	
	public boolean justUniqueDigits() {
		for(int freq:getFrequencyMap()) {
			if(freq>1) return false;
		}
		return true;
	}
	
	public boolean justEvenDigits() {
		boolean flag=true;
		for(Digit digit:digits) { flag = flag && digit.value%2==0;}
		return flag;
	}
	
	public int getEvenDigitsCount() {
		int count=0;
		for(Digit digit:digits) {
			if(digit.value%2==0) count++;
		}
		return count;
	}

	public int getOddDigitsCount() {
		int count=0;
		for(Digit digit:digits) {
			if(digit.value%2!=0) count++;
		}
		return count;
	}
	
	public boolean isOrdered() {
		if(digits.length>0) {
			Digit prev=digits[0];
			for(int k=1;k<digits.length;k++) {
				Digit curr=digits[k];
				if(curr.compareTo(prev)<0) return false;
				else prev=curr;
			}			
		}
		return true;
	}
	
	public boolean isPalindrome() {
		int i=0,j=digits.length-1;
		while(i<=j) {
			if(digits[i].compareTo(digits[j])!=0) return false;
			i++; j--;
		}
		return true;
	}

}