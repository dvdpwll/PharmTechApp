package com.groupproject.android.PharmTech_SA_TN_DP_FY;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Authors: David Powell, Saadia Achouham, Thomas Newell, Fikir Yilma
 * DrugListFragment: shows a list of drugs
 */

public class DrugListFragment extends Fragment{
    //implements SearchView.OnQueryTextListener{
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private static final int REQUEST_DRUG = 1;
    private static final String TAG = "DrugListFragment";
    private static final String EXTRA_DRUG_WHERE = "where";
    private static final String EXTRA_DRUG_ARGS= "args";
    private RecyclerView mDrugRecyclerView;
    private DrugAdapter mAdapter;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    //private DrugAdapter mAdapterM;

    //list of drugs that are as the result of the query from the source button page
    private  List<Drug> drugs = new ArrayList<Drug>();
    //private SearchView searchView;
    private  MenuItem searchItem;

    //------filter list------
    private List<Drug> filteredList;

    //-----list of all drugs
   // List<Drug> drugsList;

    String where;
    String argsTemp;


    //toolBar option
    private boolean mSubtitleVisible;
    private int saved;

    //Receiving menu callbacks
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //-----------list of all drugs
       // drugsList = DrugLab.get(getActivity()).getDrugs();

        //------filter list------
        filteredList = new ArrayList<Drug>();
        filteredList.addAll(drugs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_list, container, false);

        mDrugRecyclerView = (RecyclerView) view.findViewById(R.id.drug_recycler_view);
        //add decoration
        mDrugRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        assert mDrugRecyclerView != null;
        mDrugRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mDrugRecyclerView.setHasFixedSize(true);

        //connect mAdapter to your RecyclerView
        updateUI();

        return view;
    }


    @Override
    public void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mDrugRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    // to account for saving of an instance in rotation or scrolling between recyclerview and drugfragmen
    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mDrugRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    //Saving subtitle visibility, account for device rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    //Inflating a menu resource
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.fragment_drug_list, menu);

        //Toggling the action item title
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

        // hook up a listener for when a search is performed:
        searchItem = menu.findItem(R.id.menu_item_search_drug);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        /*final TextView htmltextview = (TextView) menu.findItem(R.id.search_text);
        searchView.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View v) {
                htmltextview.setCursorVisible(true);
                return false;
            }
        });*/


        //event handler of search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit (String query) {
                // perform query here

                String whereG = "generic";
                String whereB = "brand";
                //capitalize the first letter of a drug
                query = query.substring(0, 1).toUpperCase() + query.substring(1).toLowerCase();

                String[] args = {query};
                //list of drugs that have this generic name
                List<Drug>  drugsG = DrugLab.get(getActivity()).getDrugs(whereG, args);

                String[] argsB = {query+"®"};
                //list of drugs that have this brand name
                List<Drug>  drugsB = DrugLab.get(getActivity()).getDrugs(whereB, argsB);

                //list of drugs that have this generic or brand name
                drugsG.addAll(drugsB);

                if (drugsG.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                }
                else {
                    //get the only drug from the List bcz each generic name is associated to only one drug
                    Drug drug = drugsG.get(0);
                    Intent intent = DrugPagerActivity.newIntent(getActivity(), drug.getId());
                    intent.putExtra("where", where);
                    intent.putExtra("args", argsTemp);
                    startActivity(intent);
                }

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

                return true;
            }

            @Override
            public boolean onQueryTextChange (String newText) {

                newText = newText.toLowerCase();
                mAdapter.getFilter().filter(newText);
                mAdapter = new DrugAdapter(filteredList);
                mDrugRecyclerView.setAdapter(mAdapter);

                /*List<Drug> newList = new ArrayList<>();

                for(Drug drug : filteredList){
                    String name = drug.getGeneric_Name().toLowerCase();
                    if(name.contains(newText)){
                        newList.add(drug);
                    }
                }
                mAdapterM.setFilter(newList);*/

                return true;
            }

        });
        //searchView.clearFocus();

        super.onCreateOptionsMenu(menu, inflater);
    }//end of onCreateOptionsMenu

    //Responding to menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String args = " ";
        String where = "generic";

        switch(item.getItemId()) {
            case R.id.menu_item_search_drug:
                //i dont know why this is empty (David)
                //Responding to Show Subtitle action item
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                //getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Setting the toolbar’s subtitle
    private void updateSubtitle() {
        DrugLab drugLab = DrugLab.get(getActivity());
        int drugCount = drugs.size();

        //String subtitle = getString(R.string.subtitle_format, String.valueOf(drugCount));
        String subtitle = getResources()
                .getQuantityString(R.plurals.subtitle_plural, drugCount, drugCount);

        //Showing or hiding the subtitle
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }




    // called by ... onResume(), onCreateView()
    // added per p 184 and altered per p 271
    // -------> intents for query's added here <--------
    private void updateUI(){
        DrugLab drugLab = DrugLab.get(getActivity());
        // List<Drug> drugs;

        //get Intents for query's
        where = getActivity().getIntent().getStringExtra(EXTRA_DRUG_WHERE);
        argsTemp = getActivity().getIntent().getStringExtra(EXTRA_DRUG_ARGS);
        String[] args = {argsTemp};

        //
        if(where != null && args != null) {
            drugs = drugLab.getDrugs(where, args);
        }
        else{
            drugs = drugLab.getDrugs();
        }
        filteredList.addAll(drugs);

        // will set the list to recyclerview
        if(mAdapter == null){
            mAdapter = new DrugAdapter(drugs);
            mDrugRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDrugs(drugs);
            mAdapter.notifyDataSetChanged();

            //reload a single item in the list
            mAdapter.notifyItemChanged(saved);
        }
        if (drugs.isEmpty()){
        }
    }

    //---------------------ViewHolder class----------------------------
    private class DrugHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Drug mDrug;
        private TextView mGenericTextView;
        private TextView mBrandTV;

        //constructor
        public DrugHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mGenericTextView = (TextView) itemView.findViewById(R.id.list_item_drug_generic_text_view);
            mBrandTV = (TextView) itemView.findViewById(R.id.list_item_drug_brand_text_view);
            //itemView.setOnCreateContextMenuListener(this);
        }

        public void bindDrug(Drug drug) {
            mDrug = drug;
            mGenericTextView.setText(mDrug.getGeneric_Name());
            mBrandTV.setText((mDrug.getBrand_Name()));
        }

        // to account for identifying which drug was selected, to redraw old selection and new
        @Override
        public void onClick(View v) {
            saved = getAdapterPosition();

            Intent intent = DrugPagerActivity.newIntent(getActivity(), mDrug.getId());
            intent.putExtra("where", where);
            intent.putExtra("args", argsTemp);
            startActivityForResult(intent, REQUEST_DRUG);
        }
    } // end of inner class: DrugHolder

    //--------------filter class ----------
    //--------------filter class ----------
    public class CustomFilter extends Filter {
        private DrugAdapter mAdapterF;
        private CustomFilter(DrugAdapter mAdapterF) {
            super();
            this.mAdapterF = mAdapterF;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(drugs);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Drug mDrug : drugs) {
                    if (mDrug.getGeneric_Name().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(mDrug);
                        System.out.println("\t : "+filteredList);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.mAdapterF.notifyDataSetChanged();
        }
    }//end of Flter class


    //----------------Adapter class------------
    // to connect/fill the view with the list of drugs
    private class DrugAdapter extends RecyclerView.Adapter<DrugHolder> implements Filterable {
        private List<Drug> mDrugs;

        //----------------CustomFilter------------------
        private CustomFilter mFilter;

        //constructor
        DrugAdapter(List<Drug> drugs) {
            mDrugs = drugs;
            mFilter = new CustomFilter(DrugAdapter.this);
        }

        @Override
        public DrugHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_drug, parent, false);
            return new DrugHolder(view);
        }

        @Override
        public void onBindViewHolder(DrugHolder holder, int position) {
            Drug drug = mDrugs.get(position);
            holder.bindDrug(drug);
        }

        @Override
        public int getItemCount() {
            return mDrugs.size();
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        // called by updateUI
        public void setDrugs(List<Drug> drugs){
            mDrugs = drugs;
        }

        public void setFilter(List<Drug> newList){

            DrugLab drugLab = DrugLab.get(getActivity());
            filteredList = drugLab.getDrugs();
            filteredList.addAll(newList);

            //refresh Adapter
            notifyDataSetChanged();
        }

    } // end of DrugAdapter

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DRUG) {
            // Handle result
        }
    } // end of DrugAdapter class

} // end of DrugListFragment
