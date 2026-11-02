package dev.nova.client;

public final class NovaState {
    private NovaState() {}

    public static boolean sprintToggled = false;
    public static boolean zoomHeld = false;
    public static int cps = 0;
    public static int clickCount = 0;
    public static long cpsWindowStart = System.currentTimeMillis();
}
