package com.ra.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category implements IEntity{
    private int id;
    private  String name;
    private String keyword;
    private String description;
    private int status;

    public Category() {
    }

    public Category(int id, String name, String keyword, String description, int status) {
        this.id = id;
        this.name = name;
        this.keyword = keyword;
        this.description = description;
        this.status = status;
    }

    public Category(String name, String keyword, String description, int status) {
        this.name = name;
        this.keyword = keyword;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public void init(ResultSet rs) throws SQLException {
        setId(rs.getInt("CategoryID"));
        setName(rs.getString("CategoryName"));
        setDescription(rs.getString("Description"));
        setStatus(rs.getInt("Status"));
    }
}
