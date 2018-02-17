package sachika.eMart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRScanFragment extends Fragment {

    ImageView ivScan;

    public QRScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qrscan, container, false);

        //initialize widgets
        ivScan = (ImageView) view.findViewById(R.id.ivScan);

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan QR");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });


        return view;
    }

    //QrCode
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(getContext(), "You Canceled the scan", Toast.LENGTH_SHORT).show();
            }else {
                String qrResult=result.getContents();
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, qrResult, Toast.LENGTH_SHORT).show();
                /*String array[] = qrResult.split(" ");
                int pro_id=Integer.parseInt(array[0]);
                int sub_cat_id=Integer.parseInt(array[1]);
                int brand_id=Integer.parseInt(array[2]);*/
                //error // Toast.makeText(this, pro_id+ " id" , Toast.LENGTH_SHORT).show();


            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
