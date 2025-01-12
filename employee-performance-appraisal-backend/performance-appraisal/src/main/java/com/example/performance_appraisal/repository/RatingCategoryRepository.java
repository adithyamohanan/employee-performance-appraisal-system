package com.example.performance_appraisal.repository;

import com.example.performance_appraisal.model.RatingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingCategoryRepository extends JpaRepository<RatingCategory, String> {
}
