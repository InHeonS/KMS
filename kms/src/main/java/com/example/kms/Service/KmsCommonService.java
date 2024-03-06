package com.example.kms.Service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class KmsCommonService {
	   public String rtnData (Map<String, Object> map){
		    // 다음과 같이 구성
//	    	rtncd : 코드 명
//		    rtnmsg : 코드 메세지
//		    rtndata : 데이터가 있으면 데이터로 전달, 데이터가 없으면 {};
	    	
	// 에러코드 기준
	// 에러코드 0000 : 정상처리
	// 에러코드 1000 ~ : api 에러
	// 에러코드 9998 : 세션 에러
	// 에러코드 9999 : 기타오류
	// 에러코드 엑셀 표 참고
	    	switch (map.get("rtncd").toString()) {
	    		case "0000": 
	    			map.put("rtnmsg", "정상");
	    			break;		
	    		case "1001": 
	    			map.put("rtnmsg", "중복로그인");
	    			break;
	    		case "1002": 
	    			map.put("rtnmsg", "유저이름 혹은 비밀번호 오류");
	    			break;
	    		case "1003": 
	    			map.put("rtnmsg", "비밀번호 기간 만료");
	    			break;
	    		case "1004": 
	    			map.put("rtnmsg", "비밀번호 횟수 초과로 관리 문의");
	    			break;
	    		case "1005": 
	    			map.put("rtnmsg", "중복아이디 입니다");
	    			break;
	    		case "1006": 
	    			map.put("rtnmsg", "회원가입 실패");
	    			break;
	    		case "9998": 
	    			map.put("rtnmsg", "만료된 세션입니다");
	    			break;	
	    		case "9999": 
	    			map.put("rtnmsg", "기타오류");
	    			break;
	    		default :
	    			map.put("rtnmsg", "오류발생");
	    	}
	    	
	    	
	        String data = map.containsKey("rtndata") != false ? map.get("rtndata").toString() : "{}";
	        System.out.println(String.format("{rtncd:\"%s\", rtnmsg:\"%s\", data:%s}", map.get("rtncd"), map.get("rtnmsg"), data));
	        return String.format("{rtncd:\"%s\", rtnmsg:\"%s\", data:%s}", map.get("rtncd"), map.get("rtnmsg"), data);	

	    }

}
