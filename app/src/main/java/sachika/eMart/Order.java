package sachika.eMart;

import java.io.Serializable;

/**
 * Created by Sachika on 12/27/2016.
 */

public class Order implements Serializable {
    public int order_id;
    public int cust_id;
    public double amount;
    public String o_date;
}
