package com.example.demo.dataroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DataroomDao extends JpaRepository<Dataroom,String> {
    @Transactional
    @Modifying
    @Query(value="update data set cnt=cnt+1 where num=:num", nativeQuery=true)
    void updateCnt(@Param("num") int num);
}
