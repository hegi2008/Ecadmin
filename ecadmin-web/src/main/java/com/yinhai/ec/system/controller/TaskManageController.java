package com.yinhai.ec.system.controller;

import com.yinhai.ec.base.controller.BaseController;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.system.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cjh
 * @version 1.0
 * @package com.yinhai.ec.base.system.controller
 * <p>Title: UserManagerController.java</p>
 * <p>Description: 任务调度管理</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 陈瓜瓜</p>
 * @date 2016-1-12 下午3:08:42
 */
@Controller
@RequestMapping("system/quartz/taskManager")
public class TaskManageController extends BaseController {
    @Autowired
    private TaskManagerService taskManagerService;

    @RequestMapping("/default")
    public String execute() throws Exception {
        return "/system/taskManager.jsp";
    }

    @RequestMapping("/toAdd")
    public String toAdd() throws Exception {
        return "/system/task_add.jsp";
    }

    @RequestMapping("/toEdit")
    public String toEdit(ModelMap map, HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        map.put("job", taskManagerService.getJob(pageParam));
        return "/system/task_edit.jsp";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        pageParam.setList(taskManagerService.getJobList(pageParam));
        pageParam.setTotal(taskManagerService.getJobListCount(pageParam));
        return pageParam.toDatagridMap();
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean resultBean = getResultBean();
        try {
            taskManagerService.addJob(pageParam);
            resultBean.setSuccess_msg("添加任务成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setError(true);
            resultBean.setError_msg("添加任务失败");
        }
        return resultBean;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean resultBean = getResultBean();
        try {
            taskManagerService.modifyJobTime(pageParam);
            resultBean.setSuccess_msg("修改表达式成功");
        } catch (Exception e) {
            resultBean.setError(true);
            resultBean.setError_msg("修改表达式失败");
        }
        return resultBean;
    }

    @RequestMapping("/pause")
    @ResponseBody
    public Object pause(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean resultBean = getResultBean();
        try {
            taskManagerService.pauseJob(pageParam);
            resultBean.setSuccess_msg("暂停任务成功");
        } catch (Exception e) {
            resultBean.setError(true);
            resultBean.setError_msg("暂停任务失败");
        }
        return resultBean;
    }

    @RequestMapping("/resume")
    @ResponseBody
    public Object resume(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean resultBean = getResultBean();
        try {
            taskManagerService.resumeJob(pageParam);
            resultBean.setSuccess_msg("恢复启动任务成功");
        } catch (Exception e) {
            resultBean.setError(true);
            resultBean.setError_msg("恢复启动任务失败");
        }
        return resultBean;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Object remove(HttpServletRequest request) throws Exception {
        PageParam pageParam = getPageParam(request);
        ResultBean resultBean = getResultBean();
        try {
            taskManagerService.removeJob(pageParam);
            resultBean.setSuccess_msg("移除任务成功");
        } catch (Exception e) {
            resultBean.setError(true);
            resultBean.setError_msg("移除任务失败");
        }
        return resultBean;
    }

}
 