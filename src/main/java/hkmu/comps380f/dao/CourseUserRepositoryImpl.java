/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Issac
 */
@Repository 
public class CourseUserRepositoryImpl implements CourseUserRepository { 

    private final JdbcOperations jdbcOp; 

    @Autowired 
    public CourseUserRepositoryImpl(DataSource dataSource) { 
        jdbcOp = new JdbcTemplate(dataSource); 
    }
    
    private static final class UserExtractor implements ResultSetExtractor<List<CourseUser>> { 
        @Override 
        public List<CourseUser> extractData(ResultSet rs)  
                throws SQLException, DataAccessException { 
            Map<String, CourseUser> map = new HashMap<>(); 
            while (rs.next()) { 
                String username = rs.getString("username"); 
                CourseUser user = map.get(username); 
                if (user == null) { 
                    user = new CourseUser(); 
                    user.setUsername(username); 
                    user.setPassword(rs.getString("password")); 
                    map.put(username, user); 
                } 
                user.getRoles().add(rs.getString("role")); 
            } 
            return new ArrayList<>(map.values()); 
        } 
    }
    
    @Override 
    @Transactional 
    public void save(CourseUser user) { 
        final String SQL_INSERT_USER 
                = "insert into users (username, password) values (?, ?)"; 
        final String SQL_INSERT_ROLE 
                = "insert into user_roles (username, role) values (?, ?)"; 
        jdbcOp.update(SQL_INSERT_USER, user.getUsername(), user.getPassword()); 
        System.out.println("User " + user.getUsername() + " inserted"); 

        for (String role : user.getRoles()) { 
            jdbcOp.update(SQL_INSERT_ROLE, user.getUsername(), role); 
            System.out.println("User_role " + role + " of user "  
                    + user.getUsername() + " inserted"); 
        } 
    } 
 
    @Override 
    @Transactional(readOnly = true) 
    public List<CourseUser> findAll() { 
        final String SQL_SELECT_USERS 
                = "select users.*, user_roles.role from users, user_roles" 
                + " where users.username = user_roles.username"; 
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor()); 
    }
    
    @Override 
    @Transactional(readOnly = true) 
    public List<CourseUser> findById(String username) { 
        final String SQL_SELECT_USER 
                = "select users.*, user_roles.role from users, user_roles" 
                + " where users.username = user_roles.username" 
                + " and users.username = ?"; 
        return jdbcOp.query(SQL_SELECT_USER, new UserExtractor(), username); 
    }

    @Override 
    @Transactional 
    public void delete(String username) { 
        final String SQL_DELETE_USER = "delete from users where username=?"; 
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?"; 
        jdbcOp.update(SQL_DELETE_ROLES, username); 
        jdbcOp.update(SQL_DELETE_USER, username); 
    }
}