package com.spring_boot.CSTS.model;

public class CategoryDTO {
    public CategoryDTO(Long id,String name){
        this.id=id;
        this.name=name;
    }

    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
