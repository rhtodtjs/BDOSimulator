package com.kkk8888.bdosimulator;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alfo06-18 on 2017-08-23.
 */

public class BoardAdapter extends RecyclerView.Adapter {

    ArrayList<BoardItem> board;
    Context mContext;

    public BoardAdapter(ArrayList<BoardItem> board, Context mContext) {
        this.board = board;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_enchant_board, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder h = ((MyHolder) holder);
        h.writer.setText(board.get(position).getWriter());
        if (board.get(position).getWriter().equals("공지사항")) {
            h.text.setTextColor(Color.RED);
            h.text.setTextSize(15.1f);
            h.writer.setTextColor(Color.RED);
        } else{
            h.text.setTextColor(0xE5D85C);
        }
        h.text.setText(board.get(position).getText());
        Picasso.with(mContext).load("http://ec2-52-78-134-69.ap-northeast-2.compute.amazonaws.com/" + board.get(position).getImgUrl()).resize(110, 70).into(h.little);

    }

    class MyHolder extends RecyclerView.ViewHolder {

        OutlineTextView writer;
        OutlineTextView text;
        ImageView little;


        public MyHolder(View itemView) {
            super(itemView);

            writer = (OutlineTextView) itemView.findViewById(R.id.board_writer);
            text = (OutlineTextView) itemView.findViewById(R.id.board_text);
            little = (ImageView) itemView.findViewById(R.id.board_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(board.get(getLayoutPosition()).getWriter() + "님이 작성하신 글입니다.");
                    View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_enchant_board_view, null);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    Button closeBtn = (Button) view.findViewById(R.id.closeBtn);
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    OutlineTextView title = (OutlineTextView) view.findViewById(R.id.scroll_Text);
                    title.setText(board.get(getLayoutPosition()).getText());
                    TextView writer = (TextView) view.findViewById(R.id.scroll_ID);
                    writer.setText(board.get(getLayoutPosition()).getWriter());
                    ImageView iv = (ImageView) view.findViewById(R.id.imgUrl);
                    Glide.with(mContext).load("http://ec2-52-78-134-69.ap-northeast-2.compute.amazonaws.com/" + board.get(getLayoutPosition()).getImgUrl()).into(iv);
                    TextView content = (TextView) view.findViewById(R.id.content);
                    content.setText(board.get(getLayoutPosition()).getContent());


                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return board.size();
    }


}
