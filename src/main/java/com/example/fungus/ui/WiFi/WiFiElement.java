package com.example.fungus.ui.WiFi;

public class WiFiElement {
    private String name;
    private String protection;
    private String level;

    public WiFiElement(String name, String protection, String level) {
        this.name = name;
        this.protection = protection;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getProtection() {
        return protection;
    }

    public String getLevel() {
        return level;
    }
}
