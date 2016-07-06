package uk.co.littlehugs.sacramentagenda;

/**
 * Created by hugs on 12/02/16.
 */
public class Hymns {
    private int HYMN_ID;
    private int HYMN_NUM;
    private String HYMN_NAME;

    public Hymns()
    {
        HYMN_ID=0;
        HYMN_NUM=0;
        HYMN_NAME="";
    }

    public Hymns(int hYMN_NUM, String hYMN_NAME) {
        HYMN_NUM = hYMN_NUM;
        HYMN_NAME = hYMN_NAME;
    }

    public int getID()
    {
        return HYMN_ID;
    }
    public int getHYMN_NUM() {
        return HYMN_NUM;
    }
    public String getHYMN_NAME() {return HYMN_NAME; }

    public void setHYMN_ID(int id)
    {
        HYMN_ID=id;
    }
    public void setHYMN_NUM(int hYMN_NUM) {
        HYMN_NUM = hYMN_NUM;
    }
    public void setHYMN_NAME(String hYMN_NAME) { HYMN_NAME = hYMN_NAME; }
}