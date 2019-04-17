package com.tmall.util;

public class Page {
    private int start;//开始index
    private int count;//每页显示条数
    private int total;//总个数
    private String param;//参数

    private static final int defaultCount = 5; //默认每页显示5条数据

    public Page() {
        this.count = defaultCount;
    }

    public Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;
    }

    public boolean isHasPrevious() {
        if (0 == start) return false;
        return true;
    }

    public boolean isHasNext() {
        if (start == getLast()) return false;
        return true;
    }

    public int getTotalPage() {
        int totalPage;

        if (0 == total % count) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }

        if (0 == totalPage) {
            totalPage = 1;
        }

        return totalPage;
    }

    //获取最后一页的开始index
    public int getLast() {
        int last;

        if (0 == total % count) {
            //假设总数是50，是能够被5整除的，那么最后一页的开始就是45
            last = total - count;
        } else {
            //假设总数是52，是能够被5整除的，那么最后一页的开始就是50
            last = total - total % count;
        }

        //假设总数是2，则last = 2 - 5 = -3
        last = last < 0 ? 0 : last;

        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPrevious()=" + isHasPrevious() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
