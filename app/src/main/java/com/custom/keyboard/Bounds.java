package com.custom.keyboard;

class Bounds {
    public int minX;
    public int minY;
    public int maxX;
    public int maxY;

    public int dX;
    public int dY;

    public Bounds(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.dX = Math.abs(this.maxX - this.minX);
        this.dY = Math.abs(this.maxY - this.minY);
    }

    public Bounds(int[] bounds) {
        if (bounds.length < 4) return;
        this.minX = bounds[0];
        this.minY = bounds[1];
        this.maxX = bounds[2];
        this.maxY = bounds[3];

        this.dX = Math.abs(this.maxX - this.minX);
        this.dY = Math.abs(this.maxY - this.minY);
    }

    @Override
    public String toString() {
        return    "minX: "+minX
                +" minY: "+minY
                +" maxX: "+maxX
                +" maxY: "+maxY
                +" dX: "+dX
                +" dY: "+dY
                ;
    }
}