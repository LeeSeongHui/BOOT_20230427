package com.example.repository;



import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu1;

@Repository
public interface Menu1Repository extends JpaRepository<Menu1, BigInteger>{

    // findBy변수명_하위변수
    List<Menu1> findByRestaurant1_phone(String phone);
    

}
