package com.chlorophilia.ui.fragmentWhatsnew;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chlorophilia.R;

public class WhatsNewFragment extends Fragment {

    private WhatsNewViewModel whatsNewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        whatsNewViewModel =
                new ViewModelProvider(this).get(WhatsNewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_whatsnew, container, false);

        TextView email = root.findViewById(R.id.email);
        TextView tipee = root.findViewById(R.id.tipee);

        String tipeeAddress = "https://fr.tipeee.com/chlorophilia/";
        String linkedEmailText = String.format("<a href=\"%s\">Tipee</a> ", tipeeAddress);
        tipee.setText(HtmlCompat.fromHtml(linkedEmailText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        tipee.setMovementMethod(LinkMovementMethod.getInstance());

        String emailAddress = "mailto:chlorophilia.app@gmail.com";
        String linkedTipeeText = String.format("<a href=\"%s\">chlorophilia.app@gmail.com</a> ", emailAddress);
        email.setText(HtmlCompat.fromHtml(linkedTipeeText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        email.setMovementMethod(LinkMovementMethod.getInstance());

        return root;
    }
}