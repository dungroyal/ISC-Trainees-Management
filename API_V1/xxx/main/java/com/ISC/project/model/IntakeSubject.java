package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "IntakeSubject")
public class IntakeSubject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmbemdedIntakeSubjectId id = new EmbemdedIntakeSubjectId();
	
	//map to intake
		@ManyToOne(optional = false)
		@MapsId("intakeId")
		private Intake intake;
		
		//map to subject
		@ManyToOne(optional = false)
		@MapsId("subjectId")
		private Subject subject;

		
		
		//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(intake, subject);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       IntakeSubject that = (IntakeSubject) o;
	       return Objects.equals(this.intake, that.intake) &&
	              Objects.equals(this.subject, that.subject);
	   }
}
