package com.degiske.play2.chameleon;

public class ThemeRenderException extends RuntimeException {

    public ThemeRenderException(String s) {
        super(s);
    }

    public ThemeRenderException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
