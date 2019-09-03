package it.polito.tdp.food.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.food.model.Food;

public class TestDao {

	public static void main(String[] args) {
		FoodDao dao = new FoodDao();
		Map<Integer, Food> map=new HashMap<>();
		
		System.out.println("Printing all the condiments...");
		//System.out.println(dao.listAllCondiments());
		
		System.out.println("Printing all the foods...");
		dao.listAllFoodsbyPortion(1, map);
		System.out.println(dao.listacibicomuni().toString());
		System.out.println(map.values().toString());
		System.out.println(map.values().size());
		
		System.out.println("Printing all the portions...");
		//System.out.println(dao.listAllPortions());
	}

}
