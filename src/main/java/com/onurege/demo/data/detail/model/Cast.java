package com.onurege.demo.data.detail.model;

public class Cast {
    private Integer id;
    private String name;
    private String genderRole;
    private String character;
    private String profilePath;

    public Cast() {
    }

    public Cast(Integer id, String name, String genderRole, String character, String profilePath) {
        this.id = id;
        this.name = name;
        this.genderRole = genderRole;
        this.character = character;
        this.profilePath = profilePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenderRole() {
        return genderRole;
    }

    public void setGenderRole(String genderRole) {
        this.genderRole = genderRole;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
