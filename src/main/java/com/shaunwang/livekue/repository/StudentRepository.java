package com.shaunwang.livekue.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shaunwang.livekue.model.Status;
import com.shaunwang.livekue.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	// query
	List<Student> findByStatus(Status status);
	
	Student findById(long id);
	
	Student findFirstByEmailOrderByInlineDateTimeDesc(String email);
	
//	@Query(value = "select top 1 * "
//			+ "from Student s where s.status = 3 "
//			+ "and s.plannedtime < NOW() "
//			+ "order by s.inlinetime asc", nativeQuery = true)
	Student findFirstByStatusAndPlannedTimeLessThanOrderByInlineDateTimeAsc(Status status, DateTime dateTime);
	
	@Query("select s.displayName from Student s where s.status = 3")
	List<String> queryInlineStudents();
	
	int countByEmailAndStatus(String email, Status status);
	
	int countByIdLessThanAndStatus(long id, Status status);
	
	int countByStatus(Status status);
	
	//List<Student> getEmailR
	// create
	
	
	// update
	
	@Modifying
	@Query("update Student s set s.description = ?1 where s.id = ?2")
	void updateDescription(long id, String description);
	
	// delete
}
