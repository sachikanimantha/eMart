package sachika.eMart;

import java.io.Serializable;

/**
 * Created by Sachika on 12/10/2016.
 */

public class Cart implements Serializable {

    public int pid;
    public int sub_cat_id;
    public String pname;
    public int brand;
    public String description;
    public int qty;
    public double price;
    public String pimg;
    public int cart_id;
    public int cust_id;
    public int pro_id;
    public int cart_qty;
    public double total;
    public int status;
    public  boolean success;

    /*
        pid
        sub_cat_id
        pname
        brand
        description
        qty
        price
        pimg
        cart_id
        cust_id
        pro_id
        cart_qty
        total
        status
     */
}
