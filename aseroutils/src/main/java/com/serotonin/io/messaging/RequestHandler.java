package com.serotonin.io.messaging;

@Deprecated
public abstract interface RequestHandler
{
  public abstract MessageResponse handleRequest(MessageRequest paramMessageRequest)
    throws Exception;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.RequestHandler
 * JD-Core Version:    0.6.2
 */