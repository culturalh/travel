package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询是否存在
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 注册
     */
    public void save(User user);


    User fingByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
