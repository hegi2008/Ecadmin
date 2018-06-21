package com.yinhai.hyman.weixin.company.api.handle;

import java.util.Observable;

import com.yinhai.hyman.weixin.company.api.config.QYConfigChangeNotice;
import com.yinhai.hyman.weixin.handle.ApiConfigChangeHandle;
import com.yinhai.hyman.weixin.util.BeanUtil;

/**
 *  
 *  ====================================================================
 *   
 *  --------------------------------------------------------------------
 *  @author Nottyjay
 *  @version 1.0.beta
 *  ====================================================================
 */
public abstract class AbstractQYApiConfigChangeHandle implements ApiConfigChangeHandle{

    public void update(Observable o, Object arg){
        if(BeanUtil.nonNull(arg) && arg instanceof QYConfigChangeNotice){
            configChange((QYConfigChangeNotice) arg);
        }
    }

    /**
     * 子类实现，当配置变化时触发该方法
     * @param notice 消息
     */
    public abstract void configChange(QYConfigChangeNotice notice);
}
