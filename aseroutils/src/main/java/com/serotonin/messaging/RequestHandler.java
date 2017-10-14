package com.serotonin.messaging;

public abstract interface RequestHandler
{
  public abstract OutgoingResponseMessage handleRequest(IncomingRequestMessage paramIncomingRequestMessage)
    throws Exception;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.RequestHandler
 * JD-Core Version:    0.6.2
 */