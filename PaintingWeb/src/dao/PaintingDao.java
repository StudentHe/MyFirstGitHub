package dao;

import entity.Painting;
import utils.PageModel;
import utils.XmlDataSource;

import java.util.ArrayList;
import java.util.List;
//dao类只负责数据的增删，不进行判断等其他额外操作
public class PaintingDao {
    /**
     *分页查询油画数据，就是根据指定页号和每页记录数，返回一个当前页对象
     *
     * @param page  页号
     * @param rows   每页记录数
     * @return  当前页对象
     */
    public PageModel pagination(int page,int rows)
    {
        List<Painting> list= XmlDataSource.getRawData();//获得painting.xml文件的原始数据
         PageModel pagemodel=new PageModel(list,page,rows);//获得PageModel对象，并传参
         return pagemodel;//返回当前页对象
    }
    //重载方法
    public PageModel pagination(int page,int rows,int category)
    {
        List<Painting> list= XmlDataSource.getRawData();//获得painting.xml文件的原始数据
        List<Painting> listNew=new ArrayList<>();
        for(Painting p:list)
        {
            if(p.getCategory()==category)
            {
                listNew.add(p);

            }
        }
        PageModel pagemodel=new PageModel(listNew,page,rows);//获得PageModel对象，并传参
        return pagemodel;//返回当前页对象
    }
    public static void create(Painting painting)
    {      //调用XmlDataSource得append方法
        XmlDataSource.append(painting);
    }
}
