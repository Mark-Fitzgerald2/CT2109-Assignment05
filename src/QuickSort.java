/** QuickSort.java: 
 *  Based on code from Goodrich & Tamassia, “Data Structures and Algorithms in Java”
 *  With some simplifications for clarity, and test code at the end.
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Comparator;


public class QuickSort
{
    /** QuickSort method:
	  * Sorts the elements of array arr in nondecreasing order according
	  * to comparator c, using the quick-sort algorithm. Most of the work
	  * is done by the auxiliary recursive method quickSortStep.
	  **/
	private static int compare = 0; //create counter to measure comparisons 
	private static int move = 0; //create counter to measure moves
	
	public static void quickSort (Object[] arr, Comparator c) {
		if((!(compare==0)) || (!(move==0))){
			compare = 0; //resets the counters to zero
			move = 0; //in case a previous sorting algorithm was called and these are non zero
		}
		if (arr.length < 2) return; // the array is already sorted in this case
	    quickSortStep(arr, c, 0, arr.length-1); // call the recursive sort method
	}
	  
	/** QuickSortStep method: 
	  * Method called by QuickSort(), which sorts the elements of array s between
	  * indices leftBound and rightBound, using a recursive, in-place,
	  * implementation of the quick-sort algorithm.
	 **/
	private static void quickSortStep (Object[] s, Comparator c,
            int leftBound, int rightBound ) 
	{
		if (leftBound >= rightBound) return; // the indices have crossed
		Object temp;  // temp object used for swapping

		// Set the pivot to be the last element
		int middle = (leftBound + rightBound) / 2; 
		//creates reference to element in the middle of array
		if(((String) s[middle]).compareTo((String) s[leftBound])<0){ 
			//checks if middle value is less than leftBound
			swapElements(s,leftBound,middle);
			//swaps the elements
		}
		if(((String) s[rightBound]).compareTo((String) s[leftBound])<0){
			//checks if rightbound is less than leftbound
			swapElements(s,leftBound,rightBound);
			//swaps the elements
		}
		if(((String) s[rightBound]).compareTo((String) s[middle])<0){
			//checks if rightbound is less than middle
			swapElements(s,middle,rightBound);
			//swaps the elements
		}
		
		swapElements(s,middle,rightBound);
		//places median at the rightbound position
		Object pivotValue = s[rightBound];

		// Now partition the array 
		int upIndex = leftBound;     // will scan rightward, 'up' the array
		int downIndex = rightBound-1; // will scan leftward, 'down' the array
		while (upIndex <= downIndex) 
		{ 
			// scan right until larger than the pivot
			while ( (upIndex <= downIndex) && (c.compare(s[upIndex], pivotValue)<=0) ) {
				upIndex++; 
				compare++; //comparison is made
			}

			// scan leftward to find an element smaller than the pivot
			while ( (downIndex >= upIndex) && (c.compare(s[downIndex], pivotValue)>=0)){
				downIndex--;
				compare++; //comparison is made
			}
			
			if (upIndex < downIndex) { // both elements were found
				temp = s[downIndex]; 
				s[downIndex] = s[upIndex]; // swap these elements
				move++; //one element is moved
				s[upIndex] = temp;
				move++; //second element is moved
			}
		} // the loop continues until the indices cross
		
		int pivotIndex = upIndex;
		temp = s[rightBound]; // swap pivot with the element at upIndex
		s[rightBound] = s[pivotIndex]; 
		move++; //one element is moved
		s[pivotIndex] = temp; 
		move++; //second element is moved
		
		// the pivot is now at upIndex, so recursively quicksort each side
		quickSortStep(s, c, leftBound, pivotIndex-1);
		quickSortStep(s, c, pivotIndex+1, rightBound);
	}

	
	private static void swapElements(Object[] s, int element1, int element2) {
		Object temp = s[element1];
		s[element1] = s[element2];
		s[element2] = temp;
		//method to switch elements in array
	}

	/** Main method to test QuickSort */
	public static void main(String[] args)
	{
		/*
		JFrame frame = new JFrame("Size of array"); 
	    //creates a frame for us to ask the user to input a value
	    String noStrings = JOptionPane.showInputDialog(frame, "What do you want the size of the array to be?");
	    //makes a input box and question appear
	    int n = Integer.parseInt(noStrings); 
	    //converts the inputed value to an int
		String[] arr = createArray(n);
		//calls createArray method to set up an array. 
		
		JOptionPane.showMessageDialog(null, "Array length is "+ arr.length);
		//prints length

		long randT1 = System.currentTimeMillis();
		//starts a timer
		quickSort(arr, new StringComparator());
		//calls quicksort
		long randT2 = System.currentTimeMillis();
		//ends timer
		//JOptionPane.showMessageDialog(null, "After Sorting Random array with QuickSort:\n " + array2String(arr));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user value of compare and move counters
		JOptionPane.showMessageDialog(null, "The time for quickSort for a random array was\n " + (randT2 - randT1) + "mS");
		//prints out time
		
		String[] arrDes = orderDescending(arr, n);
		//creates descending array using the sorted array
		
		//prints array
		long ascT1 = System.currentTimeMillis();
		//starts timer
		quickSort(arr, new StringComparator());
		//passes in the already sorted array
		long ascT2 = System.currentTimeMillis();
		//ends timer
		//JOptionPane.showMessageDialog(null,  "After QuickSort:\n" + array2String(arr));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user value of compare and move counters
		JOptionPane.showMessageDialog(null, "The time for quickSort for an array in ascending order was\n " + (ascT2 - ascT1) + "mS");
		//prints time
		
		// quickSort method's first parameter is just the array; 
		// second is a newly created string comparator object (class defined later in this file)
		long desT1 = System.currentTimeMillis();
		//starts timer
		quickSort(arrDes, new StringComparator());
		//calls quickSort
		long desT2 = System.currentTimeMillis();
		//ends timer

		//JOptionPane.showMessageDialog(null, "Array after sorting:\n" + array2String(arrDes));
		//prints sorted array
		JOptionPane.showMessageDialog(null, "The value of the compare incrementer is:\n " + compare);
		JOptionPane.showMessageDialog(null, "The value of the move incrementer is:\n " + move);
		//tells user the value of the compare and move methods
		JOptionPane.showMessageDialog(null, "The time for quickSort in descending order was\n " + (desT2 - desT1) + "mS");
		//tells user the time taken */
		selectionSort();
		System.exit(0);
	}

	

	private static void selectionSort() {
		int a[] = {3, 9, 1, 11, 5, 2, 8, 4};
		int n = a.length;
		
		for(int j=n-1; j>=1; j--){
			int MaxPos = 0;
			for(int i=1; i<=j-1; i++){
				if(a[i]>a[MaxPos]){
					MaxPos = i;
				}
			}
			if(a[MaxPos]>a[j]){
				int temp = a[MaxPos];
				a[MaxPos] = a[j];
				a[j] = temp;
			}
		}
		for(int y=0; y<=n-1; y++){
			System.out.println(a[y]);
		}
	}

	private static String[] orderDescending(String[] arr, int n) {
		//passes in sorted array and size n
		String refArr[];
		refArr = new String[n];
		//creates reference array to pass back
		int j = 0;
		//creates incrementer
		for(int i = n-1; i>=0; i--){
			//creates decrementer
			refArr[i] = arr[j];
			//starts at the rightbound element of refArr
			//passes in the leftbound element of the sorted array
			//incrementer and decrementer change
			j++; //increase j
		}
		return refArr; //return new array
	}

	private static String[] createArray(int n) {
		String refArr[];
		refArr = new String[n];
		//creates referene array
		for(int i = 0; i < n; i++) {
			//cycles through array values until value of n
			refArr[i]= String.valueOf(Math.random());
			//adds random numbers to array
		}
		return refArr; //returns array
	}

	/** utility method to return string representation of array of strings */
	private static String array2String(String[] a)
	{
		String text="[";
		for (int i=0; i<a.length; i++) {
			text += a[i];
			if (i<a.length-1)
				text += ",";
		}
		text += "]";
		return text;
	}
}

/** Comparator class for case-insensitive comaprison of strings */
class StringComparator implements Comparator
{
	public int compare(Object ob1, Object ob2)
	{
		String s1 = (String)ob1;
		String s2 = (String)ob2;
		//return s1.compareTo(s2); // use compareTo for case-sensitive comparison
		return s1.compareToIgnoreCase(s2);
	}
}