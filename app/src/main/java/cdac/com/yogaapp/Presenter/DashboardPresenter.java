package cdac.com.yogaapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
//import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cdac.com.yogaapp.helper.StringValueHelper;

/**
 * Created by vinod on 4/11/2019.
 */

public class DashboardPresenter implements IDashboardPresenter{

    Context context;

    public DashboardPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onTextMarquee(TextView textView) {
        textView.setSelected(true);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
    }




}
