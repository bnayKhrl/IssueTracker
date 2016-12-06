package tracker.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import tracker.database.Database;
import tracker.task.entity.Task;

public class TaskDaoImpl implements TaskDao {

	@Override
	public Collection<Task> findAll() throws SQLException {
		List<Task> tasklist = new ArrayList<>();
		String sql = "select * from task";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setName(rs.getString("name"));
			task.setStartdate(rs.getTime("startdate"));
			tasklist.add(task);
		}
		return tasklist;
	}

	@Override
	public void saveData(List<Task> tasks) throws SQLException {
		
		Connection conn = Database.getConnection();
		String sql = "Insert into task (name, startdate) values (?,?)";
		for (Task task : tasks) {
				
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, task.getName());
			stmt.setTime(2, task.getStartdate());
			stmt.executeUpdate();
			stmt.close();
					
		}
		
	}

	@Override
	public Collection<Task> findByTime()  throws SQLException {
		Date date = new Date();
		Time timeStamp = new Time(date.getHours(), date.getMinutes(), date.getSeconds());
		System.out.println(timeStamp);	
		List<Task> tasklist = new ArrayList<>();
		Connection conn = Database.getConnection();
		String sql = "select id, name from task where startdate = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setTime(1, timeStamp);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setName(rs.getString("name"));
			tasklist.add(task);
		}
		
		return tasklist;
	
	}
	
	
	
}
