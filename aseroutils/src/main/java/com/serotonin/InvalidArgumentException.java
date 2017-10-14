/*    */ package com.serotonin;
/*    */ 
/*    */ public class InvalidArgumentException extends Exception
/*    */ {
/*    */   static final long serialVersionUID = -1L;
/*    */ 
/*    */   public InvalidArgumentException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public InvalidArgumentException(String message, Throwable cause)
/*    */   {
/* 18 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   public InvalidArgumentException(String message) {
/* 22 */     super(message);
/*    */   }
/*    */ 
/*    */   public InvalidArgumentException(Throwable cause) {
/* 26 */     super(cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.InvalidArgumentException
 * JD-Core Version:    0.6.2
 */