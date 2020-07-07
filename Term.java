
import java.util.Arrays;
import java.util.Comparator;





/*

Author Preston Porter
Class CS404
Homework 3


*/
// An immutable data type that represents an autocomplete term: a query string 
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    // Construct a term given the associated query string, having weight 0.
    public Term(String query) {
        //...
        if (query == null) throw new NullPointerException("Query cannot be null");
        this.query = query;
        this.weight = 0;

    }

    // Construct a term given the associated query string and weight.
    public Term(String query, long weight) {
        //...
        
        if (query == null) throw new NullPointerException("Query cannot be null");
        if (weight < 0) throw new IllegalArgumentException("weight must not be less than 0");
        else {
		this.query = query;
        this.weight = weight;
        }
    }

    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        //...
        return new ReverseWeightOrder();
    }

    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
          //  ...
          int value;
           if(v.weight == w.weight){value = 0;}
           else{
               value = (v.weight > w.weight)? -1: 1;
            }
          
          return value;
        }
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int r) {
        //...
        if(r < 0)//check if the search term is valid
        {
            throw new IllegalArgumentException();
        }
        return new PrefixOrder(r);
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r;
        private String prefixV;
        private String prefixW;
        private PrefixOrder(int r) {
            //...
            this.r = r;
        }

        public int compare(Term v, Term w) {
          //  ...
          
          prefixV = (v.query.length() < r)? v.query : v.query.substring(0, r);
          prefixW = (w.query.length() < r)? w.query : w.query.substring(0, r);
		

			return prefixV.compareTo(prefixW);
        }
    
    }

    // Compare this term to that in lexicographic order by query and 
    // return a negative, zero, or positive integer based on whether this 
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {
        //...
        return this.query.compareTo(that.query);
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        System.out.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
        System.out.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
    }
}
