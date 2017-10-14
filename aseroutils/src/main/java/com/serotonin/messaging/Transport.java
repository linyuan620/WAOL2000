package com.serotonin.messaging;

import java.io.IOException;

public abstract interface Transport
{
  public abstract void setConsumer(DataConsumer paramDataConsumer)
    throws IOException;

  public abstract void removeConsumer();

  public abstract void write(byte[] paramArrayOfByte)
    throws IOException;

  public abstract void write(byte[] paramArrayOfByte, int paramInt)
    throws IOException;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.Transport
 * JD-Core Version:    0.6.2
 */