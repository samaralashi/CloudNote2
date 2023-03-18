package com.example.cloudnote22;

class Note {
    String id;
    String titleN;
    String contentN;

    private Note() {}
    Note(String id, String title, String content) {
        this.id = id;
        this.titleN = title;
        this.contentN = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return titleN;
    }

    public void setTitle(String title) {
        this.titleN = title;
    }

    public String getContent() {
        return contentN;
    }

    public void setContent(String content) {
        this.contentN = content;
    }


}
