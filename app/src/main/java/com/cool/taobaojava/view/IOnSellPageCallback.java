package com.cool.taobaojava.view;

import com.cool.taobaojava.base.IBaseCallback;
import com.cool.taobaojava.model.domain.OnSellContent;

public interface IOnSellPageCallback extends IBaseCallback {
    void onContentLoadSuccess(OnSellContent result);

    void onMoreLoaded(OnSellContent result);

    void onMoreLoadedError();

    void onMoreLoadedEmpty();

}
