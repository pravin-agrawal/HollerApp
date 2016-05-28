package com.holler.holler_dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.holler.holler_dao.entity.enums.DocumentType;

@Entity
@Table(name = "user_documents")
public class UserDocument extends BaseEntity{
	private static final long serialVersionUID = -5464369837316338488L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	
	@Column(name = "document_url")
	private String documentUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "document_type")
	private DocumentType documentType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	
	public UserDocument(){
		
	}

	public UserDocument(User user, String documentUrl, DocumentType documentType){
		this.user = user;
		this.documentUrl = documentUrl;
		this.documentType = documentType;
	}

}
