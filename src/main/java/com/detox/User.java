package com.detox;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an application user and their screen time preferences.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private int dailySafeLimit;   // in minutes
    private int sleepStartHour;   // 24-hour format
    private int sleepEndHour;     // 24-hour format

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScreenTimeRecord> records = new ArrayList<>();

    public User() {} // Default constructor for JPA

    public User(String username, String password, String name, int dailySafeLimit, int sleepStartHour, int sleepEndHour) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dailySafeLimit = dailySafeLimit;
        this.sleepStartHour = sleepStartHour;
        this.sleepEndHour = sleepEndHour;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName()       { return name; }
    public void setName(String name)               { this.name = name; }

    public int getDailySafeLimit() { return dailySafeLimit; }
    public void setDailySafeLimit(int limit)       { this.dailySafeLimit = limit; }

    public int getSleepStartHour() { return sleepStartHour; }
    public void setSleepStartHour(int hour)        { this.sleepStartHour = hour; }

    public int getSleepEndHour()   { return sleepEndHour; }
    public void setSleepEndHour(int hour)          { this.sleepEndHour = hour; }

    public List<ScreenTimeRecord> getRecords() { return records; }
    public void setRecords(List<ScreenTimeRecord> records) { this.records = records; }

    @Override
    public String toString() {
        return String.format(
            "User: %s (%s) | Safe Limit: %d min | Sleep: %02d:00 - %02d:00",
            name, username, dailySafeLimit, sleepStartHour, sleepEndHour
        );
    }
}
