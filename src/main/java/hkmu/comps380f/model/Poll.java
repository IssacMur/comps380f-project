/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

import java.io.Serializable;

/**
 *
 * @author Cheuk
 */
public class Poll implements Serializable{
    private String subjectId;
    private String subjectName;
    private String studentId;
    private String studentName;
    private String vote;
 
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubject(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setVote(String vote){
        this.vote = vote;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubject() {
        return subjectName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getVote(){
        return vote;
    }
}