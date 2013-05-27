package com.blstream.patronage.ctf.web.converter;

import com.blstream.patronage.ctf.model.BaseModel;
import com.blstream.patronage.ctf.web.ui.BaseUI;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mkr
 * Date: 4/22/13
 */
public abstract class BaseUIConverter<UI extends BaseUI<ID>, T extends BaseModel<ID>, ID> {
    public abstract T convert(UI source);
    public abstract UI convert(T source);

    public List<T> convertUIList(List<UI> sourceList) {
        if (sourceList == null)
            return null;

        List<T> targetList = new ArrayList<T>();

        for (UI source : sourceList) {
            T target = convert(source);
            if (target != null)
                targetList.add(target);
        }

        return targetList;
    }

    public List<UI> convertModelList(List<T> sourceList) {
        List<UI> targetList = new ArrayList<UI>();

        if (sourceList != null) {
            for (T source : sourceList) {
                UI target = convert(source);
                if (target != null)
                    targetList.add(target);
            }
        }

        return targetList;
    }
}
