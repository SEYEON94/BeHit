package com.behit.creator.dto;

import org.apache.ibatis.type.Alias;

import lombok.Setter;

import lombok.Getter;

@Alias("creatorperm")
@Getter
@Setter
public class CreatorPermDTO {
	private String perm_emp_id;
	private int perm_cre_idx;
}
