package chapter2;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

/**
 * The {@code NumberSequence} class encapsulates list of cardinal numbers {@link chapter2.Number} and 
 * provides set of methods to inspect and transform them
 * @see Number
 * 
 * @author Serhii Pylypenko
 * @version 1.0
 * @since 2020-02-15
 *
 */
public class NumberSequence {
	
	/**
	 * quantity of numbers this sequence contains
	 */
	private int size;
	/**
	 * list of numbers
	 */
	private List<Number> numbers;
	
	/**
	 * Constructs empty number sequence and preallocates up to {@code size} items
	 * @param size quantity of numbers to preallocate
	 */
	public NumberSequence(final int size) {
		this.size=size;
		numbers=new ArrayList<>(this.size);
	}
	
	/**
	 * Constructs number sequence from passed parameter
	 * @param list of numbers
	 */
	public NumberSequence(final List<Number> list) {
		this.size=list.size();
		numbers=list;
	}
	
	/**
	 * 	@return quantity of numbers the sequence holds
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @return string representation of number sequence
	 */
	@Override public String toString() {
		final String separator=", ";
		StringBuilder s=new StringBuilder();
		for(Number n:numbers) {
			s.append(n).append(separator);
		}
		if(numbers.size()>0) s.delete(s.length()-separator.length(), s.length()-1);
		return s.toString();
	}

	/**
	 * Reads list of numbers and puts them in the sequence
	 * @param prompt ask user to input data if {@code true} passed
	 * @param in source to read data from 
	 */
	public void read(boolean prompt,Reader in){
		Scanner scanner=null;
		try {
			scanner=new Scanner(in);
			if(prompt) {
				System.out.printf("Please enter %d cardinal numbers: ",size);
			}
			for(int k=0;k<size;k++) {
				numbers.add(new Number(scanner.nextInt()));
			}
		}finally {
			if(scanner!=null) scanner.close();
		}
	}
	
	static Comparator<? super Number> byNumberSize(boolean reversed) {
		return reversed?
							new NumberSizeComparator<>().reversed():
								new NumberSizeComparator<>();
	}
	
	static Comparator<? super Number> byUniqueCount(boolean reversed){
		return reversed?
				new UniqueNumberCountComparator<>().reversed():
					new UniqueNumberCountComparator<>();
	}
	
	/**
	 * Collects numbers from sequence into set which sorted according to passed comparator
	 * @param comp determines order of result
	 * @return sorted set of numbers 
	 */
	public SortedSet<Number> getSortedSet(Comparator<? super Number> comp){
		SortedSet<Number> set=new TreeSet<>(comp);
		set.addAll(numbers);
		return Collections.unmodifiableSortedSet(set);		
	}

	/**
	 * Collects numbers from sequence into list which sorted according to passed comparator 
	 * @param comp determines order of result
	 * @return sorted list of numbers
	 */
	public List<Number> getSortedList(Comparator<? super Number> comp){
		List<Number> sortedList=new ArrayList<>(numbers);
		Collections.sort(sortedList, comp);
		return Collections.unmodifiableList(sortedList);
	}
	
	/**
	 * Finds shortest number in sequence
	 * @return shortest number of sequence
	 */
	public Number getShortestNumber() {
		return getSortedSet(byNumberSize(false)).first();//getPriorityQueue(false).peek();
	}
	
	/**
	 * Finds longest number in sequence
	 * @return longest number of sequence
	 */
	public Number getLongestNumber() {
		return getSortedSet(byNumberSize(false)).last();//getPriorityQueue(true).peek();
	}
	
	/**
	 * Holds pair of indices that represents continuous block of number sequence
	 * 
	 * @author Serhii Pylypenko
	 * @version 1.0
	 * @since 2020-02-15
	 */
	static class Range{
		private final int from, to;
		 Range(final int from,final int to){
			 this.from=from;
			 this.to=to;
		 }
		 int getFrom() { return from;}
		 int getTo() { return to;}
	}
	
	/**
	 * Finds sequence block that consist just of one mean value
	 * @return pair of indices of sequence block that holds mean value
	 */
	public Range getMedianRange() {
		int from,to;
		from=to=getMedianIndex();
		Number median=numbers.get(from);
		while(from>0 && median.length()==numbers.get(from-1).length()) from--;//spread median range to left
		while(to<numbers.size()-1 && median.length()==numbers.get(to+1).length()) to++;// and right
		return new Range(from,to);
	}
	
	/**
	 * Fetches left or right part of sequence that doesn't include median block
	 * @param greater {@code true}, if right part should be returned, {@code false} otherwise
	 * @return left or right part of sequence excluding central median block
	 */
	public List<Number> getPartByMedian(boolean greater){ 
		List<Number> list=getSortedList(byNumberSize(false)); 
		return greater? 
		  list.subList(getMedianRange().getTo()+1, list.size()): 
			  list.subList(0,getMedianRange().getFrom()); 
	}
	  
	/**
	 * Finds median length of number sequence
	 * @return median length of number sequence
	 */
	public int getMedianLength() { 
		List<Number> list=getSortedList(byNumberSize(false)); 
		return list.get(getMedianIndex()).length(); 
	}

	private int getMedianIndex() { return numbers.size()/2; }
	
	/**
	 * Constructs new number sequence by applying filter to this sequence
	 * @param p filter predicate to be applied to the sequence 
	 * @return new filtered out sequence
	 */
	public NumberSequence filter(Predicate<Number> p){
		List<Number> filteredList=new ArrayList<>();
		for(Number n:numbers) {
			if(p.test(n)) filteredList.add(n);
		}
		return new NumberSequence(filteredList);
	}
	
	/**
	 * Finds first item of sequence, if non-empty, or empty optional, if otherwise 
	 * @return first time of sequence, if non-empty, otherwise empty optional
	 */
	public Optional<Number> first() {
		if(numbers.size()>0) return Optional.of(numbers.get(0));
		else return Optional.ofNullable(null);
	}
	
	/**
	 * Finds second item of sequence, if it contains 2 or more items, first item, if it contains exactly one, or empty optional, if otherwise 
	 * @return second item of sequence, if it contains 2 or more items, first item, if it contains exactly one, or empty optional, if otherwise
	 */
	public Optional<Number> secondOrOnlyOne(){
		if(numbers.size()>1) return Optional.of(numbers.get(1));
		else if(numbers.size()>0) return Optional.of(numbers.get(0));
		else return Optional.ofNullable(null);
	}
	 
	
	/*
	 * public PriorityQueue<Number> getPriorityQueue(boolean reversed){
	 * PriorityQueue<Number> queue=new PriorityQueue<>( numbers.size(), reversed?
	 * new NumberSizeComparator<>().reversed(): new NumberSizeComparator<>());
	 * queue.addAll(numbers); return queue; }
	 */	
}
