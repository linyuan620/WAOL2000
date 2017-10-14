/*    */ package com.serotonin.util;
/*    */ 
/*    */ public abstract class ProgressiveTask
/*    */   implements Runnable
/*    */ {
/*  7 */   private boolean cancelled = false;
/*  8 */   protected boolean completed = false;
/*    */   private ProgressiveTaskListener listener;
/*    */ 
/*    */   public ProgressiveTask()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ProgressiveTask(ProgressiveTaskListener l)
/*    */   {
/* 16 */     this.listener = l;
/*    */   }
/*    */ 
/*    */   public void cancel() {
/* 20 */     this.cancelled = true;
/*    */   }
/*    */ 
/*    */   public boolean isCancelled() {
/* 24 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   public boolean isCompleted() {
/* 28 */     return this.completed;
/*    */   }
/*    */ 
/*    */   public final void run() {
/*    */     do {
/* 33 */       if (isCancelled()) {
/* 34 */         declareFinished(true);
/* 35 */         break;
/*    */       }
/*    */ 
/* 38 */       runImpl();
/*    */     }
/* 40 */     while (!isCompleted());
/* 41 */     declareFinished(false);
/*    */ 
/* 45 */     this.completed = true;
/*    */   }
/*    */ 
/*    */   protected void declareProgress(float progress) {
/* 49 */     ProgressiveTaskListener l = this.listener;
/* 50 */     if (l != null)
/* 51 */       l.progressUpdate(progress);
/*    */   }
/*    */ 
/*    */   private void declareFinished(boolean cancelled) {
/* 55 */     ProgressiveTaskListener l = this.listener;
/* 56 */     if (l != null)
/* 57 */       if (cancelled)
/* 58 */         l.taskCancelled();
/*    */       else
/* 60 */         l.taskCompleted();
/*    */   }
/*    */ 
/*    */   protected abstract void runImpl();
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.util.ProgressiveTask
 * JD-Core Version:    0.6.2
 */