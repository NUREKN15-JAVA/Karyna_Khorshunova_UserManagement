package ua.nure.kn156.khorshunova;

import java.util.Calendar;
import java.util.Date;

public class User {
 private Long id;
 private String firstName;
 private String lastName;
 private Date date;
 
public User(User user) {
	id=user.getId();
	firstName=user.getFirstName();
	lastName=user.getLastName();
	date=user.getDate();
}
public User(){
	
}
public Long getId() {
	return id;
}
public void setId(Long id) {
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
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getFullName() {
	// TODO Auto-generated method stub
	if(getFirstName() == null || getLastName() == null){
		throw new IllegalStateException("firstName is null or lastName is null");
	}
	return new StringBuilder().append(getLastName()).append(", ").append(getFirstName()).toString();
}

public long getAge(){
	Calendar calendar=Calendar.getInstance();
	long currentYear= calendar.get(Calendar.YEAR);
	long currentDay= calendar.get(Calendar.DAY_OF_YEAR);
	calendar.setTime(getDate());
	long yearOfBith= calendar.get(Calendar.YEAR);
	long dayOfBith=calendar.get(Calendar.DAY_OF_YEAR);
	long age =currentYear-yearOfBith;
	if(currentDay<dayOfBith){
		return --age;
	}else{
	return age;
	}
}
 
}
