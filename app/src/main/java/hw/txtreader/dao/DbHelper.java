package hw.txtreader.dao;


import org.greenrobot.greendao.database.Database;

import hw.txtreader.MApplication;

public class DbHelper {
    private DaoMaster.DevOpenHelper mHelper;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DbHelper() {
        mHelper = new DaoMaster.DevOpenHelper(MApplication.getInstance(), "reader.db", null);
        db = mHelper.getWritableDb();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private static DbHelper instance;

    public static DbHelper getInstance() {
        if (null == instance) {
            synchronized (DbHelper.class) {
                if (null == instance) instance = new DbHelper();
            }
        }
        return instance;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public Database getDb() {
        return db;
    }
}
