package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //1.封装pageBean对象
        PageBean<Route> pb = new PageBean<>();
        //2.设置当前页码
        pb.setCurrentPage(currentPage);
        //3.设置每页条数
        pb.setPageSize(pageSize);
        //4.设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //5.设置每页数据集合
        //当前起始位置 start = (currentPage - 1) * pageSize
        int start =  (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);
        //6.设置总页数 totalPage = totalCount / pageSize
        int totalPage =  totalCount % pageSize == 0?totalCount / pageSize:(totalCount / pageSize)+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据rid去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.根据route的rid查询routeImg图片的集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2将图片集合信息放入route对象中
        route.setRouteImgList(routeImgList);
        //3.根据route的sid(商家id）查询商家信息
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
