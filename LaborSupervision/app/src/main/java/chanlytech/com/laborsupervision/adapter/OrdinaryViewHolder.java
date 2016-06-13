package chanlytech.com.laborsupervision.adapter;

import android.view.View;

import butterknife.ButterKnife;


/**
 * Created by Lyy on 2015/3/12.
 *
 */
public abstract class OrdinaryViewHolder {

    public OrdinaryViewHolder(View view){
        ButterKnife.inject(this, view);
    }

}