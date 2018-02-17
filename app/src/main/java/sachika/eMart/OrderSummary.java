package sachika.eMart;

import java.io.Serializable;

/**
 * Created by Sachika on 12/27/2016.
 */

public class OrderSummary implements Serializable {

    public int order_id;
    public int cart_id;
    public int pro_id;
    public String pname;
    public double price;
    public int cart_qty;
    public double total;
    public String pimg;


}
