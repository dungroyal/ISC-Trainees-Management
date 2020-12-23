package com.ISC.project.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "IntakeStudent")
public class IntakeStudent {
	@EmbeddedId
	private EmbemdedIntakeStudentId id = new EmbemdedIntakeStudentId();
	
	//map to intake
			@ManyToOne(optional = false)
			@MapsId("intakeId")
			private Intake intake;
			
			//map to student
			@ManyToOne(optional = false)
			@MapsId("studentId")
			private Student student;

			public IntakeStudent(EmbemdedIntakeStudentId id, Intake intake, Student student) {
				super();
				this.id = id;
				this.intake = intake;
				this.student = student;
			}

			public IntakeStudent() {
				super();
			}

			public EmbemdedIntakeStudentId getId() {
				return id;
			}

			public void setId(EmbemdedIntakeStudentId id) {
				this.id = id;
			}

			public Intake getintake() {
				return intake;
			}

			public void setintake(Intake intake) {
				this.intake = intake;
			}

			public Student getStudent() {
				return student;
			}

			public void setStudent(Student student) {
				this.student = student;
			}
			
			//hash code
			 @Override
			    public int hashCode() {
			        return Objects.hash(intake, student);
			    }
			
			//Override equals
			@Override
		   public boolean equals(Object o) {
		       if (this == o) return true;

		       if (o == null || getClass() != o.getClass())
		           return false;

		       IntakeStudent that = (IntakeStudent) o;
		       return Objects.equals(this.intake, that.intake) &&
		              Objects.equals(this.student, that.student);
		   }
}
