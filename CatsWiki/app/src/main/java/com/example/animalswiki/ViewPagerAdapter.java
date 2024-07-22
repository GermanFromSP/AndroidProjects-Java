package com.example.animalswiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.animalswiki.entities.CatImage;
import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    List<CatImage> imagesUrl;
    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context,  List<CatImage> images) {
        this.context = context;
        this.imagesUrl = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.breed_image, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        Glide.with(context)
                .load(imagesUrl.get(position).getUrl())
                .centerCrop()
                .into(imageView);

        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}
