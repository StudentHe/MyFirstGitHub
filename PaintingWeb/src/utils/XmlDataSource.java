package utils;

import entity.Painting;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
//获得xml原始数据源,并将数据导出为一个个javabean类对象，然后存储在集合中
public class XmlDataSource {
    //通过static静态关键字保证数据全局唯一
    private static List<Painting> data=new ArrayList<>();
    private static String dataFile;//储存数据的xml文件路径，先定义为全局变量，后面多个方法都会用到
    static{//静态代码块，当对象被new后就会执行

        //获得painting.xml完整物理路径
            dataFile=XmlDataSource.class.getResource("/painting.xml").getPath();//初步获得路径，但是会把空格转换成"%20"
        //创建URLDecoder对初步获得的物理路径进行处理
        reload();//调用reload()方法对xml文件数据进行读取

    }
    public static void reload()
    {
        URLDecoder decoder=new URLDecoder();
        try {
            decoder.decode(dataFile,"UTF-8");//通过decode方法将物理路径中的"%20"转换成正常的空格
            //然后就得到了最终的painting.xml完整物理路径，路径前会多一个"/"，问题不大
            System.out.println(dataFile);
            //利用dom4j对xml文件进行解析
            SAXReader reader=new SAXReader();
            Document document=reader.read(dataFile);//获得pinting.xml的document对象
            List<Node> nodes=document.selectNodes("/root/painting");//通过xpath获得所有的painting标签

            //对原始数据集合进行清空，并清空的话当多次调用reload方法时，data集合每次都会在集合原先数据的后面添加一次xml文件的数据
            data.clear();
            for(Node n:nodes)
            {
                Element e=(Element)n;
                String id=e.attributeValue("id");
                String pname=e.elementText("pname");

                System.out.println("id:"+id+",pname:"+pname);
                Painting paint=new Painting();//创建javabean类存储数据
                //通过set对Painting对象进行初始化
                paint.setId(Integer.parseInt(id));
                paint.setPname(pname);
                paint.setCategory(Integer.parseInt(e.elementText("category")));
                paint.setPrice(Integer.parseInt(e.elementText("price")));
                paint.setPreview(e.elementText("preview"));
                paint.setDescription(e.elementText("description"));
                //把初始化后的Painting脆响存入list集合中
                data.add(paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public static List<Painting> getRawData()
{//获得存数据的集合list的静态方法
    return  data;
}//获得xml文件里的原始数据集合

    public static void append(Painting paint)
    {//向xml文件中添加数据

        //1.读取XML文档，获取Document对象
        SAXReader reader=new SAXReader();
        OutputStreamWriter writer=null;
        try {
            Document document=reader.read(dataFile);
            Element root=document.getRootElement();
            //2.创建新的painting节点
           Element painting= root.addElement("painting");//向根元素内添加一个painting标签

            //data是xml文件的原始数据集合，两个参数都是String类型的，所以需要转换
            //3.创建painting节点的各个子结点
          painting.addAttribute("id",String.valueOf(data.size()+1));
          painting.addElement("pname").setText(paint.getPname());
            painting.addElement("category").setText(paint.getCategory().toString());
            painting.addElement("price").setText(paint.getPrice().toString());
            painting.addElement("preview").setText(paint.getPreview());
            painting.addElement("description").setText(paint.getDescription());
            //4.将document写入XML，完成追加操作
             writer=new OutputStreamWriter(new FileOutputStream(dataFile),"UTF-8");
            document.write(writer);//调用write()方法将添加完数据的Doucument对象输出到原先的xml文件中,(会覆盖之前的文件)

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(writer!=null)
            {
                try {
                    writer.close();//如果正确执行了try里面的内容，就对输出流进行关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //向xml文件添加完数据后，必须执行一次reload()方法对程序中的data集合进行更新，使
            reload();
        }
    }
    public static void main(String[] args) {
        new XmlDataSource();//测试静态代码块内容
        List<Painting> test=XmlDataSource.getRawData();//获得初始化后的集合
        System.out.println(test);
    }
}
