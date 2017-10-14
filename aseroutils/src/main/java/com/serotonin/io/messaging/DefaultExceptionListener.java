/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ @Deprecated
/*    */ public class DefaultExceptionListener
/*    */   implements MessagingConnectionListener
/*    */ {
/*    */   public void receivedException(Exception e)
/*    */   {
/*  9 */     e.printStackTrace();
/*    */   }
/*    */ 
/*    */   public void receivedMessageMismatchException(Exception e) {
/* 13 */     e.printStackTrace();
/*    */   }
/*    */ 
/*    */   public void receivedResponseException(Exception e) {
/* 17 */     e.printStackTrace();
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.DefaultExceptionListener
 * JD-Core Version:    0.6.2
 */