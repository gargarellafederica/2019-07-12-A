package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.InComune;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> listAllFoodsbyPortion(int porzione, Map<Integer, Food> idmappa){
		String sql = "SELECT f.food_code, f.display_name " + 
				"FROM food f, portion p " + 
				"WHERE f.food_code=p.food_code " + 
				"GROUP BY f.food_code, f.display_name " + 
				"HAVING COUNT( p.portion_id) <= ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, porzione);
			List<Food> lista= new ArrayList<>();
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idmappa.get(res.getInt("food_code"))==null) {
						Food cibo= new Food (res.getInt("food_code"),
									res.getString("display_name"));
						idmappa.put(cibo.getFood_code(), cibo);
						lista.add(cibo);
					}
					else {
						lista.add(idmappa.get(res.getInt("food_code")));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<InComune> listacibicomuni(){
		String sql = "SELECT distinct fc1.food_code as cibo1,fc2.food_code as cibo2, AVG(c.condiment_calories)AS media " + 
				"FROM condiment c,food_condiment fc1, food_condiment fc2 " + 
				"WHERE fc1.food_code<fc2.food_code " + 
				"AND fc1.condiment_code=fc2.condiment_code " + 
				"AND c.condiment_code=fc1.condiment_code " + 
				"GROUP BY fc1.food_code, fc2.food_code " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<InComune> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new InComune(res.getInt("cibo1"),
							res.getInt("cibo2"), 
							res.getDouble("media")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
}
