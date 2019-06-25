package hw.txtreader.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 书架
 */
@Entity
public class BookShelfBean {
    @Id
    private String noteUrl; //对应BookInfoBean noteUrl;

    private int durChapter;   //当前章节 （包括番外）

    //private int durChapterPage = BookContentView.DURPAGEINDEXBEGIN;  // 当前章节位置   用页码

    private long finalDate;  //最后阅读时间

    private String tag;

    @Transient
    private BookBean bookBean = new BookBean();

    @Generated(hash = 1754467827)
    public BookShelfBean(String noteUrl, int durChapter, long finalDate, String tag) {
        this.noteUrl = noteUrl;
        this.durChapter = durChapter;
        this.finalDate = finalDate;
        this.tag = tag;
    }

    @Generated(hash = 1462228839)
    public BookShelfBean() {
    }

    public String getNoteUrl() {
        return this.noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public int getDurChapter() {
        return this.durChapter;
    }

    public void setDurChapter(int durChapter) {
        this.durChapter = durChapter;
    }

    public long getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(long finalDate) {
        this.finalDate = finalDate;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "BookShelfBean{" +
                "noteUrl='" + noteUrl + '\'' +
                ", durChapter=" + durChapter +
                ", finalDate=" + finalDate +
                ", tag='" + tag + '\'' +
                ", bookBean=" + bookBean +
                '}';
    }
}
