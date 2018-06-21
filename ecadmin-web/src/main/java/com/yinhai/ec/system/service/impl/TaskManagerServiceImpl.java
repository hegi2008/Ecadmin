package com.yinhai.ec.system.service.impl;

import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.quartz.manager.QuartzManager;
import com.yinhai.ec.system.service.TaskManagerService;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskManagerServiceImpl extends BaseServiceImpl implements TaskManagerService {

    @Autowired
    private QuartzManager quartzManager;

    @Override
    public void addJob(PageParam pageParam) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("description", pageParam.get("description"));
        quartzManager.addJob(pageParam.get("job_name") + "", pageParam.get("job_group") + "", Class.forName(pageParam.get("job_class_name") + ""), "" + pageParam.get("cron_expression"), "" + pageParam.get("description"));
    }

    @Override
    public void modifyJobTime(PageParam pageParam) throws Exception {
        quartzManager.modifyJobTime(pageParam.get("job_name") + "", pageParam.get("cron_expression") + "");
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getJob(PageParam pageParam) throws Exception {
        return sqlSession.selectOne("qrtz.getJob", pageParam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getJobList(PageParam pageParam) throws Exception {
        return sqlSession.selectList("qrtz.getJobList", pageParam);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getJobListCount(PageParam pageParam) throws Exception {
        return sqlSession.selectOne("qrtz.getJobListCount", pageParam);
    }

    @Override
    public void resumeJob(PageParam pageParam) throws Exception {
        quartzManager.resumeJob(JobKey.jobKey(pageParam.get("job_name") + "", pageParam.get("job_group") + ""));
    }

    @Override
    public void pauseJob(PageParam pageParam) throws Exception {
        quartzManager.pauseJob(JobKey.jobKey(pageParam.get("job_name") + "", pageParam.get("job_group") + ""));
    }

    @Override
    public void removeJob(PageParam pageParam) throws Exception {
        quartzManager.removeJob(pageParam.get("job_name") + "", pageParam.get("job_group") + "");
    }
}
 