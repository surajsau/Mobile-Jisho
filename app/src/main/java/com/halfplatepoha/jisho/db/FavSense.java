package com.halfplatepoha.jisho.db;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class FavSense extends RealmObject {

    private RealmList<RealmString> english_definitions;
    private RealmList<RealmString> parts_of_speech;
    private RealmList<FavLink> links;
    private RealmList<RealmString> tags;
    private RealmList<RealmString> see_also;
    private RealmList<RealmString> info;

    public RealmList<RealmString> getEnglish_definitions() {
        return english_definitions;
    }

    public void setEnglish_definitions(ArrayList<String> english_definitions) {
        if(english_definitions != null) {
            for(String str : english_definitions) {
                RealmString rs = new RealmString();
                rs.setValue(str);
                this.english_definitions.add(rs);
            }
        }
    }

    public RealmList<RealmString> getParts_of_speech() {
        return parts_of_speech;
    }

    public void setParts_of_speech(ArrayList<String> parts_of_speech) {
        if(parts_of_speech != null) {
            for(String str : parts_of_speech) {
                RealmString rs = new RealmString();
                rs.setValue(str);
                this.english_definitions.add(rs);
            }
        }
    }

    public RealmList<FavLink> getLinks() {
        return links;
    }

    public void setLinks(RealmList<FavLink> links) {
        this.links = links;
    }

    public RealmList<RealmString> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        if(tags != null) {
            for(String str : tags) {
                RealmString rs = new RealmString();
                rs.setValue(str);
                this.tags.add(rs);
            }
        }
    }

    public RealmList<RealmString> getSee_also() {
        return see_also;
    }

    public void setSee_also(ArrayList<String> see_also) {
        if(see_also != null) {
            for(String str : see_also) {
                RealmString rs = new RealmString();
                rs.setValue(str);
                this.see_also.add(rs);
            }
        }
    }

    public RealmList<RealmString> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<String> info) {
        if(info != null) {
            for(String str : info) {
                RealmString rs = new RealmString();
                rs.setValue(str);
                this.info.add(rs);
            }
        }
    }
}
