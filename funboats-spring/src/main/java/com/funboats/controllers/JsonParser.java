package com.funboats.controllers;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author David Godi
 *
 */
public class JsonParser {
	private Map<Integer, String> map = new HashMap<>();
	
	JsonParser(String jsonObject){
		parseObject(jsonObject);
	}
	
	public void set(String jsonObject){
		parseObject(jsonObject);
	}
	
	private void parseObject(String jsonObject){
		 System.out.println(" JsonParser  " + jsonObject);
		for (String retval: jsonObject.split(":")){
			System.out.println(" JsonParser 1  " + jsonObject);
			char[] symbols = {'"', '{', '}'};
	         int i = 0;
	         for(int j = 0; j < 3; j++, i++)
	        	 retval = retval.replace(String.valueOf(symbols[j]), "");

	         map.put(i, retval); 
	         i++;
	    }
	}
	
	public String get(){
		return map.get(3);
	}
	
	public String get(int index){
		return map.get(index);
	}
}
