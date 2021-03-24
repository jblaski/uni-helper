package com.julianblaskiewicz.unihelper.repository;
import com.julianblaskiewicz.unihelper.entity.LearningProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningProviderRepository extends JpaRepository<LearningProvider, Long> {
}
