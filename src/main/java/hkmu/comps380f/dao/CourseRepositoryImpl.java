/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Issac
 */
@Repository 
public class CourseRepositoryImpl implements CourseRepository { 
    private final JdbcOperations jdbcOp; 
    @Autowired 
    public CourseRepositoryImpl(DataSource dataSource) { 
        jdbcOp = new JdbcTemplate(dataSource); 
    } 
    
    private static final class CourseExtractor implements ResultSetExtractor<List<Course>> { 
        @Override 
        public List<Course> extractData(ResultSet rs) throws SQLException, DataAccessException { 
            Map<Long, Course> map = new HashMap<>(); 
            while (rs.next()) { 
                Long id = rs.getLong("id"); 
                Course course = map.get(id); 
                if (course == null) { 
                    course = new Course(); 
                    course.setId(id); 
                    course.setLecturerName(rs.getString("name")); 
                    course.setSubject(rs.getString("subject")); 
                    course.setBody(rs.getString("body")); 
                    map.put(id, course); 
                } 
                String filename = rs.getString("filename"); 
                if (filename != null) { 
                    Attachment attachment = new Attachment(); 
                    attachment.setName(rs.getString("filename")); 
                    attachment.setMimeContentType(rs.getString("content_type")); 
                    attachment.setLectureId(id); 
                    course.addAttachment(attachment); 
                } 
            } 
            return new ArrayList<>(map.values()); 
        } 
    }
    
    @Override 
    @Transactional 
    public long createCourse(final String lecturerName, final String subject,  
    final String body, List<MultipartFile> attachments) 
            throws IOException { 
        final String SQL_INSERT_COURSE
                = "insert into course (name, subject, body) values (?, ?, ?)"; 
        final String SQL_INSERT_ATTACHMENT 
                = "insert into attachment (filename, content, content_type," 
                   + " course_id) values (?, ?, ?, ?)"; 

        KeyHolder keyHolder = new GeneratedKeyHolder(); 
        jdbcOp.update(new PreparedStatementCreator() { 
            @Override 
            public PreparedStatement createPreparedStatement(Connection connection) 
                 throws SQLException { 
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_COURSE,  
                                                                   new String[] {"id"}); 
                ps.setString(1, lecturerName); 
                ps.setString(2, subject); 
                ps.setString(3, body); 
                return ps; 
            } 
        }, keyHolder); 

        Long course_id = keyHolder.getKey().longValue(); 
        System.out.println("Course " + course_id + " inserted"); 

        for (MultipartFile filePart : attachments) { 
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler(); 
                jdbcOp.update(SQL_INSERT_ATTACHMENT, 
                        new Object[]{filePart.getOriginalFilename(), 
                            new SqlLobValue(filePart.getInputStream(),  
                                            (int) filePart.getSize(), handler), 
                            filePart.getContentType(), 
                            course_id}, 
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR,Types.INTEGER}); 
                System.out.println("Attachment " + filePart.getOriginalFilename() 
                                    + " of Course " + course_id + " inserted"); 
            } 
        } 
        return course_id; 
    }
    
    @Override 
    @Transactional(readOnly = true) 
    public List<Course> getCourses() {
        final String SQL_SELECT_COURSES = "select t.*, a.filename, a.content_type," 
                + " a.content from course as t left join attachment as a" 
                + " on t.id = a.course_id"; 
        return jdbcOp.query(SQL_SELECT_COURSES, new CourseExtractor()); 
    } 

    @Override 
    @Transactional(readOnly = true) 
    public List<Course> getCourse(long id) { 
        final String SQL_SELECT_COURSE = "select t.*, a.filename, a.content_type," 
                + " a.content from course as t left join attachment as a" 
                + " on t.id = a.course_id where t.id = ?"; 
        return jdbcOp.query(SQL_SELECT_COURSE, new CourseExtractor(), id); 
    }
    
    @Override 
    @Transactional 
    public void updateCourse(long course_id, String subject, String body,  
            List<MultipartFile> attachments) throws IOException { 
        final String SQL_UPDATE_COURSE = "update course set subject=?, body=? where id=?"; 
        final String SQL_INSERT_ATTACHMENT 
        = "insert into attachment (filename,content,content_type,course_id) values (?,?,?,?)"; 

        jdbcOp.update(SQL_UPDATE_COURSE, subject, body, course_id); 
        System.out.println("Course " + course_id + " updated"); 

        for (MultipartFile filePart : attachments) { 
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) { 
                LobHandler handler = new DefaultLobHandler(); 
                jdbcOp.update(SQL_INSERT_ATTACHMENT, 
                        new Object[]{filePart.getOriginalFilename(), 
                            new SqlLobValue(filePart.getInputStream(),  
    (int) filePart.getSize(), handler), 
                            filePart.getContentType(), 
                            course_id}, 
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.INTEGER}); 
                System.out.println("Attachment " + filePart.getOriginalFilename() 
                        + " of Course " + course_id + " inserted"); 
            } 
        } 
    }
    
    @Override 
    @Transactional 
    public void deleteCourse(long id) { 
        final String SQL_DELETE_COURSE = "delete from course where id=?"; 
        final String SQL_DELETE_ATTACHMENTS = "delete from attachment where course_id=?"; 
        jdbcOp.update(SQL_DELETE_ATTACHMENTS, id); 
        jdbcOp.update(SQL_DELETE_COURSE, id); 
        System.out.println("Course " + id + " deleted"); 
    } 

    @Override 
    @Transactional 
    public void deleteAttachment(long courseId, String name) { 
        final String SQL_DELETE_ATTACHMENT  
                 = "delete from attachment where course_id=? and filename=?"; 
        jdbcOp.update(SQL_DELETE_ATTACHMENT, courseId, name); 
        System.out.println("Attachment " + name + " of Course " + courseId + " deleted"); 
    }
    
    private static final class AttachmentRowMapper implements RowMapper<Attachment> { 
        @Override 
        public Attachment mapRow(ResultSet rs, int i) throws SQLException { 
            Attachment entry = new Attachment(); 
            entry.setName(rs.getString("filename")); 
            entry.setMimeContentType(rs.getString("content_type")); 
            Blob blob = rs.getBlob("content"); 
            byte[] bytes = blob.getBytes(1l, (int) blob.length()); 
            entry.setContents(bytes); 
            entry.setLectureId(rs.getInt("course_id")); 
            return entry; 
        } 
    } 

    @Override 
    @Transactional 
    public Attachment getAttachment(long courseId, String name) { 
        final String SQL_SELECT_ATTACHMENT = "select * from attachment where course_id=? and filename=?"; 
        return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT, new AttachmentRowMapper(), courseId, name); 
    }
}


