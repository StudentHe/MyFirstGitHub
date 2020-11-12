package utils;

import java.util.ArrayList;
import java.util.List;
//实现分页效果
public class PageModel {
    private int page;//页号
    private int totalPages;//总页数
    private int rows;//每页记录数，就是每页最多的数量
    private  int totalRows;//总记录数，就是总数量
    private int pageStartRow;//当前页从第n个(总数量的第n个)记录数开始，可以理解为将所有数据看成数组，n就是数组下标
    private  int pageEndRow;//当前页从第n个(总数量的第n个)记录数结束，可以理解为将所有数据看成数组，n就是数组下标
    private boolean hasNextPage;//是否存在下一页
    private  boolean hasPreviousPage;//是否存在上一页
    private List pageData;//当前页面记录数集合
    public PageModel(){};

    public PageModel(List data,int page,int rows) {

        this.page = page;
        this.rows = rows;
        totalRows=data.size();//总记录数就是保存painting.xml的原始数据的集合的长度

        // ceil向上取整返回值是double，通过包装类Double将double类型先转换成Double类型,再通过intValue()方法转换成int类型
        //totalRows和rows都是int型，得将他两其中一个乘以1f(浮点数1)
        totalPages=Double.valueOf(Math.ceil(totalRows/(this.rows*1f))).intValue();

            pageStartRow = (page - 1) * rows;//得到当前页第几个记录数开始


        pageEndRow=page*rows;//得到当前页第几个记录数结束，当当前也不是最后一页时!
        if(pageEndRow>totalRows)//当前页是最后一页时，获得当前页第几行结束
        {
            pageEndRow=totalRows;
        }
        if(page>1)
        {
            hasPreviousPage=true;//当前页大于1时，有上一页，hasPreviousPage返回true
        }
        else
        {
            hasPreviousPage=false;//当前页小于1时，没有上一页，hasPreviousPage返回false
        }
        if(page<totalPages)
          {
              hasNextPage=true;//当前页是最后一页时，hasNextPage返回false，没有下一页

          }
          else
          {
              hasNextPage=false;//当前页不是最后一页时，hasNextPage返回true，有下一页

          }
          pageData=data.subList(pageStartRow,pageEndRow);//获得分页集合，就是当前页的各个记录数组成一个集合

    }
//getter和setter方法
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public static void main(String[] args) {
        //测试用
        List sample=new ArrayList();
        for(int i=0;i<100;i++)
        {
            sample.add(i+1);
        }
        PageModel pageTest=new PageModel(sample,6,8);
        System.out.println("当前页："+pageTest.getPage());
        System.out.println("当前页开始记录数:"+pageTest.getPageStartRow());
        System.out.println("当前页结束记录数:"+pageTest.getPageEndRow());
        System.out.println("总页数:"+pageTest.getTotalPages());
        System.out.println("总记录数:"+pageTest.getTotalRows());
        System.out.println("每页最多记录数:"+pageTest.getRows());
        System.out.println("是否有下一页:"+pageTest.isHasNextPage());
        System.out.println("是否有上一页:"+pageTest.isHasPreviousPage());
        System.out.println(pageTest.getPageData());
    }
}
