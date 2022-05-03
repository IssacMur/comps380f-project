package hkmu.comps380f.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable { 
    private long id; 
    private String lecturerName; 
    private String subject; 
    private String body; 
    private final Map<String, Attachment> attachments = new HashMap<>(); 
 
    public void addAttachment(Attachment attachment) { 
        this.attachments.put(attachment.getName(), attachment); 
    } 
    public Collection<Attachment> getAttachments() { 
        return this.attachments.values(); 
    } 
    public int getNumberOfAttachments() { 
        return this.attachments.size(); 
    } 
 
    // getters and setters of all properties except attachments
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
} 