package com.detox;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Holds one day's screen-time data for a user.
 */
@Entity
@Table(name = "screen_time_records")
public class ScreenTimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int studyTime;         // minutes
    private int socialTime;        // minutes
    private int entertainmentTime; // minutes
    private int totalTime;         // computed
    private int peakUsageHour;     // 0-23, optional

    public ScreenTimeRecord() {} // Default constructor for JPA

    public ScreenTimeRecord(LocalDate date, User user, int studyTime, int socialTime,
                            int entertainmentTime, int totalTime, int peakUsageHour) {
        this.date = date;
        this.user = user;
        this.studyTime = studyTime;
        this.socialTime = socialTime;
        this.entertainmentTime = entertainmentTime;
        this.totalTime = totalTime;
        this.peakUsageHour = peakUsageHour;
    }

    public ScreenTimeRecord(User user, int studyTime, int socialTime,
                            int entertainmentTime, int peakUsageHour) {
        this(LocalDate.now(), user, studyTime, socialTime, entertainmentTime, 
             studyTime + socialTime + entertainmentTime, peakUsageHour);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate()           { return date; }
    public void setDate(LocalDate date)    { this.date = date; }

    public User getUser()                { return user; }
    public void setUser(User user)       { this.user = user; }

    public int getStudyTime()            { return studyTime; }
    public void setStudyTime(int time)   { this.studyTime = time; }

    public int getSocialTime()           { return socialTime; }
    public void setSocialTime(int time)  { this.socialTime = time; }

    public int getEntertainmentTime()    { return entertainmentTime; }
    public void setEntertainmentTime(int time) { this.entertainmentTime = time; }

    public int getTotalTime()            { return totalTime; }
    public void setTotalTime(int time)   { this.totalTime = time; }

    public int getPeakUsageHour()        { return peakUsageHour; }
    public void setPeakUsageHour(int hour) { this.peakUsageHour = hour; }

    @Override
    public String toString() {
        return String.format(
            "[%s] %s | Study: %d | Social: %d | Entertainment: %d | Total: %d min | Peak Hour: %02d:00",
            date, user != null ? user.getUsername() : "Unknown", studyTime, socialTime, entertainmentTime, totalTime, peakUsageHour
        );
    }
}
