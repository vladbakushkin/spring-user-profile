package org.bakushkin.springuserprofile.repository;

import org.bakushkin.springuserprofile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
