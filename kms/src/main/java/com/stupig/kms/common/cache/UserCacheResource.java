package com.stupig.kms.common.cache;

import com.stupig.kms.common.utils.SessionUtils;
import com.stupig.kms.common.vo.CommonCacheVO;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;


public class UserCacheResource {

    private final ConcurrentHashMap<Long, CommonCacheVO> userCacheMap = new ConcurrentHashMap<>();

    private final long holdingTime = 10L;

    public void putUserCache(Long seq) {
        this.userCacheMap.put(seq, new CommonCacheVO(
                SessionUtils.getSessionId(),
                LocalDateTime.now().plusMinutes(this.holdingTime)
        ));
    }

    public CommonCacheVO getUserCache(Long seq) {
        return this.userCacheMap.get(seq);
    }

    public void removeUserCache(Long seq) {
        this.userCacheMap.remove(seq);
    }


}
