package com.example.aluno2017.login;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {

            R.drawable.eat_icon,
            R.drawable.code_icon,
            R.drawable.sleep_icon
    };

    public String[] slide_headings = {

            "1º Passo",
            "2º Passo",
            "3º Passo"
    };

    public String[] slide_descriptions = {

            "Descrição",
            "Descrição",
            "Descrição"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.SlideImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.Slide_Heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.Slide_Description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        container.addView(view);

        return view;
    }

    //Assim que chega à ultima página para, em vez de continuar a criar slides
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);

    }
}
