package com.example.kms.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.kms.DTO.User;
import com.example.kms.Service.KmsCommonService;
import com.example.kms.Service.KmsService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
@SessionAttributes
public class KmsUserController {
	
	@Autowired	// DB 서비스
	private KmsService ks;
	
	@Autowired	// 공통 서비스
	private KmsCommonService kcs;
	
	// 로그인 서비스
	@PostMapping("/signIn")
	public String signIn(@RequestParam String id, String password, HttpServletRequest req) {
		System.out.println("<로그인>");
		Map<String, String> ud = new HashMap<String, String>();
		Map<String, Object> se = new HashMap<String, Object>();
	    Map<String, Object> rtn = new HashMap<String, Object>();
		try {	    
		    // 유저 테이블 체크
		    try {
			    ud.put("id", id);
			    ud.put("password", password);
			    ud = ks.signIn(ud);
		    }catch(Exception e1) {
				rtn.put("rtncd", "1002");
				return kcs.rtnData(rtn);
		    }
			
			// 세션테이블 고객 ID 체크
		    try {
		    	se = ks.chkSessionById(id);		
		    }catch(Exception e1) {
				rtn.put("rtncd", "9998");
				return kcs.rtnData(rtn);
		    }
			
			if(se != null) {
	//			ks.deleteSessionById(id); // 세션 테이블에 기존 등록된 세션아이디가 있다면 삭제
				rtn.put("rtncd", "1001");
				return kcs.rtnData(rtn);
			}
			
			// 세션 ID 셍성
			HttpSession session = req.getSession(); 	    
			String sessionid = session.getId();
			
			// 세션에 새로 등록
			ks.createSession(id, sessionid);
			
		   	// 세션에 Timeout 설정
	//	   	session.setMaxInactiveInterval(15*60); // 15분 (분 * 초)
	//	   	session.setMaxInactiveInterval(120);	// test
	        
		   	System.out.println(session.getMaxInactiveInterval());
		   	
			rtn.put("rtncd", "0000");
			return kcs.rtnData(rtn);
		}catch(Exception e1) {
			rtn.put("rtncd", "9999");
			return kcs.rtnData(rtn);
		}
	}
	
	// 회원가입 서비스
	@ResponseBody
	@PostMapping("/create")
	public String create(@RequestBody HashMap<String,Object> map,HttpServletRequest req) {
		System.out.println("<회원가입>");
		Map<String, Object> rtn = new HashMap<String, Object>();
	    try {
		    try {
		    	ks.createUser(map);
		    }catch(Exception e1) {
				rtn.put("rtncd", "1006");
				return kcs.rtnData(rtn);
		    }
		    
			rtn.put("rtncd", "0000");
			return kcs.rtnData(rtn);
	    }catch(Exception e1) {
			rtn.put("rtncd", "9999");
			return kcs.rtnData(rtn);
	    }
	}
	// 
	@PostMapping("/signConfirm")
	public String signConfirm(@RequestParam String id, String name, String auth, HttpServletRequest req) {
		System.out.println("<회원가입 요청 승인>");
//		Map<String, Object> userData = new HashMap<String, Object>();
		Map<String, Object> groupData = new HashMap<String, Object>();
	    Map<String, Object> rtn = new HashMap<String, Object>();
	    try {
		    // 기존 세션데이터 가져오기
		    String sessionid = req.getRequestedSessionId();	    
			
			// 세션테이블 세션 체크
			try {
				ks.chkSessionBySId(sessionid);
			}catch(Exception e1) {
				rtn.put("rtncd", "9998");
				return kcs.rtnData(rtn);
			}
	//		// 고객정보 조회
	//		userData = ks.chkUser(id);
			// 유저테이블 권한 추가
			ks.updateUserAuth(auth, id);
			
			// 그룹 조회
			groupData = ks.chkGroup(name);
			
			// 그룹유저테이블 추가
			ks.createGroupUser(groupData.get("id").toString(), id);
	        
			rtn.put("rtncd", "0000");
			return kcs.rtnData(rtn);
	    }catch(Exception e1) {
			rtn.put("rtncd", "9999");
			return kcs.rtnData(rtn);
	    }
	}
	// 아이디 중복 체크
	@PostMapping("/idCheck")
	public String idCheck(@RequestParam String id) {
		System.out.println("<아이디 중복 체크>");
		Map<String, Object> userData = new HashMap<String, Object>();
	    Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			// 고객정보 조회
			userData = ks.chkUser(id);
			
			if(userData != null) {
				rtn.put("rtncd", "1005");
				return kcs.rtnData(rtn);
			}
			
			rtn.put("rtncd", "0000");
			return kcs.rtnData(rtn);
		}catch(Exception e1) {
			rtn.put("rtncd", "9999");
			return kcs.rtnData(rtn);
		}
	}
	
	@PostMapping("/signOut")
	public String signOut(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("<로그아웃>");
//		Map<String, Object> se = new HashMap<String, Object>();
	    Map<String, Object> rtn = new HashMap<String, Object>();
		try {
		    // 기존 세션데이터 가져오기
		    String sessionid = req.getRequestedSessionId();
			
		    // 세션테이블 세션 체크
			try {
				ks.chkSessionBySId(sessionid);
			}catch(Exception e1) {
				rtn.put("rtncd", "9998");
				return kcs.rtnData(rtn);
			}
			// 세션아이디로 세션테이블 제거
			ks.deleteSessionBySId(sessionid);
			// 세션에 저장된 모든 데이터 무효화
			req.getSession().invalidate();
			// 모든 쿠키 제거
			Cookie[] cookies = req.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
		    if (cookies != null) { // 쿠키가 한개라도 있으면 실행
		        for (int i = 0; i < cookies.length; i++) {
		            cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
		            res.addCookie(cookies[i]); // 응답에 추가하여 만료시키기.
		        }
		    }
			
			rtn.put("rtncd", "0000");
			return kcs.rtnData(rtn);
		}catch(Exception e1) {
			rtn.put("rtncd", "9999");
			return kcs.rtnData(rtn);
		}
	}
}
