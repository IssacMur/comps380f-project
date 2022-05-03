package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CourseRepository;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import hkmu.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

@Controller 
@RequestMapping("/course") 
public class CourseController { 
    @Resource 
    private CourseRepository courseRepo; 
    /*
    private volatile long COURSE_ID_SEQUENCE = 1;
    private Map<Long, Course> courseDatabase = new ConcurrentHashMap<>();
*/
            
    // Controller methods, Form object, ...
    @GetMapping({"", "/list"}) 
    public String list(ModelMap model) { 
        model.addAttribute("courseDatabase", courseRepo.getCourses()); 
        return "list"; 
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "courseForm", new Form());
    }

    public static class Form {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;

        // Getters and Setters of subject, body, attachments
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

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/create") 
     public String create(Form form, Principal principal) throws IOException { 
     long courseId = courseRepo.createCourse(principal.getName(), 
                form.getSubject(), form.getBody(), form.getAttachments()); 
        return "redirect:/course/view/" + courseId; 
    } 

    @GetMapping("/view/{courseId}") 
    public String view(@PathVariable("courseId") long courseId, ModelMap model) { 
        List<Course> courses = courseRepo.getCourse(courseId); 
        if (courses.isEmpty()) { 
            return "redirect:/course/list"; 
        } 
        model.addAttribute("courseId", courseId); 
        model.addAttribute("course", courses.get(0)); 
        return "view"; 
    } 

    @GetMapping("/{courseId}/attachment/{attachment:.+}") 
    public View download(@PathVariable("courseId") long courseId, 
            @PathVariable("attachment") String name) { 
        Attachment attachment = courseRepo.getAttachment(courseId, name); 
        if (attachment != null) { 
            return new DownloadingView(attachment.getName(), 
                    attachment.getMimeContentType(), attachment.getContents()); 
        } 
        return new RedirectView("/course/list", true); 
    } 

    @GetMapping("/{courseId}/delete/{attachment:.+}") 
    public String deleteAttachment(@PathVariable("courseId") long courseId, 
            @PathVariable("attachment") String name) { 
        courseRepo.deleteAttachment(courseId, name); 
        return "redirect:/course/edit/" + courseId; 
    } 

    @GetMapping("/delete/{courseId}") 
    public String deleteCourse(@PathVariable("courseId") long courseId) { 
        courseRepo.deleteCourse(courseId); 
        return "redirect:/course/list"; 
    }
    
    @GetMapping("/edit/{courseId}") 
    public ModelAndView showEdit(@PathVariable("courseId") long courseId, 
            Principal principal, HttpServletRequest request) { 
        List<Course> courses = courseRepo.getCourse(courseId); 
        if (courses.isEmpty() 
                || (!request.isUserInRole("ROLE_ADMIN") 
                && !principal.getName().equals(courses.get(0).getLecturerName()))) { 
            return new ModelAndView(new RedirectView("/course/list", true)); 
        } 
        Course course = courses.get(0); 
        ModelAndView modelAndView = new ModelAndView("edit"); 
        modelAndView.addObject("courseId", courseId); 
        modelAndView.addObject("course", course); 
        Form courseForm = new Form(); 
        courseForm.setSubject(course.getSubject()); 
        courseForm.setBody(course.getBody()); 
        modelAndView.addObject("courseForm", courseForm); 
        return modelAndView; 
    } 

    @PostMapping("/edit/{courseId}") 
    public String edit(@PathVariable("courseId") long courseId, Form form, 
            Principal principal, HttpServletRequest request) throws IOException { 
        List<Course> courses = courseRepo.getCourse(courseId); 
        if (courses.isEmpty() 
                || (!request.isUserInRole("ROLE_ADMIN") 
                && !principal.getName().equals(courses.get(0).getLecturerName()))) { 
            return "redirect:/course/list"; 
        } 
        courseRepo.updateCourse(courseId, form.getSubject(), 
                form.getBody(), form.getAttachments()); 
        return "redirect:/course/view/" + courseId; 
    }
}
