/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ @Deprecated
/*    */ public class MessageSendException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */ 
/*    */   public MessageSendException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MessageSendException(String message, Throwable cause)
/*    */   {
/* 15 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   public MessageSendException(String message) {
/* 19 */     super(message);
/*    */   }
/*    */ 
/*    */   public MessageSendException(Throwable cause) {
/* 23 */     super(cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.MessageSendException
 * JD-Core Version:    0.6.2
 */