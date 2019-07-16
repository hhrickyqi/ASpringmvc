package com.weichuang.controller;

import com.weichuang.pojo.Item;
import com.weichuang.service.ItemService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ItemController {

    /*@Autowired
    private JdbcTemplate jt;*/
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/list.do" , "/itemList.do"}, method = {RequestMethod.GET , RequestMethod.POST})//在浏览器中打开时输入为括号内容
    public ModelAndView getItemList(){

        /*String sql = "select * from items";
        List<Item> itemList = jt.query(sql, new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int i) throws SQLException {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setDetail(resultSet.getString("detail"));
                item.setCreatetime(resultSet.getDate("createtime"));
                return item;
            }
        });         //整合之前所用方法*/
        List<Item> itemList = itemService.getAllItem();
        ModelAndView mav = new ModelAndView();
        mav.addObject("itemList" , itemList);
        mav.setViewName("itemList");
        return mav;
    }

    @RequestMapping("/itemEdit.do")
    public String itemEdit(@RequestParam(value = "id" , defaultValue = "1" , required = false) Integer dsdds , Boolean status, Model model) throws UnsupportedEncodingException {
        Item item = itemService.getItemById(dsdds);
        System.out.println("status= " + status);
       /* ModelAndView mav = new ModelAndView();
        mav.addObject("item" , item);
        mav.setViewName("editItem");*/
        model.addAttribute("item" , item);
        return "editItem";
    }

    @RequestMapping("/updateItem.do")
    public ModelAndView updateItem(@RequestParam(value = "pictureFile" , required = false) MultipartFile pictureFile , Item item , HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");//由于是post提交，需要在web.xml里面配置过滤其解决乱码问题
        //使用随机串新命名一个文件
        String newFileName = UUID.randomUUID().toString().replace("-" , "");
        //获取上传文件的路径
        String realPath = request.getServletContext().getRealPath("image");
        //获取文件的扩展名 jpg , png
        String extension = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //上传文件
        pictureFile.transferTo(new File(realPath + "/" + newFileName + "." + extension));
        //存文件名称至对象
        item.setPic(newFileName + "." + extension);

        System.out.println("realPath = " + realPath);
        itemService.updateItem(item);
       // List<Item> itemList = itemService.getAllItem();
        ModelAndView mav = new ModelAndView();
        //mav.addObject("itemList" , itemList);
        mav.addObject("item" , item);
        mav.setViewName("editItem");
        return mav;
    }
    @RequestMapping("/deleteItems.do")
    public ModelAndView deleteItems(String[] ids){
        System.out.println(ids);
        itemService.deleteItems(ids);
        List<Item> itemList = itemService.getAllItem();
        ModelAndView mav = new ModelAndView();
        mav.addObject("itemList" , itemList);
        mav.setViewName("itemList");
        return mav;
    }

    @RequestMapping("/addItem.do")
    public String addItem(@RequestParam(value = "pictureFile" , required = false) MultipartFile pictureFile , Item item , HttpServletRequest request , Model model) throws IOException {
        //使用随机串新命名一个文件
        String newFileName = UUID.randomUUID().toString().replace("-" , "");
        //获取上传文件的路径
        String realPath = request.getServletContext().getRealPath("image");
        //获取文件的扩展名 jpg , png
        String extension = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //上传文件
        pictureFile.transferTo(new File(realPath + "/" + newFileName + "." + extension));
        //存文件名称至对象
        item.setPic(newFileName + "." + extension);
        System.out.println("realPath = " + realPath);

        item.setCreatetime(new Date());
        itemService.addItem(item);
        List<Item> itemList = itemService.getAllItem();
        model.addAttribute("itemList" , itemList);
        return "itemList";
    }

    @RequestMapping("/addUI.do")
    public String addUI(Model model){
        model.addAttribute("数据" , "");
        return "addItem";
    }

    @RequestMapping("/item_list.do")
    public void getTest(HttpServletRequest  request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("aa" , "");
        response.getWriter().write("json格式的数据");
        request.getRequestDispatcher("视图").forward(request , response);
        response.sendRedirect("视图");
    }


}
