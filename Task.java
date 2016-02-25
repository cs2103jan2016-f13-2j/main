import java.io.Serializable;

public class Task implements Serializable {
	
	String title;
	//String category;
	//String location;
	int startDate;
	int endDate;
	//boolean repeat;
	//boolean alert;
	String notes;
	
	//Constructor
	public Task(String title, int startDate, int endDate, String notes) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.notes = notes;
	}
	
	
	//Get Functions
	
	public String getTitle() {
		return title;
	}
	
	public int getStartDate() {
		return startDate;
	}
	
	public int getEndDate() {
		return endDate;
	}
	
	public String getNotes() {
		return notes;
	}
	
	
	//Edit Functions
	
	public void updateTitle(String text) {
		title = text;
	}
	
	public void updateStartDate(int date) {
		startDate = date;
	}
	
	public void updateEndDate(int date) {
		endDate = date;
	}
	
	public void updateNotes(String text) {
		notes = text;
	}
}
