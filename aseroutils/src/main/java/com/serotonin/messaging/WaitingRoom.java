/*     */ package com.serotonin.messaging;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ class WaitingRoom
/*     */ {
/*     */   private final Map<WaitingRoomKey, Member> waitHere;
/*     */ 
/*     */   WaitingRoom()
/*     */   {
/*  12 */       this.waitHere = new HashMap<>(); //this.waitHere = new HashMap();
/*     */   }
/*     */ 
/*     */   void enter(WaitingRoomKey key)
/*     */     throws WaitingRoomException
/*     */   {
/*  21 */     Member member = new Member();
/*  22 */     synchronized (this) {
/*  23 */       Member dup = (Member)this.waitHere.get(key);
/*  24 */       if (dup != null)
/*  25 */         throw new WaitingRoomException("Waiting room too crowded. Already contains the key " + key);
/*  26 */       this.waitHere.put(key, member);
/*     */     }
/*     */   }
/*     */ 
/*     */   IncomingResponseMessage getResponse(WaitingRoomKey key, long timeout)
/*     */     throws WaitingRoomException
/*     */   {
/*     */     Member member;
/*  33 */     synchronized (this) {
/*  34 */       member = (Member)this.waitHere.get(key);
/*     */     }
/*     */ 
/*  37 */     if (member == null) {
/*  38 */       throw new WaitingRoomException("No member for key " + key);
/*     */     }
/*     */ 
/*  41 */     return member.getResponse(timeout);
/*     */   }
/*     */ 
/*     */   void leave(WaitingRoomKey key)
/*     */   {
/*  46 */     synchronized (this) {
/*  47 */       this.waitHere.remove(key);
/*     */     }
/*     */   }
/*     */ 
/*     */   void response(IncomingResponseMessage response)
/*     */     throws WaitingRoomException
/*     */   {
/*  59 */     WaitingRoomKey key = response.getWaitingRoomKey();
/*     */     Member member;
/*  62 */     synchronized (this) {
/*  63 */       member = (Member)this.waitHere.get(key);
/*     */     }
/*     */ 
/*  66 */     if (member != null)
/*  67 */       member.setResponse(response);
/*     */     else
/*  69 */       throw new WaitingRoomException("No recipient was found waiting for response for key " + key);
/*     */   }
/*     */ 
/*     */   class Member
/*     */   {
/*     */     private IncomingResponseMessage response;
/*     */ 
/*     */     Member()
/*     */     {
/*     */     }
/*     */ 
/*     */     synchronized void setResponse(IncomingResponseMessage response)
/*     */     {
/*  84 */       this.response = response;
/*  85 */       notify();
/*     */     }
/*     */ 
/*     */     synchronized IncomingResponseMessage getResponse(long timeout)
/*     */     {
/*  90 */       if (this.response != null) {
/*  91 */         return this.response;
/*     */       }
/*     */ 
/*  94 */       waitNoThrow(timeout);
/*  95 */       return this.response;
/*     */     }
/*     */ 
/*     */     private void waitNoThrow(long timeout) {
/*     */       try {
/* 100 */         wait(timeout);
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.WaitingRoom
 * JD-Core Version:    0.6.2
 */