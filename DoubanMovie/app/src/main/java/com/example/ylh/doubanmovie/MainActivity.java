package com.example.ylh.doubanmovie;

import android.content.Context;
import android.content.Entity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ylh.doubanmovie.entity.MovieListItemEntity;
import com.example.ylh.doubanmovie.http.HttpMethods;
import com.example.ylh.doubanmovie.tools.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_search)
    Button tvSearch;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.lv_seach_list)
    ListView lvSeachList;
    @BindView(R.id.tv_result_title)
    TextView tvResultTitle;

    Context context;

    Subscriber subscriber;
    ItemAdapter adapter;
    List<MovieListItemEntity.SubjectsBean> subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        subjectsList = new ArrayList<>();
        adapter = new ItemAdapter(subjectsList);
        lvSeachList.setAdapter(adapter);
    }

    @OnClick({
            R.id.tv_search,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                getMovieList();
                break;
        }
    }

    private void getMovieList() {
        subscriber = new Subscriber<MovieListItemEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieListItemEntity movieListItemEntitiy) {
                MovieListItemEntity entity = movieListItemEntitiy;
                tvResultTitle.setText(entity.getTitle());
                subjectsList = entity.getSubjects();
                adapter.refresh(subjectsList);
            }
        };
        HttpMethods.getInstance().getMovieList(subscriber, etInput.getText().toString());
    }

    /**
     * 适配器
     */
    public class ItemAdapter extends BaseAdapter {
        List<MovieListItemEntity.SubjectsBean> list;

        public ItemAdapter(List<MovieListItemEntity.SubjectsBean> list) {
            this.list = list;
        }

        public void refresh(List<MovieListItemEntity.SubjectsBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MovieListItemEntity.SubjectsBean data = list.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.listview_moview_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//            viewHolder.ivMovieImage.setImageDrawable();
            viewHolder.tvMovieName.setText(data.getTitle());
            StringBuffer names = new StringBuffer();
            for (MovieListItemEntity.SubjectsBean.CastsBean castsBean:data.getCasts()) {
                names.append(",").append(castsBean.getName());
            }
            String name = names.toString();
            viewHolder.tvMovieActors.setText(TextUtils.isEmpty(name) ? "":name.substring(1));
            viewHolder.tvMovieTime.setText(data.getYear() + "年");
            StringBuffer cateGory = new StringBuffer();
            for (String cg:data.getGenres()) {
                cateGory.append(cg).append(" ");
            }
            viewHolder.tvMovieCategory.setText(cateGory.toString());
            ImageUtils.getInstance().displayImage(context, data.getImages().getMedium(), viewHolder.ivMovieImage);
            return convertView;
        }


    }
    static class ViewHolder {
        @BindView(R.id.iv_movie_image)
        ImageView ivMovieImage;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindView(R.id.tv_movie_category)
        TextView tvMovieCategory;
        @BindView(R.id.tv_movie_time)
        TextView tvMovieTime;
        @BindView(R.id.tv_movie_actors)
        TextView tvMovieActors;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
