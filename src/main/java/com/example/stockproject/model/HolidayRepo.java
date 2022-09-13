package com.example.stockproject.model;

import com.example.stockproject.model.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepo extends JpaRepository<Holiday, Integer> {

    Holiday findByHoliday(String holiday);
}
