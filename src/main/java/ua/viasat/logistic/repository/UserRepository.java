package ua.viasat.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.viasat.logistic.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 User findById(int id);
	@Modifying
	@Query(value = "update User u set u.user_type='userType' where u.user_id='userId'", nativeQuery=true )
	 void editUserType(@Param("userType") String userType, @Param("userId") int userId);
	@Modifying
	@Query(value = "update User u set u.password='password' where u.user_id='userId'", nativeQuery=true)
	 void editUserPassword(@Param("password") String password, @Param("userId") int userId);
}
