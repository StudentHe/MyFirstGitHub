package controller;

import Service.PaintingService;
import utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlet")
public class PaintingController extends HttpServlet {
private PaintingService paintingService=new PaintingService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收http数据
        String page= request.getParameter("p");//获得当前页数参数
             String rows=request.getParameter("r");//获得每页最多的记录数参数
             String category=request.getParameter("c");//获得风格参数
             //当url中没有设置p或r时，会使用下面设置的默认值
             if(page==null)
             {
                 page="1";
             }
             if(rows==null)
             {
                 rows="6";
             }
             //2.调用service方法，得到处理结果

             PageModel pageModel=paintingService.pagination(Integer.parseInt(page),Integer.parseInt(rows),category);
             request.setAttribute("PageModel",pageModel);
             //3.请求转发至对应jsp（View)进行数据展现
             request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request,response);
    }
}
