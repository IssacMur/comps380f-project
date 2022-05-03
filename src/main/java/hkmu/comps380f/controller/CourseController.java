package hkmu.comps380f.controller;

import hkmu.comps380f.dao.LectureRepository;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Lecture;
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
    private LectureRepository lectureRepo; 
    /*
    private volatile long COURSE_ID_SEQUENCE = 1;
    private Map<Long, Course> courseDatabase = new ConcurrentHashMap<>();
*/
            
    // Controller methods, Form object, ...
    @GetMapping({"", "/listLecture"}) 
    public String list(ModelMap model) { 
        model.addAttribute("lectureDatabase", lectureRepo.getLectures()); 
        return "listLecture"; 
    }

    @GetMapping("/createLecture")
    public ModelAndView create() {
        return new ModelAndView("addLecture", "lectureForm", new Form());
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

    @PostMapping("/createLecture") 
     public String create(Form form, Principal principal) throws IOException { 
     long lectureId = lectureRepo.createLecture(principal.getName(), 
                form.getSubject(), form.getBody(), form.getAttachments()); 
        return "redirect:/course/viewLecture/" + lectureId; 
    } 

    @GetMapping("/viewLecture/{lectureId}") 
    public String view(@PathVariable("lectureId") long lectureId, ModelMap model) { 
        List<Lecture> lectures = lectureRepo.getLecture(lectureId); 
        if (lectures.isEmpty()) { 
            return "redirect:/course/listLecture"; 
        } 
        model.addAttribute("lectureId", lectureId); 
        model.addAttribute("lecture", lectures.get(0)); 
        return "viewLecture"; 
    } 

    @GetMapping("/{lectureId}/attachment/{attachment:.+}") 
    public View download(@PathVariable("lectureId") long lectureId, 
            @PathVariable("attachment") String name) { 
        Attachment attachment = lectureRepo.getAttachment(lectureId, name); 
        if (attachment != null) { 
            return new DownloadingView(attachment.getName(), 
                    attachment.getMimeContentType(), attachment.getContents()); 
        } 
        return new RedirectView("/course/listLecture", true); 
    } 

    @GetMapping("/{lectureId}/delete/{attachment:.+}") 
    public String deleteAttachment(@PathVariable("lectureId") long lectureId, 
            @PathVariable("attachment") String name) { 
        lectureRepo.deleteAttachment(lectureId, name); 
        return "redirect:/course/editLecture/" + lectureId; 
    } 

    @GetMapping("/delete/{lectureId}") 
    public String deleteLecture(@PathVariable("lectureId") long lectureId) { 
        lectureRepo.deleteLecture(lectureId); 
        return "redirect:/course/listLecture"; 
    }
    
    // show course edit page and edit it
    @GetMapping("/editLecture/{lectureId}") 
    public ModelAndView showEdit(@PathVariable("lectureId") long lectureId, 
            Principal principal, HttpServletRequest request) { 
        List<Lecture> lectures = lectureRepo.getLecture(lectureId); 
        if (lectures.isEmpty() 
                || (!request.isUserInRole("ROLE_ADMIN") 
                && !principal.getName().equals(lectures.get(0).getLecturerName()))) { 
            return new ModelAndView(new RedirectView("/course/listLecture", true)); 
        } 
        Lecture lecture = lectures.get(0); 
        ModelAndView modelAndView = new ModelAndView("editLecture"); 
        modelAndView.addObject("lectureId", lectureId); 
        modelAndView.addObject("lecture", lecture); 
        Form lectureForm = new Form(); 
        lectureForm.setSubject(lecture.getSubject()); 
        lectureForm.setBody(lecture.getBody()); 
        modelAndView.addObject("lectureForm", lectureForm); 
        return modelAndView; 
    } 

    // post edited course
    @PostMapping("/editLecture/{lectureId}") 
    public String edit(@PathVariable("lectureId") long lectureId, Form form, 
            Principal principal, HttpServletRequest request) throws IOException { 
        List<Lecture> lectures = lectureRepo.getLecture(lectureId); 
        if (lectures.isEmpty() 
                || (!request.isUserInRole("ROLE_ADMIN") 
                && !principal.getName().equals(lectures.get(0).getLecturerName()))) { 
            return "redirect:/course/listLecture"; 
        } 
        lectureRepo.updateLecture(lectureId, form.getSubject(), 
                form.getBody(), form.getAttachments()); 
        return "redirect:/course/viewLecture/" + lectureId; 
    }
}
