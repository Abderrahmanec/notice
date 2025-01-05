package org.bootstmytool.notizenbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class NoteDTO {

    private String title;
    private String content;
    private List<String> tags;

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }


}
