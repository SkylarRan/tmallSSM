package com.tmall.controller;

import com.tmall.pojo.PropertyValue;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping("admin_propertyValue_list")
    public String list(int pid, Model model){
        List<PropertyValue> pvs = propertyValueService.list(pid);
        model.addAttribute("pvs", pvs);
        return "";
    }
}
