/*   */ package com.serotonin.messaging;
/*   */ 
/*   */ public class DefaultMessagingExceptionHandler
/*   */   implements MessagingExceptionHandler
/*   */ {
/*   */   public void receivedException(Exception e)
/*   */   {
/* 5 */     e.printStackTrace();
/*   */   }
/*   */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.DefaultMessagingExceptionHandler
 * JD-Core Version:    0.6.2
 */