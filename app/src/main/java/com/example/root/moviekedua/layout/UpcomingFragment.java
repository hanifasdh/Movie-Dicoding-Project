package com.example.root.moviekedua.layout;import android.app.ProgressDialog;import android.content.Context;import android.net.Uri;import android.os.Bundle;import android.support.v4.app.Fragment;import android.support.v7.widget.DividerItemDecoration;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.JsonObjectRequest;import com.android.volley.toolbox.Volley;import com.example.root.moviekedua.BuildConfig;import com.example.root.moviekedua.R;import com.example.root.moviekedua.helper.MoviesAdapter;import com.example.root.moviekedua.helper.MoviesModel;import com.google.gson.Gson;import org.json.JSONArray;import org.json.JSONException;import org.json.JSONObject;import java.util.ArrayList;import java.util.List;import java.util.Objects;public class UpcomingFragment extends Fragment {    private List<MoviesModel> moviesModels;    private RecyclerView.Adapter adapter;    RecyclerView recyclerView;    private String API_KEY = BuildConfig.API_KEY;    private String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";    @Override    public void onViewCreated(View view, Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        getActivity().setTitle(getString(R.string.title_upcoming));        recyclerView = (RecyclerView) view.findViewById(R.id.tv_upcoming);        moviesModels    = new ArrayList<MoviesModel>();        adapter         = new MoviesAdapter(getContext(), moviesModels);        adapter.notifyDataSetChanged();        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), linearLayoutManager.getOrientation());        recyclerView.setHasFixedSize(true);        recyclerView.setLayoutManager(linearLayoutManager);        recyclerView.addItemDecoration(dividerItemDecoration);        recyclerView.setAdapter(adapter);        getData();    }    private void getData(){        final ProgressDialog dialog = new ProgressDialog(getContext());        dialog.setMessage(getString(R.string.message));        dialog.show();        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,                new Response.Listener<JSONObject>() {                    @Override                    public void onResponse(JSONObject response) {                        try {                            Gson gson = new Gson();                            JSONArray aray = response.getJSONArray("results");                            for (int i=0; i<aray.length(); i++){                                JSONObject object = aray.getJSONObject(i);                                MoviesModel model = new MoviesModel();                                model.setMov_title(object.getString("title"));                                model.setMov_release(object.getString("release_date"));                                model.setMov_overview(object.getString("overview"));                                model.setMov_poster(object.getString("poster_path"));                                model.setMov_backdrop(object.getString("backdrop_path"));                                model.setMov_rate(object.getString("vote_count"));                                model.setMov_rate_average(object.getString("vote_average"));                                moviesModels.add(model);                            }                            adapter = new MoviesAdapter(getContext(), moviesModels);                            recyclerView.setAdapter(adapter);                        } catch (JSONException e){                            e.printStackTrace();                            dialog.dismiss();                        }                        adapter.notifyDataSetChanged();                        dialog.dismiss();                    }                }, new Response.ErrorListener() {            @Override            public void onErrorResponse(VolleyError error) {                Log.e("Error", error.toString());                dialog.dismiss();            }        });        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));        requestQueue.add(request);    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        // Inflate the layout for this fragment        return inflater.inflate(R.layout.fragment_upcoming, container, false);    }    public interface OnFragmentInteractionListener {        // TODO: Update argument type and name        void onFragmentInteraction(Uri uri);    }}