package com.taoxu.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.taoxu.repairviewpager.RepairPrecisionPagerAdapter;
import com.taoxu.repairviewpager.RepairPrecisionViewPager;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> photos = new ArrayList<>();
    private Activity mActivity = MainActivity.this;
    private DisplayImageOptions options;
    private RepairPrecisionViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mActivity.getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        photos.add("http://img4.imgtn.bdimg.com/it/u=3330325448,3545219178&fm=21&gp=0.jpg");
        photos.add("http://d.hiphotos.baidu.com/zhidao/pic/item/3b87e950352ac65c1b6a0042f9f2b21193138a97.jpg");
        photos.add("http://h.hiphotos.baidu.com/lvpics/h=800/sign=5c5e6d075dafa40f23c6c3dd9b65038c/03087bf40ad162d9dd7db82916dfa9ec8a13cd70.jpg");


        myViewPager = (RepairPrecisionViewPager) findViewById(R.id.viewpager);
        final MyViewPagerAdapter adapter = new MyViewPagerAdapter(mActivity);

        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
    }


    public class MyViewPagerAdapter extends RepairPrecisionPagerAdapter {

        private LinkedList<View> mViewCache = null;

        private Context mContext;

        private LayoutInflater mLayoutInflater = null;


        public MyViewPagerAdapter(Context context) {
            super();
            this.mContext = context;
            this.mLayoutInflater = LayoutInflater.from(mContext);
            this.mViewCache = new LinkedList<>();
        }

        @Override
        public int getCount() {
            return photos != null && photos.size() != 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public int getItemPosition(Object object) {
            Log.e("test", "getItemPosition ");
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("test", "instantiateItem " + position);
            ViewHolder viewHolder = null;
            View convertView = null;
            if (mViewCache.size() == 0) {
                Log.e("test", "mViewCache.size() == 0 ");
                viewHolder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.item, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(viewHolder);
            } else {
                Log.e("test", "mViewCache.size() != 0 ");
                convertView = mViewCache.removeFirst();
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText("当前position为" + position + "对应的%值为" + position % photos.size());
            ImageLoader.getInstance().displayImage(photos.get(position % photos.size()), viewHolder.imageView, options);

            container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return convertView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.e("test", "destroyItem " + position);
            View contentView = (View) object;
            container.removeView(contentView);
            this.mViewCache.add(contentView);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        public final class ViewHolder {
            public TextView textView;
            public ImageView imageView;
        }
    }
}
