package chapter2;

import java.io.InputStreamReader;

public class Runner {

	public static final int NUMBER_COUNT=5;

	public static void main(String... args) {
		NumberSequence seq=new NumberSequence(NUMBER_COUNT);
		seq.read(true,new InputStreamReader(System.in));
		System.out.printf("You entered list of %d numbers: %s\n", seq.getSize(), seq);
		System.out.println();

		System.out.printf("Shortest number %s has %d digits.\n", seq.getShortestNumber(), seq.getShortestNumber().length());
		System.out.printf("Longest number %s has %d digits.\n", seq.getLongestNumber(), seq.getLongestNumber().length());
		System.out.println();

		System.out.printf("List of numbers sorted by ascending length: %s\n", 
				seq.getSortedList(NumberSequence.byNumberSize(false)));
		System.out.printf("List of numbers sorted by descending length: %s\n", 
				seq.getSortedList(NumberSequence.byNumberSize(true)));
		System.out.println();
		
		System.out.printf("Median number length: %s\n", seq.getMedianLength());
		System.out.printf("List of numbers shorter than median: %s\n", seq.getPartByMedian(false));
		System.out.printf("List of numbers equal or longer than median: %s\n", seq.getPartByMedian(true));
		System.out.println();
		
		System.out.printf("First number that has minimal set of different digits: %s\n", seq.getSortedList(NumberSequence.byUniqueCount(false)).get(0));
		System.out.println();
		
		System.out.printf("Quantity of numbers that comprise only even digits: %s (%s)\n", seq.filter(Number::justEvenDigits).getSize(),seq.filter(Number::justEvenDigits));
		System.out.printf("Numbers that comprise same quantity of even and odd digits: %s (%s)\n\n", seq.filter(Number::sameOddEvenDigitsCount).getSize(),seq.filter(Number::sameOddEvenDigitsCount));
		
		System.out.printf("First number with ascending digits: %s\n", seq.filter(Number::isAscending).first());
		System.out.printf("First number with descending digits: %s\n", seq.filter(Number::isDescending).first());

	}

}
