package com.serotonin.io.messaging;

import com.serotonin.io.DataConsumer;
import java.io.IOException;

@Deprecated
public abstract class Transport
{
  abstract void setConsumer(DataConsumer paramDataConsumer)
    throws IOException;

  abstract void removeConsumer();

  abstract void write(byte[] paramArrayOfByte)
    throws IOException;

  abstract void write(byte[] paramArrayOfByte, int paramInt)
    throws IOException;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.Transport
 * JD-Core Version:    0.6.2
 */