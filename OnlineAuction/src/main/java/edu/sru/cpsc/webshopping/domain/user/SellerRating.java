package edu.sru.cpsc.webshopping.domain.user;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

// This Entity is used to describe how good of a seller a particular user is
// RatingName will be a one or two word description of the user's rating
@Entity
public class SellerRating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String RatingName;
	
	@NonNull
	private float minPercent;
	
	@NonNull
	private float maxPercent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRatingName() {
		return RatingName;
	}

	public void setRatingName(String ratingName) {
		RatingName = ratingName;
	}

	public float getMinPercent() {
		return minPercent;
	}

	public void setMinPercent(float minPercent) {
		this.minPercent = minPercent;
	}

	public float getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(float maxPercent) {
		this.maxPercent = maxPercent;
	}
	
}
