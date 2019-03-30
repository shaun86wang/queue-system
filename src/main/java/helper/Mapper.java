package helper;

import com.shaunwang.livekue.dto.StudentDto;
import com.shaunwang.livekue.model.Student;

public class Mapper {
	public StudentDto mapStudentToDto(Student student){
		StudentDto studentDto = new StudentDto();
		studentDto.setEmail(student.getEmail());
		studentDto.setStudentName(student.getStudentName());
		studentDto.setStudentNumber(student.getStudentNumber());
		studentDto.setDisplayName(student.getDisplayName());
		studentDto.setId(student.getId());
		studentDto.setDescription(student.getDescription());
		studentDto.setServiceType(student.getServiceType());
		return studentDto;
	}
}
