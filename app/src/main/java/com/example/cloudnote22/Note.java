package com.example.cloudnote22;

class Note {
    String id;
    String titleN;


    private Note() {}
    Note(String id, String title) {
        this.id = id;
        this.titleN = title;

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




}
