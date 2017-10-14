package com.serotonin.util;

public abstract interface ProgressiveTaskListener
{
  public abstract void progressUpdate(float paramFloat);

  public abstract void taskCancelled();

  public abstract void taskCompleted();
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.util.ProgressiveTaskListener
 * JD-Core Version:    0.6.2
 */