package com.detox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScreenTimeRepository extends JpaRepository<ScreenTimeRecord, Long> {
    List<ScreenTimeRecord> findByUserOrderByDateDesc(User user);
}
