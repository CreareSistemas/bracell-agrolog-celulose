package br.com.crearesistemas.service.impl.helper;

public interface EventsFenceTrack<T>  {
    void onEnter(T tracking);
    void onExit(T tracking);
    void onStayInside(T tracking);
    void onStayOutside(T tracking);
}
