package STUDENT_MODEL;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Table(name = "students")
public class Student_Model  implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "Gender")
    private String gender;

    @Column(name = "Email")
    private String email;

    @Lob
    @Column(name = "passport_picture")
    private byte[] passportPicture;

    @Lob
    @Column(name = "certificate_pdf")
    private byte[] certificatePDF;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public byte[] getPassportPicture() {
		return passportPicture;
	}

	public void setPassportPicture(byte[] passportPicture) {
		this.passportPicture = passportPicture;
	}

	public byte[] getCertificatePDF() {
		return certificatePDF;
	}

	public void setCertificatePDF(byte[] certificatePDF) {
		this.certificatePDF = certificatePDF;
	}
}
