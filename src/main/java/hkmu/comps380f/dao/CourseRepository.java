package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CourseRepository { 
    public long createCourse(String lecturerName, String subject, String body,  
                             List<MultipartFile> attachments) throws IOException; 
    public List<Course> getCourses(); 
    public List<Course> getCourse(long id); 
    public void updateCourse(long course_id, String subject, String body,          
                             List<MultipartFile> attachments) throws IOException; 
    public void deleteCourse(long id); 
    public void deleteAttachment(long courseId, String name); 
    public Attachment getAttachment(long courseId, String name); 
}