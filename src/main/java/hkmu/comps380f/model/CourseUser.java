/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Issac
 */
public class CourseUser implements Serializable { 
    private String username; 
    private String password; 
    private List<String> roles = new ArrayList<>(); 
 
    public CourseUser() {} 
 
    public CourseUser(String username, String password, String[] roles) { 
        this.username = username; 
        this.password = "{noop}" + password; 
        this.roles = new ArrayList<>(Arrays.asList(roles)); 
    } 
 
    // getters and setters of all properties 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
}
