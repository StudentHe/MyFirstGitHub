package controller;

import Service.PaintingService;
import entity.Painting;
import jdk.jfr.Frequency;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.PageModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/management")
public class ManagementController extends HttpServlet {
    private int i=16;
    private PaintingService paintingService=new PaintingService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//用于doPost
        response.setContentType("text/html;charset=utf-8");
        String method=request.getParameter("method");
        if(method.equals("list"))
        {
            this.list(request,response);//调用油画列表显示方法
        }
        else if(method.equals("showCreate"))
        {
            this.showCreatePage(request,response);//调用新增油画表单界面
        }
        else if(method.equals("create"))
        {
            this.create(request,response);//调用新增油画表单提交数据处理方法
        }
    }
    private void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String page= request.getParameter("p");//获得当前页数参数
        String rows=request.getParameter("r");//获得每页最多的记录数参数
        //当url中没有设置p或r时，会使用下面设置的默认值
        if(page==null)
        {
            page="1";
        }
        if(rows==null)
        {
            rows="6";
        }
        PageModel pageModel= paintingService.pagination(Integer.parseInt(page),Integer.parseInt(rows));
        request.setAttribute("PageModel",pageModel);
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request,response);
    }
    private void showCreatePage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(request,response);
    }
    private void create(HttpServletRequest request,HttpServletResponse response)
    {//文件上传是的数据处理与标准表单完全不同
        //1.初始化FileUpload组件
        FileItemFactory  factory=new DiskFileItemFactory();//用于将前端表单的数据转化为一个个FileItem对象
        ServletFileUpload stf=new ServletFileUpload(factory);//为FileUpload组件提供Java Web的Http请求解析
        //2.遍历所有的FileItem
        try {
            List<FileItem> formData=stf.parseRequest(request);//通过表单发来的请求获得表单中的所有数据并转换为FileItem对象
            Painting painting=new Painting();
            for(FileItem fi:formData)
            {
                   if(fi.isFormField())//返回true表示fi是普通表单项，否则表示fi是文件表单项
                   {
                       //getString("UTF-8")获得输入框标签的值,getFieldName()获得输入框标签的name属性
                       System.out.println("普通输入项:"+fi.getFieldName()+" "+fi.getString("UTF-8"));
                       switch (fi.getFieldName())
                       {
                           case "pname": painting.setPname(fi.getString("UTF-8"));
                               break;
                           case "category":painting.setCategory(Integer.parseInt(fi.getString("UTF-8")));
                               break;
                           case "price":painting.setPrice(Integer.parseInt(fi.getString("UTF-8")));
                               break;
                           case "description":painting.setDescription(fi.getString("UTF-8"));
                               break;
                           default:
                               break;
                       }
                   }
                   else
                   {
                       System.out.println("文件上传项:"+fi.getFieldName());
                       //3.文件保存到服务器目录
                       //获得tomcat在实际运行环境下对应目录的物理地址，也就是说文件是上传到服务器目录中，并不是项目目录里的upload文件夹
                       //获得upload目录的完整物理地址
                       String path=request.getServletContext().getRealPath("/upload");
                       /* 将要上传的文件的文件名截取拓展名并通过全局变量i进行递加重命名上传到web项目upload目录下
                       String fileName1=i+fi.getName().substring(fi.getName().lastIndexOf("."));
                       i++;
                       */
                       //通过UUID随机生成全世界唯一的字符串进行重命名上传到web项目upload目录下
                       //对文件名进行重命名
                       String fileName2= UUID.randomUUID().toString();
                       // "文件上传项.getName()"方法获取上传文件的文件名
                       //获取文件的扩展名，扩展名不要变
                       String suffix= fi.getName().substring(fi.getName().lastIndexOf("."));
                       System.out.println(path);
                       System.out.println(fileName2+suffix);
                       //FileItem对象的write方法将文件上传到服务器，new File()是被上传后的文件
                       fi.write(new File(path,fileName2+suffix));
                       painting.setPreview("/upload/"+fileName2+suffix);
                   }
            }
            PaintingService.create(painting);//调用Service得create方法进行逐级传递
            //如果跳转的新界面跟新增数据关系紧密，而且需要对新增数据进行下一步操作就要使用请求转发到一个操作页面，
            //但是如果跳转界面跟新增数据关系不紧密，是跳转到一个页面做其他处理，则使用响应重定向更好
            response.sendRedirect("/management?method=list");//重定向到油画列表界面



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
