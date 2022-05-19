package com.example.meal_ordering_system.service.impl;

import com.example.meal_ordering_system.dao.MenusMapper;
import com.example.meal_ordering_system.entity.Menus;
import com.example.meal_ordering_system.service.MenusService;
import com.example.meal_ordering_system.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MenusServiceImpl implements MenusService {
    @Autowired
    private MenusMapper menusMapper;
    @Override
    public List<Menus> allMenus(Menus menus) {
        List<Menus> menus1 = menusMapper.selectByExample(null);
        return menus1;
    }

    @Override
    public Integer toAddPage(Menus menus) {
        return menusMapper.insertSelective(menus);
    }

    @Override
    public Menus updateToPage(Integer id) {
        return menusMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(Menus menus) {
        return menusMapper.updateByPrimaryKeySelective(menus);
    }

    @Override
    public ModelAndView insert(HttpSession session, Menus menus, MultipartFile img, ModelAndView mv) {
        String path = "D:\\apache-server\\apache-tomcat-8.5.37\\webapps\\upload";
        if (img != null && !img.isEmpty()){
            File pathFile = new File(path);

            String filename = img.getOriginalFilename();

            String suffix = filename.substring(filename.lastIndexOf('.'));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

            String prefix = formatter.format(new Date());

            filename = prefix+suffix;

            try {
                img.transferTo(new File(pathFile,filename));

                //上传成功之后需要把新头像的路径给存储到menus对象中，因为等会需要刷新数据库
                menus.setImgpath("img/"+filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //插入
        int affectedRows = menusMapper.insertSelective(menus);

        if (affectedRows > 0){
            //插入成功后更新数据
            List<Menus> list = menusMapper.selectByExample(null);

            //刷新页面
            if (menus != null){
                session.setAttribute("list",list);

                mv.setViewName("/admin/menus");
            }
        }
        return mv;
    }

    @Override
    public ResultVo delete(Integer id) {
        ResultVo resultVo = new ResultVo();

        int rows = menusMapper.deleteByPrimaryKey(id);

        if (rows > 0){
            resultVo.setCode(200);
        }else {
            resultVo.setCode(-100);
        }
        return resultVo;
    }
}
