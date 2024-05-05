package edu.duke.ece651.team4.TrackerServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.ece651.team4.TrackerServer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    @Query("select u from User u where u.identity = :identity")
    List<User> findByIdentity(@Param("identity") String identity);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.notifyChoice = :notifyChoice WHERE u.netid = :netid")
    void updateNotifyChoice(String netid, Boolean notifyChoice);
    
}
