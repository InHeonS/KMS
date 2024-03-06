package com.example.kms.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kms.DAO.KmsMapper;

@Service
public class KmsService {

	@Autowired
	KmsMapper km;
	
	// 로그인 서비스
	public Map<String,  String> signIn (Map<String, String> map) {
		return km.signIn(map);
	}
	// 아이디로 유저테이블 체크
	public Map<String, Object> chkUser (String id) {
		return km.chkUser(id);
	}
	// 그룹명으로 그룹 체크 
	public Map<String, Object> chkGroup (String name) {
		return km.chkGroup(name);
	}
	// 아이디로 세션테이블 체크
	public Map<String, Object> chkSessionById (String id){
		return km.chkSessionById(id);
	}
	// 세션아이디로 세션테이블 체크
	public Map<String, Object> chkSessionBySId (String sid){
		return km.chkSessionBySId(sid);
	}
	// 그룹유저테이블 등록
	public void createGroupUser(String groupid, String userid) {
		km.createGroupUser(groupid, userid);
	}
	// 세션테이블 등록
	public void createSession(String id, String sessionid) {
		km.createSession(id,sessionid);
	}
	// 유저테이블 등록
	public void createUser(Map<String, Object> map) {
		km.createUser(map);
	}
	public void updateUserAuth(String auth, String id) {
		km.updateUserAuth(auth, id);
	}
	// 아이디로 세션테이블 제거
	public void deleteSessionById(String id) {
		km.deleteSessionById(id);
	}
	// 세션아이디로 세션테이블 제거
	public void deleteSessionBySId(String sessionid) {
		km.deleteSessionBySId(sessionid);
	}
}
