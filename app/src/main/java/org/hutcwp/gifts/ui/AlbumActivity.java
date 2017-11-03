package org.hutcwp.gifts.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import org.hutcwp.gifts.R;
import org.hutcwp.gifts.adapter.AlbumAdapter;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private AlbumAdapter adapter;
    RecyclerView rvAlbum;

    ArrayList <String > imgList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        rvAlbum = (RecyclerView) findViewById(R.id.rv_album);

        initList();

        adapter = new AlbumAdapter(AlbumActivity.this, imgList);

        rvAlbum.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvAlbum.addItemDecoration(new SpacesItemDecoration(14));
        rvAlbum.setAdapter(adapter);
    }

    private void initList() {

        imgList.add("http://a1.qpic.cn/psb?/V12hNaWK24SBqH/zEE.Y9vTXtBK9R3E85opQpbdQKU5ePKAfFTmlEc.Maw!/b/dGgBAAAAAAAA&bo=AAWrBqAFgAcFCaA!&rf=viewer_4");
        imgList.add("http://a2.qpic.cn/psb?/V12hNaWK24SBqH/wwgKENXGlHqE4NGn5Tml4DDT5nxspdmCvMTkWZMufEs!/b/dAYBAAAAAAAA&bo=OASgBTgEoAUFCSo!&rf=viewer_4");
        imgList.add("http://a3.qpic.cn/psb?/V12hNaWK24SBqH/XOSOqZLxBq9*dIwd9Sbfi44Ap8DFvVT0CVe5s3REuPk!/b/dMgAAAAAAAAA&bo=0AIABdACAAUFCSo!&rf=viewer_4");
        imgList.add("http://a2.qpic.cn/psb?/V12hNaWK24SBqH/.yAw.Ug9ZTXv20kK7*zzRSJaD4WSHx4*mGfWCXqgKMk!/b/dAMBAAAAAAAA&bo=0AIABdACAAUFCSo!&rf=viewer_4");
        imgList.add("http://a1.qpic.cn/psb?/V12hNaWK24SBqH/cd1fGxi1oaQ8THrWxEv8FRIhXi*F6EkZ8KN*WbI9KaE!/b/dD4BAAAAAAAA&bo=AAWrBjAMQBARCfo!&rf=viewer_4");
        imgList.add("http://a2.qpic.cn/psb?/V12hNaWK315vsd/sjPtL8bl90DvBT*pyz*OmnOAniiVbl1zomZIz6pR.q0!/b/dMcAAAAAAAAA&bo=gAJVA4ACVQMFCSo!&rf=viewer_4");
    }

    /**
     * Recycler的分割线
     */
    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }


}
