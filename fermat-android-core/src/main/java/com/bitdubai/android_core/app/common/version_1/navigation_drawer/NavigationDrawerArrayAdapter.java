package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.fermat.R;

import java.util.List;


public class NavigationDrawerArrayAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final List<String> values;


    public NavigationDrawerArrayAdapter(Context context, List<String> values) {
        super(context, R.layout.wallet_framework_activity_main_navigation_drawer_row_layout_empty, values);
        try
        {

            this.context = context;
            this.values = values;
        }
        catch (Exception e)
        {
            throw e;
        }

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        try
        {
            if (position == 0) {


                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                //testing

                rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row, parent, false);
                /*switch (ApplicationSession.getActivityId())
                {
                    case "DesktopActivity":
                        rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_navigation_drawer_first_row, parent, false);
                        break;
                      default:
                        rowView = inflater.inflate(R.layout.wallet_manager_main_activity_navigation_drawer_first_row_empty, parent, false);
                          break;

                }*/
                TextView txtView_description = (TextView) rowView.findViewById(R.id.txtView_description);
                if(txtView_description != null){

                    txtView_description.setTypeface(ApplicationSession.getDefaultTypeface(), 1);

                    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    txtView_description.setText("Tessa Crankston");
                }


                //        ImageView iconEdit = (ImageView) rowView.findViewById(R.id.icon_edit_profile);
                //        iconEdit.setOnClickListener(new View.OnClickListener() {

                //            @Override
                //            public void onClick(View v) {
                //                Intent intent;

                //            }
                //       });

            }
            else {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.wallet_framework_activity_framework_navigation_drawer_row_layout, parent, false);


                //test mati
                rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_framework_navigation_drawer_row_layout, parent, false);
                /*switch (ApplicationSession.getActivityId()) {
                    case "DesktopActivity":
                        rowView = inflater.inflate(R.layout.wallet_manager_desktop_activity_framework_navigation_drawer_row_layout, parent, false);
                        break;
                      default:
                        rowView = inflater.inflate(R.layout.wallet_framework_activity_main_navigation_drawer_row_layout_empty, parent, false);
                        break;

                }
                */
                ImageView imageView = null;
                imageView = (ImageView) rowView.findViewById(R.id.icon);
                if(rowView.findViewById(R.id.label) != null)
                {
                    TextView textView = (TextView) rowView.findViewById(R.id.label);
                    textView.setTypeface(ApplicationSession.getDefaultTypeface(), 1);

                    textView.setText(values.get(position));
                }


                //if (ApplicationSession.getActivityId() == "DesktopActivity") {

                    switch (position) {
                        case 1:
                            imageView.setImageResource(R.drawable.ic_action_store);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.ic_action_wallet);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.ic_action_factory);
                            break;
                        case 4:
                            imageView.setImageResource(R.drawable.ic_action_wallet_published);
                            break;
                        case 5:
                            imageView.setImageResource(R.drawable.ic_action_wallet);
                            break;

                        case 6:
                            imageView.setImageResource(R.drawable.ic_action_exit);
                            break;
                        default:
                            imageView.setImageResource(R.drawable.unknown_icon);
                    }



                }

            //}
        }
        catch (Exception e){
            throw e;
        }


        return rowView;
    }
}