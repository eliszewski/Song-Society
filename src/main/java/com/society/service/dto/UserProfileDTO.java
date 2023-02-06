package com.society.service.dto;

public class UserProfileDTO {

    private String login;
    private String societyTag;

    public UserProfileDTO(String login, String societyTag) {
        this.login = login;
        this.societyTag = societyTag;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSocietyTag() {
        return societyTag;
    }

    public void setSocietyTag(String societyTag) {
        this.societyTag = societyTag;
    }
}
