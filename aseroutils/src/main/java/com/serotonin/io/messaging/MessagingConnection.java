/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ import com.serotonin.io.DataConsumer;
/*    */ import com.serotonin.io.StreamUtils;
/*    */ import com.serotonin.util.queue.ByteQueue;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ 
/*    */ @Deprecated
/*    */ public abstract class MessagingConnection
/*    */   implements DataConsumer
/*    */ {
/* 20 */   public boolean DEBUG = false;
/*    */ 
/* 22 */   protected ByteQueue dataBuffer = new ByteQueue();
/*    */   protected Transport transport;
/*    */   protected MessageParser messageParser;
/* 25 */   private final List<MessagingConnectionListener> listeners = new CopyOnWriteArrayList<>();
/*    */ 
/*    */   public void start(Transport transport, MessageParser messageParser) throws IOException {
/* 28 */     this.transport = transport;
/* 29 */     this.messageParser = messageParser;
/* 30 */     this.transport.setConsumer(this);
/*    */   }
/*    */ 
/*    */   public void addListener(MessagingConnectionListener l) {
/* 34 */     this.listeners.add(l);
/*    */   }
/*    */ 
/*    */   public void removeListener(MessagingConnectionListener l) {
/* 38 */     this.listeners.remove(l);
/*    */   }
/*    */ 
/*    */   public void close() {
/* 42 */     this.transport.removeConsumer();
/*    */   }
/*    */ 
/*    */   public void data(byte[] b, int len)
/*    */   {
/* 49 */     if (this.DEBUG) {
/* 50 */       System.out.println("MessagingConnection.read: " + StreamUtils.dumpMessage(b, 0, len));
/*    */     }
/* 52 */     this.dataBuffer.push(b, 0, len);
/*    */     try
/*    */     {
/* 56 */       data();
/*    */     }
/*    */     catch (Exception e) {
/* 59 */       handleException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected abstract void data() throws Exception;
/*    */ 
/*    */   public void handleMessageMismatchException(MessageMismatchException e) {
/* 66 */     for (int i = 0; i < this.listeners.size(); i++)
/* 67 */       ((MessagingConnectionListener)this.listeners.get(i)).receivedMessageMismatchException(e);
/*    */   }
/*    */ 
/*    */   public void handleResponseException(Exception e) {
/* 71 */     for (int i = 0; i < this.listeners.size(); i++)
/* 72 */       ((MessagingConnectionListener)this.listeners.get(i)).receivedResponseException(e);
/*    */   }
/*    */ 
/*    */   public void handleException(Exception e) {
/* 76 */     for (int i = 0; i < this.listeners.size(); i++)
/* 77 */       ((MessagingConnectionListener)this.listeners.get(i)).receivedException(e);
/*    */   }
/*    */ 
/*    */   public void handleIOException(IOException e) {
/* 81 */     for (int i = 0; i < this.listeners.size(); i++)
/* 82 */       ((MessagingConnectionListener)this.listeners.get(i)).receivedException(e);
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.MessagingConnection
 * JD-Core Version:    0.6.2
 */