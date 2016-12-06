package tracker.project.entity;

import java.time.LocalDate;

public class Project {
	
	private Integer id;
	private String title;
	private LocalDate startdate;
	private LocalDate deadlinedate;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getDeadlinedate() {
		return deadlinedate;
	}
	public void setDeadlinedate(LocalDate deadlinedate) {
		this.deadlinedate = deadlinedate;
	}
	

}
