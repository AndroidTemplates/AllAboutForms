package forms.formtemplates.FireBase;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import forms.formtemplates.Profile.Profile;
import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FireBaseDetailRecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FireBaseDetailRecyclerViewFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

View fireBaseRecyclerDetailView = null;

    Button logout;
    RecyclerView detailRecyclerView;
    private DatabaseReference mReference;
    Firebase ref;
    public FireBaseDetailRecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FireBaseDetailRecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FireBaseDetailRecyclerViewFragment newInstance(String param1, String param2) {
        FireBaseDetailRecyclerViewFragment fragment = new FireBaseDetailRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initDataBaseReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fireBaseRecyclerDetailView =  inflater.inflate(R.layout.fragment_fire_base_detail_recycler_view, container, false);
        initViews(fireBaseRecyclerDetailView);
        registerListeners();
        setAdapter();
        return  fireBaseRecyclerDetailView;
    }
         private  void setAdapter(){
           FirebaseRecyclerAdapter<Profile,FireBaseViewHolder> firebaseRecyclerAdapter =
            new FirebaseRecyclerAdapter<Profile, FireBaseViewHolder>(
                    Profile.class,
                    R.layout.firebase_list_item,
                    FireBaseViewHolder.class,
                    mReference
            ) {
                @Override
                protected void populateViewHolder(FireBaseViewHolder fireBaseViewHolder, Profile profile, int i) {
                    fireBaseViewHolder.setProfileName(profile.getFname());
                    fireBaseViewHolder.setProfilePic(profile.getDownloadURI(),getActivity());

                }
            };

    detailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    RecyclerView.ItemDecoration itemDecoration =
            new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
    detailRecyclerView.addItemDecoration(itemDecoration);
    detailRecyclerView.setItemAnimator(new DefaultItemAnimator());
    detailRecyclerView.setAdapter(firebaseRecyclerAdapter);

}

    public static class FireBaseViewHolder extends  RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView profileName;
        Context ctx;
        public  FireBaseViewHolder(View itemView){
            super(itemView);
            profilePic = (ImageView) itemView.findViewById(R.id.image_firebase);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
        }

        public void setProfileName(String name){
            profileName.setText(name);
        }

        public  void setProfilePic(String profileURL,Context ctx){
            this.ctx = ctx;
            Glide.with(ctx).load(profileURL)
                    //.override(200, 200)
                    //.fitCenter()
                    .centerCrop()
                    .into(profilePic);
        }


    }
    private void initViews(View view){
        logout = (Button) view.findViewById(R.id.logout);
        detailRecyclerView = (RecyclerView) view.findViewById(R.id.firebase_recycler);
    }

    private  void registerListeners(){
        logout.setOnClickListener(this);
    }

    private void  initDataBaseReference(){
        mReference = FirebaseDatabase.getInstance().getReference().child("profile");
        ref = new Firebase("https://fir-authen-39277.firebaseio.com/profile");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                break;
        }
    }


}
