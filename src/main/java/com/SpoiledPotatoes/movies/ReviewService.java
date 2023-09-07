package com.SpoiledPotatoes.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId){
        // data pushed into database is returned to the review object
        Review review = reviewRepository.insert(new Review(reviewBody));

        // mongoTemplate is used to update the movie using the imdbId with the new review pushed into the array using the repository
        mongoTemplate.update(Movie.class).matching(Criteria.where("imdbId").is(imdbId)).apply(new Update().push("reviewIds").value(review)).first();

        return review;
    }
}
