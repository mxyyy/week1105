package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bwie.week1105.R;

import view.WaveView;


public class MineFragment extends Fragment {

    private WaveView wv;
    private ImageView imgCursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wv.setOnWaveChangeListener(new WaveView.OnWaveChangeListener() {
            @Override
            public void onWaveChange(float y) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgCursor.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, (int) y);
                imgCursor.setLayoutParams(layoutParams);
            }
        });
    }

    private void initView(View view) {
        wv = view.findViewById(R.id.wv);
        imgCursor = view.findViewById(R.id.img_cursor);
    }
}
