package com.school.controller;

import com.school.services.interfaces.SearchNeedInfo;
import com.school.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SearchInfoController {
    @Autowired
    private SearchNeedInfo searchNeedInfo;
    @ResponseBody
    @RequestMapping("/searchInfo")
    public ServerResponse searchInfo(String value) {
        return searchNeedInfo.searchInfo(value);
    }
}
