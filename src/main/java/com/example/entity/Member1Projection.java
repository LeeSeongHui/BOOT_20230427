package com.example.entity;

// Entitiy에서 꺼내고 싶은 항목만 만든 것
public interface Member1Projection {
    // get + 변수()
    String getId();

    String getName();

    int getAge();
}
