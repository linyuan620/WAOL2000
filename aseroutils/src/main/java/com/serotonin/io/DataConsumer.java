package com.serotonin.io;

import java.io.IOException;

@Deprecated
public abstract interface DataConsumer
{
  public abstract void data(byte[] paramArrayOfByte, int paramInt)
    throws IOException;

  public abstract void handleIOException(IOException paramIOException);
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.DataConsumer
 * JD-Core Version:    0.6.2
 */