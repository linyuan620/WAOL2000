/*     */ package com.serotonin.io.messaging;
/*     */ 
/*     */ import com.serotonin.io.StreamUtils; 
/*     */ import java.io.IOException; 
/*     */ 
/*     */ @Deprecated
/*     */ public class SenderConnection extends MessagingConnection
/*     */ {
/*  18 */   private static int DEFAULT_RETRIES = 2;
/*  19 */   private static int DEFAULT_TIMEOUT = 500;
/*     */ 
/*  21 */   private final Boolean LOCK = new Boolean(false);
/*     */   private byte[] tempData;
/*  24 */   private MessageResponse response = null;
/*  25 */   private ResponseParseException exception = null;
/*  26 */   private int retries = DEFAULT_RETRIES;
/*  27 */   private int timeout = DEFAULT_TIMEOUT;
/*     */ 
/*     */   public int getRetries() {
/*  30 */     return this.retries;
/*     */   }
/*     */ 
/*     */   public void setRetries(int retries) {
/*  34 */     this.retries = retries;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/*  38 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/*  42 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public MessageResponse send(MessageRequest request, int retries, int timeout)
/*     */     throws MessageSendException
/*     */   {
/*  59 */     synchronized (this.LOCK) {
/*  60 */       this.response = null;
/*  61 */       this.exception = null;
/*  62 */       this.dataBuffer.clear();
/*     */       do
/*     */       {
/*  65 */         if (this.DEBUG) {
/*  66 */           System.out.println("MessagingConnection.write: " + StreamUtils.dumpMessage(request.getMessageData()));
/*     */         }
/*     */         try
/*     */         {
/*  70 */           this.transport.write(request.getMessageData());
/*     */         }
/*     */         catch (IOException e) {
/*  73 */           throw new MessageSendException(e);
/*     */         }
/*     */ 
/*  76 */         if (!request.expectsResponse())
/*     */         {
/*  78 */           return null;
/*     */         }
/*  80 */         synchronized (this) {
/*     */           try {
/*  82 */             wait(timeout);
/*     */           }
/*     */           catch (InterruptedException e)
/*     */           {
/*     */           }
/*     */         }
/*     */ 
/*  89 */         if (this.exception != null) {
/*  90 */           throw this.exception;
/*     */         }
/*     */ 
/*  93 */         if (this.response != null)
/*  94 */           request.isValidResponse(this.response);
/*     */       }
/*  96 */       while ((this.response == null) && (retries-- > 0));
/*     */ 
/*  98 */       if (this.response == null) {
/*  99 */         throw new SendTimeoutException();
/*     */       }
/* 101 */       return this.response;
/*     */     }
/*     */   }
/*     */ 
/*     */   public MessageResponse send(MessageRequest request) throws MessageSendException {
/* 106 */     return send(request, this.retries, this.timeout);
/*     */   }
/*     */ 
/*     */   public void data()
/*     */   {
/* 112 */     this.tempData = this.dataBuffer.peekAll();
/*     */     try
/*     */     {
/* 116 */       this.response = this.messageParser.parseResponse(this.dataBuffer);
/*     */ 
/* 118 */       if (this.response == null)
/*     */       {
/* 121 */         this.dataBuffer.clear();
/* 122 */         this.dataBuffer.push(this.tempData);
/*     */       }
/*     */       else
/*     */       {
/* 126 */         synchronized (this) {
/* 127 */           notify();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 132 */       this.exception = new ResponseParseException(e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.SenderConnection
 * JD-Core Version:    0.6.2
 */