package com.behit.alarm.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.behit.alarm.dao.AlarmDAO;
import com.behit.alarm.dto.AlarmDTO;

@Service
public class AlarmService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired AlarmDAO alarmDao;
	
	public ArrayList<AlarmDTO> alarmList(String login_id) {
		
		return alarmDao.alarmList(login_id);
		
	}

}
