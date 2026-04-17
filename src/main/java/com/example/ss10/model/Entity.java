package com.example.ss10.model;

public class Entity {
    private int id;
    private String name;
    private ItemType type;
    private String imageUrl;
    private int stock; // Nếu là phòng Lab thì stock thường là 1
    private String description;

    public Entity() {
    }

    public Entity(int id, String name, ItemType type, String imageUrl, int stock, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", imageUrl='" + imageUrl + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}
