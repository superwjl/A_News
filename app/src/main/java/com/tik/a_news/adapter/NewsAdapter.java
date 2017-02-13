package com.tik.a_news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tik.a_news.R;
import com.tik.a_news.bean.NewBean;

import java.util.List;

import static android.R.string.no;
import static android.os.Build.VERSION_CODES.N;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<NewBean> mNewsList;

    private OnItemClickLitener mOnItemClickLitener;

    private View mFooterView;
    private boolean isFooterShow = false;

    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_NORMAL = 1;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public NewsAdapter(Context mContext, List<NewBean> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
        this.mFooterView = new TextView(mContext);
        ((TextView)mFooterView).setText("正在加载");
        mFooterView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
        ((TextView) mFooterView).setGravity(Gravity.CENTER);
    }

    public void setData(List<NewBean> mNewsList){
        this.mNewsList = mNewsList;
    }

    public void setFooterViewVisible(boolean isVisible){
        isFooterShow = isVisible;
    }

    public void setDataAll(boolean isAll){
        isFooterShow = true;
        if(isAll){
            ((TextView)mFooterView).setText("数据全部加载完毕");
        }else{
            ((TextView)mFooterView).setText("正在加载");
        }
    }

    public View getFooterView(){
        return mFooterView;
    }

    @Override
    public int getItemViewType(int position) {
        if(mFooterView == null){
            return TYPE_NORMAL;
        }
        if(position == mNewsList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mFooterView != null && viewType == TYPE_FOOTER){
            FooterViewHolder holder = new FooterViewHolder(mFooterView);
            return holder;
        }
        MyViewHolder holder = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHolder){
            bindData(holder, position);
        }
    }

    private void bindData(RecyclerView.ViewHolder viewHolder, final int position){
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        holder.title.setText(mNewsList.get(position).getTitle());
        holder.from.setText(mNewsList.get(position).getAuthor_name());
        holder.time.setText(mNewsList.get(position).getDate());
        String thumb1 = mNewsList.get(position).getThumbnail_pic_s();
        String thumb2 = mNewsList.get(position).getThumbnail_pic_s02();
        String thumb3 = mNewsList.get(position).getThumbnail_pic_s03();
        boolean isTwo = thumb2 != null && thumb2.length() != 0;
        boolean isThree = thumb3 != null && thumb3.length() != 0;
        if(isThree){
            holder.layout.setVisibility(View.VISIBLE);
            holder.iv.setVisibility(View.GONE);
            holder.iv1.setVisibility(View.VISIBLE);
            holder.iv2.setVisibility(View.VISIBLE);
            holder.iv3.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(thumb1).into(holder.iv1);
            Glide.with(mContext).load(thumb2).into(holder.iv2);
            Glide.with(mContext).load(thumb3).into(holder.iv3);
        }else if(isTwo){
            holder.layout.setVisibility(View.VISIBLE);
            holder.iv.setVisibility(View.GONE);
            holder.iv1.setVisibility(View.VISIBLE);
            holder.iv2.setVisibility(View.VISIBLE);
            holder.iv3.setVisibility(View.INVISIBLE);
            Glide.with(mContext).load(thumb1).into(holder.iv1);
            Glide.with(mContext).load(thumb2).into(holder.iv2);
        }else{
            holder.layout.setVisibility(View.GONE);
            holder.iv.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(thumb1).into(holder.iv);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickLitener != null){
                    mOnItemClickLitener.onItemClick(holder.container, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return isFooterShow ? mNewsList.size() + 1 : mNewsList.size();
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        private TextView mFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mFooter = (TextView) itemView;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout container;
        private ImageView iv;
        private TextView title;
        private TextView from;
        private TextView time;
        private LinearLayout layout;
        private ImageView iv1;
        private ImageView iv2;
        private ImageView iv3;

        public MyViewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout) itemView.findViewById(R.id.new_container);
            iv = (ImageView) itemView.findViewById(R.id.news_avatar);
            title = (TextView) itemView.findViewById(R.id.news_title);
            from = (TextView) itemView.findViewById(R.id.news_from);
            time = (TextView) itemView.findViewById(R.id.news_time);
            layout = (LinearLayout) itemView.findViewById(R.id.news_thumb_layout);
            iv1 = (ImageView) itemView.findViewById(R.id.news_thumb_01);
            iv2 = (ImageView) itemView.findViewById(R.id.news_thumb_02);
            iv3 = (ImageView) itemView.findViewById(R.id.news_thumb_03);
        }
    }

}
