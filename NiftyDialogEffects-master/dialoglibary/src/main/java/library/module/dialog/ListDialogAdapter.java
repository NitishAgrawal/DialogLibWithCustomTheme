package library.module.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListDialogAdapter extends SimpleAdapter {

    public static final String TITLE_COLOR = "titleColor";
    public static final String SUMMARY_COLOR = "summaryColor";
    public static final String IMAGE_BG_COLOR = "imageBgColor";
    public static final String ROW_BG_COLOR = "rowBgColor";

    private int titleColor = Color.GRAY;
    private int summaryColor = Color.GRAY;
    private int imageBgColor = Color.BLUE;
    private int rowBgColor = Color.TRANSPARENT;

    ArrayList<HashMap<String, Object>> list;
    HashMap<String, Integer> colorMap;
    Context context;

    public ListDialogAdapter(Context var1, int var2, ArrayList<HashMap<String, Object>> var3,HashMap<String, Integer> colorMap) {
        super(var1, var3, var2, new String[]{"title", "summary", "image"}, new int[]{R.id.Title, R.id.Summary, R.id.Image});
        this.list = var3;
        this.colorMap= colorMap ;
        this.context = var1;
        if(colorMap != null){
            titleColor = colorMap.get(TITLE_COLOR);
            summaryColor = colorMap.get(SUMMARY_COLOR);
            imageBgColor = colorMap.get(IMAGE_BG_COLOR);
            rowBgColor = colorMap.get(ROW_BG_COLOR);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final HashMap<String, Object> item = list.get(position);

        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dialog_two_line_row, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.Image= (ImageView) convertView.findViewById(R.id.Image);
            viewHolder.Title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.Summary= (TextView) convertView.findViewById(R.id.Summary);
            viewHolder.lstImgLayout= (LinearLayout) convertView.findViewById(R.id.lstImgLayout);
            viewHolder.lstRowBackground = (LinearLayout) convertView.findViewById(R.id.lstRowBackground);
            convertView.setTag(viewHolder);
            //setImage(viewHolder.userImage, item, true);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
            //setImage(viewHolder.userImage, item);
        }

        String titleStr = item.get("title").toString();
        String summaryStr = item.get("summary").toString();
        int imageRes = (Integer) item.get("image");

        if(toggleView(viewHolder.Title,titleStr)) {
            viewHolder.Title.setText(titleStr);
        }

        if(toggleView(viewHolder.Summary,summaryStr)) {
            viewHolder.Summary.setText(summaryStr);
        }

        if(imageRes != 0) {
            viewHolder.Image.setVisibility(View.VISIBLE);
            viewHolder.lstImgLayout.setVisibility(View.VISIBLE);
            viewHolder.Image.setImageResource(imageRes);
        }else {
            viewHolder.Image.setVisibility(View.GONE);
            viewHolder.lstImgLayout.setVisibility(View.GONE);
        }

        viewHolder.Title.setTextColor(titleColor);
        viewHolder.Summary.setTextColor(summaryColor);
        viewHolder.lstImgLayout.setBackgroundColor(imageBgColor);
        viewHolder.lstRowBackground.setBackgroundColor(rowBgColor);

        return convertView;
    }

    class ViewHolderItem{
        public ImageView Image;
        public TextView Title,Summary;
        public LinearLayout lstImgLayout;
        public LinearLayout lstRowBackground;
    }

    private boolean toggleView(View view,Object obj){
        if (obj==null){
            view.setVisibility(View.GONE);
            return false;
        }else {
            view.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public Object getItem(int position) {
        //return this.runnableList != null && position <= this.runnableList.size() - 1?this.runnableList.get(position):null;
        return list.get(position);
    }

    public void setViewImage(ImageView var1, int var2) {
        if(var2 == 0) {
            var1.setVisibility(View.GONE);
        } else {
            var1.setVisibility(View.VISIBLE);
            super.setViewImage(var1, var2);
        }
    }



    public void setViewText(TextView var1, String var2) {
        if(var2 == null || var2.length() == 0) {
            var1.setVisibility(View.GONE);
        } else {
            var1.setVisibility(View.VISIBLE);
            super.setViewText(var1, var2);
        }
    }
}


/*

public class ListDialogAdapter extends ArrayAdapter {

    public static final String TITLE_COLOR = "titleColor";
    public static final String SUMMARY_COLOR = "summaryColor";
    public static final String IMAGE_BG_COLOR = "imageBgColor";
    public static final String ROW_BG_COLOR = "rowBgColor";

    private int titleColor = Color.GRAY;
    private int summaryColor = Color.GRAY;
    private int imageBgColor = Color.TRANSPARENT;
    private int rowBgColor = Color.TRANSPARENT;

    Context context;
    ArrayList<HashMap<String, Object>> list;

    public ListDialogAdapter(Context context, int resource, ArrayList<HashMap<String, Object>> list,HashMap<String, Integer> colorMap) {
        super(context, resource);
        this.list = list;
        if(colorMap != null){
            titleColor = colorMap.get(TITLE_COLOR);
            summaryColor = colorMap.get(SUMMARY_COLOR);
            imageBgColor = colorMap.get(IMAGE_BG_COLOR);
            rowBgColor = colorMap.get(ROW_BG_COLOR);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final HashMap<String, Object> item = list.get(position);

        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dialog_two_line_row, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.Image= (ImageView) convertView.findViewById(R.id.Image);
            viewHolder.Title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.Summary= (TextView) convertView.findViewById(R.id.Summary);
            viewHolder.lstImgLayout= (LinearLayout) convertView.findViewById(R.id.lstImgLayout);
            viewHolder.lstRowBackground = (LinearLayout) convertView.findViewById(R.id.lstRowBackground);
            convertView.setTag(viewHolder);
            //setImage(viewHolder.userImage, item, true);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
            //setImage(viewHolder.userImage, item);
        }

        String titleStr = item.get("title").toString();
        String summaryStr = item.get("summary").toString();
        int imageRes = (Integer) item.get("image");

        if(toggleView(viewHolder.Title,titleStr)) {
            viewHolder.Title.setText(titleStr);
        }

        if(toggleView(viewHolder.Summary,summaryStr)) {
            viewHolder.Summary.setText(summaryStr);
        }

        if(imageRes != 0) {
            viewHolder.Image.setImageResource(imageRes);
        }else {
            viewHolder.Image.setVisibility(View.GONE);
        }

        viewHolder.Title.setTextColor(titleColor);
        viewHolder.Summary.setTextColor(summaryColor);
        viewHolder.lstImgLayout.setBackgroundColor(imageBgColor);
        viewHolder.lstRowBackground.setBackgroundColor(rowBgColor);

        return convertView;
    }

    class ViewHolderItem{
        public ImageView Image;
        public TextView Title,Summary;
        public LinearLayout lstImgLayout;
        public LinearLayout lstRowBackground;
    }

    private boolean toggleView(View view,Object obj){
        if (obj==null){
            view.setVisibility(View.GONE);
            return false;
        }else {
            view.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public Object getItem(int position) {
        //return this.runnableList != null && position <= this.runnableList.size() - 1?this.runnableList.get(position):null;
        return list.get(position);
    }

    /*public void setViewImage(ImageView var1, int var2) {
        if(var2 == 0) {
            var1.setVisibility(View.GONE);
        } else {
            var1.setVisibility(View.VISIBLE);
            super.setViewImage(var1, var2);
        }
    }

    public void setViewText(TextView var1, String var2) {
        if(var2 == null || var2.length() == 0) {
            var1.setVisibility(View.GONE);
        } else {
            var1.setVisibility(View.VISIBLE);
            super.setViewText(var1, var2);
        }
    }*/


