package com.spring.management.repository.procedure;

import com.spring.management.model.procedure.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {


    @Query(value = "exec [friend-finder].[dbo].[ADD_USER_OR_ORG_FROM_USER_MANAGEMENT] @USER_ID = :user_id, @SCOPE = :scope", nativeQuery = true)
    void addUserToFriendFinder(@Param("user_id") Long userId, @Param("scope") String scope);
}
