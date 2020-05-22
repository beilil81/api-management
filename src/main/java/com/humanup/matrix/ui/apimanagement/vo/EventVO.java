package com.humanup.matrix.ui.apimanagement.vo;

public class EventVO {

	private String libelle;
	private String description;
	private TypeEventsVO typeEvents;
	private PersonVO person;

	protected EventVO() {
	}

	public EventVO(String libelle, String description, TypeEventsVO typeEvents, PersonVO person) {
		this.libelle = libelle;
		this.description = description;
		this.typeEvents = typeEvents;
		this.person = person;;
	}


	public String getLibelle() {
		return libelle;
	}

	public String getDescription() {
		return description;
	}

	public TypeEventsVO getTypeEvents() {
		return typeEvents;
	}

	public PersonVO getPerson(){return person;}

	public static class Builder {

		private String libelle;
		private String description;
		private TypeEventsVO typeEvents;
		private PersonVO person;

		public Builder() {
		}

		public Builder setDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder setLibelle(String libelle) {
			this.libelle = libelle;
			return this;
		}

		public Builder setTypeEvents(TypeEventsVO typeEvents) {
			this.typeEvents = typeEvents;
			return this;
		}
		public Builder setPerson(PersonVO person) {
			this.person = person;
			return this;
		}

		public EventVO build() {
			return new EventVO(libelle, description,typeEvents,person);
		}

	}
}
