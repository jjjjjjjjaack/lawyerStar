package framework.mvp1.base.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.qbo.lawyerstar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzj on 2017/8/24.
 */

public class MCommAdapter<B extends Object> extends MBaseAdapter<MCommVH> {


    private Context mContext;
    private List<B> beanList = new ArrayList<B>();
    private LayoutInflater mLayoutInflater;

    //    private MCommAdapterInterface mCommAdapterInterface;
    private MCommVH.MCommVHInterface<B> mCommVHInterface;

    private boolean showEmptyView;
    private boolean isEmpty;
    private View emptyVIew;
    public Map<String, Object> variableMap = new HashMap<>();

    public interface MCommAdapterInterface {
        void isNoData(boolean flag);
    }

    public MCommAdapter(Context context, MCommVH.MCommVHInterface<B> mCommVHInterface) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
//        this.mCommAdapterInterface = mCommAdapterInterface;
        this.mCommVHInterface = mCommVHInterface;
    }

    public List<B> getBeanList() {
        return beanList;
    }

    public void setData(List<B> list) {
        if (list == null) {
            return;
        }
        this.beanList.clear();
        this.beanList.addAll(list);
        isEmpty = false;
        if (showEmptyView) {
            if (this.beanList.size() == 0) {
                isEmptyData();
            } else {
                notEmptyData();
            }
        }
        notifyDataSetChanged();
//        mCommAdapterInterface.isNoData(getItemCount() <= 0);
    }

    public void addData(List<B> list) {
        if (list == null) {
            return;
        }
        this.beanList.addAll(list);
        isEmpty = false;
        if (showEmptyView) {
            if (this.beanList.size() == 0) {
                isEmptyData();
            } else {
                notEmptyData();
            }
        }
        notifyDataSetChanged();
//        mCommAdapterInterface.isNoData(getItemCount() <= 0);
    }


    public void addOneData(B b, int index) {
        this.beanList.add(index, b);
        isEmpty = false;
        if (showEmptyView) {
            if (this.beanList.size() == 0) {
                isEmptyData();
            } else {
                notEmptyData();
            }
        }
        notifyDataSetChanged();
//        mCommAdapterInterface.isNoData(getItemCount() <= 0);
    }

    public void addOneData(B b) {
        this.beanList.add(b);
        isEmpty = false;
        if (showEmptyView) {
            if (this.beanList.size() == 0) {
                isEmptyData();
            } else {
                notEmptyData();
            }
        }
        notifyDataSetChanged();
//        mCommAdapterInterface.i
    }


    @Override
    public int getItemCount() {
        return isEmpty ? 1 : beanList.size();
    }

    /**
     * @param
     * @return
     * @description 返回当前数组大小
     * @author jiejack
     * @time 2022/2/4 9:36 下午
     */
    public int getSize() {
        return beanList.size();
    }

    @Override
    public MCommVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
//                synchronized (isSetLm) {
//                    if (!isSetLm) {
            return new MCommVH(mLayoutInflater.inflate(R.layout.view_listdata_empty,
                    parent, false), mContext, mCommVHInterface, this);
        }
//        try {
//            if (showEmptyView) {
//                RecyclerView.LayoutManager lm = ((RecyclerView) parent).getLayoutManager();
//                if (setlayoutManager != null && setlayoutManager != lm) {
//                    ((RecyclerView) parent).setLayoutManager(setlayoutManager);
//                    setlayoutManager = null;
//                }
//            }
//        } catch (Exception e) {
//        }
        return new MCommVH(mLayoutInflater.inflate(mCommVHInterface.setLayout(), parent, false), mContext, mCommVHInterface, this);
    }

//    public void changeEmptyLm(){
//        if (showEmptyView) {
//            try {
//                RecyclerView.LayoutManager lm = ((RecyclerView) parent).getLayoutManager();
//                if (!(lm instanceof LinearLayoutManager)) {
//                    setlayoutManager = lm;
//                    ((RecyclerView) parent).setLayoutManager(new LinearLayoutManager(mContext));
//                }
//            } catch (Exception e) {
//            }
//        }
//    }

    @Override
    public void onBindViewHolder(@NonNull MCommVH holder, int position) {
        if (!isEmpty) {
            holder.bindData(position, beanList.get(position));
        }
    }


    @Override
    public int getItemViewType(int position) {
        return isEmpty ? -1 : super.getItemViewType(position);
    }

    RecyclerView.LayoutManager setlayoutManager;
    RecyclerView recyclerView;
    Boolean isSettingLm = new Boolean(false);

    /**
     * @param
     * @return
     * @description 设置是否显示空视图
     * @author jiejack
     * @time 2022/2/3 12:31 上午
     */
    public void setShowEmptyView(boolean showEmptyView, RecyclerView recyclerView) {
        this.showEmptyView = showEmptyView;
        this.recyclerView = recyclerView;
    }

    public void setShowEmptyView(boolean showEmptyView) {
        this.showEmptyView = showEmptyView;
        this.recyclerView = null;
    }

    public void notEmptyData() {
        isEmpty = false;
        try {
            if (showEmptyView) {
                if (recyclerView == null) {
                    return;
                }
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                if (setlayoutManager != null && setlayoutManager != lm) {
                    synchronized (isSettingLm) {
                        isSettingLm = true;
                        recyclerView.setLayoutManager(setlayoutManager);
                        setlayoutManager = null;
                        isSettingLm = false;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void isEmptyData() {
        isEmpty = true;
        if (showEmptyView) {
            try {
                if (recyclerView == null) {
                    return;
                }
                synchronized (isSettingLm) {
                    RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                    if ((lm instanceof GridLayoutManager)) {
                        setlayoutManager = lm;
                        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * @param
     * @return
     * @description 交换位置
     * @author jieja
     * @time 2022/4/24 10:09
     */
    public void move(int origin, int target) {
        if (origin < target) {
            for (int i = origin; i < target; i++) {
                Collections.swap(getBeanList(), i, i + 1);
            }
        }
        if (origin > target) {
            for (int i = origin; i > target; i--) {
                Collections.swap(getBeanList(), i, i - 1);
            }
        }
        notifyItemMoved(origin, target);
    }

    /**
     * @param
     * @return
     * @description
     * @author jieja
     * @time 2022/4/12 9:09
     */
    public void setVarValue(String key, Object value) {
        variableMap.put(key, value);
    }

    public Object getVarValue(String key) {
        return variableMap.get(key);
    }
}

