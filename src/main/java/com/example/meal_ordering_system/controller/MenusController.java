package com.example.meal_ordering_system.controller;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowPlanCacheStatusStatement;
import com.example.meal_ordering_system.entity.Menus;
import com.example.meal_ordering_system.service.MenusService;
import com.example.meal_ordering_system.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("menus")
public class MenusController {
    @Autowired
    private MenusService menusService;
    //菜单信息查询
    @RequestMapping(value = "/allMenus",method = RequestMethod.GET)
    public String allMenus(Menus menus, HttpSession session){
        List<Menus> menus1 = menusService.allMenus(menus);
        session.setAttribute("list",menus1);
        return "/admin/menus";
    }

    //进入菜单添加页
    @RequestMapping(value = "toAddPage",method = RequestMethod.GET)
    public String toAddPage(Menus menus){
        return "/admin/menus_add";
    }

    //进入菜单修改页
    @RequestMapping(value = "toUpdatePage",method = RequestMethod.GET)

    public String updateToPage(Integer id,HttpSession session){
        Menus menus = menusService.updateToPage(id);
        session.setAttribute("menus",menus);
        return "/admin/menus_update";
    }


    //修改菜单信息
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ModelAndView update(Menus menus,HttpSession session){
        Integer integer = menusService.update(menus);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/menus/allMenus");
        return mv;
    }

    /**
     * 菜单添加
     * 文件上传
     *
     * @param menus
     * @return
     */
    @RequestMapping(value = "insert",method = RequestMethod.GET)
    public ModelAndView insert(HttpSession session, ModelAndView mv, Menus menus, MultipartFile img){
        return menusService.insert(session,menus,img,mv);
    }


    /*
     * 后台菜单删除
     * 没有弹框提示
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView menusDel(Integer id,ModelAndView mv,HttpSession session,Menus menus){
        ResultVo resultVo = menusService.delete(id);
        this.allMenus(menus,session);
        mv.addObject("resultVo",resultVo);
        mv.setViewName("/admin/menus");
        return mv;
    }

}
