package tracker.project.dao;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import tracker.dao.GenericDao;
import tracker.project.entity.Project;

public interface ProjectDao extends GenericDao<Project> {

	public PdfPTable reportGenerator() throws SQLException, FileNotFoundException, DocumentException;
}
