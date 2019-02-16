package com.timmy.thirdframework.database;

import android.os.Bundle;
import android.view.View;

import com.timmy.baselib.base.BaseActivity;
import com.timmy.baselib.utils.LogUtils;
import com.timmy.thirdframework.R;
import com.timmy.thirdframework.database.bean.Photo;
import com.timmy.thirdframework.database.bean.User;
import com.timmy.thirdframework.database.core.BaseDao;
import com.timmy.thirdframework.database.core.BaseDaoFactory;
import com.timmy.thirdframework.database.sub_sqlite.BaseDaoSubFactory;
import com.timmy.thirdframework.database.sub_sqlite.PhotoDao;

import java.util.List;


public class DataBaseActivity extends BaseActivity {

    private BaseDao baseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        baseDao = BaseDaoFactory.getInstance().getBaseDao(BaseDao.class,User.class);
    }

    public void insert(View view) {
        for (int i = 0; i < 15; i++) {
            User user = new User();
            user.setName("jeorge" + i);
            user.setAge(i + 4);
            user.setId(i + 1000);
            baseDao.insert(user);
        }
    }

    public void delete(View view) {
        baseDao.delete("id = ", new String[]{"1004"});
    }

    public void update(View view) {
        User user = new User();
        user.setName("Timmy11111");
        user.setAge(28);
        user.setId(1121);
        baseDao.update(user,"_id = ?",new String[]{"1006"});
    }

    public void find(View view) {
        List<User> users = baseDao.queryAll();
        LogUtils.d(users.toString());
    }

    public void subDatabase(View view) {
        PhotoDao photoDao = BaseDaoSubFactory.getOurInstance().getBaseDao(PhotoDao.class, Photo.class);
        long insert = photoDao.insert(new Photo("123.jpg", "293749872"));
        LogUtils.d(insert);
    }

}
