package com.spring.payloads;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

	private Integer categoryId;
	@NotEmpty(message = "Title should not be empty")
	@Size(min = 4,message = "Minimum size for title is 4")
	private String categoryTitle;
	@NotEmpty(message = "Description shoult not be empty")
	@Size(min = 10,message = "Minimum size for desc is 10")
	private String categoryDescription;
}
