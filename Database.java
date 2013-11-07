package com.thumbtack.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	// Main database.
	Map<String,Integer> map = new HashMap<String, Integer>();
	
	// Local transactional database will be stored in 'transactions'.
	List<HashMap<String, Integer>> transactions = new ArrayList<HashMap<String, Integer>>();
	
	Map<Integer, Integer> count = new HashMap<Integer, Integer>();
	
	int tranNum = 0;
	
	// O(1)
	void set (String key, Integer value) {
		if (tranNum > 0) {
			transactions.get(transactions.size()-1).put(key, map.get(key));
			if (count.get(map.get(key)) != null)
				count.put(map.get(key), count.get(map.get(key))-1);
		}
		if (count.get(map.get(key)) != null)
			count.put(map.get(key), count.get(map.get(key))-1);
		map.put(key, value);
		if (value != null)
			valCount(value);
	}
	
	void valCount (int value) {
		if (count.get(value) != null)
			count.put(value, count.get(value)+1);
		else
			count.put(value, 1);
	}
	
	// O(1)
	int get (String key) {
		return map.get(key);
	}
	
	// O(1)
	void unset (String key) {
		if (tranNum > 0) {
			transactions.get(transactions.size()-1).put(key, map.get(key));
		}
		count.put(map.get(key), count.get(map.get(key))-1);
		map.remove(key);
	}
	
	// O(1)
	int numEqualTo (int value) {
		return count.get(value);
	}
	
	// O(1)
	void begin () {
		transactions.add(new HashMap<String, Integer>());
		tranNum = transactions.size();
	}
	
	// Worst case can be O(n) only if all the values are being changed in a transaction.
	String rollback () {
		if (tranNum == 0)
			return "NO TRANSACTION\n";
		else{
			if (transactions.get(transactions.size()-1) != null)
				for (Map.Entry<String, Integer> entry : transactions.get(transactions.size()-1).entrySet()){
					this.set(entry.getKey(),entry.getValue());
				}
			transactions.remove(--tranNum);
		}
		return "";
	}
	
	// O(1)
	String commit () {
		if (tranNum == 0)
			return "NO TRANSACTION\n";
		else{
			transactions = null;
			tranNum = 0;
		}
		return "";
	}

}
