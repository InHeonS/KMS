package com.stupig.kms.user.mapper;

import com.stupig.kms.common.vo.PageVO;
import com.stupig.kms.user.vo.UserMgmtPVO;
import com.stupig.kms.user.vo.UserMgmtRVO;
import com.stupig.kms.user.vo.sign.SignUpListPVO;
import com.stupig.kms.user.vo.sign.SignUpListRVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SignMapper {

    UserMgmtRVO selectUserInfoById(String id);

    boolean updatePasswordCount(UserMgmtPVO userMgmtPVO);

    boolean insertUser(UserMgmtPVO userMgmtPVO);

    UserMgmtRVO selectUserInfo(Long seq);

    boolean signConfirm(UserMgmtPVO userMgmtPVO);

    List<SignUpListRVO> selectSignUpList(PageVO pvo);

    Integer selectSignUpListCount(SignUpListPVO pvo);
}
