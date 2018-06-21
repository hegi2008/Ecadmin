package com.yinhai.ec.system.service;

import com.yinhai.ec.base.service.BaseService;
import com.yinhai.ec.base.util.PageParam;

import java.util.List;
import java.util.Map;

public interface TaskManagerService extends BaseService {

    void addJob(PageParam pageParam) throws Exception;

    void modifyJobTime(PageParam pageParam) throws Exception;

    Map<String, Object> getJob(PageParam pageParam) throws Exception;

    List<Map<String, Object>> getJobList(PageParam pageParam) throws Exception;

    Long getJobListCount(PageParam pageParam) throws Exception;

    void resumeJob(PageParam pageParam) throws Exception;

    void pauseJob(PageParam pageParam) throws Exception;

    void removeJob(PageParam pageParam) throws Exception;

}
 