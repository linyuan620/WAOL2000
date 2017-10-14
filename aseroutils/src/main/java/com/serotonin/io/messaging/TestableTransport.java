/*    */ package com.serotonin.io.messaging;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ @Deprecated
/*    */ public class TestableTransport extends StreamTransport
/*    */ {
/*    */   public TestableTransport(InputStream in, OutputStream out, String threadName)
/*    */   {
/* 18 */     super(new TestableBufferedInputStream(in), out, threadName);
/*    */   }
/*    */ 
/*    */   public void testInputStream() throws IOException {
/* 22 */     ((TestableBufferedInputStream)this.in).test();
/*    */   }
/*    */ 
/*    */   static class TestableBufferedInputStream extends BufferedInputStream {
/*    */     public TestableBufferedInputStream(InputStream in) {
/* 27 */       super(in);
/*    */     }
/*    */ 
/*    */     public synchronized int read(byte[] buf) throws IOException
/*    */     {
/* 32 */       return super.read(buf);
/*    */     }
/*    */ 
/*    */     public synchronized void test() throws IOException {
/* 36 */       mark(1);
/* 37 */       int i = read();
/* 38 */       if (i == -1)
/* 39 */         throw new IOException("Stream closed");
/* 40 */       reset();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.TestableTransport
 * JD-Core Version:    0.6.2
 */