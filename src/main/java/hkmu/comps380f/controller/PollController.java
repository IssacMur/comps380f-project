/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PollRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;





/**
 *
 * @author Issac
 */
@Controller 
@RequestMapping("/course") 
public class PollController {
    private PollRepository pollRepo; 
    
    @GetMapping("/createPoll")
    public ModelAndView create() {
        return new ModelAndView("addPoll", "pollForm", new Form());
    }
    
        public static class Form {

        private String question;
        private String radio;

        // Getters and Setters of subject, body, attachments
        public String getQuestion() {
            return question;
        }

        public void setQestion(String question) {
            this.question = question;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }
    }
/*
    @PostMapping("/createPoll") 
    public String create(PollController.Form form, Principal principal) throws IOException { 
    long pollId = pollRepo.createPoll(principal.getName(), 
        form.getRadio(), form.getRadio());
        return "redirect:/course/viewLecture/" + pollId; 
    }
        */
}
