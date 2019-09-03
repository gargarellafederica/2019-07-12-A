package it.polito.tdp.food.model;

public class InComune implements Comparable<InComune>{
	private int cibo1;
	private int cibo2;
	private double media;
	
	public InComune(int cibo1, int cibo2, double media) {
		super();
		this.cibo1 = cibo1;
		this.cibo2 = cibo2;
		this.media = media;
	}
	
	public int getCibo1() {
		return cibo1;
	}
	public void setCibo1(int cibo1) {
		this.cibo1 = cibo1;
	}
	public int getCibo2() {
		return cibo2;
	}
	public void setCibo2(int cibo2) {
		this.cibo2 = cibo2;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}

	@Override
	public int compareTo(InComune o) {
		return Double.compare(this.media, o.getMedia());
	}


}
