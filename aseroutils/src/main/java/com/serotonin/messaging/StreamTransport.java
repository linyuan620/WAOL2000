/*    */ package com.serotonin.messaging;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class StreamTransport
/*    */   implements Transport, Runnable
/*    */ {
/*    */   protected OutputStream out;
/*    */   protected InputStream in;
/*    */   private InputStreamListener listener;
/*    */ 
/*    */   public StreamTransport(InputStream in, OutputStream out)
/*    */   {
/* 22 */     this.out = out;
/* 23 */     this.in = in;
/*    */   }
/*    */ 
/*    */   public void setReadDelay(int readDelay) {
/* 27 */     if (this.listener != null)
/* 28 */       this.listener.setReadDelay(readDelay);
/*    */   }
/*    */ 
/*    */   public void start(String threadName) {
/* 32 */     this.listener.start(threadName);
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 36 */     this.listener.stop();
/*    */   }
/*    */ 
/*    */   public void run() {
/* 40 */     this.listener.run();
/*    */   }
/*    */ 
/*    */   public void setConsumer(DataConsumer consumer) {
/* 44 */     this.listener = new InputStreamListener(this.in, consumer);
/*    */   }
/*    */ 
/*    */   public void removeConsumer() {
/* 48 */     this.listener.stop();
/* 49 */     this.listener = null;
/*    */   }
/*    */ 
/*    */   public void write(byte[] data) throws IOException {
/* 53 */     this.out.write(data);
/* 54 */     this.out.flush();
/*    */   }
/*    */ 
/*    */   public void write(byte[] data, int len) throws IOException {
/* 58 */     this.out.write(data, 0, len);
/* 59 */     this.out.flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.StreamTransport
 * JD-Core Version:    0.6.2
 */