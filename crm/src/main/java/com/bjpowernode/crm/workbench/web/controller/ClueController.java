package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.HSSFUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request) {

        //调用service层方法，查询所有用户
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        //把数据保存到request中
        request.setAttribute("userList", userList);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("sourceList", sourceList);
        //请求转发
        return "workbench/clue/index";
    }

    /**
     * 创建线索
     *
     * @param clue
     * @return
     */
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody
    Object insertClue(Clue clue, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.dateTimeFormate(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，保存创建的线索
            int ret = clueService.saveCreateClue(clue);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("创建失败");
        }
        return returnObject;
    }

    /**
     * 线索详情
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/workbench/clue/detailClue.do")
    public String queryClueForDetailById(String id, HttpServletRequest request) {
        //调用service层方法，查询数据
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);

        //封装参数
        request.setAttribute("clue", clue);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("activityList", activityList);

        //请求转发
        return "workbench/clue/detail";
    }

    /**
     * 分页查询
     *
     * @param
     * @param company
     * @param phone
     * @param source
     * @param owner
     * @param mphone
     * @param state
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/workbench/clue/queryClueListForPageByCondition.do")
    public @ResponseBody
    Object queryClueListForPageByCondition(String fullname, String company, String phone, String source, String owner, String mphone, String state,
                                           int pageNo, int pageSize) {

        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);

        //调用service层方法，查询数据
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryCountClueByCondition(map);
        //根据查询结果查询相应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("clueList", clueList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    /**
     * 根据id查询线索信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/workbench/clue/saveEditClueById.do")
    public @ResponseBody
    Object saveEditClueById(String id) {
        //根据id查询线索信息
        Clue clue = clueService.queryClueById(id);
        return clue;
    }

    /**
     * 保存更新的线索
     *
     * @param clue
     * @param session
     * @return
     */
    @RequestMapping("/workbench/clue/saveUpdateClue.do")
    public @ResponseBody
    Object saveEditClueById(Clue clue, HttpSession session) {

        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clue.setEditBy(user.getId());
        clue.setEditTime(DateUtils.dateTimeFormate(new Date()));

        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = clueService.saveEditClue(clue);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("更新失败");
        }
        return returnObject;
    }

    /**
     * 删除线索
     *
     * @param id
     * @return
     */
    @RequestMapping("/workbench/clue/deleteClueByIds.do")
    public @ResponseBody
    Object deleteClueByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = clueService.deleteClueByIds(id);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMsg("删除成功");
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("删除失败");
        }
        return returnObject;
    }

    /**
     * 根据name模糊查询市场活动，并且把已经跟clueId关联过的市场活动排除
     *
     * @param activityName
     * @param clueId
     * @return
     */
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    public @ResponseBody
    Object queryActivityForDetailByNameClueId(String activityName, String clueId) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        //根据查询结果，返回响应体信息
        return activityList;
    }

    /**
     * 根据ids查询市场活动的明细信息
     *
     * @return
     */

    @RequestMapping("/workbench/clue/saveBund.do")
    public @ResponseBody
    Object saveBund(String[] activityId, String clueId) {
        ClueActivityRelation car = null;
        List<ClueActivityRelation> relationList = new ArrayList<>();
        for (String ai : activityId) {
            car = new ClueActivityRelation();
            car.setId(UUIDUtils.getUUID());
            car.setActivityId(ai);
            car.setClueId(clueId);
            relationList.add(car);
        }
        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service方法，批量保存线索和市场活动的关联关系
            int ret = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("关联失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("关联失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/saveUnbund.do")
    public @ResponseBody
    Object saveUnbund(ClueActivityRelation relation) {
        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = clueActivityRelationService.saveCreateClueActivityRelationByClueActivityId(relation);
            if (ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("删除失败");
        }
        return returnObject;
    }

}
