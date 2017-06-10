package com.routeadvisor.app.service.impl;

import com.routeadvisor.app.domain.Review;
import com.routeadvisor.app.repository.ReviewRepository;
import com.routeadvisor.app.service.ReviewService;
import com.routeadvisor.app.service.dto.ReviewDTO;
import com.routeadvisor.app.service.mapper.ReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Review.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        ReviewDTO result = reviewMapper.toDto(review);
        return result;
    }

    /**
     * Get all the reviews.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reviews");
        Page<Review> result = reviewRepository.findAll(pageable);
        return result.map(review -> reviewMapper.toDto(review));
    }

    /**
     * Get one review by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public ReviewDTO findOne(String id) {
        log.debug("Request to get Review : {}", id);
        Review review = reviewRepository.findOne(id);
        ReviewDTO reviewDTO = reviewMapper.toDto(review);
        return reviewDTO;
    }

    /**
     * Delete the  review by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.delete(id);
    }
}
