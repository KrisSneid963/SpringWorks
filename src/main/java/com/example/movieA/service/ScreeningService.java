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

    // all screenings
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    // by id
    public Optional<Screening> getScreeningById(Long id) {
        return screeningRepository.findById(id);
    }

    // new screening
    public Screening saveScreening(Screening screening) {
        return screeningRepository.save(screening);
    }

    //update screening
    public Screening updateScreening(Long id, Screening updatedScreening) {
        return screeningRepository.findById(id).map(existingScreening -> {
            existingScreening.setScreeningDate(updatedScreening.getScreeningDate());
            existingScreening.setLocation(updatedScreening.getLocation());
            return screeningRepository.save(existingScreening);
        }).orElseThrow(() -> new RuntimeException("Screening not found with id: " + id));
    }


    public boolean deleteScreening(Long id) {
        if (screeningRepository.existsById(id)) {
            screeningRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
