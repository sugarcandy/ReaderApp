package hw.txtreader.bean;

import com.bifan.txtreaderlib.bean.Chapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 读取进来的文本信息
 */
@Entity
public class BookBean {
    private String name; //小说名

    private String tag;

    @Id
    private String noteUrl; //小说本地MD5

    private String chapterUrl;  //章节目录地址

    @Transient
    private List<Chapter> chapterlist = new ArrayList<>();    //章节列表

    private long finalRefreshData;  //章节最后更新时间

    private String coverUrl; //小说封面

    private String author;//作者

    private String introduce; //简介

    private String origin; //来源

    @Generated(hash = 169255905)
    public BookBean(String name, String tag, String noteUrl, String chapterUrl,
            long finalRefreshData, String coverUrl, String author, String introduce,
            String origin) {
        this.name = name;
        this.tag = tag;
        this.noteUrl = noteUrl;
        this.chapterUrl = chapterUrl;
        this.finalRefreshData = finalRefreshData;
        this.coverUrl = coverUrl;
        this.author = author;
        this.introduce = introduce;
        this.origin = origin;
    }

    @Generated(hash = 269018259)
    public BookBean() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNoteUrl() {
        return this.noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public String getChapterUrl() {
        return this.chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public long getFinalRefreshData() {
        return this.finalRefreshData;
    }

    public void setFinalRefreshData(long finalRefreshData) {
        this.finalRefreshData = finalRefreshData;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", noteUrl='" + noteUrl + '\'' +
                ", chapterUrl='" + chapterUrl + '\'' +
                ", chapterlist=" + chapterlist +
                ", finalRefreshData=" + finalRefreshData +
                ", coverUrl='" + coverUrl + '\'' +
                ", author='" + author + '\'' +
                ", introduce='" + introduce + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
