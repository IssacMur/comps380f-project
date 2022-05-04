package hkmu.comps380f.dao;

import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Course;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PollRepository { 
    public long createPoll(String pollName, String question, String radio,  
                             List<MultipartFile> attachments) throws IOException; 
    public List<Course> getPolls(); 
    public List<Course> getPoll(long id); 
    public void updatePoll(long poll_id, String question, String radio,          
                             List<MultipartFile> attachments) throws IOException; 
    public void deletePoll(long id); 
}