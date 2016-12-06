package tracker.task.entity;

import java.sql.Date;
import java.sql.Time;

public class Task {
	
	private int id;
	private String name;
	private Time startdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getStartdate() {
		return startdate;
	}
	public void setStartdate(Time startdate) {
		this.startdate = startdate;
	}
}
