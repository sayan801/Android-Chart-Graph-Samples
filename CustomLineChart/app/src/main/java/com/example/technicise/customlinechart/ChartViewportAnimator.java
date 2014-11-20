package com.example.technicise.customlinechart;


public interface ChartViewportAnimator {

	public static final int FAST_ANIMATION_DURATION = 300;

	public void startAnimation(Viewport startViewport, Viewport targetViewport);

	public void cancelAnimation();

	public boolean isAnimationStarted();

	public void setChartAnimationListener(ChartAnimationListener animationListener);

}
