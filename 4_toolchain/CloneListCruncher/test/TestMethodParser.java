/*
 * @author Jan
 */
package test;

import control.MethodParser;

public class TestMethodParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String path = "D:/gitRepos/func-clones/study-objects/GoogleCodeJamC/1/src/080.c";
		String method = "gcd";
		
		MethodParser mp = new MethodParser();
		
		
		if(mp.isInsideBounds(method, path,1,31)){
			System.out.println("Jup");
		}
		
		System.out.println("start: "+mp.getStartLineOfFunc()+" end: "+mp.getEndLineOfFunc());
		
		
	}

}
