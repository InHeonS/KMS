package com.example.kms.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.kms.user.vo.UserMgmtPVO;
import com.example.kms.user.vo.UserMgmtRVO;
import com.example.kms.user.vo.sign.SignUpList;

@Mapper
public interface SignMapper {

    UserMgmtRVO selectUserInfoById(String id);

    boolean updatePasswordCount(UserMgmtPVO userMgmtPVO);

    boolean insertUser(UserMgmtPVO userMgmtPVO);

    UserMgmtRVO selectUserInfo(Long userSeq);

    boolean signConfirm(UserMgmtPVO userMgmtPVO);
    
    List<SignUpList> selectUserList(int value, int row_count);
}
