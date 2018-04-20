package com.wanli.page;

import com.wanli.classforevery.R;

import java.util.ArrayList;  
import java.util.List;  
import android.os.Bundle;  
import android.app.Activity;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.Window;  
import android.widget.ImageButton;  
import android.widget.LinearLayout;  
  
public class MainActivity extends Activity implements  
        android.view.View.OnClickListener {  
  
    private ViewPager mViewPager;// �������ý����л�  
    private PagerAdapter mPagerAdapter;// ��ʼ��View������  
    private List<View> mViews = new ArrayList<View>();// �������Tab01-04  
    // �ĸ�Tab��ÿ��Tab����һ����ť  
    private LinearLayout mTabWeiXin;  
    private LinearLayout mTabAddress;  
    private LinearLayout mTabFrd;  
    private LinearLayout mTabSetting;  
    // �ĸ���ť  
    private ImageButton mWeiXinImg;  
    private ImageButton mAddressImg;  
    private ImageButton mFrdImg;  
    private ImageButton mSettingImg;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_main);  
        initView();  
        initViewPage();  
        initEvent();  
    }  
  
    private void initEvent() {  
        mTabWeiXin.setOnClickListener(this);  
        mTabAddress.setOnClickListener(this);  
        mTabFrd.setOnClickListener(this);  
        mTabSetting.setOnClickListener(this);  
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {  
            /** 
            *ViewPage���һ���ʱ 
            */  
            @Override  
            public void onPageSelected(int arg0) {  
                int currentItem = mViewPager.getCurrentItem();  
                switch (currentItem) {  
                case 0:  
                     resetImg();  
                    mWeiXinImg.setImageResource(R.drawable.classroom2);  
                    break;  
                case 1:  
                     resetImg();  
                    mAddressImg.setImageResource(R.drawable.answer2);  
                    break;  
                case 2:  
                     resetImg();  
                    mFrdImg.setImageResource(R.drawable.question2);  
                    break;  
                case 3:  
                     resetImg();  
                    mSettingImg.setImageResource(R.drawable.personal2);  
                    break;  
                default:  
                    break;  
                }  
            }  
  
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
  
            }  
  
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
  
            }  
        });  
    }  
  
    /** 
     * ��ʼ������ 
     */  
    private void initView() {  
        mViewPager = (ViewPager) findViewById(R.id.id_viewpage);  
        // ��ʼ���ĸ�LinearLayout  
        mTabWeiXin = (LinearLayout) findViewById(R.id.id_classroom);  
        mTabAddress = (LinearLayout) findViewById(R.id.id_answer);  
        mTabFrd = (LinearLayout) findViewById(R.id.id_question);  
        mTabSetting = (LinearLayout) findViewById(R.id.id_personal);  
        // ��ʼ���ĸ���ť  
        mWeiXinImg = (ImageButton) findViewById(R.id.btn_classroom);  
        mAddressImg = (ImageButton) findViewById(R.id.btn_answer);  
        mFrdImg = (ImageButton) findViewById(R.id.btn_question);  
        mSettingImg = (ImageButton) findViewById(R.id.btn_personal);  
    }  
  
    /** 
     * ��ʼ��ViewPage 
     */  
    private void initViewPage() {  
  
        // ���軯�ĸ�����  
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);  
        View tab01 = mLayoutInflater.inflate(R.layout.classroom, null);  
        View tab02 = mLayoutInflater.inflate(R.layout.answer, null);  
        View tab03 = mLayoutInflater.inflate(R.layout.question, null);  
        View tab04 = mLayoutInflater.inflate(R.layout.personal, null);  
  
        mViews.add(tab01);  
        mViews.add(tab02);  
        mViews.add(tab03);  
        mViews.add(tab04);  
  
        // ��������ʼ��������  
        mPagerAdapter = new PagerAdapter() {  
  
            @Override  
            public void destroyItem(ViewGroup container, int position,  
                    Object object) {  
                container.removeView(mViews.get(position));  
  
            }  
  
            @Override  
            public Object instantiateItem(ViewGroup container, int position) {  
                View view = mViews.get(position);  
                container.addView(view);  
                return view;  
            }  
  
            @Override  
            public boolean isViewFromObject(View arg0, Object arg1) {  
  
                return arg0 == arg1;  
            }  
  
            @Override  
            public int getCount() {  
  
                return mViews.size();  
            }  
        };  
        mViewPager.setAdapter(mPagerAdapter);  
    }  
  
    /** 
     * �ж��ĸ�Ҫ��ʾ�������ð�ťͼƬ 
     */  
    @Override  
    public void onClick(View arg0) {  
  
        switch (arg0.getId()) {  
        case R.id.id_classroom:  
            mViewPager.setCurrentItem(0);  
            resetImg();  
            mWeiXinImg.setImageResource(R.drawable.classroom2);  
            break;  
        case R.id.id_answer:  
            mViewPager.setCurrentItem(1);  
            resetImg();  
            mAddressImg.setImageResource(R.drawable.answer2);  
            break;  
        case R.id.id_question:  
            mViewPager.setCurrentItem(2);  
            resetImg();  
            mFrdImg.setImageResource(R.drawable.question2);  
            break;  
        case R.id.id_personal:  
            mViewPager.setCurrentItem(3);  
            resetImg();  
            mSettingImg.setImageResource(R.drawable.personal2);  
            break;  
        default:  
            break;  
        }  
    }  
  
    /** 
     * ������ͼƬ�䰵 
     */  
    private void resetImg() {  
        mWeiXinImg.setImageResource(R.drawable.classroom);  
        mAddressImg.setImageResource(R.drawable.answer);  
        mFrdImg.setImageResource(R.drawable.question);  
        mSettingImg.setImageResource(R.drawable.personal);  
    }  
  
}
