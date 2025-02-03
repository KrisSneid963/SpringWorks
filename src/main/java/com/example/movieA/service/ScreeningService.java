package com.example.movieA.service;

import com.example.movieA.model.Screening;
import com.example.movieA.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public Optional<Screening> getScreeningById(Long id) {
        return screeningRepository.findById(id);
    }

    public Screening saveScreening(Screening screening) {
        return screeningRepository.save(screening);
    }

    public boolean deleteScreening(Long id) {
        screeningRepository.deleteById(id);
        return false;
    }
}
