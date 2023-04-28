package com.spring.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
	private Integer id;
	@NotEmpty
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDTO category;
	private UserDTO user;
	private Set<CommentsDTO> comments=new HashSet<>();
}
