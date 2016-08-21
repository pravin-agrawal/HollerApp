package com.holler.holler_dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "holler_faqs")
public class Faqs extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -48489231371179433L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "commonQuery")
	private String question;

	@Column(name = "answers")
	private String answer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
