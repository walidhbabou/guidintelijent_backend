package com.example.coreservice.service;

import com.example.coreservice.entity.Review;
import com.example.coreservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findByPlaceId(Long placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }

    public Review update(Long id, Review review) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        existing.setComment(review.getComment());
        existing.setRating(review.getRating());
        return reviewRepository.save(existing);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
