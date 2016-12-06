package tracker.project.dao;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import tracker.database.Database;
import tracker.project.entity.Project;

public class ProjectDaoImpl implements ProjectDao {

	public void save(Project project) throws SQLException {
		String sql = "insert into projects(title,startdate,deadlinedate) values(?,?,?)";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, project.getTitle());
		stmt.setDate(2, Date.valueOf(project.getStartdate()));
		stmt.setDate(3, Date.valueOf(project.getDeadlinedate()));
		stmt.execute();
		stmt.close();
	}

	@Override
	public int delete(Project account) throws SQLException {
		String commandString = "delete from projects where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, account.getId());
		stmt.executeUpdate();
		stmt.close();
		return 1;
	}

	@Override
	public int edit(Project account) throws SQLException {
		String commandString = "update projects set title=?,startdate=?,deadlinedate=? where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setString(1, account.getTitle());
		stmt.setDate(2, Date.valueOf(account.getStartdate()));
		stmt.setDate(3, Date.valueOf(account.getDeadlinedate()));
		stmt.setInt(4, account.getId());
		stmt.executeUpdate();
		stmt.close();
		return 1;
	}

	@Override
	public Collection<Project> findAll() throws SQLException {
		List<Project> projectList = new ArrayList<>();
		String sql = "select * from projects";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Project project = new Project();
			project.setId(rs.getInt("id"));
			project.setTitle(rs.getString("title"));
			project.setStartdate(rs.getDate("startdate").toLocalDate());
			project.setDeadlinedate(rs.getDate("deadlinedate").toLocalDate());
			projectList.add(project);
		}
		return projectList;
	}

	@Override
	public Project findById(Integer id) throws SQLException {
		String commandString = "select * from projects where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Project project = new Project();
		if (rs.next()) {
			project.setId(rs.getInt("id"));
			project.setTitle(rs.getString("title"));
			project.setStartdate(rs.getDate("startdate").toLocalDate());
			project.setDeadlinedate(rs.getDate("deadlinedate").toLocalDate());
		}
		return project;
	}

	@Override
	public PdfPTable reportGenerator() throws SQLException, FileNotFoundException, DocumentException {
		String sql = "select * from projects";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		PdfPTable table = new PdfPTable(4);
		table.setTotalWidth(new float[] {50,320,100,100});
		table.setLockedWidth(true);
		PdfPCell tableCell;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
		for (int i = 0; i < 1; i++) {
			table.addCell("Id");
			table.addCell("Title");
			table.addCell("Startdate");
			table.addCell("Deadline");
		}
		
		table.setHeaderRows(1);
		int counter =1;
		while(rs.next()){
			String str = Integer.toString(counter);
			tableCell = new PdfPCell(new Phrase(str));
			table.addCell(tableCell);
						
			String title = rs.getString("title");
			tableCell = new PdfPCell(new Phrase(title));
			table.addCell(tableCell);
			
			Date startDate = rs.getDate("startdate");
			String start = df.format(startDate);
			tableCell = new PdfPCell(new Phrase(start));
			table.addCell(tableCell);
			
			Date deadline = rs.getDate("deadlinedate");
			String end = df.format(deadline);
			tableCell = new PdfPCell(new Phrase(end));
			table.addCell(tableCell);
			counter++;
					
		}
		rs.close();
		stmt.close();
		conn.close();
		return table;

	}

}