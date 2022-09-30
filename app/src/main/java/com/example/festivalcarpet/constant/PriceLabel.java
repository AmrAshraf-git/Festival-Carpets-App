package com.example.festivalcarpet.constant;

public enum PriceLabel {
    EGYPTIAN_POUND,DOLLAR;

    public String toString() {
        switch(this) {
            case EGYPTIAN_POUND: return "L.E ";
            case DOLLAR: return "$ ";
            default:       return "  ";
        }
    }


}
