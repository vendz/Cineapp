package cf.vandit.movie_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.FavouritesPagerAdapter;

public class FavouritesFragment extends Fragment {

    private SmartTabLayout mSmartTabLayout;
    private ViewPager mViewPager;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        mSmartTabLayout = (SmartTabLayout) view.findViewById(R.id.tab_view_pager_fav);
        mViewPager = view.findViewById(R.id.view_pager_fav);
        mViewPager.setAdapter(new FavouritesPagerAdapter(getChildFragmentManager(), getContext()));
        mSmartTabLayout.setViewPager(mViewPager);

        return view;
    }
}