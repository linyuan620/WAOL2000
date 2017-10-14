/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ import com.serotonin.io.DataConsumer;
/*    */ import com.serotonin.io.InputStreamListener;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ @Deprecated
/*    */ public class StreamTransport extends Transport
/*    */ {
/*    */   protected OutputStream out;
/*    */   protected InputStream in;
/*    */   private InputStreamListener listener;
/*    */   private final String threadName;
/*    */ 
/*    */   public StreamTransport(InputStream in, OutputStream out, String threadName)
/*    */   {
/* 21 */     this.out = out;
/* 22 */     this.in = in;
/* 23 */     this.threadName = threadName;
/*    */   }
/*    */ 
/*    */   public void setReadDelay(int readDelay) {
/* 27 */     if (this.listener != null)
/* 28 */       this.listener.setReadDelay(readDelay);
/*    */   }
/*    */ 
/*    */   void setConsumer(DataConsumer consumer)
/*    */   {
/* 38 */     this.listener = new InputStreamListener(this.in, consumer, this.threadName);
/* 39 */     this.listener.startListener();
/*    */   }
/*    */ 
/*    */   void removeConsumer()
/*    */   {
/* 44 */     if (this.listener != null)
/* 45 */       this.listener.stopListener();
/*    */   }
/*    */ 
/*    */   void write(byte[] data) throws IOException
/*    */   {
/* 50 */     this.out.write(data);
/* 51 */     this.out.flush();
/*    */   }
/*    */ 
/*    */   void write(byte[] data, int len) throws IOException
/*    */   {
/* 56 */     this.out.write(data, 0, len);
/* 57 */     this.out.flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.StreamTransport
 * JD-Core Version:    0.6.2
 */