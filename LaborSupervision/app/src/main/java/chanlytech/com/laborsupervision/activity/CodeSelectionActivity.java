package chanlytech.com.laborsupervision.activity;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.CodeAdapter;
import chanlytech.com.laborsupervision.adapter.ZhangAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.BookEntity;
import chanlytech.com.laborsupervision.entiy.ChapterEntity;
import chanlytech.com.laborsupervision.fragment.InformationFragment;
import chanlytech.com.laborsupervision.fragment.LegalProvisionsFragment;

/**
 * 法典选择
 */
public class CodeSelectionActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.code_list)
    ListView listView;
    private CodeAdapter mCodeAdapter;
    private PopupWindow mPopupWindow;
    private TextView temText;
    private BookEntity TemBook;
    private List<BookEntity> bookEntities=new ArrayList<>();

    @Override
    public int setContentView() {
        return R.layout.activity_code_selection;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
    }

    private void initView() {
        tv_title.setText("法典选择");
        String book = getIntent().getStringExtra("book");
         bookEntities = JSON.parseArray(book, BookEntity.class);
        mCodeAdapter=new CodeAdapter(this,bookEntities);
        listView.setAdapter(mCodeAdapter);
    }

    private void initLinster(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
                BookEntity bookEntity = bookEntities.get(position);
                if(temText!=null){
                    temText.setTextColor(getResources().getColor(R.color.black));
                }
                tv_name.setTextColor(getResources().getColor(R.color.red));
                temText=tv_name;
                if (TemBook!=null){
                    TemBook.setIsCheck(false);
                }
                bookEntity.setIsCheck(true);
                TemBook=bookEntity;
                mCodeAdapter.notifyDataSetChanged();
                LegalProvisionsActivity.bookname=bookEntity.getName();
                showPop(bookEntity.getZhangjie());
            }
        });
    }

    private void showPop(final List<ChapterEntity>chapterEntities) {
        View inflate = View.inflate(this, R.layout.pop_zhang, null);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);
        FrameLayout frameLayout = (FrameLayout) inflate.findViewById(R.id.framelayout);
        ListView list = (ListView) inflate.findViewById(R.id.list_zhang);
        list.setAdapter(new ZhangAdapter(this,chapterEntities));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChapterEntity chapterEntity = chapterEntities.get(position);
                LegalProvisionsActivity.catname=chapterEntity.getName();
                LegalProvisionsActivity.catid=chapterEntity.getCatid();
                LegalProvisionsActivity.state=2;
                LegalProvisionsActivity.isEnter=false;
                mPopupWindow.dismiss();
                finish();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

    }
}
