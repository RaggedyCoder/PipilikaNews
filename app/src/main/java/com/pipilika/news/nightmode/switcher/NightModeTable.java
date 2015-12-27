package com.pipilika.news.nightmode.switcher;

import com.asha.nightowllib.NightOwlTable;
import com.asha.nightowllib.handler.annotations.OwlAttr;
import com.asha.nightowllib.handler.annotations.OwlAttrScope;
import com.asha.nightowllib.handler.annotations.OwlStyleable;
import com.pipilika.news.R;
import com.pipilika.news.nightmode.handler.CardViewHandler;
import com.pipilika.news.nightmode.handler.ToolBarHandler;

/**
 * Created by sajid on 12/24/2015.
 */
public class NightModeTable {
    public static final int CardViewScope = 10300;
    public static final int ToolbarScope = 10100;

    @OwlAttrScope(ToolbarScope)
    public interface NightModeToolbar extends NightOwlTable.OwlView {
        @OwlStyleable
        int[] NIGHT_MODE_TOOL_BAR = R.styleable.nightModeToolbar;
        @OwlAttr(ToolBarHandler.TitleTextColorPaint.class)
        int NIGHT_TITLE_TEXT_COLOR = R.styleable.nightModeToolbar_nightTitleTextColor;
        @OwlAttr(ToolBarHandler.PopupThemePaint.class)
        int NIGHT_POPUP_THEME = R.styleable.nightModeToolbar_nightPopupTheme;
    }


    @OwlAttrScope(CardViewScope)
    public interface NightModeCardView {

        @OwlStyleable
        int[] NIGHT_MODE_CARD_VIEW = R.styleable.nightModeCardView;
        @OwlAttr(CardViewHandler.BackgroundPaint.class)
        int NIGHT_CARD_BACKGROUND_COLOR = R.styleable.nightModeCardView_nightCardBackgroundColor;
    }
}
