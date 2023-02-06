package com.society.service.dto;

public class UserProfileDTO {

    private String login;
    private String societyTag;
    private Long id;

    public UserProfileDTO(Long id, String login, String societyTag) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
