package com.detox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tracker orchestrates a full session:
 * input → analyse → alert → save → report.
 */
@Service
public class Tracker {

    @Autowired
    private ScreenTimeRepository screenTimeRepository;

    @Autowired
    private UserRepository userRepository;

    private final Analyzer analyzer = new Analyzer();
    private final AlertSystem alertSystem = new AlertSystem();
    private final ReportGenerator reporter = new ReportGenerator();

    /**
     * Runs the full tracking pipeline for one day's entry.
     *
     * @param user            the active user
     * @param studyTime       minutes of study screen time
     * @param socialTime      minutes of social-media screen time
     * @param entertainmentTime minutes of entertainment screen time
     * @param peakUsageHour   hour (0-23) when usage was highest
     */
    public void processDay(User user, int studyTime, int socialTime,
                           int entertainmentTime, int peakUsageHour) {

        // 1. Build the record
        ScreenTimeRecord record = new ScreenTimeRecord(
            user, studyTime, socialTime, entertainmentTime, peakUsageHour
        );

        // 2. Analyse pattern
        String pattern = analyzer.analyzePattern(record, user);

        // 3. Show alerts
        alertSystem.checkAndAlert(record, user);

        // 4. Save to database
        screenTimeRepository.save(record);

        // 5. Calculate streak (Simple JPA-based streak calculation)
        int streak = calculateStreak(user);

        // 6. Print full report
        reporter.printDailyReport(record, user, streak);
    }

    private int calculateStreak(User user) {
        // This is a simplified version for the report
        return screenTimeRepository.findByUserOrderByDateDesc(user).size();
    }
}
