package com.serotonin.messaging;

public abstract interface OutgoingRequestMessage extends OutgoingMessage
{
  public abstract boolean expectsResponse();

  public abstract WaitingRoomKey getWaitingRoomKey();
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.OutgoingRequestMessage
 * JD-Core Version:    0.6.2
 */