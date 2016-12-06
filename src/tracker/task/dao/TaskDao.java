package tracker.task.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import tracker.dao.GenericDao;
import tracker.project.entity.Project;
import tracker.task.entity.Task;

public interface TaskDao{
	
	public Collection<Task> findAll() throws SQLException;
	public void saveData(List<Task> task) throws SQLException;
	public Collection <Task> findByTime() throws SQLException;
	
	
}
