package edu.sru.cpsc.webshopping.domain.misc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.sru.cpsc.webshopping.domain.widgets.ImagePacket;


@Entity
@Table(name = "image")
public class Image{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "image_id")
	private Long image_id;
	
	String imageName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_packet_packet_id")
	private ImagePacket imagePacket;
	
}



