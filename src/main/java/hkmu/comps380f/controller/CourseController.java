/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.controller;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Issac
 */

// Add course(s)
@Controller 
@RequestMapping("/course") 
public class CourseController {
    private volatile long COURSE_ID_SEQUENCE = 1; 
    private Map<Long, Course> courseDatabase = new ConcurrentHashMap<>(); 
 
    // Controller methods, Form object, ... 
    @GetMapping(value = {"", "/listCourses"}) 
    public String list(ModelMap model) { 
        model.addAttribute("courseDatabase", courseDatabase); 
        return "listCourses"; 
    } 
    
    @GetMapping("/create") 
    public ModelAndView create() { 
        return new ModelAndView("addCourse", "courseForm", new Form()); 
    } 

    public static class Form { 
        private String customerName; 
        private String subject; 
        private String body; 
        private List<MultipartFile> attachments; 
        
        // Getters and Setters of customerName, subject, body, attachments
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
        
        public String getCustomerName() {
            return customerName;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }
    }
    
    @PostMapping("/create") 
    public View create(Form form) throws IOException { 
        Course course = new Course(); 
        course.setId(this.getNextCourseId()); 
        course.setCustomerName(form.getCustomerName()); 
        course.setSubject(form.getSubject()); 
        course.setBody(form.getBody()); 

        for (MultipartFile filePart : form.getAttachments()) { 
            Attachment attachment = new Attachment(); 
            attachment.setName(filePart.getOriginalFilename()); 
            attachment.setMimeContentType(filePart.getContentType()); 
            attachment.setContents(filePart.getBytes()); 
            if (attachment.getName() != null && attachment.getName().length() > 0 
                && attachment.getContents() != null && attachment.getContents().length > 0) 
                course.addAttachment(attachment); 
        } 
        this.courseDatabase.put(course.getId(), course); 
        return new RedirectView("/course/viewCourse/" + course.getId(), true); 
    } 

    private synchronized long getNextCourseId() { 
        return this.COURSE_ID_SEQUENCE++; 
    }
    
    @GetMapping("/viewCourse/{courseId}") 
    public String view(@PathVariable("courseId") long courseId,  
                       ModelMap model) { 
        Course course = this.courseDatabase.get(courseId); 
        if (course == null) { 
            return "redirect:/course/listCourses"; 
        } 
        model.addAttribute("courseId", courseId); 
        model.addAttribute("course", course); 
        return "viewCourse"; 
    }
    
    @GetMapping("/{courseId}/attachment/{attachment:.+}") 
    public View download(@PathVariable("courseId") long courseId, 
                         @PathVariable("attachment") String name) { 
        Course course = this.courseDatabase.get(courseId); 
        if (course != null) { 
            Attachment attachment = course.getAttachment(name); 
            if (attachment != null) 
                return new DownloadingView(attachment.getName(), 
                        attachment.getMimeContentType(), attachment.getContents()); 
        } 
        return new RedirectView("/course/listCourses", true); 
    } 
}
