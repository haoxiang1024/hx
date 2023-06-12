package com.school.controller;


import com.school.dao.LostFoundDao;
import com.school.entity.Lost;
import com.school.entity.Lostfoundtype;
import com.school.services.interfaces.LostDetail;
import com.school.services.interfaces.LostFoundType;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LostController {
    @Resource
    private LostFoundType lostFoundType;
    @Resource
    private LostDetail lostDetail;
    @Resource
    private LostFoundDao lostFoundDao;


    @ResponseBody
    @RequestMapping("/getalltype")
    public ServerResponse getalltype() {
        return lostFoundType.getAllType();
    }

    @ResponseBody
    //获取分类下的内容
    @RequestMapping("/DetailByTitle")
    public ServerResponse getDetailByTitle(HttpSession session, String title) {
        //获取标题id
        int id = 0;
        List<Lostfoundtype> lostfoundtypes = lostFoundDao.GetAll();
        //中英文转换
        String[] en = {"Digital Devices", "Certificates", "Daily Necessities", "Clothing and Apparel", "Other"};
        String[] cn = {"数码设备", "证件", "日用品", "服饰", "其他"};
        Map<String, String> map = new HashMap<>();//建立关系
        for (int i = 0; i < en.length; i++) {
            map.put(en[i], cn[i]);
        }
        for (Lostfoundtype lostfoundtype : lostfoundtypes) {
            if (lostfoundtype.getName().equals(title)) {
                //中文
                id = lostfoundtype.getId();
            } else {
                //英文
                String cnValue = map.get(title);
                if (lostfoundtype.getName().equals(cnValue)) {
                    id = lostfoundtype.getId();
                }
            }
        }
        ServerResponse lostDetailList = lostDetail.getLostDetailList(id);
        //更新图片地址
        List<Lost> data = (List<Lost>) lostDetailList.getData();
        String updatePic = "";
        for (Lost lost : data) {
            if (lost.getImg() == null) {
                break;
            } else {
                updatePic = Util.updatePic(lost.getImg());
                lost.setImg(updatePic);
            }

        }
        //更新数据
        lostDetailList.setData(data);
        if (lostDetailList.isSuccess()) {
            session.setAttribute("detailList", lostDetailList.getData());
        }
        return lostDetailList;
    }

    @ResponseBody
    @RequestMapping("/getUname")
    public ServerResponse getUname(HttpSession session, int id) {
        ServerResponse lostDetailUserName = lostDetail.getUserName(id);
        if (lostDetailUserName.isSuccess()) {
            session.setAttribute("nickname", lostDetailUserName.getData());
        }
        return lostDetailUserName;
    }

    //获取redis缓存中的分类数据
    @ResponseBody
    @RequestMapping("/getAllTypeByR")
    public ServerResponse getAllTypeByR() {
        return lostFoundType.getAllTypeByR();
    }

    //获取类型id
    @ResponseBody
    @RequestMapping("/getTypeid")
    public ServerResponse getTypeIdByName(String name) {
        return lostFoundType.getIdByName(name);
    }

    //获取发布信息
    @ResponseBody
    @RequestMapping("/getAllLostUserId")
    public ServerResponse getAllByUserId(int user_id) {
        return lostDetail.getAllByIdLostList(user_id);
    }

    //更改状态
    @ResponseBody
    @RequestMapping("/updateLostState")
    public ServerResponse updateState(int id, String state, int user_id) {
        return lostDetail.updateState(id, state, user_id);
    }

    //置顶信息
    @ResponseBody
    @RequestMapping("/showLostList")
    public ServerResponse showLostList(int stick) {
        return lostDetail.showLostList(stick);
    }
}
