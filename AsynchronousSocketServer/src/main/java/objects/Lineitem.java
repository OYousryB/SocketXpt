package objects;

import java.io.*;

public class Lineitem implements Serializable {
    private long l_orderkey;
    private long l_partkey;
    private long l_suppkey;
    private long l_linenumber;
    private double l_quantity;
    private double l_extendedprice;
    private double l_discount;
    private double l_tax;
    private String l_returnflag;
    private String l_linestatus;
    private String l_shipdate;
    private String l_commitdate;
    private String l_receiptdate;
    private String l_shipinstruct;
    private String l_shipmode;
    private String l_comment;

    public Lineitem() {
    }

    public Lineitem(long l_orderkey, long l_partkey, long l_suppkey, long l_linenumber, double l_quantity, double l_extendedprice, double l_discount, double l_tax, String l_returnflag, String l_linestatus, String l_shipdate, String l_commitdate, String l_receiptdate, String l_shipinstruct, String l_shipmode, String l_comment) {
        this.l_orderkey = l_orderkey;
        this.l_partkey = l_partkey;
        this.l_suppkey = l_suppkey;
        this.l_linenumber = l_linenumber;
        this.l_quantity = l_quantity;
        this.l_extendedprice = l_extendedprice;
        this.l_discount = l_discount;
        this.l_tax = l_tax;
        this.l_returnflag = l_returnflag;
        this.l_linestatus = l_linestatus;
        this.l_shipdate = l_shipdate;
        this.l_commitdate = l_commitdate;
        this.l_receiptdate = l_receiptdate;
        this.l_shipinstruct = l_shipinstruct;
        this.l_shipmode = l_shipmode;
        this.l_comment = l_comment;
    }

    public long getL_orderkey() {
        return l_orderkey;
    }

    public void setL_orderkey(long l_orderkey) {
        this.l_orderkey = l_orderkey;
    }

    public long getL_partkey() {
        return l_partkey;
    }

    public void setL_partkey(long l_partkey) {
        this.l_partkey = l_partkey;
    }

    public long getL_suppkey() {
        return l_suppkey;
    }

    public void setL_suppkey(long l_suppkey) {
        this.l_suppkey = l_suppkey;
    }

    public long getL_linenumber() {
        return l_linenumber;
    }

    public void setL_linenumber(long l_linenumber) {
        this.l_linenumber = l_linenumber;
    }

    public double getL_quantity() {
        return l_quantity;
    }

    public void setL_quantity(double l_quantity) {
        this.l_quantity = l_quantity;
    }

    public double getL_extendedprice() {
        return l_extendedprice;
    }

    public void setL_extendedprice(double l_extendedprice) {
        this.l_extendedprice = l_extendedprice;
    }

    public double getL_discount() {
        return l_discount;
    }

    public void setL_discount(double l_discount) {
        this.l_discount = l_discount;
    }

    public double getL_tax() {
        return l_tax;
    }

    public void setL_tax(double l_tax) {
        this.l_tax = l_tax;
    }

    public String getL_returnflag() {
        return l_returnflag;
    }

    public void setL_returnflag(String l_returnflag) {
        this.l_returnflag = l_returnflag;
    }

    public String getL_linestatus() {
        return l_linestatus;
    }

    public void setL_linestatus(String l_linestatus) {
        this.l_linestatus = l_linestatus;
    }

    public String getL_shipdate() {
        return l_shipdate;
    }

    public void setL_shipdate(String l_shipdate) {
        this.l_shipdate = l_shipdate;
    }

    public String getL_commitdate() {
        return l_commitdate;
    }

    public void setL_commitdate(String l_commitdate) {
        this.l_commitdate = l_commitdate;
    }

    public String getL_receiptdate() {
        return l_receiptdate;
    }

    public void setL_receiptdate(String l_receiptdate) {
        this.l_receiptdate = l_receiptdate;
    }

    public String getL_shipinstruct() {
        return l_shipinstruct;
    }

    public void setL_shipinstruct(String l_shipinstruct) {
        this.l_shipinstruct = l_shipinstruct;
    }

    public String getL_shipmode() {
        return l_shipmode;
    }

    public void setL_shipmode(String l_shipmode) {
        this.l_shipmode = l_shipmode;
    }

    public String getL_comment() {
        return l_comment;
    }

    public void setL_comment(String l_comment) {
        this.l_comment = l_comment;
    }
}
