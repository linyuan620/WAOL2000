package com.serotonin.messaging;

import java.io.IOException;

public abstract interface DataConsumer
{
  public abstract void data(byte[] paramArrayOfByte, int paramInt);

  public abstract void handleIOException(IOException paramIOException);
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.DataConsumer
 * JD-Core Version:    0.6.2
 */