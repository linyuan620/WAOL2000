/*     */ package com.serotonin.io;
/*     */ 
/*     */ import com.serotonin.util.StringUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ public class StreamUtils
/*     */ {
/*     */   public static void transfer(InputStream in, OutputStream out)
/*     */     throws IOException
/*     */   {
/*  23 */     transfer(in, out, -1L);
/*     */   }
/*     */ 
/*     */   public static void transfer(InputStream in, OutputStream out, long limit) throws IOException {
/*  27 */     byte[] buf = new byte[1024];
/*     */ 
/*  29 */     long total = 0L;
/*     */     int readcount;
/*  30 */     while ((readcount = in.read(buf)) != -1) {
/*  31 */       if ((limit != -1L) && 
/*  32 */         (total + readcount > limit)) {
/*  33 */         readcount = (int)(limit - total);
/*     */       }
/*     */ 
/*  36 */       if (readcount > 0) {
/*  37 */         out.write(buf, 0, readcount);
/*     */       }
/*  39 */       total += readcount;
/*  40 */       if ((limit != -1L) && (total >= limit))
/*  41 */         break;
/*     */     }
/*  43 */     out.flush();
/*     */   }
/*     */ 
/*     */   public static void transfer(InputStream in, SocketChannel out) throws IOException {
/*  47 */     byte[] buf = new byte[1024];
/*  48 */     ByteBuffer bbuf = ByteBuffer.allocate(1024);
/*     */     int len;
/*  50 */     while ((len = in.read(buf)) != -1) {
/*  51 */       bbuf.put(buf, 0, len);
/*  52 */       bbuf.flip();
/*  53 */       while (bbuf.remaining() > 0)
/*  54 */         out.write(bbuf);
/*  55 */       bbuf.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void transfer(Reader reader, Writer writer) throws IOException {
/*  60 */     transfer(reader, writer, -1L);
/*     */   }
/*     */ 
/*     */   public static void transfer(Reader reader, Writer writer, long limit) throws IOException {
/*  64 */     char[] buf = new char[1024];
/*     */ 
/*  66 */     long total = 0L;
/*     */     int readcount;
/*  67 */     while ((readcount = reader.read(buf)) != -1) {
/*  68 */       if ((limit != -1L) && 
/*  69 */         (total + readcount > limit)) {
/*  70 */         readcount = (int)(limit - total);
/*     */       }
/*     */ 
/*  73 */       if (readcount > 0) {
/*  74 */         writer.write(buf, 0, readcount);
/*     */       }
/*  76 */       total += readcount;
/*  77 */       if ((limit != -1L) && (total >= limit))
/*  78 */         break;
/*     */     }
/*  80 */     writer.flush();
/*     */   }
/*     */ 
/*     */   public static byte[] read(InputStream in) throws IOException {
/*  84 */     ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
/*  85 */     transfer(in, out);
/*  86 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */   public static char[] read(Reader reader) throws IOException {
/*  90 */     CharArrayWriter writer = new CharArrayWriter();
/*  91 */     transfer(reader, writer);
/*  92 */     return writer.toCharArray();
/*     */   }
/*     */ 
/*     */   public static char readChar(InputStream in) throws IOException {
/*  96 */     return (char)in.read();
/*     */   }
/*     */ 
/*     */   public static String readString(InputStream in, int length) throws IOException {
/* 100 */     StringBuilder sb = new StringBuilder(length);
/* 101 */     for (int i = 0; i < length; i++)
/* 102 */       sb.append(readChar(in));
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static byte readByte(InputStream in) throws IOException {
/* 107 */     return (byte)in.read();
/*     */   }
/*     */ 
/*     */   public static int read4ByteSigned(InputStream in) throws IOException {
/* 111 */     return in.read() | in.read() << 8 | in.read() << 16 | in.read() << 24;
/*     */   }
/*     */ 
/*     */   public static long read4ByteUnsigned(InputStream in) throws IOException {
/* 115 */     return in.read() | in.read() << 8 | in.read() << 16 | in.read() << 24;
/*     */   }
/*     */ 
/*     */   public static int read2ByteUnsigned(InputStream in) throws IOException {
/* 119 */     return in.read() | in.read() << 8;
/*     */   }
/*     */ 
/*     */   public static short read2ByteSigned(InputStream in) throws IOException {
/* 123 */     return (short)(in.read() | in.read() << 8);
/*     */   }
/*     */ 
/*     */   public static void writeByte(OutputStream out, byte b) throws IOException {
/* 127 */     out.write(b);
/*     */   }
/*     */ 
/*     */   public static void writeChar(OutputStream out, char c) throws IOException {
/* 131 */     out.write((byte)c);
/*     */   }
/*     */ 
/*     */   public static void writeString(OutputStream out, String s) throws IOException {
/* 135 */     for (int i = 0; i < s.length(); i++)
/* 136 */       writeChar(out, s.charAt(i));
/*     */   }
/*     */ 
/*     */   public static void write4ByteSigned(OutputStream out, int i) throws IOException {
/* 140 */     out.write((byte)(i & 0xFF));
/* 141 */     out.write((byte)(i >> 8 & 0xFF));
/* 142 */     out.write((byte)(i >> 16 & 0xFF));
/* 143 */     out.write((byte)(i >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static void write4ByteUnsigned(OutputStream out, long l) throws IOException {
/* 147 */     out.write((byte)(int)(l & 0xFF));
/* 148 */     out.write((byte)(int)(l >> 8 & 0xFF));
/* 149 */     out.write((byte)(int)(l >> 16 & 0xFF));
/* 150 */     out.write((byte)(int)(l >> 24 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static void write2ByteUnsigned(OutputStream out, int i) throws IOException {
/* 154 */     out.write((byte)(i & 0xFF));
/* 155 */     out.write((byte)(i >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static void write2ByteSigned(OutputStream out, short s) throws IOException {
/* 159 */     out.write((byte)(s & 0xFF));
/* 160 */     out.write((byte)(s >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */   public static String dumpArray(byte[] b) {
/* 164 */     return dumpArray(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public static String dumpArray(byte[] b, int pos, int len) {
/* 168 */     StringBuilder sb = new StringBuilder();
/* 169 */     sb.append('[');
/* 170 */     for (int i = pos; i < len; i++) {
/* 171 */       if (i > 0)
/* 172 */         sb.append(",");
/* 173 */       sb.append(b[i]);
/*     */     }
/* 175 */     sb.append(']');
/* 176 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String dumpMessage(byte[] b) {
/* 180 */     return dumpMessage(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public static String dumpMessage(byte[] b, int pos, int len) {
/* 184 */     StringBuilder sb = new StringBuilder();
/* 185 */     sb.append('[');
/* 186 */     for (int i = pos; i < len; i++) {
/* 187 */       switch (b[i]) {
/*     */       case 2:
/* 189 */         sb.append("&STX;");
/* 190 */         break;
/*     */       case 3:
/* 192 */         sb.append("&ETX;");
/* 193 */         break;
/*     */       case 27:
/* 195 */         sb.append("&ESC;");
/* 196 */         break;
/*     */       default:
/* 198 */         sb.append((char)b[i]);
/*     */       }
/*     */     }
/* 201 */     sb.append(']');
/* 202 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String dumpArrayHex(byte[] b) {
/* 206 */     return dumpArrayHex(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public static String dumpArrayHex(byte[] b, int pos, int len) {
/* 210 */     StringBuilder sb = new StringBuilder();
/* 211 */     sb.append('[');
/* 212 */     for (int i = pos; i < len; i++) {
/* 213 */       if (i > 0)
/* 214 */         sb.append(",");
/* 215 */       sb.append(Integer.toHexString(b[i] & 0xFF));
/*     */     }
/* 217 */     sb.append(']');
/* 218 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String dumpHex(byte[] b) {
/* 222 */     return dumpHex(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public static String dumpHex(byte[] b, int pos, int len) {
/* 226 */     StringBuilder sb = new StringBuilder();
/* 227 */     for (int i = pos; i < len; i++)
/* 228 */       sb.append(StringUtils.pad(Integer.toHexString(b[i] & 0xFF), '0', 2));
/* 229 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String readFile(String filename) throws IOException {
/* 233 */     FileReader in = null;
/*     */     try {
/* 235 */       in = new FileReader(filename);
/* 236 */       StringWriter out = new StringWriter();
/* 237 */       transfer(in, out);
/* 238 */       return out.toString();
/*     */     }
/*     */     finally {
/* 241 */       if (in != null)
/* 242 */         in.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void readLines(String filename, LineHandler lineHandler) throws IOException {
/* 247 */     BufferedReader in = null;
/*     */     try {
/* 249 */       in = new BufferedReader(new FileReader(filename));
/*     */       String line;
/* 251 */       while ((line = in.readLine()) != null)
/* 252 */         lineHandler.handleLine(line);
/* 253 */       lineHandler.done();
/*     */     }
/*     */     finally {
/* 256 */       if (in != null)
/* 257 */         in.close();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.StreamUtils
 * JD-Core Version:    0.6.2
 */