package tracker.projectissue.dao;

import java.sql.SQLException;
import java.util.Collection;
import tracker.dao.GenericDao;
import tracker.projectissue.entity.IssueComment;
import tracker.projectissue.entity.ProjectIssue;
import tracker.userproject.entity.UserProjectDetail;

public interface ProjectIssueDao extends GenericDao<ProjectIssue> {
public Collection<ProjectIssue> findIssueAssignedProject (int id) throws SQLException;
public Collection<UserProjectDetail> findUserAssignedProject (int id)throws SQLException;
public void saveData(ProjectIssue projectissue, int id) throws SQLException;
public Collection<IssueComment> issueComment(int id)throws SQLException;
public Collection<ProjectIssue> titleAndUser(int id) throws SQLException;

}
