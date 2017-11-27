package com.florian.projet.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Site;

import java.util.List;

public class ProductionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production, container, false);

        SiteManager siteManager = SiteManager.getInstance();
        List<Site> siteList = siteManager.getListSite();

        TextView textView = view.findViewById(R.id.test_text);

        if (textView != null) {
            for (Site site : siteList) {
                textView.setText(textView.getText() + "Site : " + site.getId() + " = " + site.getVolume() + ";\n");
            }
        }

        return view;
    }

}
