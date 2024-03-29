package com.myselfie.myselfie.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.myselfie.myselfie.R;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnAlbumsListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

@SuppressLint("InflateParams")
public class TabsFragment extends Fragment {
	private static final String TAG = "TabsFragment";
    TabManager mTabManager;
    TextView statusText;
    private SimpleFacebook mSimpleFacebook;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabManager = new TabManager(getActivity(), getChildFragmentManager(),
                R.id.realtabcontent);
        
        mSimpleFacebook = SimpleFacebook.getInstance(this.getActivity());        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabs, container, false);
        statusText = (TextView) v.findViewById(R.id.tv_title);
        mTabManager.handleCreateView(v);        
        mTabManager.addTab(R.string.lbl_master_detail, R.drawable.ico_star,CameraFragment.class, savedInstanceState);
        mTabManager.addTab(R.string.lbl_swipe, R.drawable.ico_list, MasterDetailFragment.class,savedInstanceState);
        mTabManager.addTab(R.string.lbl_check_list, R.drawable.ico_check,MasterDetailFragment.class, savedInstanceState);
        mTabManager.addTab(R.string.lbl_settings, R.drawable.ico_settings,SettingsFragment.class,savedInstanceState);
                      
        mSimpleFacebook.getAlbums(new OnAlbumsListener() {

			@Override
			public void onException(Throwable throwable) {
				Log.i(TAG, "Album disparou exce��o = ",throwable);
			}

			@Override
			public void onFail(String reason) {
				Log.i(TAG, "Album falhou = " + reason);
			}

			@Override
			public void onComplete(List<Album> response) {
				Log.i(TAG, "Number of albums = " + response.size());
				Boolean hasAlbum = false;
				String albumId = "";
				for(Album a: response){
					if(a.getName().compareToIgnoreCase("myselfie") == 0){
						hasAlbum = true;
						albumId = a.getId();
					}
				}
				if(!hasAlbum){
				    //  cria o album MySelfie
			    	Album album = new Album.Builder()
			    	.setName("MySelfie")
			    	.setMessage("Album create by Myselfie Application to store my photos")
			    	.build();
			    	
			    	mSimpleFacebook.publish(album, new OnPublishListener() {
			    		@Override
			    		public void onComplete(String id) {
			    			Log.i(TAG, "Published successfully. id = " + id);
			    			Photo photo = new Photo.Builder()
						    .setImage(new BitmapFactory().decodeResource(getResources(),R.drawable.brave))
						    .setName("Submit Photo to facebook")
						    .build();
							
							mSimpleFacebook.publish(photo,id, new OnPublishListener() {
							    @Override
							    public void onComplete(String id) {
							        Log.i(TAG, "Published successfully. id = " + id);
							    }

							    /* 
							     * You can override other methods here: 
							     * onThinking(), onFail(String reason), onException(Throwable throwable)
							     */
							});
			    		}
			    	});
				}
				else{
					Photo photo = new Photo.Builder()
				    .setImage(new BitmapFactory().decodeResource(getResources(),R.drawable.brave))
				    .setName("Submit Photo to facebook")
				    .build();
					
					mSimpleFacebook.publish(photo,albumId , new OnPublishListener() {
					    @Override
					    public void onComplete(String id) {
					        Log.i(TAG, "Published successfully. id = " + id);
					    }

					    /* 
					     * You can override other methods here: 
					     * onThinking(), onFail(String reason), onException(Throwable throwable)
					     */
					});
				}
			}
		});
        return v;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mTabManager.handleViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabManager.handleDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTabManager.handleSaveInstanceState(outState);
    }

    /**
     * This is a helper class that implements a generic mechanism for
     * associating fragments with the tabs in a tab host.  DO NOT USE THIS.
     * If you want tabs in a fragment, use the support v13 library's
     * FragmentTabHost class, which takes care of all of this for you (in
     * a simpler way even).
     */
    public class TabManager implements TabHost.OnTabChangeListener {
        private final Context mContext;
        private final FragmentManager mManager;
        private final int mContainerId;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        private TabHost mTabHost;
        private TabInfo mLastTab;
        private boolean mInitialized;
        private String mCurrentTabTag;

        final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        public TabManager(Context context, FragmentManager manager, int containerId) {
            mContext = context;
            mManager = manager;
            mContainerId = containerId;
        }

        public TabHost handleCreateView(View root) {
            if (mTabHost != null) {
                throw new IllegalStateException("TabHost already set");
            }
            mTabHost = (TabHost)root.findViewById(android.R.id.tabhost);
            mTabHost.setup();
            mTabHost.setOnTabChangedListener(this);
            return mTabHost;
        }

        public void addTab(int labelID,int drawableId, Class<?> clss, Bundle args) {
            
        	View tabview = createTabView(mTabHost.getContext(),drawableId);
        	String tag = mTabHost.getContext().getString(labelID);
        	
            TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabContentFactory() {
                    public View createTabContent(String tag) {
                    	View view = new View(mContext);
                    	view.setMinimumWidth(0);
                    	view.setMinimumHeight(0);
                    	return view;}
            });
	        TabInfo info = new TabInfo(tag, clss, args);
	        mTabs.add(info);
            mTabHost.addTab(setContent);
            
        }
        
        private View createTabView(final Context context, int drawableId) {
            View view = LayoutInflater.from(context).inflate(R.layout.tab_indicator, null);
            ImageView iv = (ImageView) view.findViewById(R.id.icon);
            iv.setImageDrawable(context.getResources().getDrawable(drawableId));
            return view;
        }

        public void handleViewStateRestored(Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                mCurrentTabTag = savedInstanceState.getString("tab");
            }
            mTabHost.setCurrentTabByTag(mCurrentTabTag);

            String currentTab = mTabHost.getCurrentTabTag();

            // Go through all tabs and make sure their fragments match
            // the correct state.
            FragmentTransaction ft = null;
            for (int i=0; i<mTabs.size(); i++) {
                TabInfo tab = mTabs.get(i);
                tab.fragment = mManager.findFragmentByTag(tab.tag);
                if (tab.fragment != null && !tab.fragment.isDetached()) {
                    if (tab.tag.equals(currentTab)) {
                        // The fragment for this tab is already there and
                        // active, and it is what we really want to have
                        // as the current tab.  Nothing to do.
                        mLastTab = tab;
                    } else {
                        // This fragment was restored in the active state,
                        // but is not the current tab.  Deactivate it.
                        if (ft == null) {
                            ft = mManager.beginTransaction();
                        }
                        ft.detach(tab.fragment);
                    }
                }
            }

            // We are now ready to go.  Make sure we are switched to the
            // correct tab.
            mInitialized = true;
            ft = doTabChanged(currentTab, ft);
            if (ft != null) {
                ft.commit();
                mManager.executePendingTransactions();
            }
        }

        public void handleDestroyView() {
            mCurrentTabTag = mTabHost.getCurrentTabTag();
            mTabHost = null;
            mTabs.clear();
            mInitialized = false;
        }

        public void handleSaveInstanceState(Bundle outState) {
            outState.putString("tab", mTabHost != null
                    ? mTabHost.getCurrentTabTag() : mCurrentTabTag);
        }

        @Override
        public void onTabChanged(String tabId) {
            if (!mInitialized) {
                return;
            }
            statusText.setText(tabId);
            FragmentTransaction ft = doTabChanged(tabId, null);
            if (ft != null) {
                ft.commit();
            }
        }

        private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
            TabInfo newTab = null;
            for (int i=0; i<mTabs.size(); i++) {
                TabInfo tab = mTabs.get(i);
                if (tab.tag.equals(tabId)) {
                    newTab = tab;
                }
            }
            if (newTab == null) {
                throw new IllegalStateException("No tab known for tag " + tabId);
            }
            if (mLastTab != newTab) {
                if (ft == null) {
                    ft = mManager.beginTransaction();
                }
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mContext,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }
                mLastTab = newTab;
            }
            return ft;
        }
    }
}
