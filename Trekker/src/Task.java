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
	
	public Task(String title, int startDate, int endDate, String notes) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.notes = notes;
	}
	
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
}
