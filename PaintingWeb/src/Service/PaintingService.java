package Service;

import dao.PaintingDao;
import entity.Painting;
import utils.PageModel;

public class PaintingService {
    private PaintingDao paintingDao = new PaintingDao();

    /**
     * service获得dao中通过pagination方法获得的当前页对象
     *
     * @param page 当前页
     * @param rows 每页记录数
     * @return 当前页数据
     */
    //把可变参数设置为String类型比int类型方便，可以通过可变参数的长度length和可变参数第一个数据是否为空判断是否传递可变参数到方法，
    // 在后面转换成int类型
    public PageModel pagination(int page, int rows, String... category) {
        if (rows == 0) {
            throw new RuntimeException("无效的rows参数");
        }
        if (category.length == 0||category[0] == null) {
            return paintingDao.pagination(page, rows);
        }
 else{
     return paintingDao.pagination(page,rows,Integer.parseInt(category[0]));
        }
    }
    public static void create(Painting painting)
    {    //调用dao中的create方法
        PaintingDao.create(painting);
    }
}
