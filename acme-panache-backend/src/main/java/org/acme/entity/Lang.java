package org.acme.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "lang")
public class Lang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "langId", nullable = false)
    private Integer langId;

    @Size(max = 2)
    @NotNull
    @Column(columnDefinition = "char", name = "code", nullable = false, length = 2)
    private String code;

    @Size(max = 50)
    @Column(name = "description", length = 50)
    private String description;

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}