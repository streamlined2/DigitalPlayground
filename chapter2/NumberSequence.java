package chapter2;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class NumberSequence {
	
	static class Range{
		private final int from, to;
		 Range(final int from,final int to){
			 this.from=from;
			 this.to=to;
		 }
		 int getFrom() { return from;}
		 int getTo() { return to;}
	}
	
	private int size;
	private List<Number> numbers;
	
	public NumberSequence(final int size) {
		this.size=size;
		numbers=new ArrayList<>(this.size);
	}
	
	public int getSize() {
		return size;
	}
	
	@Override public String toString() {
		final String separator=", ";
		StringBuilder s=new StringBuilder();
		for(Number n:numbers) {
			s.append(n).append(separator);
		}
		s.delete(s.length()-separator.length(), s.length()-1);
		return s.toString();
	}

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
	
	public SortedSet<Number> getSortedSet(Comparator<? super Number> comp){
		SortedSet<Number> set=new TreeSet<>(comp);
		set.addAll(numbers);
		return Collections.unmodifiableSortedSet(set);		
	}

	public List<Number> getSortedList(Comparator<? super Number> comp){
		List<Number> sortedList=new ArrayList<>(numbers);
		Collections.sort(sortedList, comp);
		return Collections.unmodifiableList(sortedList);
	}
	
	public Number getShortestNumber() {
		return getSortedSet(byNumberSize(false)).first();//getPriorityQueue(false).peek();
	}
	
	public Number getLongestNumber() {
		return getSortedSet(byNumberSize(false)).last();//getPriorityQueue(true).peek();
	}
	
	public Range getMedianRange() {
		int from,to;
		from=to=getMedianIndex();
		Number median=numbers.get(from);
		while(from>0 && median.length()==numbers.get(from-1).length()) from--;//spread median range to left
		while(to<numbers.size()-1 && median.length()==numbers.get(to+1).length()) to++;// and right
		return new Range(from,to);
	}
	
	public List<Number> getPartByMedian(boolean greater){ 
		List<Number> list=getSortedList(byNumberSize(false)); 
		return greater? 
		  list.subList(getMedianRange().getTo()+1, list.size()): 
			  list.subList(0,getMedianRange().getFrom()); 
	}
	  
	public int getMedianLength() { 
		List<Number> list=getSortedList(byNumberSize(false)); 
		return list.get(getMedianIndex()).length(); 
	}

	private int getMedianIndex() { return numbers.size()/2; }
	 
	
	/*
	 * public PriorityQueue<Number> getPriorityQueue(boolean reversed){
	 * PriorityQueue<Number> queue=new PriorityQueue<>( numbers.size(), reversed?
	 * new NumberSizeComparator<>().reversed(): new NumberSizeComparator<>());
	 * queue.addAll(numbers); return queue; }
	 */	
}
