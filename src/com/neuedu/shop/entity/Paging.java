package com.neuedu.shop.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author 杜晓鹏
 * @create 2018-11-30 16:16
 */
@Data
public class Paging<T> {
    //limit的偏移量
    private int startIndex;
    //当前页数
    private int pageNumber;
    //一页显示多少个
    private int pageSize;
    //一共多少条数据
    private int count;
    //首页
    private int startPage;
    //结束页
    private int endPage;
    //一共多少页
    private int pages;
    //存当前页显示的数据
    private List<T> objects;

    public Paging() {

    }

    public Paging(int pNumber, int pageSize, int count) {


        this.pageSize = pageSize;

        //总页数
        if (count % pageSize == 0) {
            this.pages = count / pageSize;
        } else {
            this.pages = count / pageSize + 1;
        }
        //给pageNumber赋值
        if (pages != 0){
            if (pNumber > 0 && pNumber <= pages) {
                this.pageNumber = pNumber;
            } else if (pNumber <= 0) {
                this.pageNumber = 1;
            } else {
                this.pageNumber = pages;
            }
        }else {
            this.pageNumber = 1;
        }


        this.startIndex = (this.pageNumber - 1) * pageSize;
        //一共有多少页
        this.endPage = (int) Math.ceil((double)count / (double)pageSize);

      /*  if (pages <= 5){
            this.startPage = 1;
            this.endPage = pages;
        }else {
            if (pageNumber <= 3){
                this.startPage = 1;
            } else {
                this.startPage = pageNumber - 2;
                if (pages - pageNumber <= 2){
                    this.endPage = pages;
                }else {
                    this.endPage = pageNumber + 2;
                }
            }
        }*/
    }

}