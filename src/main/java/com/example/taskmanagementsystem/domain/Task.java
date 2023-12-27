package com.example.taskmanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1105122041958251257L;
    @JsonManagedReference
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(name = "descr")
    private String description;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private User author;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performerId")
    private User performer;
    @Column(columnDefinition = "enum", name = "stage")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Status status;
    @Column(columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Priority priority;
    @CreationTimestamp
    @DateTimeFormat(pattern = Utils.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    @Transient
    private transient Task previousState;

    public Task() {
    }

    public Task(String title, User author) {
        this.title = title;
        this.author = author;
    }

    public Task(String name, User author, Status status) {
        this.title = name;
        this.author = author;
        this.status = status;
    }

    public Task(int id, String name, String description, User author, User performer, Status status, Priority priority) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.author = author;
        this.performer = performer;
        this.status = status;
        this.priority = priority;
    }

    @PostLoad
    private void setPreviousState() {
        this.previousState = SerializationUtils.clone(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Task getPreviousState() {
        return previousState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && Objects.equals(description, task.description)
                && author.equals(task.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, author);
    }
}
