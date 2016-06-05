package com.cdt.bombeachguide.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdt.bombeachguide.HTTP.Item;
import com.cdt.bombeachguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguyen on 5/22/2016.
 */
public class ListItemAdapter extends ArrayAdapter<Item>
{
    Activity context=null;
    ArrayList<Item> myArray=null;
    int layoutId;
    /**
     * Constructor này dùng để khởi tạo các giá trị
     * từ MainActivity truyền vào
     * @param context : là Activity từ Main
     * @param layoutId: Là layout custom do ta tạo (my_item_layout.xml)
     * @param arr : Danh sách nhân viên truyền từ Main
     */
    public ListItemAdapter(Activity context,
                          int layoutId,
                          ArrayList<Item>arr){
        super(context, layoutId, arr);
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
    }
    /**
     * hàm dùng để custom layout, ta phải override lại hàm này
     * từ MainActivity truyền vào
     * @param position : là vị trí của phần tử trong danh sách nhân viên
     * @param convertView: convertView, dùng nó để xử lý Item
     * @param parent : Danh sách nhân viên truyền từ Main
     * @return View: trả về chính convertView
     */
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        /**
         * bạn chú ý là ở đây Tôi không làm:
         * if(convertView==null)
         * {
         * LayoutInflater inflater=
         * context.getLayoutInflater();
         * convertView=inflater.inflate(layoutId, null);
         * }
         * Lý do là ta phải xử lý xóa phần tử Checked, nếu dùng If thì
         * nó lại checked cho các phần tử khác sau khi xóa vì convertView
         * lưu lại trạng thái trước đó
         */
        LayoutInflater inflater=
                context.getLayoutInflater();
        convertView=inflater.inflate(layoutId, null);
        //chỉ là test thôi, bạn có thể bỏ If đi
        if(myArray.size()>0 && position>=0)
        {
            //dòng lệnh lấy TextView ra để hiển thị Mã và tên lên
            final Item item=myArray.get(position);
            final ImageView imgitem=(ImageView)
                    convertView.findViewById(R.id.item_imageview);
            imgitem.setImageResource(R.drawable.header);
            Picasso.with(context)
                    .load(item.imageurl)
                    .into(imgitem);
            //nếu là Nữ thì lấy hình con gái
            final TextView tvitem=(TextView)convertView.findViewById(R.id.item_textview);
            tvitem.setTextColor(Color.RED);
            tvitem.setText(item.title);
        }
        //Vì View là Object là dạng tham chiếu đối tượng, nên
        //mọi sự thay đổi của các object bên trong convertView
        //thì nó cũng biết sự thay đổi đó
        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }
}