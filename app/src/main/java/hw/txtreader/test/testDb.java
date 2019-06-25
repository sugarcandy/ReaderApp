package hw.txtreader.test;

import java.util.List;

import hw.txtreader.dao.DbHelper;
import hw.txtreader.bean.BookBean;


public class testDb{
    public static String insert(){
        BookBean bookBean=new BookBean();
        bookBean.setChapterUrl("124");
        bookBean.setAuthor("李四");
        bookBean.setName("仙剑奇侠传");
        bookBean.setNoteUrl("124");
        long res=DbHelper.getInstance().getmDaoSession().getBookBeanDao().insertOrReplace(bookBean);
        if(res>0)
            return "插入成功"+res+"条数据！";
        else
            return "插入数据失败！";

    }

    public static List<BookBean> query(){
        return DbHelper.getInstance().getmDaoSession().getBookBeanDao().loadAll();
    }



}
