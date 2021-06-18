package com.ljp.utils;

import java.util.ArrayList;
import java.util.List;

public class MyCollenctionUtils {
	
	public static <T> List<T> addSomeToList(List<T> list,int start, int size){
		if(list==null||size < 1) {
			return null;
		}
		List<T> result =new ArrayList<T>(size);
		int end=size+start;
		for(int i=start;i<list.size()&&i<end;i++) {
			result.add(list.get(i));
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<String> list=new ArrayList<>();
		for(int i=0;i<24;i++) {
			list.add(""+i);
		}
		
		List<String> addSomeToList = addSomeToList(list,20,10);
		System.out.println(addSomeToList);
	}
}
