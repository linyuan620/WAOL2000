/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ListenerConnection extends MessagingConnection
/*    */ {
/*    */   private byte[] tempData;
/*    */   private MessageRequest request;
/*    */   private MessageResponse response;
/*    */   private final RequestHandler handler;
/*    */ 
/*    */   public ListenerConnection(RequestHandler requestHandler)
/*    */   {
/* 19 */     this.handler = requestHandler;
/*    */   }
/*    */ 
/*    */   public void data()
/*    */     throws Exception
/*    */   {
/*    */     while (true)
/*    */     {
/* 27 */       this.tempData = this.dataBuffer.peekAll();
/*    */ 
/* 30 */       this.request = this.messageParser.parseRequest(this.dataBuffer);
/*    */ 
/* 32 */       if (this.request == null)
/*    */       {
/* 35 */         this.dataBuffer.popAll();
/* 36 */         this.dataBuffer.push(this.tempData);
/*    */       }
/*    */       else
/*    */       {
/*    */         try
/*    */         {
/* 44 */           this.response = this.handler.handleRequest(this.request);
/*    */           try
/*    */           {
/* 47 */             if (this.response != null)
/* 48 */               this.transport.write(this.response.getMessageData());
/*    */           }
/*    */           catch (Exception e) {
/* 51 */             handleException(e);
/*    */           }
/*    */         }
/*    */         catch (Exception e) {
/* 55 */           handleResponseException(e);
/*    */         }
/*    */ 
/* 58 */         if (this.dataBuffer.size() == 0)
/* 59 */           break;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.ListenerConnection
 * JD-Core Version:    0.6.2
 */