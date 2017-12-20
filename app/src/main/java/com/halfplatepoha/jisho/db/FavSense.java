package com.halfplatepoha.jisho.db;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by surjo on 22/04/17.
 */

public class FavSense extends RealmObject {

    private RealmList<String> english_definitions;
    private RealmList<String> parts_of_speech;
    private RealmList<FavLink> links;
    private RealmList<String> tags;
    private RealmList<String> see_also;
    private RealmList<String> info;

    public RealmList<String> getEnglish_definitions() {
        return english_definitions;
    }

    public void setEnglish_definitions(ArrayList<String> english_definitions) {
        if(english_definitions != null) {
            for(String str : english_definitions) {
                this.english_definitions.add(str);
            }
        }
    }

    public RealmList<String> getParts_of_speech() {
        return parts_of_speech;
    }

    public void setParts_of_speech(ArrayList<String> parts_of_speech) {
        if(parts_of_speech != null) {
            for(String str : parts_of_speech) {
                this.english_definitions.add(str);
            }
        }
    }

    public RealmList<FavLink> getLinks() {
        return links;
    }

    public void setLinks(RealmList<FavLink> links) {
        this.links = links;
    }

    public RealmList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        if(tags != null) {
            for(String str : tags) {
                this.tags.add(str);
            }
        }
    }

    public RealmList<String> getSee_also() {
        return see_also;
    }

    public void setSee_also(ArrayList<String> see_also) {
        if(see_also != null) {
            for(String str : see_also) {
                this.see_also.add(str);
            }
        }
    }

    public RealmList<String> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<String> info) {
        if(info != null) {
            for(String str : info) {
                this.info.add(str);
            }
        }
    }
}
