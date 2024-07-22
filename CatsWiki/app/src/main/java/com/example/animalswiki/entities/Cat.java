package com.example.animalswiki.entities;

import com.google.gson.annotations.SerializedName;

public class Cat {
    @SerializedName("weight")
    private Weight weight;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("temperament")
    private String temperament;
    @SerializedName("origin")
    private String origin;
    @SerializedName("description")
    private String description;
    @SerializedName("life_span")
    private String lifeSpan;
    @SerializedName("affection_level")
    private int affection;
    @SerializedName("dog_friendly")
    private int dog;
    @SerializedName("child_friendly")
    private int child;
    @SerializedName("energy_level")
    private int energy;

    public Cat(
            Weight weight,
            String id,
            String name,
            String temperament,
            String origin,
            String description,
            String lifeSpan,
            int affection,
            int dog,
            int child,
            int energy
    ) {
        this.weight = weight;
        this.id = id;
        this.name = name;
        this.temperament = temperament;
        this.origin = origin;
        this.description = description;
        this.lifeSpan = lifeSpan;
        this.affection = affection;
        this.dog = dog;
        this.child = child;
        this.energy = energy;
    }

    public Weight getWeight() {
        return weight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDescription() {
        return description;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public int getAffection() {
        return affection;
    }

    public int getDog() {
        return dog;
    }

    public int getChild() {
        return child;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "weight=" + weight +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", temperament='" + temperament + '\'' +
                ", origin='" + origin + '\'' +
                ", description='" + description + '\'' +
                ", lifeSpan='" + lifeSpan + '\'' +
                ", affection=" + affection +
                ", dog=" + dog +
                ", child=" + child +
                ", energy=" + energy +
                '}';
    }
}
