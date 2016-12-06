package tracker.dao;

import java.sql.SQLException;
import java.util.Collection;

import tracker.project.entity.Project;

public interface GenericDao<T> {
	public void save(T account) throws SQLException;

	public int delete(T account) throws SQLException;

	public int edit(T account) throws SQLException;

	public Collection<T> findAll() throws SQLException;

	public T findById (Integer id) throws SQLException;

}