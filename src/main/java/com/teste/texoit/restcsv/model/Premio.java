package com.teste.texoit.restcsv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "premio")
public class Premio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column(name = "producers")
	public String producers;

	@Column(name = "title")
	public String title;

	@Column(name = "year")
	public Integer year;

	@Column(name = "studios")
	public String studios;

	@Column(name = "winner")
	public Boolean winner;

	public Premio() {
	}

	public Premio(Integer year, String title, String studios, String producers, Boolean winner) {
		
		this.producers = producers;
		this.title = title;
		this.year = year;
		this.studios = studios;
		this.winner = winner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProducers() {
		return producers;
	}

	public void setProducers(String producers) {
		this.producers = producers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getStudios() {
		return studios;
	}

	public void setStudios(String studios) {
		this.studios = studios;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Premio [producers=" + producers + ", title=" + title + ", year=" + year + ", studios=" + studios
				+ ", winner=" + winner + "]";
	}

}
