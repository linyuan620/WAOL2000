package com.serotonin.io.messaging;

@Deprecated
public abstract interface MessageRequest extends Message
{
  public abstract boolean expectsResponse();

  public abstract void isValidResponse(MessageResponse paramMessageResponse)
    throws MessageMismatchException;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.MessageRequest
 * JD-Core Version:    0.6.2
 */