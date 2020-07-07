/*
Author Preston Porter
Class CS404
Homework 3


*/
import java.util.Arrays;
import java.util.Comparator;

// Implements binary search for clients that may want to know the index of 
// either the first or last key in a (sorted) collection of keys.
public class BinarySearchDeluxe {
    // The index of the first key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {

                                            if (a.length <= 0)//check for invalid length
                                            {
                                                return -1;
                                            }
                                            if(a == null || key == null|| comparator == null){
                                                throw new NullPointerException("cannot be null");
                                            }
                                int min = 0;
                                int max = a.length - 1;
                                int middle; 
            while (min <= max)
             {                       
                     middle = (max - min) / 2 + min;
            
                if (comparator.compare(key, a[middle]) > 0)//passed key is greater than key in
                {                                          //the middle position
                    min = middle + 1;
                }
                else if (comparator.compare(key, a[middle]) < 0)//passed key is less than key in
                {                                               //the middle position
                    max = middle - 1;
                } 
            else                                       //passed key is equal to the key in
            {                                          //the middle position
                
                if (middle == 0)//check for the first position of the array
                {
                    return middle;
                } 
                else if (comparator.compare(key, a[middle - 1]) > 0)//check if position a[middle--] is also
                                                                       //greater than the passed key
                {
                    return middle;
                } 
                else    //the previous position is also equal or greater than the passed key
                {
                    max = middle - 1;//set max to middle-- and go through the checks again
                }//end inner if else loop
            }//end outer if else loop
        }//end while
        //...
        return -1;
    }

    // The index of the last key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, 
                                        Comparator<Key> comparator) {
        //...
        if (a.length <= 0)//check for invalid length
                                            {
                                                return -1;
                                            }
                                            if(a == null || key == null|| comparator == null){
                                                throw new NullPointerException("cannot be null");
                                            }
        int min = 0;
        int max = a.length;
        int middle;
        while (min <= max)
        {
            middle = (max - min) / 2 + min;
            if (comparator.compare(key, a[middle]) > 0)//passed key is greater than key in
            {                                          //the middle position 
                min = middle + 1;
            }
            else if (comparator.compare(key, a[middle]) < 0)//passed key is less than key in
            {                                               //the middle position
                max = middle - 1;
            } 
            else        //passed key is equal to the key in
            {          //the middle position
                
                if (middle == a.length) //is this the last pos in the array?                  
                {                                       
                    return middle;
                }
                else if (comparator.compare(key, a[middle + 1]) < 0)//is the pos next position greater than the key?
                {
                    return middle;
                } 
                else                        //the next position is also equal or less than
                {                            
                    min = middle + 1;  //set min for another run through to check the next higher position
                }//end inner if else loop
            }//end outer if else loop
        }//end while
        return -1;
    }//end lastIndexOf()
    

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight);  
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        System.out.println(count);
    }
}
