/*    */ package com.serotonin;
/*    */ 
/*    */ public class ShouldNeverHappenException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */ 
/*    */   public ShouldNeverHappenException(String message)
/*    */   {
/* 14 */     super(message);
/*    */   }
/*    */ 
/*    */   public ShouldNeverHappenException(Throwable cause) {
/* 18 */     super(cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.ShouldNeverHappenException
 * JD-Core Version:    0.6.2
 */