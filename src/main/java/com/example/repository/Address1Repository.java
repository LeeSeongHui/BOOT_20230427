package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Address1;
import com.example.entity.Address1Projection;

// 저장소 생성 JpaRepository에는 기본적인 crud구현 되어 있음.
@Repository
public interface Address1Repository extends JpaRepository<Address1, Long>{

    // select ... WHERE address=?
    List<Address1> findByAddress(String address);
    
    // select ... WHERE postcode=?
    List<Address1> findByPostcode(String postcode);
    
    // select ... WHERE address=? AND postcode=?
    List<Address1> findByAddressAndPostcode(String address, String postcode);

    // WHERE member1.id=?  =>  member1은 객체이기 때문에 _ 를 이용해서 id값 가져옴
    List<Address1> findByMember1_idOrderByNoDesc(String id);

    // select count(*) from address1 where member1.id=?
    long countByMember1_id(String id);

    // WHERE member1.id=? ORDER BY no DESC + 페이지 네이션 기능 포함
    List<Address1> findByMember1_idOrderByNoDesc(String id, Pageable pageable);

    // select no, address, m.id, m.name from address1 a, member1 m order by no desc
    //List<Address1Projection> findAllByOrderByNoDesc();
    
    // 제너릭을 이용한 타입 설정
    <T> List<T> findAllByOrderByNoDesc(Class<T> type);


    
}
