package com.edu.nju.kanbanboard.web.base;

import com.edu.nju.kanbanboard.comm.Const;
import com.edu.nju.kanbanboard.model.domain.KBUser;
import com.edu.nju.kanbanboard.utils.Des3Util;
import com.edu.nju.kanbanboard.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String getPwd(String password){
        try{
            return MD5Util.encrypt(password+Const.PASSWORD_KEY);
        }catch (Exception e){
            logger.error("密码加密异常:",e);
        }
        return null;
    }

    protected String cookieSign(String value){
        try{
            value = value + Const.PASSWORD_KEY;
            return Des3Util.encode(Const.DES3_KEY,value);
        }catch (Exception e){
            logger.error("cookie签名异常:",e);
        }
        return null;
    }

}
