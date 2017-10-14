/*    */ package com.serotonin.messaging;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class TestableTransport extends StreamTransport
/*    */ {
/*    */   public TestableTransport(InputStream in, OutputStream out)
/*    */   {
/* 15 */     super(new TestableBufferedInputStream(in), out);
/*    */   }
/*    */ 
/*    */   public void testInputStream() throws IOException {
/* 19 */     ((TestableBufferedInputStream)this.in).test();
/*    */   }
/*    */ 
/*    */   static class TestableBufferedInputStream extends BufferedInputStream {
/*    */     public TestableBufferedInputStream(InputStream in) {
/* 24 */       super(in);
/*    */     }
/*    */ 
/*    */     public synchronized int read(byte[] buf) throws IOException
/*    */     {
/* 29 */       return super.read(buf);
/*    */     }
/*    */ 
/*    */     public synchronized void test() throws IOException {
/* 33 */       mark(1);
/* 34 */       int i = read();
/* 35 */       if (i == -1)
/* 36 */         throw new IOException("Stream closed");
/* 37 */       reset();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.TestableTransport
 * JD-Core Version:    0.6.2
 */