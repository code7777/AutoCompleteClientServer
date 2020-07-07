
import java.util.Arrays;
import java.util.Comparator;
import java.io.*;
import java.lang.*;
/*
Author Preston Porter
Class CS404
Homework 3


// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe.
*/ 
public class Autocomplete {
    private final Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        //...
        if (terms == null) throw new NullPointerException("Terms can't be null");
        this.terms = new Term[terms.length];
    	for (int i = 0; i < terms.length; i++) 
            {this.terms[i] = terms[i];}
        
            Arrays.sort(this.terms);
        
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
       
        if (prefix == null) throw new NullPointerException("Prefix can't be null");
    	/*for (int i = 0; i < terms.length; i++) {
            StringBuilder sb =new StringBuilder(s);  this.terms[i].replaceAll("\\d+","");
        }*/
    	int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    	if (firstIndex == -1) return new Term[0];
    	int lastIndex  = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    	Term[] matchTerms = new Term[1 + lastIndex - firstIndex];
    	
    	for (int i = 0; i < matchTerms.length; i++)
    		matchTerms[i] = terms[firstIndex++];

    	Arrays.sort(matchTerms, Term.byReverseWeightOrder());
    	
		return matchTerms;

    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        //...
        if (prefix == null) throw new NullPointerException("Prefix can't be null");
		return 1 + 
				BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())) - 
				BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    }

   
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) throws IOException{
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                System.out.println(results[i]);
            }
        }
    }
}
