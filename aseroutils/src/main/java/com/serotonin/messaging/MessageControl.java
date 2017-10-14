/*     */ package com.serotonin.messaging;
/*     */ 
/*     */ import com.serotonin.io.StreamUtils;
/*     */ import com.serotonin.util.queue.ByteQueue;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ public class MessageControl
/*     */   implements DataConsumer
/*     */ {
/*  19 */   private static int DEFAULT_RETRIES = 2;
/*  20 */   private static int DEFAULT_TIMEOUT = 500;
/*     */ 
/*  22 */   public boolean DEBUG = false;
/*     */   private Transport transport;
/*     */   private MessageParser messageParser;
/*     */   private RequestHandler requestHandler;
/*  27 */   private MessagingExceptionHandler exceptionHandler = new DefaultMessagingExceptionHandler();
/*  28 */   private int retries = DEFAULT_RETRIES;
/*  29 */   private int timeout = DEFAULT_TIMEOUT;
/*     */ 
/*  31 */   private final WaitingRoom waitingRoom = new WaitingRoom();
/*  32 */   private final ByteQueue dataBuffer = new ByteQueue();
/*     */ 
/*     */   public void start(Transport transport, MessageParser messageParser, RequestHandler handler) throws IOException {
/*  35 */     this.transport = transport;
/*  36 */     this.messageParser = messageParser;
/*  37 */     this.requestHandler = handler;
/*  38 */     transport.setConsumer(this);
/*     */   }
/*     */ 
/*     */   public void close() {
/*  42 */     this.transport.removeConsumer();
/*     */   }
/*     */ 
/*     */   public void setExceptionHandler(MessagingExceptionHandler exceptionHandler) {
/*  46 */     if (exceptionHandler == null)
/*  47 */       this.exceptionHandler = new DefaultMessagingExceptionHandler();
/*     */     else
/*  49 */       this.exceptionHandler = exceptionHandler;
/*     */   }
/*     */ 
/*     */   public int getRetries() {
/*  53 */     return this.retries;
/*     */   }
/*     */ 
/*     */   public void setRetries(int retries) {
/*  57 */     this.retries = retries;
/*     */   }
/*     */ 
/*     */   public int getTimeout() {
/*  61 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   public void setTimeout(int timeout) {
/*  65 */     this.timeout = timeout;
/*     */   }
/*     */ 
/*     */   public IncomingResponseMessage send(OutgoingRequestMessage request) throws IOException {
/*  69 */     return send(request, this.timeout, this.retries);
/*     */   }
/*     */ 
/*     */   public IncomingResponseMessage send(OutgoingRequestMessage request, int timeout, int retries) throws IOException {
/*  73 */     byte[] data = request.getMessageData();
/*  74 */     if (this.DEBUG) {
/*  75 */       System.out.println("MessagingControl.send: " + StreamUtils.dumpMessage(data));
/*     */     }
/*  77 */     IncomingResponseMessage response = null;
/*     */ 
/*  79 */     if (request.expectsResponse()) {
/*  80 */       WaitingRoomKey key = request.getWaitingRoomKey();
/*     */       try
/*     */       {
/*  84 */         this.waitingRoom.enter(key);
/*     */         do
/*     */         {
/*  88 */           write(data);
/*     */ 
/*  91 */           response = this.waitingRoom.getResponse(key, timeout);
/*     */ 
/*  93 */           if ((this.DEBUG) && (response == null)) {
/*  94 */             System.out.println("Timeout waiting for response");
/*     */           }
/*  96 */           if (response != null) break;  } while (retries-- > 0);
/*     */       }
/*     */       finally
/*     */       {
/* 100 */         this.waitingRoom.leave(key);
/*     */       }
/*     */ 
/* 103 */       if (response == null)
/* 104 */         throw new TimeoutException("request=" + request);
/*     */     }
/*     */     else {
/* 107 */       write(data);
/*     */     }
/* 109 */     return response;
/*     */   }
/*     */ 
/*     */   public void send(OutgoingResponseMessage response) throws IOException {
/* 113 */     write(response.getMessageData());
/*     */   }
/*     */ 
/*     */   public void data(byte[] b, int len)
/*     */   {
/* 120 */     if (this.DEBUG) {
/* 121 */       System.out.println("MessagingConnection.read: " + StreamUtils.dumpMessage(b, 0, len));
/*     */     }
/* 123 */     this.dataBuffer.push(b, 0, len);
/*     */     while (true)
/*     */     {
/*     */       try
/*     */       {
/* 131 */         this.dataBuffer.mark();
/*     */ 
/* 133 */         IncomingMessage message = this.messageParser.parseMessage(this.dataBuffer);
/*     */ 
/* 135 */         if (message == null)
/*     */         {
/* 137 */           this.dataBuffer.reset();
/* 138 */           break;
/*     */         }
/*     */ 
/* 141 */         if ((message instanceof IncomingRequestMessage))
/*     */         {
/* 143 */           if (this.requestHandler != null) {
/* 144 */             OutgoingResponseMessage response = this.requestHandler.handleRequest((IncomingRequestMessage)message);
/*     */ 
/* 147 */             if (response != null) {
/* 148 */               send(response);
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/* 153 */           this.waitingRoom.response((IncomingResponseMessage)message);
/*     */       }
/*     */       catch (Exception e) {
/* 156 */         this.exceptionHandler.receivedException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void write(byte[] data) throws IOException {
/* 162 */     synchronized (this.transport) {
/* 163 */       this.transport.write(data);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void handleIOException(IOException e) {
/* 168 */     this.exceptionHandler.receivedException(e);
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.MessageControl
 * JD-Core Version:    0.6.2
 */