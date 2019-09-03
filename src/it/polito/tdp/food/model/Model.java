package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Map<Integer, Food> mappacibi;
	private FoodDao dao;
	private List<Food> listacibi;
	List<InComune> caloriemax;
	
	
	public Model() {
		this.dao= new FoodDao();
	}

	public void creagrafo(int porzione) {
		this.mappacibi= new HashMap<>();
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		listacibi=this.dao.listAllFoodsbyPortion(porzione, mappacibi);	
		Graphs.addAllVertices(this.grafo, mappacibi.values());
		for(InComune c: this.dao.listacibicomuni()) {
			Food partenza=mappacibi.get(c.getCibo1());
			Food arrivo=mappacibi.get(c.getCibo2());
			if(partenza!=null && arrivo!=null)
				if (!this.grafo.containsEdge(partenza, arrivo)&& !this.grafo.containsEdge(arrivo,partenza))
					Graphs.addEdge(this.grafo, mappacibi.get(c.getCibo1()), mappacibi.get(c.getCibo2()), c.getMedia());
			}
		}	

	public int getnvertici() {
		return this.grafo.vertexSet().size();
	}
	public int getnarchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Food> getvertici() {
		return listacibi;
	}

	public List<InComune> getcaloriemax(Food scelta) {
		caloriemax=new LinkedList<>();
		for( Food f: Graphs.neighborListOf(this.grafo, scelta)) {
			for(InComune c: this.dao.listacibicomuni()) {
				if((f.getFood_code()==c.getCibo1()&& scelta.getFood_code()==c.getCibo2())||
						(f.getFood_code()==c.getCibo2()&& scelta.getFood_code()==c.getCibo1())) {
					caloriemax.add(c);
				}
			}
		}
		Collections.sort(caloriemax);
		return caloriemax;
	}
	public Food prendivicino(Food scelta, InComune c) {
			if(c.getCibo1()==scelta.getFood_code())
				return mappacibi.get(c.getCibo2());
			else if(c.getCibo2()==scelta.getFood_code())
				return mappacibi.get(c.getCibo1());
			return null;
	}
}
