package com.detox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DetoxController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScreenTimeRepository screenTimeRepository;

    private final Analyzer analyzer = new Analyzer();
    private final DetoxScoreCalculator scoreCalculator = new DetoxScoreCalculator();

    @GetMapping("/hello")
    public String hello() {
        return "Digital Detox Tracker API is running!";
    }

    @PostMapping("/log")
    public Map<String, Object> logScreenTime(@RequestBody Map<String, Object> payload) {
        User user = getCurrentUser();
        
        int study = (int) payload.getOrDefault("study", 0);
        int social = (int) payload.getOrDefault("social", 0);
        int entertainment = (int) payload.getOrDefault("entertainment", 0);
        int peakHour = (int) payload.getOrDefault("peakHour", 0);

        ScreenTimeRecord record = new ScreenTimeRecord(user, study, social, entertainment, peakHour);
        
        String pattern = analyzer.analyzePattern(record, user);
        int score = scoreCalculator.calculateScore(record, user, pattern);

        screenTimeRepository.save(record);

        return Map.of(
            "status", "success",
            "message", "Screen time logged successfully",
            "score", score,
            "user", Map.of(
                "username", user.getUsername(),
                "name", user.getName(),
                "dailySafeLimit", user.getDailySafeLimit()
            )
        );
    }

    @GetMapping("/history")
    public List<ScreenTimeRecord> getHistory() {
        User user = getCurrentUser();
        return screenTimeRepository.findByUserOrderByDateDesc(user);
    }

    private User getCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow();
    }
}
