package library.module.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class ListBuilder {
    private final ArrayList<HashMap<String, Object>> _listData;
    private final WeakReference<Context> _context;
    private final int dialogRow;

    public ListBuilder(Context context, int dialogRow) {
        this._listData = new ArrayList();
        this._context = new WeakReference(context);
        this.dialogRow = dialogRow;
    }

    public ListBuilder(Context context) {
        this(context, R.layout.list_dialog_two_line_row);
    }

    public ListBuilder addItems(ListDialogItemModel[] listDialogItemModels) {
        for(int i = 0; i < listDialogItemModels.length; ++i) {
            // DialogItemModel dialogItemModel = dialogItemModels[i];
            // this.addItem(dialogItemModel.title, dialogItemModel.summary, dialogItemModel.image, dialogItemModel.runnable);
            this.addItem(listDialogItemModels[i]);
        }
        return this;
    }

    public ListBuilder addItem(ListDialogItemModel listDialogItemModel) {
        return this.addItem(listDialogItemModel.title, listDialogItemModel.summary,
                listDialogItemModel.image);
    }

    public ListBuilder addItem(int titleStrId, int subHeadStrId, int imageId) {
        Context mContext = (Context)this._context.get();
        if(mContext == null) {
            return this;
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("title", titleStrId != 0?mContext.getString(titleStrId):null);
            hashMap.put("summary", subHeadStrId != 0?mContext.getString(subHeadStrId):null);
            hashMap.put("image", Integer.valueOf(imageId));
            this._listData.add(hashMap);
            return this;
        }
    }

    public ListBuilder addItem(int var1, int var2, Drawable var3) {
        Context var5 = (Context)this._context.get();
        if(var5 == null) {
            return this;
        } else {
            HashMap var6 = new HashMap();
            var6.put("title", var1 != 0?var5.getString(var1):null);
            var6.put("summary", var2 != 0?var5.getString(var2):null);
            var6.put("image", var3);
            this._listData.add(var6);
            return this;
        }
    }

    public ListBuilder addItem(String var1, String var2, int var3) {
        Context var5 = (Context)this._context.get();
        if(var5 == null) {
            return this;
        } else {
            HashMap var6 = new HashMap();
            var6.put("title", var1);
            var6.put("summary", var2);
            var6.put("image", Integer.valueOf(var3));
            this._listData.add(var6);
            return this;
        }
    }

    public ListBuilder addItem(String var1, String var2, Drawable var3) {
        Context var5 = (Context)this._context.get();
        if(var5 == null) {
            return this;
        } else {
            HashMap var6 = new HashMap();
            var6.put("title", var1);
            var6.put("summary", var2);
            var6.put("image", var3);
            this._listData.add(var6);
            return this;
        }
    }

    public DialogBuilder build(boolean isCancelable,Effectstype effect,int time,boolean isShowBtn1,DialogBuilder.ThemeType themeType) {
        //DialogBuilder.setDefaultCustomDialogList(builder,"Title",true,effect,Effectstype.Fadein,700,false);
        return this.build((String)null,isCancelable,effect,time,isShowBtn1,themeType);
    }

    public DialogBuilder build(String dialogTitle,boolean isCancelable,Effectstype effect,int time
            ,boolean isShowBtn1,DialogBuilder.ThemeType themeType) {
        Context context = (Context)this._context.get();
        //Log.e("print list size",""+_listData.size());
        return (context == null?null:DialogBuilder.getInstance(context, dialogTitle, this.dialogRow, this._listData,isCancelable,effect,
                time,isShowBtn1,themeType));
    }

    /*public void bindList(Context var1, ListView var2) {
        this.bindList(var1, var2, R.layout.list_dialog_two_line_row);
    }

    public void bindList(Context context, final ListView listView, int resource) {
        ListDialogAdapter var4 = new ListDialogAdapter(context, resource, this._listData);
        listView.setAdapter(var4);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListDialogAdapter var6 = (ListDialogAdapter)listView.getAdapter();
                Runnable runnable = (Runnable)var6.getItem(position);
                if(runnable != null) {
                    runnable.run();
                }

            }
        });
    }*/
}