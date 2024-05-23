package cdac.com.yogaapp.Adapter;

//import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import cdac.com.yogaapp.Model.DownloadPojo;
import cdac.com.yogaapp.R;
import cdac.com.yogaapp.View.PlayVideoActivity;
import cdac.com.yogaapp.helper.EncryptDecryptUtils;
import cdac.com.yogaapp.helper.FileUtils;

/**
 * Created by vinod on 5/22/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<DownloadPojo> values;
    private Context context;

    public CustomAdapter(Context context, ArrayList<DownloadPojo> myDataset) {
        values = myDataset;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recyler_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DownloadPojo myPojo = values.get(position);
        holder.imageViewVideo.setImageResource(myPojo.getImage());
        holder.textViewName.setText(myPojo.getName());
        holder.textViewTime.setText(myPojo.getTime());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playVideo(FileUtils.getTempFileDescriptor(context, decrypt(myPojo.getName())));
                } catch (IOException e) {
                }

            }
        });


    }

    @OptIn(markerClass = UnstableApi.class) private void playVideo(String tempFileDescriptor) {
        Intent intent = new Intent(context, PlayVideoActivity.class);
        intent.putExtra("url", tempFileDescriptor);
        context.startActivity(intent);
    }

    private byte[] decrypt(String name) {
        try {
            byte[] fileData = FileUtils.readFile(FileUtils.getFilePath(context, name));
            byte[] decryptedBytes = EncryptDecryptUtils.decode(EncryptDecryptUtils.getInstance(context).getSecretKey(), fileData);
            return decryptedBytes;
        } catch (Exception e) {
        }
        return null;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewName;
        public TextView textViewTime;
        public ImageView imageViewVideo;
        public LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.textViewName);
            textViewTime = (TextView) v.findViewById(R.id.textViewTime);
            imageViewVideo = (ImageView) v.findViewById(R.id.imageViewVideo);
            linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
        }
    }


}