package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.jdb.JishoList;

/**
 * Created by surjo on 08/01/18.
 */

public class ListObject {

    public String name;
    public boolean isSelected;
    public boolean isNameChange;
    public int count;

    public static ListObject fromJishoListName(String name, int count) {
        if(name != null) {
            ListObject listObject = new ListObject();
            listObject.name = name;
            listObject.count = count;
            return listObject;
        }
        return null;
    }

}
