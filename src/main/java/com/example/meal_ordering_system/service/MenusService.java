package com.example.meal_ordering_system.service;

import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.example.meal_ordering_system.entity.Menus;
import com.example.meal_ordering_system.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MenusService {
    List<Menus> allMenus(Menus menus);
    Integer toAddPage(Menus menus);

    Menus updateToPage(Integer id);

    Integer update(Menus menus);

    ModelAndView insert(HttpSession session, Menus menus, MultipartFile img, ModelAndView mv);

    ResultVo delete(Integer id);
}
