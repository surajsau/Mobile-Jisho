package com.halfplatepoha.jisho.lists.listsfragment;

import com.halfplatepoha.jisho.jdb.JishoList;

/**
 * Created by surjo on 08/01/18.
 */

public class ListObject {

    public String name;
    public boolean isSelected;
    public boolean isNameChange;

    public static ListObject fromJishoListName(String name) {
        if(name != null) {
            ListObject listObject = new ListObject();
            listObject.name = name;
            return listObject;
        }
        return null;
    }

}
