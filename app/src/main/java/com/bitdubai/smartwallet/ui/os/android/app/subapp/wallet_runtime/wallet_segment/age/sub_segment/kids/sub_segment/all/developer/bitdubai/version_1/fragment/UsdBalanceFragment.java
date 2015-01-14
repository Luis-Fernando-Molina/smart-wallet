package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.bitdubai.smartwallet.R;


public class UsdBalanceFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    private String[] tickets;
    private String[] countries;

    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;

    public static UsdBalanceFragment newInstance(int position) {
        UsdBalanceFragment f = new UsdBalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tickets = new String[]{"usd_1", "usd_1", "usd_1", "usd_5","usd_10","usd_20", "usd_100", "Mariana Duyos", "Pedro Perrotta", "Simon Cushing","Stephanie Himonidis","Taylor Backus", "Ginny Kaltabanis","Piper Faust","Deniz Caglar","Helen Nisbet","Dea Vanagan","Tim Hunter","Madeleine Jordan","Kate Bryan","Victoria Gandit","Jennifer Johnson","Robert Wint","Kevin Helms","Teddy Truchot","Hélène Derosier","John Smith","Caroline Mignaux","Guillaume Thery","Brant Cryder","Thomas Levy","Louis Stenz" };

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.wallets_kids_fragment_usd_balance, container, false); //Contains empty RelativeLayout
        ImageView money = (ImageView)view.findViewById(R.id.ticket1);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket2);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket3);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket4);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket5);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        return view;
    }

    private final class dragTouchListener implements View.OnTouchListener {
        public boolean onTouch(View imageView, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
            /*start the drag - contains the data to be dragged,
            metadata for this data and callback for drawing shadow*/
                imageView.startDrag(clipData, shadowBuilder, imageView, 0);
//        we're dragging the shadow so make the view invisible
                imageView.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class dropListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View receivingLayoutView, DragEvent dragEvent) {
            View draggedImageView = (View) dragEvent.getLocalState();
            int action = dragEvent.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                   // Log.i(TAG, "drag action started");

                    // Determines if this View can accept the dragged data
                    if (dragEvent.getClipDescription()
                            .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                       // Log.i(TAG, "Can accept this data");

                        // returns true to indicate that the View can accept the dragged data.
                        return true;

                    } else {
                       // Log.i(TAG, "Can not accept this data");

                    }

                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                 //   Log.i(TAG, "drag action entered");
//                the drag point has entered the bounding box
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                   // Log.i(TAG, "drag action location");
                /*triggered after ACTION_DRAG_ENTERED
                stops after ACTION_DRAG_EXITED*/
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                 //   Log.i(TAG, "drag action exited");
//                the drag shadow has left the bounding box
                    return true;
                case DragEvent.ACTION_DROP:
                    try {
                        ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedImageView.getParent();
                        draggedImageViewParentLayout.removeView(draggedImageView);
                       // ImageView bottomLinearLayout = (ImageView) receivingLayoutView;
                       // bottomLinearLayout.(draggedImageView);
                        draggedImageView.setVisibility(View.VISIBLE);
                    }
                    catch (Exception ex) {
                        String strError = ex.getMessage();
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                   // Log.i(TAG, "drag action ended");
                   // Log.i(TAG, "getResult: " + dragEvent.getResult());

//                if the drop was not successful, set the ball to visible
                    if (!dragEvent.getResult()) {
                      //  Log.i(TAG, "setting visible");
                        try
                        {
                            draggedImageView.setVisibility(View.VISIBLE);

                        }
                        catch(Exception ex)
                        {
                            String strError = ex.getMessage();
                        }

                    }


                    return true;

                default:
                    break;
            }
            return true;
        }

    }


}