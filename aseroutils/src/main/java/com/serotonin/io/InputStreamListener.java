/*    */ package com.serotonin.io;
/*    */ 
/*    */ import com.serotonin.util.StringUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ @Deprecated
/*    */ public class InputStreamListener extends Thread
/*    */ {
/*    */   //private static final int DEFAULT_READ_DELAY = 50;
/*    */   private final InputStream in;
/*    */   private final DataConsumer consumer;
/*    */   private volatile boolean running;
/* 29 */   private int readDelay = 50;
/*    */ 
/*    */   public InputStreamListener(InputStream in, DataConsumer consumer, String threadName) {
/* 32 */     super(threadName);
/* 33 */     setDaemon(true);
/* 34 */     this.in = in;
/* 35 */     this.consumer = consumer;
/*    */   }
/*    */ 
/*    */   public int getReadDelay() {
/* 39 */     return this.readDelay;
/*    */   }
/*    */ 
/*    */   public void setReadDelay(int readDelay) {
/* 43 */     if (readDelay < 1)
/* 44 */       throw new IllegalArgumentException("readDelay cannot be less than one");
/* 45 */     this.readDelay = readDelay;
/*    */   }
/*    */ 
/*    */   public void startListener() {
/* 49 */     this.running = true;
/* 50 */     start();
/*    */   }
/*    */ 
/*    */   public void stopListener() {
/* 54 */     this.running = false;
/* 55 */     synchronized (this) {
/* 56 */       notify();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 62 */     byte[] buf = new byte[1024];
/*    */     try
/*    */     {
/* 65 */       while (this.running)
/*    */         try {
/* 67 */           if (this.in.available() == 0) {
/* 68 */             synchronized (this) {
/*    */               try {
/* 70 */                 wait(this.readDelay);
/*    */               }
/*    */               catch (InterruptedException e)
/*    */               {
/*    */               }
/*    */             }
/*    */           }
/*    */           else
/*    */           {
/* 79 */             int readcount = this.in.read(buf);
/* 80 */             this.consumer.data(buf, readcount);
/*    */           }
/*    */         } catch (IOException e) {
/* 83 */           if (StringUtils.isEqual(e.getMessage(), "Stream closed."))
/* 84 */             this.running = false;
/* 85 */           this.consumer.handleIOException(e);
/*    */         }
/*    */     }
/*    */     finally
/*    */     {
/* 90 */       this.running = false;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.InputStreamListener
 * JD-Core Version:    0.6.2
 */