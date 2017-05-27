package com.orton.myapplication;

/**
 * Created by Orton on 25-05-2017.
 */

public class WordClass  {
    private String mDefaultTranslation;
    private String mMiwokTranslation ;
    private int mAudioResource=image_resource_constant ;
    private static final int image_resource_constant = -1 ;
    private int mImageResourceId = image_resource_constant ;   ///stores image resource id

    WordClass(String defaultTranslation,String miwokTranslation,int mImage,int audioResource)
    {
        mAudioResource= audioResource ;
        mImageResourceId = mImage ;
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    /**
     * constructor is for phrases Activity
     * @param defaultTranslation english word
     * @param miwokTranslation miwok translation of that word
     */
    WordClass(String defaultTranslation,String miwokTranslation,int audioResource)
    {
        mAudioResource=audioResource ;
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
    }
    public String getDefault()
    {
        return mDefaultTranslation;
    }
    public String getMiwok()
    {
        return mMiwokTranslation;
    }
    public int getImageResource()
    {
        return mImageResourceId;
    }
    public int getAudioResource(){return mAudioResource;}
    public boolean findImage()
    {
        return mImageResourceId != -1 ;
    }
}
