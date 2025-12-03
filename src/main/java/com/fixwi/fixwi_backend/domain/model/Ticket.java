package com.fixwi.fixwi_backend.domain.model;

import java.time.LocalDateTime;

public class Ticket {
    private Long id;
    private User user;
    private String description;
    private String title;
    private Status status;
    private Category category;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Ticket(Long id, User user, String description, String title, Status status, Category category, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.title = title;
        this.status = status;
        this.category = category;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
