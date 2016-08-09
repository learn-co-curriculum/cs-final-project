package com.flatironschool.javacs;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;


import redis.clients.jedis.Jedis;

public class Runner {
	Jedis jedis;
	JedisIndex index;
	WikiSearch search;
	Stemmer stemmer;
	public Runner() throws IOException {
		// make a JedisIndex
		jedis = JedisMaker.make();
		index = new JedisIndex(jedis);	
		stemmer = new Stemmer();
		
	}
	
	public List<Entry<String, Integer>> search(String term) {
		search = WikiSearch.search(stemText(term), index);
		return search.sort();
	}
	
	private String stemText(String text) {
		stemmer.add(text.toCharArray(), text.length());
		stemmer.stem();
		System.out.println("Runner : " + stemmer.toString());
		return stemmer.toString();
	}
	
	public void print() {
		search.print();
	}

}
