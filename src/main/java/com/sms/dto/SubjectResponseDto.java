package com.sms.dto;

import com.sms.enums.RecordStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(
        description = "Subject Response"
)
@Getter
@Setter
public class SubjectResponseDto {
	
	@Schema(example = "1")
    private Long id;

	@Schema(example = "SUB001")
    private String subjectCode;

	@Schema(example = "Advanced Java Programming")
    private String subjectName;

	@Schema(example = "4")
    private Integer credits;

	@Schema(example = "70")
    private Integer theoryMarks;

	@Schema(example = "30")
    private Integer practicalMarks;

	@Schema(example = "1")
    private Long semesterId;

	@Schema(example = "Semester 1")
    private String semesterName;

	private RecordStatus status;

}