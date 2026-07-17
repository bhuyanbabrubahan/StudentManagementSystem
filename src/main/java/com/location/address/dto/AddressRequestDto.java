package com.location.address.dto;

import com.location.address.enums.AddressType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AddressRequestDto", description = "Address details")
public class AddressRequestDto {

	@NotBlank(message = "House number is required")
	@Size(max = 100)
	@Schema(example = "12A")
	private String houseNumber;

	@NotBlank(message = "Street is required")
	@Size(max = 150)
	@Schema(example = "University Road")
	private String street;

	@Size(max = 150)
	@Schema(example = "Near SBI Bank")
	private String landmark;



	@NotNull(message = "Address type is required")
	@Schema(example = "PERMANENT")
	private AddressType addressType;

	@NotNull(message = "Village is required")
	@Schema(example = "105")
	private Long villageId;
}