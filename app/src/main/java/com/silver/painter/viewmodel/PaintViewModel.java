package com.silver.painter.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Color;

import com.silver.painter.model.Painter;

public class PaintViewModel extends ViewModel {

    private final MutableLiveData<Painter> painter = new MutableLiveData<>();

    public MutableLiveData<Painter> getPainter() {
        return painter;
    }

    public void setPencilMode() {
        Painter painter = new Painter();
        painter.mode = Painter.Mode.PENCIL;
        painter.width = 20;
        painter.color = Color.BLACK;
        this.painter.setValue(painter);
    }

    public void setEraserMode() {
        Painter painter = new Painter();
        painter.mode = Painter.Mode.ERASER;
        painter.width = 50;
        painter.color = Color.TRANSPARENT;
        this.painter.setValue(painter);
    }
}
