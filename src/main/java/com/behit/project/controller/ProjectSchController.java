package com.behit.project.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.behit.project.service.ProjectSchService;

@EnableScheduling
@Component
public class ProjectSchController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired ProjectSchService service;
	
	@PostConstruct
    public void init() {
        logger.info("초기 실행 :: 지연된 프로젝트 확인");
        service.delayProj();
    }
	
	@Scheduled(cron = "0 0 0 * * *") // 자정에 한번씩 지연된 프로젝트 확인후 업데이트	
	public void DelayProject() {
		logger.info("SCHEDULING :: 지연된 프로젝트 확인");
		
		service.delayProj();
		
	}
	
}
