package edu.sru.cpsc.webshopping.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

// This Entity is used to describe how good of a seller a particular user is
// RatingName will be a one or two word description of the user's rating
@Entity
public class SellerRating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NonNull
	private float rating;

	@NonNull
	private int numRatings;

	@OneToOne(mappedBy = "sellerRating")
    private User user;

	public SellerRating(User user) {
		this.user = user;
		this.rating = 0;
		this.numRatings = 0;
	}

	public SellerRating() {
		this.rating = 0;
		this.numRatings = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getRating() {
		return rating;
	}

	// Set rating with new incoming rating from a user. This will update the average rating.
	public void setRating(float newRating) {
		if (newRating < 0 || newRating > 5)
			throw new IllegalArgumentException("Rating must be between 0 and 5");
		setNumRatings(1 + getNumRatings());
		float newAvg = (rating + newRating) / getNumRatings();
		System.out.println("New rating: " + newAvg);
		this.rating = newAvg;
	}

	public int getNumRatings() {
		return numRatings;
	}

	public void setNumRatings(int numRatings) {
		if (numRatings < 0)
			throw new IllegalArgumentException("Number of ratings must be positive");
		this.numRatings = numRatings;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Return a string representation of the rating
	public String toString() {
		return "Rating: " + getRating() + " Number of ratings: " + getNumRatings() + " Rating Description: " + getRatingName();
	}

	// Return a string representation of the rating. Ex.) good, bad, etc.
	public String getRatingName() {
		if (noRatings())
			return "No Ratings";
		if (getRating() >= 4.5)
			return "Excellent";
		else if (getRating() >= 4.0)
			return "Good";
		else if (getRating() >= 3.0)
			return "Average";
		else if (getRating() >= 2.0)
			return "Poor";
		else
			return "Bad";
	}

	// Check if no ratings
	public boolean noRatings() {
		return getNumRatings() == 0;
	}
}
