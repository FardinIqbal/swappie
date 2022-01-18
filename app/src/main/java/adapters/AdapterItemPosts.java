package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swappapp.R;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import models.ModelItemPost;

public class AdapterItemPosts extends RecyclerView.Adapter<AdapterItemPosts.MyHolder> {

    Context context;
    List<ModelItemPost> postList;

    public AdapterItemPosts(Context context, List<ModelItemPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Inflate layout row_post.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        // Get data
        String uid = postList.get(i).getUid();
        String uEmail = postList.get(i).getuEmail();
        String uName = postList.get(i).getuName();
        String uDp = postList.get(i).getuDp();
        String pId = postList.get(i).getpId();
        String pTitle = postList.get(i).getpTitle();
        String pDescription = postList.get(i).getpDescription();
        String pImage = postList.get(i).getpImage();

        // Set data
        myHolder.uNameTv.setText(uName);
        myHolder.pTitleTv.setText(pTitle);
        myHolder.pDescriptionTv.setText(pDescription);
        myHolder.uNameTv.setText(uName);


        // Set user dp
        try {
            Picasso.get().load(uDp).placeholder(R.drawable.ic_post1).into(MyHolder.uPictureIv);
        }
        catch (Exception e) {

        }

        // Set post image
        if (pImage.equals("noImage")) {
            myHolder.pImageIv.setVisibility(View.GONE);
        }
        else  {
            try {
                Picasso.get().load(pImage).into(MyHolder.pImageIv);
            }
            catch (Exception e) {

            }
        }


        // handle button clicks
        myHolder.chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implament later
                Toast.makeText(context, "Chat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        // Views from row_post.xml
        public static ImageView uPictureIv, pImageIv;
        TextView uNameTv, pTitleTv, pDescriptionTv;
        ImageButton chatBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize Views
            uPictureIv = itemView.findViewById(R.id.uPictureIv);
            pImageIv = itemView.findViewById(R.id.pImageIv1);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            chatBtn = itemView.findViewById(R.id.chatBtn);

        }
    }
}