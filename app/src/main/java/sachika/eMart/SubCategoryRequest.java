package sachika.eMart;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import sachika.eMart.web.HTTPPaths;

/**
 * Created by Sachika on 12/6/2016.
 */

public class SubCategoryRequest extends StringRequest {
    private  static final String URL = HTTPPaths.baseUrl+ "sub_category.php";
    //  private  static final String URL = "http://sampletemp.96.lt/android/sub_category.php";
    Map<String, String> params;


    public SubCategoryRequest(int id, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        params = new HashMap<>();
        params.put("cat_id",id+"");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
