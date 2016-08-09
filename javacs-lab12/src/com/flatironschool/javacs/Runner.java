package com.flatironschool.javacs;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;


import redis.clients.jedis.Jedis;

public class Runner {
	Jedis jedis;
	JedisIndex index;
	WikiSearch search;
	public Runner() throws IOException {
		// make a JedisIndex
		jedis = JedisMaker.make();
		index = new JedisIndex(jedis);	
		
	}
	
	public List<Entry<String, Integer>> search(String term) {
		search = WikiSearch.search(term, index);
		return search.sort();
	}
	
	public void print() {
		search.print();
	}

}
