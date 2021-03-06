package catdata;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jparsec.error.Location;
import org.jparsec.error.ParseErrorDetails;
import org.jparsec.error.ParserException;


public class Program<X> implements Prog {
	
	public synchronized Map<X, String> invert() {
		Map<X, String> inv = new HashMap<>(exps.size());
		for (String x : exps.keySet()) {
			inv.put(exps.get(x), x);
		}
		return inv;
	}
	
		
	public long timeout() {
		
		return 30;
	}
	
		public final List<String> order = Collections.synchronizedList(new LinkedList<>());
	/*
	'lines' is keyed by the names from order and the value is the 
	offset from the beginning of the program to the name of the expression.
	The 'lines' is a bit of a misnomer as there is an entry for 
	expression and the value is measured in characters.
	*/
	public final Map<String, Integer> lines = Collections.synchronizedMap(new HashMap<>());
	/*
	'expr' contains the expressions.
	There are many types of expressions, each one has its own 
	data structure.
	see catadata.aql.exp 
	*/
	public final Map<String, X> exps = Collections.synchronizedMap(new HashMap<>());
	/*
	'options' is a dictionary of the global options.
	note: options are not considered expressions.
	*/
	/* 
	'text' is a copy of the original program.
	*/
	private final String text;
	
	@Override
	public String toString() {
		String ret = "";
		for (String s : order) {
			ret += s + " = " + exps.get(s) + "\n\n";
		}
		return ret;
	}
	
	public Function<X, String> kindOf;
	
	@Override 
	public String kind(String s) {
		return kindOf.apply(exps.get(s));
	}
	
	public Program(List<Triple<String, Integer, X>> decls, String text) {
		this(decls, text, Collections.emptyList(), x -> "");
	}
	/**
	 * The main program constructor.
	 * 
	 * @param decls 
	 * @param text
	 * @param options
	 * @param k
	 */
	public Program(List<Triple<String, Integer, X>> decls, String text, List<Pair<String, String>> options, Function<X, String> k) {
		this.text = text;
		List<Triple<String, Integer, X>> seen = new LinkedList<>();
		for (Triple<String, Integer, X> decl : decls) { 
			if (decl.second == null || decl.third == null) {
				Util.anomaly();
			}
			checkDup(seen, decl);
			X x = decl.third;
			exps.put(decl.first, x);
			lines.put(decl.first, decl.second);
			order.add(decl.first);			
			if (!decl.third.equals(decl.third)) {
				throw new RuntimeException("Please report: non-refexlive: " + decl.third.toString());
			}
			// log.info(decl.toString());
		}
		this.kindOf = k;
		
	}

	private Location conv(int i) {
		int c = 1;
		int line = 1, col = 1;
		while (c++ <= i) {
		  if (text.charAt(c) == '\n') {
		    ++line;
		    col = 1;
		  } else {
		    ++col;
		  }
		}
		return new Location(line, col);
	}
	
	@SuppressWarnings("deprecation")
	private void checkDup(List<Triple<String, Integer, X>> seen, Triple<String, Integer, X> toAdd) {
		for (Triple<String, Integer, X> other : seen) {
			if (other.first.equals(toAdd.first)) {
				if (text == null) {
					throw new RuntimeException("Duplicate name: " + toAdd.first); //TODO AQL + " on line " + other.second + " and " + toAdd.second);
				}
				throw new ParserException(new ParseErrorDetails() {

					@Override
					public String getEncountered() {
						return other.first;
					}

					@Override
					public List<String> getExpected() {
						return new LinkedList<>();
					}

					@Override
					public String getFailureMessage() {
						return "Other occurance: line " + conv(other.second).line + ", column " + conv(other.second).column;
					}

					@Override
					public int getIndex() {
						return other.second;
					}

					@Override
					public String getUnexpected() {
						return "";
					}}, "Duplicate name: " + toAdd.first, conv(toAdd.second)); //TODO AQL );

			}
		}
		seen.add(toAdd);
	}

	@Override
	public Integer getLine(String s) {
		return lines.get(s);
	}

	@Override
	public Collection<String> keySet() {
		return order;
	}


}
