/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.view;

import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;

/**
 *
 * @author Issac
 */
public class DownloadingView implements View { 
    private final String filename; 
    private final String contentType; 
    private final byte[] contents; 
 
    public DownloadingView(String filename, String contentType, byte[] contents) { 
        this.filename = filename; 
        this.contentType = contentType; 
        this.contents = contents; 
    } 
 
    @Override 
    public String getContentType() { 
        return this.contentType; 
    } 
 
    @Override 
    public void render(Map<String, ?> model, HttpServletRequest request, 
                   HttpServletResponse response) throws Exception { 
        response.setHeader("Content-Disposition", "attachment; filename=" + this.filename); 
        response.setContentType("application/octet-stream"); 
 
        ServletOutputStream stream = response.getOutputStream(); 
        stream.write(this.contents); 
    } 
}