package com.example.kms.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.kms.DTO.User;

@Mapper
public interface KmsMapper {
	// 로그인 서비스
	public Map<String, String> signIn (Map<String, String> map);
	// 아이디로 유저테이블 체크
	public Map<String, Object> chkUser (String id);
	// 아이디로 세션테이블 체크
	public Map<String, Object> chkSessionById (String id);
	// 세션 아이디로 세션테이블 체크
	public Map<String, Object> chkSessionBySId (String sid);
	// 그룹명으로 그룹테이블 체크
	public Map<String, Object> chkGroup (String name);
	// 그룹유저테이블 등록
	public void createGroupUser(String groupid, String userid);
	// 세션테이블 등록
	public void createSession(String id, String sessionid);
	// 유저테이블 등록
	public void createUser(Map<String, Object> map);
	// 유저테이블 권한 등록
	public void updateUserAuth(String auth, String id);
	// 아이디로 세션테이블 데이터 삭제
	public void deleteSessionById (String id);
	// 아이디로 세션테이블 데이터 삭제
	public void deleteSessionBySId (String sessionid);
}
