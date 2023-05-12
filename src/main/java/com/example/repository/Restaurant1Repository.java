package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Restaurant1;
import com.example.entity.Restaurant1ID;

@Repository
public interface Restaurant1Repository extends JpaRepository<Restaurant1, Restaurant1ID>{
    
    // select * from restaurant where phone like '%' || ? || '%' order by no desc
    List<Restaurant1> findByPhoneContainingOrderByNoDesc(String phone, Pageable pageable);

    // select count(*) from restaurant where phone like '%' || ? || '%'
    long countByPhoneContaining(String phone);

    // select * from restaurant where name like '%' || ? || '%' order by no desc
    List<Restaurant1> findByNameContainingOrderByNoDesc(String name, Pageable pageable);

    // select count(*) from restaurant where name like '%' || ? || '%'
    long countByNameContaining(String name);

    // select * from restaurant where address like '%' || ? || '%' order by no desc
    List<Restaurant1> findByAddressContainingOrderByNoDesc(String address, Pageable pageable);

    // select count(*) from restaurant where address like '%' || ? || '%'
    long countByAddressContaining(String address);


    
    // select * from restaurant where type=? order by no desc
    List<Restaurant1> findByTypeOrderByNoDesc(String type, Pageable pageable);

    // select count(*) from restaurant where type=?
    long countByType(String type);

}
