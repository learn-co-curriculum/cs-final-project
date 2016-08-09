package com.flatironschool.javacs;

import redis.clients.jedis.Jedis;

// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;
import view.MainFrame;
import java.lang.Math;

/**
 * Represents the results of a search query.
 *
 */
public class WikiSearch {
	
	// map from URLs that contain the term(s) to relevance score
	private Map<String, Integer> map;
	private Jedis jedis;
	private JedisIndex index;

	/**
	 * Constructor.
	 * 
	 * @param map
	 */
	public WikiSearch(Map<String, Integer> map) {
		this.map = map;
	}
	
	/**
	 * Looks up the relevance of a given URL.
	 * 
	 * @param url
	 * @return
	 */
	public Integer getRelevance(String url) {

		Integer relevance = map.get(url);
		return relevance==null ? 0: relevance;
		/* double tf = relevance ? 1 + log(relevance) : 0;
		double idf = 1;
		return (int) Math.round(tf*idf); */
	}
	
	/**
	 * Prints the contents in order of term frequency.
	 * 
	 * @param map
	 */
	public void print() {
		List<Entry<String, Integer>> entries = sort();
		for (Entry<String, Integer> entry: entries) {
			System.out.println(entry);
		}
	}
	
	/**
	 * Computes the union of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch or(WikiSearch that) {
		WikiSearch w = new WikiSearch(this.map);
		for (Map.Entry<String, Integer> e : that.map.entrySet()) {
			//if this map contains a key that that map contains
			if (this.map.containsKey(e.getKey())) {
				w.map.put(e.getKey(), w.map.get(e.getKey()) + that.map.get(e.getKey()));
			} else { //otherwise, only that map contains the key. 
				w.map.put(e.getKey(), that.map.get(e.getKey()));
			}
		}
		return w;
	}
	
	/**
	 * Computes the intersection of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch and(WikiSearch that) {
		Map m = new HashMap();
		for (Map.Entry<String, Integer> e : this.map.entrySet()) {
			//if this map contains a key that that map contains
			if (that.map.containsKey(e.getKey())) {
				m.put(e.getKey(), this.map.get(e.getKey()) + that.map.get(e.getKey()));
			}
		}
		WikiSearch w = new WikiSearch(m);
		return w;
	}
	
	/**
	 * Computes the intersection of two search results.
	 * 
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch minus(WikiSearch that) {
		Map m = new HashMap();
		for (Map.Entry<String, Integer> e : this.map.entrySet()) {
			//if this map contains a key that that map contains
			if (that.map.containsKey(e.getKey()) == false) {
				m.put(e.getKey(), this.map.get(e.getKey())/* + that.map.get(e.getKey())*/);
			}
		}
		WikiSearch w = new WikiSearch(m);
		return w;
	}
	
	/**
	 * Computes the relevance of a search with multiple terms.
	 * 
	 * @param rel1: relevance score for the first search
	 * @param rel2: relevance score for the second search
	 * @return
	 */
	protected int totalRelevance(Integer rel1, Integer rel2) {
		// simple starting place: relevance is the sum of the term frequencies.
		return rel1 + rel2;
	}

	/**
	 * Sort the results by relevance.
	 * 
	 * @return List of entries with URL and relevance.
	 */
	public List<Entry<String, Integer>> sort() {
 
        List<Entry<String, Integer>> l = new LinkedList<Entry<String, Integer>>(this.map.entrySet());
        /*
        for (Map.Entry<String, Integer> e : this.map.entrySet()) {
        	//Entry<String, Integer> n = new Entry<String, Integer>(e.getKey(), e.getValue());
        	l.add(e);
        }
        */
        Comparator<Entry<String, Integer>> comparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
            	if (getRelevance(e1.getKey()) < getRelevance(e2.getKey())) {
                    return -1;
                }
            	if (getRelevance(e2.getKey()) < getRelevance(e1.getKey())) {
                    return 1;
                }
                return 0;
            }
        };
        
       Collections.sort(l, comparator);

        // System.out.println("sorted = " + l);
		return l;
	}

	/**
	 * Performs a search and makes a WikiSearch object.
	 * 
	 * @param term
	 * @param index
	 * @return
	 */
	public static WikiSearch search(String term, JedisIndex index) {
		Map<String, Integer> map = index.getCounts(term);
		return new WikiSearch(map);
	}

	public void start() throws IOException {
		// make a JedisIndex

		jedis = JedisMaker.make();
		index = new JedisIndex(jedis); 
	}
	
	public void search(String term) {
		WikiSearch search = search(term, index);
		search.sort();
	}
	
//	public static void main(String[] args) throws IOException {
//		
//		// make a JedisIndex
//		Jedis jedis = JedisMaker.make();
//		JedisIndex index = new JedisIndex(jedis); 
//		
//		// search for the first term
//		String term1 = "java";
//		System.out.println("Query: " + term1);
//		WikiSearch search1 = search(term1, index);
//		search1.print();
//		
//		// search for the second term
//		String term2 = "programming";
//		System.out.println("Query: " + term2);
//		WikiSearch search2 = search(term2, index);
//		search2.print();
//		
//		// compute the intersection of the searches
//		System.out.println("Query: " + term1 + " AND " + term2);
//		WikiSearch intersection = search1.and(search2);
//		intersection.print();
//	}
}
