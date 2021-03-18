package com.example.myapplication1;

public class Word {

    private String word;
    private String defaultTranslation;
    private int resourceID ;
    private int audioID;
    private static final int NO_IMAGE_PROVIDED = -1;


    //Constructor for the Class
    public  Word(String defaultTranslation, String word, int aId) {
        this.word = word;
        this.defaultTranslation = defaultTranslation;
        this.resourceID = -1;
        this.audioID = aId;
    }

    //constructor with imageid
    public  Word(String defaultTranslation, String word, int Rid, int aId) {
        this.word = word;
        this.defaultTranslation = defaultTranslation;
        this.resourceID = Rid;
        this.audioID = aId;
    }

    //getter for word in native language
    public String getWord() {
        return this.word;
    }

    //getter for word in default language
    public String getDefaultTranslation() {
        return this.defaultTranslation;
    }

    public int getImageResourceId(){
        return resourceID;
    }

    public int getAudioID(){
        return audioID;
    }
    //Since the words will have same meaning no setter is required for change.

    public boolean hasImage(){
        return (resourceID != NO_IMAGE_PROVIDED);
    }

    @Override
    public String toString() {
        return "Word{"+"defaultTranslation='" + getDefaultTranslation() + '\'' +
                ", word='" + getWord() + '\'' +
                ", resourceID=" + getImageResourceId() +
                ", audioID=" + getAudioID()+
                '}';
    }
}
