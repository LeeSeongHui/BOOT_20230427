package com.example.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"regdate"}) // 날짜 안나타냄.
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private long no;
	private String title;
	private String content;
	private String writer;
	private long hit;
	private Date regdate;
}
