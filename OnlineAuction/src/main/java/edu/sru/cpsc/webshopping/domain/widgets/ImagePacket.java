package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import edu.sru.cpsc.webshopping.domain.misc.Image;

@Entity
@Table(name = "image_packet")
public class ImagePacket{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "packet_id")
	private Long id;

	@OneToMany(mappedBy = "imagePacket")
	private Set<Image> images = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void addImage(Image image) {
		this.images.add(image);
		image.setImagePacket(this);
	}

	public void removeImage(Image image) {
		this.images.remove(image);
		image.setImagePacket(null);
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
}