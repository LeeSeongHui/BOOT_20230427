package com.example.entity;

public interface Address1Projection {
    
    long getNo(); // 주소번호

    String getAddress(); // 주소

    // 외래키 가져옴
    Member1 getMember1(); // 회원정보

    // 외래키 Member1의 항목 가져오기 위해 interface 생성
    interface Mmeber1 { // 외래키 항목
        String getId(); // 아이디
        String getName(); // 이름
    }

    // 조합 (주소번호 + 주소 정보 합치기)
    default String getNoAddress() {
        return getNo() + "," + getAddress();
    }
}
