/*    */ package com.serotonin.io.serial;
/*    */ 
/*    */ public class SerialParameters
/*    */ {
/*    */   private String commPortId;
/*    */   private String portOwnerName;
/* 12 */   private int baudRate = -1;
/* 13 */   private int flowControlIn = 0;
/* 14 */   private int flowControlOut = 0;
/* 15 */   private int dataBits = 8;
/* 16 */   private int stopBits = 1;
/* 17 */   private int parity = 0;
/*    */ 
/*    */   public int getBaudRate() {
/* 20 */     return this.baudRate;
/*    */   }
/*    */   public void setBaudRate(int baudRate) {
/* 23 */     this.baudRate = baudRate;
/*    */   }
/*    */   public String getCommPortId() {
/* 26 */     return this.commPortId;
/*    */   }
/*    */   public void setCommPortId(String commPortId) {
/* 29 */     this.commPortId = commPortId;
/*    */   }
/*    */   public int getDataBits() {
/* 32 */     return this.dataBits;
/*    */   }
/*    */   public void setDataBits(int dataBits) {
/* 35 */     this.dataBits = dataBits;
/*    */   }
/*    */   public int getFlowControlIn() {
/* 38 */     return this.flowControlIn;
/*    */   }
/*    */   public void setFlowControlIn(int flowControlIn) {
/* 41 */     this.flowControlIn = flowControlIn;
/*    */   }
/*    */   public int getFlowControlOut() {
/* 44 */     return this.flowControlOut;
/*    */   }
/*    */   public void setFlowControlOut(int flowControlOut) {
/* 47 */     this.flowControlOut = flowControlOut;
/*    */   }
/*    */   public int getParity() {
/* 50 */     return this.parity;
/*    */   }
/*    */   public void setParity(int parity) {
/* 53 */     this.parity = parity;
/*    */   }
/*    */   public int getStopBits() {
/* 56 */     return this.stopBits;
/*    */   }
/*    */   public void setStopBits(int stopBits) {
/* 59 */     this.stopBits = stopBits;
/*    */   }
/*    */   public String getPortOwnerName() {
/* 62 */     return this.portOwnerName;
/*    */   }
/*    */   public void setPortOwnerName(String portOwnerName) {
/* 65 */     this.portOwnerName = portOwnerName;
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.serial.SerialParameters
 * JD-Core Version:    0.6.2
 */