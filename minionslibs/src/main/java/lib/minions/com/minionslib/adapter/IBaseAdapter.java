package lib.minions.com.minionslib.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * compile 'com.zhy:autolayout:1.4.5'
 * Created by chenguozhen on 2017/3/30.
 * 　eMail  1021632321@QQ.com
 *  ListView 的适配器
 */

public abstract class IBaseAdapter<T> extends BaseAdapter {



    public Context getContext() {
        return context;
    }

    private Context context;

    private List<T> mLists;

    public List<T> getmLists() {
        return mLists;
    }

    public void setmLists(List<T> mLists) {
        this.mLists = mLists;
    }

    public IBaseAdapter(Context context, List<T> mLists) {
        init(context,mLists);
    }

    public IBaseAdapter(Context context) {
        init(context,new ArrayList<T>());
    }

    /**
     * 更新
     */
    public void upDate(List<T> mLists){
        this.mLists = mLists;
        notifyDataSetChanged();
    }

    /**
     * 清理
     */
    public void clean(){
        if (null != mLists) {
            this.mLists.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 添加
     */
    public void addAll(List<T> list) {
        if (null != list) {
            this.mLists.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除
     */
    public void remove(int position){
        if (null != mLists) {
            this.mLists.remove(position);
            notifyDataSetChanged();
        }
    }

    private void init(Context context,List<T> mLists){
        this.context = context;
        this.mLists = mLists;
    }

    /**
     * 布局id
     */
    public abstract int getLayoutId(int position);

    public abstract void getView(int position, View convertView);

    @Override
    public int getCount() {
        return mLists == null?0:mLists.size();
    }

    @Override
    public T getItem(int position) {
        return mLists == null?null:mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            int layoutId = getLayoutId(position);
            if (layoutId == 0){
                throw new NullPointerException("layout id can't be 0");
            }
            convertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            AutoUtils.autoSize(convertView);
        }
        getView(position,convertView);
        return convertView;
    }

    /**
     * 从ViewHolder中获取View
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findChildView(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
