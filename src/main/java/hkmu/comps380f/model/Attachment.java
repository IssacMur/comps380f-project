/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.model;

import java.io.Serializable;

/**
 *
 * @author Issac
 */
public class Attachment implements Serializable { 
    private String name; 
    private String mimeContentType; 
    private byte[] contents; 
 
    // Getters and Setters of name, mimeContentType, contents 

    public void setName(String name) {
        this.name = name;
    }

    public void setMimeContentType(String mimeContentType) {
        this.mimeContentType = mimeContentType;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getMimeContentType() {
        return mimeContentType;
    }

    public byte[] getContents() {
        return contents;
    }
    
} 
