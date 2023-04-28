 package com.spring.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private int id;
	@NotEmpty(message = "Must Not Be Empty")
	private String name;
	@NotEmpty
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
    message = "At least 8 chars\r\n"
    		+ "\r\n"
    		+ "Contains at least one digit\r\n"
    		+ "\r\n"
    		+ "Contains at least one lower alpha char and one upper alpha char\r\n"
    		+ "\r\n"
    		+ "Contains at least one char within a set of special chars (@#%$^ etc.)\r\n"
    		+ "\r\n"
    		+ "Does not contain space, tab, etc.")
	private String password;
	@Email(message = "Your Email is not valid !!")
	private String email;
	@NotEmpty
	@Size(min = 10)
	private String about;
}
