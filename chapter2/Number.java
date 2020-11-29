package chapter2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@code Number} class encapsulates digits of a single cardinal number and 
 * provides set of methods to inspect them
 * 
 * @author Serhii Pylypenko
 * @version 1.0
 * @since 2020-02-15
 *
 */
public class Number implements Comparable<Number> {
	/**
	 * The {@code Digit} class holds one digit of a number
	 *
	 */
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
	
	/**
	 * list of decimal digits which represents number
	 */
	private final Digit[] digits;
	
	public final int length() {
		return digits.length;
	}

	/**
	 * The constructor takes cardinal number and parses it into array of digits
	 * @param unsigned cardinal number that object holds
	 * @exception RuntimeException thrown if negative parameter passed
	 */
	public Number(int unsigned) {
		if(unsigned<0) throw new RuntimeException("only positive numbers allowed");
		String string=String.valueOf(unsigned); 
		digits=new Digit[string.length()];
		for(int k=0;k<length();k++) {
			digits[k]=Digit.valueOf(string.charAt(k));
		}
	
	}
	
	/**
	 * Indicates whether another number is equal to this number<p>
	 * {@inheritDoc}
	 * @param o another number to compare to
	 * @return {@code true}, if both numbers are equal, {@code false} otherwise
	 */
	@Override public boolean equals(Object o) {
		if(!(o instanceof Number)) return false;
		return Arrays.equals(this.digits, ((Number)o).digits);
	}
	
	/**
	 * Provides natural ordering of numbers consistent with {@link #equals}<p>
	 * {@inheritDoc}
	 */
	@Override public int compareTo(Number o) {
		if(this.length()>o.length()) return 1;
		else if(this.length()<o.length()) return -1;
		else return Arrays.compare(this.digits, o.digits);
	}

	/**
	 * Provides string representation of this number<p>
	 * {@inheritDoc}
	 */
	@Override public String toString() {
		final StringBuilder sb=new StringBuilder();
		Arrays.asList(digits).forEach(d->sb.append(d));
		return sb.toString();//Arrays.toString(digits);
	}
	
	/**
	 * Builds frequency map of digit occurrences
	 * @return frequency map
	 */
	private int[] getFrequencyMap() {
		int freqMap[]=new int[Digit.values().length];
		for(Digit digit:digits) {
			freqMap[digit.value]++;
		}
		return freqMap;
	}
	
	/**
	 * Counts digits of the number
	 * @return count of digits
	 */
	public int getDigitsCount() {
		int count=0;
		for(int freq:getFrequencyMap()) { if(freq>0) count++; };
		return count;
	}
	
	/**
	 * Checks if number comprises just unique digits
	 * @return {@code true}, if every digit occurs exactly one time, {@code false} otherwise 
	 */
	public boolean justUniqueDigits() {
		for(int freq:getFrequencyMap()) {
			if(freq>1) return false;
		}
		return true;
	}
	
	/**
	 * Checks if number consists of even digits
	 * @return {@code true}, if every digit is even, {@code false} otherwise 
	 */
	public boolean justEvenDigits() {
		boolean flag=true;
		for(Digit digit:digits) { flag = flag && digit.value%2==0;}
		return flag;
	}
	
	/**
	 * Counts quantity of even digits
	 * @return quantity of even digits
	 */
	public int getEvenDigitsCount() {
		int count=0;
		for(Digit digit:digits) {
			if(digit.value%2==0) count++;
		}
		return count;
	}

	/**
	 * Counts quantity of odd digits
	 * @return quantity of odd digits
	 */
	public int getOddDigitsCount() {
		int count=0;
		for(Digit digit:digits) {
			if(digit.value%2!=0) count++;
		}
		return count;
	}
	
	/**
	 * Checks if number comprises same quantity of even and odd digits
	 * @return {@code true}, if quantity of even and odd digits coincide, {@code false} otherwise
	 */
	public boolean sameOddEvenDigitsCount() {
		return getOddDigitsCount()==getEvenDigitsCount();
	}
	
	/**
	 * Checks for digits ascending order
	 * @return {@code true}, if digits are arranged in ascending order, {@code false} otherwise
	 */
	public boolean isAscending() {
		return isOrdered(false);
	}
	
	/**
	 * Checks for digits descending order
	 * @return {@code true}, if digits are arranged in descending order, {@code false} otherwise
	 */
	public boolean isDescending() {
		return isOrdered(true);
	}
	
	/**
	 * Checks if digits arranged in specified order
	 * @param descending {@code true} means digits should be arranged in descending order, {@code false}, if digits should be arranged in ascending order
	 * @return {@code true}, if digits are arranged in descending order, {@code false} otherwise
	 */
	public boolean isOrdered(boolean descending) {
		Comparator<Digit> ordering=descending?Comparator.reverseOrder():Comparator.naturalOrder();
		if(digits.length>0) {
			Digit prev=digits[0];
			for(int k=1;k<digits.length;k++) {
				Digit curr=digits[k];
				if(ordering.compare(curr, prev)<0) return false;
				else prev=curr;
			}			
		}
		return true;
	}
	
	/**
	 * Checks if number is palindrome
	 * @see https://en.wikipedia.org/wiki/Palindrome
	 * @return {@code true}, if number reads from left to right same as from right to left
	 */
	public boolean isPalindrome() {
		int i=0,j=digits.length-1;
		while(i<=j) {
			if(digits[i].compareTo(digits[j])!=0) return false;
			i++; j--;
		}
		return true;
	}

}
