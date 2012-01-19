package me.longerian.interactive.demo;

public class RowModel {

	private String label; 
    private float rating=2.0f; 
 
    public RowModel(String label) {
    	this.label=label; 
    } 
 
    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String toString() { 
    	if (rating>=3.0) { 
    		return(label.toUpperCase()); 
    	} 
    	return(label); 
    }

}
