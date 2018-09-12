package com.example.root.moviekedua.helper;import android.annotation.SuppressLint;import android.content.Context;import android.content.Intent;import android.support.annotation.NonNull;import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.ImageView;import android.widget.TextView;import android.widget.Toast;import com.example.root.moviekedua.R;import com.example.root.moviekedua.layout.DetailActivity;import com.squareup.picasso.Picasso;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Date;import java.util.List;public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{    private Context context;    private List<MoviesModel> moviesModels;    public MoviesAdapter(Context context, List moviesModels) {        this.context = context;        this.moviesModels = moviesModels;    }    @NonNull    @Override    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        View view = LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);        return new MyViewHolder(view);    }    @Override    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder holder, int position) {        final MoviesModel model = moviesModels.get(position);        //setting tv title        holder.title.setText(model.getMov_title());        //setting tv release        String getDate = model.getMov_release();        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");        try {            Date date = simpleDateFormat.parse(getDate);            SimpleDateFormat newSimpleDate = new SimpleDateFormat("EEEE, dd MM yyyy");            String showdate = newSimpleDate.format(date);            holder.release.setText(showdate);        } catch (ParseException e){            e.printStackTrace();        }        //setting tv overview        String overview = "No Overview Data";        if (!model.getMov_overview().isEmpty()){            overview    = model.getMov_overview();        }        holder.overview.setText(overview);        //setting poster        String iconPath = model.getMov_poster();        Picasso.with(context).load("http://image.tmdb.org/t/p/w154/"+iconPath).placeholder(context.getResources().                getDrawable(R.mipmap.ic_launcher)).error(context.getResources().                getDrawable(R.mipmap.ic_launcher)).                into(holder.poster);        //button detail action        holder.detail.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent intent = new Intent(context, DetailActivity.class);                intent.putExtra(DetailActivity.EXTRA_TITLE, model.getMov_title());                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, model.getMov_overview());                intent.putExtra(DetailActivity.EXTRA_RELEASE, model.getMov_release());                intent.putExtra(DetailActivity.EXTRA_POSTER, model.getMov_backdrop());                intent.putExtra(DetailActivity.EXTRA_RATE, model.getMov_rate());                intent.putExtra(DetailActivity.EXTRA_RATE_AVG, model.getMov_rate_average());                context.startActivity(intent);            }        });        //button favorite action        holder.favorite.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Toast.makeText(context, "Favorite ditekan", Toast.LENGTH_LONG).show();            }        });    }    @Override    public int getItemCount() {        return moviesModels.size();    }    public class MyViewHolder extends RecyclerView.ViewHolder{        public TextView title, release, overview;        public ImageView poster;        public Button favorite, detail;        public MyViewHolder(View itemView) {            super(itemView);            title       = (TextView)itemView.findViewById(R.id.tvTitle);            release     = (TextView)itemView.findViewById(R.id.tvRelease);            overview    = (TextView)itemView.findViewById(R.id.tvDesc);            poster      = (ImageView)itemView.findViewById(R.id.tvposter);            favorite    = (Button)  itemView.findViewById(R.id.favorite);            detail      = (Button)  itemView.findViewById(R.id.btnDetail);        }    }}