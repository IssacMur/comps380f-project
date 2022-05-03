/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.dao;

import hkmu.comps380f.model.CourseUser;
import java.util.List;

/**
 *
 * @author Issac
 */
public interface CourseUserRepository { 
    public void save(CourseUser user); 
    public List<CourseUser> findAll(); 
    public List<CourseUser> findById(String username); 
    public void delete(String username); 
}