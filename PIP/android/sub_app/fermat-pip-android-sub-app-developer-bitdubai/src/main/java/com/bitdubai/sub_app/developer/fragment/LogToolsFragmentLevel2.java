package com.bitdubai.sub_app.developer.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;


import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ClassHierarchyLevels;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.ArrayListLoggers;
import com.bitdubai.sub_app.developer.common.Loggers;
import com.bitdubai.sub_app.developer.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.LogToolsFragment</code>
 * haves all methods for the log tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class LogToolsFragmentLevel2 extends Fragment {

    private Map<String, List<ClassHierarchyLevels>> pluginClasses;

    private static final String ARG_POSITION = "position";
    View rootView;

    private LogTool logTool;


    private ArrayListLoggers lstLoggers;
    private GridView gridView;

    private int loggerLevel=2;

    /**
     * SubApp Session
     */
    private SubAppsSession subAppSession;

    public static LogToolsFragmentLevel2 newInstance(int position,SubAppsSession subAppSession) {
        LogToolsFragmentLevel2 f = new LogToolsFragmentLevel2();
        f.setSubAppSession(subAppSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        try {
            ToolManager toolManager = subAppSession.getToolManager();
            try {
                logTool = toolManager.getLogTool();
            } catch (Exception e) {
                showMessage("CantGetToolManager - " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            showMessage("Unexpected error get tool manager - " + ex.getMessage());
            ex.printStackTrace();
        }

        pluginClasses = new HashMap<String,List<ClassHierarchyLevels>>();
    }



    private void changeLogLevel(String pluginKey,LogLevel logLevel, String resource) {
        try {
            //Plugins plugin = Plugins.getByKey("Bitcoin Crypto Network");
            Plugins plugin = Plugins.getByKey(pluginKey);


            //logTool.setLogLevel(plugin, logLevel);
            /**
             * Now I must look in pluginClasses map the match of the selected class to pass the full path
             */
            HashMap<String, LogLevel> data = new HashMap<String, LogLevel>();
            data.put(resource, logLevel);
            logTool.setNewLogLevelInClass(plugin, data);

        } catch (Exception e) {
            System.out.println("*********** soy un error " + e.getMessage());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_log_tools, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        try{

            Configuration config = getResources().getConfiguration();
            if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridView.setNumColumns(6);
            } else {
                gridView.setNumColumns(3);
            }

            ArrayListLoggers lstLoggersToShow=new ArrayListLoggers();
            for(Loggers loggers:lstLoggers){
                //String level_0 = loggers.level0;
                switch (loggerLevel){
                    case ArrayListLoggers.LEVEL_1:{
                        if(!lstLoggersToShow.containsLevel1(loggers)){
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                    }
                    case ArrayListLoggers.LEVEL_2:
                        if(!lstLoggersToShow.containsLevel2(loggers)){
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                    case ArrayListLoggers.LEVEL_3:
                        if(!lstLoggersToShow.containsLevel3(loggers)){
                            lstLoggersToShow.add(loggers);
                        }
                        break;
                }

            }


            AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.grid_items, lstLoggersToShow);
            _adpatrer.notifyDataSetChanged();
            gridView.setAdapter(_adpatrer);

        }catch (Exception e){
            showMessage("LogTools Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }

        //registerForContextMenu(gridView);
        return rootView;
    }




    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
    public void setLoggerLevel(int level){
        loggerLevel=level;
    }
    public int getLoggerLevel(){
        return loggerLevel;
    }

    public void setLoggers(ArrayListLoggers lstLoggers){
        this.lstLoggers=lstLoggers;
    }

    public class AppListAdapter extends ArrayAdapter<Loggers> {


        public AppListAdapter(Context context, int textViewResourceId, List<Loggers> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Loggers item = getItem(position);

            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_items_with_button2, parent, false);


                holder = new ViewHolder();




                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(),item.fullPath,Toast.LENGTH_SHORT);
                        Loggers item=(Loggers) gridView.getItemAtPosition(position);

                        // Reload current fragment
                     //   LogToolsFragmentLevel2 frg = null;
                     //   frg =(LogToolsFragmentLevel2) getFragmentManager().findFragmentByTag("fragmento2");

                        ArrayListLoggers lst = null;
                        int level = 0;

                        if(loggerLevel==ArrayListLoggers.LEVEL_1){

                             lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_1);
                           // frg.setLoggers(lst);
                           // frg.setLoggerLevel(ArrayListLoggers.LEVEL_2);
                            level = ArrayListLoggers.LEVEL_2;
                        }else if(loggerLevel==ArrayListLoggers.LEVEL_2){
                            lst = lstLoggers.getListFromLevel(item, ArrayListLoggers.LEVEL_2);
                            //frg.setLoggerLevel(ArrayListLoggers.LEVEL_3);
                            //frg.setLoggers(lst);
                            level = ArrayListLoggers.LEVEL_3;

                        }

                        //set the next fragment and params
                        Object[] params = new Object[2];

                        params[0] = lst;
                        params[1] = level;
                        ((FermatScreenSwapper)getActivity()).setScreen("DeveloperLogLevel3Fragment");
                        ((FermatScreenSwapper)getActivity()).setParams(params);
                        ((FermatScreenSwapper)getActivity()).changeScreen();


                    }
                });
                holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        String loggerText = holder.companyTextView.getText().toString();
                        /*PopupMenu popupMenu = new PopupMenu(getActivity(), view);

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                boolean result = false;
                                int itemId = menuItem.getItemId();
                                if (itemId == R.id.menu_no_logging) {
                                    //TODO: HAcer el cambio acá para que haga el changelevel
                                    changeLogLevel(item.pluginKey, LogLevel.NOT_LOGGING, item.classHierarchyLevels.getFullPath());
                                    //changeLogLevel();
                                    result = true;
                                } else if (itemId == R.id.menu_minimal) {
                                    changeLogLevel(item.pluginKey, LogLevel.MINIMAL_LOGGING, item.classHierarchyLevels.getFullPath());
                                    result = true;
                                } else if (itemId == R.id.menu_moderate) {
                                    changeLogLevel(item.pluginKey, LogLevel.MODERATE_LOGGING, item.classHierarchyLevels.getFullPath());
                                    result = true;
                                } else if (itemId == R.id.menu_aggresive) {
                                    changeLogLevel(item.pluginKey, LogLevel.AGGRESSIVE_LOGGING, item.classHierarchyLevels.getFullPath());
                                    result = true;

                                }

                                return result;
                            }
                        });

                        //popupMenu.getMenu().add();



                        popupMenu.inflate(R.menu.popup_menu);
                        /*boolean founded=false;
                        int counter=0;
                        while(!founded && counter<popupMenu.getMenu().size()){
                            MenuItem menuItem = popupMenu.getMenu().getItem(counter);
                            menuItem.setIcon(R.drawable.ic_action_accept_grey);
                            menuItem.setIcon(R.drawable.icono_banco_2);
                            //menuItem.
                            counter++;
                        }*/

                        //popupMenu.show();

                        CustomDialogClass cdd=new CustomDialogClass(getActivity(),item,item.pluginKey);
                        cdd.show();

                        return true;
                    }
                });
                //holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);

                TextView textView =(TextView) convertView.findViewById(R.id.company_text_view);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
                textView.setTypeface(tf);
                textView.setGravity(Gravity.CENTER);
                holder.companyTextView = textView;


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }



            String stringToShowLevel="Nada cargado";
            switch (loggerLevel){
                case ArrayListLoggers.LEVEL_1:{
                    //String[] level1_splitted=item.level1.split(".");
                    //tringToShowLevel=level1_splitted[level1_splitted.length-1];
                    //Toast.makeText(getActivity(),item.level1,Toast.LENGTH_SHORT);


                    stringToShowLevel=item.classHierarchyLevels.getLevel1();
                    if(item.classHierarchyLevels.getLevel2()==null){
                        stringToShowLevel=StringUtils.splitCamelCase(stringToShowLevel);
                        item.picture="java_class";
                        holder.imageView.setOnClickListener(null);
                    }else{
                        String[] stringToFormat=item.classHierarchyLevels.getLevel1().split("\\.");
                        stringToShowLevel=stringToFormat[stringToFormat.length-1];
                        stringToShowLevel=StringUtils.replaceStringByPoint(stringToShowLevel);
                        stringToShowLevel=StringUtils.replaceStringByUnderScore(stringToShowLevel);
                    }
                    //stringToShowLevel=item.level1;
                    break;
                }
                case ArrayListLoggers.LEVEL_2:{
                    stringToShowLevel= StringUtils.splitCamelCase(item.classHierarchyLevels.getLevel2());
                    if(item.classHierarchyLevels.getLevel3()==null){
                        item.picture="java_class";
                        holder.imageView.setOnClickListener(null);

                    }
                    break;
                }
                case ArrayListLoggers.LEVEL_3:{
                    stringToShowLevel=item.classHierarchyLevels.getLevel3();
                    item.picture="java_class";
                    holder.imageView.setOnClickListener(null);
                    break;
                }

            }


            holder.companyTextView.setText(stringToShowLevel);

            // holder.companyTextView.setTypeface(MyApplication.getDefaultTypeface());




            switch (item.picture) {
                case "plugin":
                    holder.imageView.setImageResource(R.drawable.folder);
                    holder.imageView.setTag("CPWWRWAKAV1M|1");
                    break;
                case "addon":
                    holder.imageView.setImageResource(R.drawable.folder);
                    holder.imageView.setTag("CPWWRWAKAV1M|2");
                    break;
                case "java_class":
                    holder.imageView.setImageResource(R.drawable.java_class);
                    holder.imageView.setTag("CPWWRWAKAV1M|3");
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.fermat);
                    holder.imageView.setTag("CPWWRWAKAV1M|4");
                    break;
            }


            return convertView;
        }

    }
    /**
     * ViewHolder.
     */
    private class ViewHolder {



        public ImageView imageView;
        public TextView companyTextView;
        public ImageView btnLogger;

    }

    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {


        private Loggers logger;
        private String pluginKey;
        public Activity c;
        public Dialog d;

        ListView list;
        String[] web = {
                "Not logging",
                "Minimal logging",
                "Moderate logging",
                "Agressive logging"
        } ;

        List<String> lstEnum;

        Integer[] img ={
                R.drawable.ic_action_accept_grey,
                0,
                0,
                0
        };

        public CustomDialogClass(Activity a,Loggers loggers,String pluginKey) {
            super(a);
            this.logger=loggers;
            this.pluginKey=pluginKey;
            testing();
            //loadEnumsLogger();
            // TODO Auto-generated constructor stub
            this.c = a;
            setLogLevelImage();

            logger.logLevel = LogLevel.NOT_LOGGING;
        }

        private void testing(){
            lstEnum=new ArrayList<>();
            for(int i=0;i<LogLevel.values().length;i++){
                lstEnum.add(LogLevel.values()[i].getDisplayName());
            }
        }
        private void setLogLevelImage(){
            if(logger.logLevel!=null) {
                switch (logger.logLevel) {
                    case NOT_LOGGING:
                        img = new Integer[]{
                                1, 0, 0, 0
                        };
                        break;
                    case MINIMAL_LOGGING:
                        img = new Integer[]{
                                0, 1, 0, 0
                        };
                        break;
                    case MODERATE_LOGGING:
                        img = new Integer[]{
                                0, 0, 1, 0
                        };
                        break;
                    case AGGRESSIVE_LOGGING:
                        img = new Integer[]{
                                0, 0, 0, 1
                        };
                        break;
                }
            }else{
                logger.logLevel= LogLevel.NOT_LOGGING;
            }
        }

        /*private void loadEnumsLogger(){
            LogLevel[] enum_logLevel = LogLevel.values();
            List<String> lstEnum = new ArrayList<String>();
            for(int i=0;i<enum_logLevel.length;i++){
                lstEnum.add(enum_logLevel[i].getDisplayName());
            }
        }
        private void setIconSelected(){
            if(logger!=null){
                logger.logLevel.getCode();
            }else{
                //logger=new LogLevel(LogLevel.);
            }

        }*/



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.popup);



            CustomList adapter = new
                    CustomList(c, lstEnum, img);
            list = (ListView) findViewById(R.id.listView);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(c,web[+ position]+" activated", Toast.LENGTH_SHORT).show();
                    String item =list.getItemAtPosition(position).toString();
                    if(item.compareTo(LogLevel.NOT_LOGGING.toString())==0) {
                        changeLogLevel(pluginKey, LogLevel.NOT_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.NOT_LOGGING;
                    }else if (item.compareTo(LogLevel.MINIMAL_LOGGING.toString())==0){
                        changeLogLevel(pluginKey, LogLevel.MINIMAL_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.MINIMAL_LOGGING;
                    }else if(item.compareTo(LogLevel.MODERATE_LOGGING.toString())==0){
                        changeLogLevel(pluginKey, LogLevel.MODERATE_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.MODERATE_LOGGING;
                    }else if (item.compareTo(LogLevel.AGGRESSIVE_LOGGING.toString())==0){
                        changeLogLevel(pluginKey, LogLevel.AGGRESSIVE_LOGGING, logger.classHierarchyLevels.getFullPath());
                        logger.logLevel = LogLevel.AGGRESSIVE_LOGGING;
                    }
                    dismiss();
                }
            });

        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            /*if (i == R.id.btn_yes) {
                c.finish();

            } else if (i == R.id.btn_no) {
                dismiss();

            } else {
            }*/
            dismiss();
        }

        public class CustomList extends ArrayAdapter<String>{

            private final Activity context;
            private final List<String> listEnumsToDisplay;
            private final Integer[] imageId;
            public CustomList(Activity context,
                              List<String> listEnumsToDisplay, Integer[] imageId) {
                super(context, R.layout.list_single, listEnumsToDisplay);
                this.context = context;
                this.listEnumsToDisplay = listEnumsToDisplay;
                this.imageId = imageId;

            }
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View rowView= inflater.inflate(R.layout.list_single, null, true);
                TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

                ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
                txtTitle.setTextColor(Color.WHITE);
                txtTitle.setText(listEnumsToDisplay.get(position));
                //txtTitle.setText(LogLevel.MINIMAL_LOGGING.toString());

                setLogLevelImage();
                if(imageId[position]!=0){
                    imageView.setImageResource(R.drawable.ic_action_accept_grey);
                }

                return rowView;
            }
        }

    }
    public void setSubAppSession(SubAppsSession subAppSession) {
        this.subAppSession = subAppSession;
    }
}