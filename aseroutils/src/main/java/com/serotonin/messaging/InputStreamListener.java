/*    */ package com.serotonin.messaging;
/*    */ 
/*    */ import com.serotonin.util.StringUtils;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class InputStreamListener
/*    */   implements Runnable
/*    */ {
/*    */   //private static final int DEFAULT_READ_DELAY = 50;
/*    */   private final InputStream in;
/*    */   private final DataConsumer consumer;
/* 20 */   private volatile boolean running = true;
/*    */ 
/* 27 */   private int readDelay = 50;
/*    */ 
/*    */   public InputStreamListener(InputStream in, DataConsumer consumer) {
/* 30 */     this.in = in;
/* 31 */     this.consumer = consumer;
/*    */   }
/*    */ 
/*    */   public int getReadDelay() {
/* 35 */     return this.readDelay;
/*    */   }
/*    */ 
/*    */   public void setReadDelay(int readDelay) {
/* 39 */     if (readDelay < 1)
/* 40 */       throw new IllegalArgumentException("readDelay cannot be less than one");
/* 41 */     this.readDelay = readDelay;
/*    */   }
/*    */ 
/*    */   public void start(String threadName) {
/* 45 */     Thread thread = new Thread(this, threadName);
/* 46 */     thread.setDaemon(true);
/* 47 */     thread.start();
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 51 */     this.running = false;
/* 52 */     synchronized (this) {
/* 53 */       notify();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void run() {
/* 58 */     byte[] buf = new byte[1024];
/*    */     try {
/*    */       while (true)
/* 61 */         if (this.running)
/*    */           try {
/* 63 */             if (this.in.available() == 0) {
/* 64 */               synchronized (this) {
/*    */                 try {
/* 66 */                   wait(this.readDelay);
/*    */                 }
/*    */                 catch (InterruptedException e)
/*    */                 {
/*    */                 }
/*    */               }
/*    */             }
/*    */             else
/*    */             {
/* 75 */               int readcount = this.in.read(buf);
/* 76 */               this.consumer.data(buf, readcount);
/*    */             }
/*    */           } catch (IOException e) {
/* 79 */             if (!StringUtils.isEqual(e.getMessage(), "Stream closed."))
/*    */             {
/* 81 */               this.consumer.handleIOException(e);
/*    */             }
/*    */           }
/*    */     }
/*    */     finally {
/* 86 */       this.running = false;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.InputStreamListener
 * JD-Core Version:    0.6.2
 */