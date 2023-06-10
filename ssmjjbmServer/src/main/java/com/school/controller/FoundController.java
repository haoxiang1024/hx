package com.school.controller;

import com.school.dao.LostFoundDao;
import com.school.entity.Found;
import com.school.entity.Lostfoundtype;
import com.school.services.interfaces.FoundDetail;
import com.school.services.interfaces.LostFoundType;
import com.school.utils.ServerResponse;
import com.school.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class FoundController {
    @Resource
    private FoundDetail foundDetail;
    @Resource
    private LostFoundDao lostFoundDao;
    @Resource
    private LostFoundType lostFoundType;

    @ResponseBody
    @RequestMapping("/getAllTypeByFound")
    public ServerResponse getAllType() {
        return lostFoundType.getAllType();
    }

    @ResponseBody
    //获取分类下的内容
    @RequestMapping("/DetailByTitleByFound")
    public ServerResponse getDetailByTitle(String title) {
        //获取标题id
        int id = 0;
        List<Lostfoundtype> lostfoundtypes = lostFoundDao.GetAll();
        for (Lostfoundtype lostfoundtype : lostfoundtypes) {
            if (lostfoundtype.getName().equals(title)) {
                id = lostfoundtype.getId();
            }
        }
        ServerResponse foundDetailList = foundDetail.getFoundDetailList(id);
        //更新图片地址
        List<Found> data = (List<Found>) foundDetailList.getData();
        String updatePic = "";
        for (Found found : data) {
            if (found.getImg() == null) {
                //如果没有图片地址则退出，防止空指针异常
                break;
            } else {
                updatePic = Util.updatePic(found.getImg());
                found.setImg(updatePic);
            }
        }
        //更新数据
        foundDetailList.setData(data);
        return foundDetailList;
    }

    @ResponseBody
    @RequestMapping("/getUnameByFound")
    public ServerResponse getUname(int id) {
        return foundDetail.getUserName(id);
    }

    //获取发布信息
    @ResponseBody
    @RequestMapping("/getAllFoundUserId")
    public ServerResponse getAllFoundUserId(int user_id) {
        return foundDetail.getAllByIdFoundList(user_id);
    }

    //更改状态
    @ResponseBody
    @RequestMapping("/updateFoundState")
    public ServerResponse updateState(int id, String state, int user_id) {
        return foundDetail.updateState(id, state, user_id);
    }

    //置顶信息
    @ResponseBody
    @RequestMapping("/showFoundList")
    public ServerResponse showLostList(int stick) {
        return foundDetail.showFoundList(stick);
    }
}
