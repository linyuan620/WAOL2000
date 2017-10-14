package com.serotonin.io.messaging;

@Deprecated
public abstract interface MessagingConnectionListener
{
  public abstract void receivedException(Exception paramException);

  public abstract void receivedMessageMismatchException(Exception paramException);

  public abstract void receivedResponseException(Exception paramException);
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.MessagingConnectionListener
 * JD-Core Version:    0.6.2
 */