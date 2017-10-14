/*     */ package com.serotonin.util.queue;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ public class ByteQueue
/*     */   implements Cloneable
/*     */ {
/*     */   private byte[] queue;
/*  13 */   private int head = -1;
/*  14 */   private int tail = 0;
/*  15 */   private int size = 0;
/*     */   private int markHead;
/*     */   private int markTail;
/*     */   private int markSize;
/*     */ 
/*     */   public ByteQueue()
/*     */   {
/*  22 */     this(1024);
/*     */   }
/*     */ 
/*     */   public ByteQueue(int initialLength) {
/*  26 */     this.queue = new byte[initialLength];
/*     */   }
/*     */ 
/*     */   public ByteQueue(byte[] b) {
/*  30 */     this(b.length);
/*  31 */     push(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public ByteQueue(byte[] b, int pos, int length) {
/*  35 */     this(length);
/*  36 */     push(b, pos, length);
/*     */   }
/*     */ 
/*     */   public void push(byte b) {
/*  40 */     if (room() == 0) {
/*  41 */       expand();
/*     */     }
/*  43 */     this.queue[this.tail] = b;
/*     */ 
/*  45 */     if (this.head == -1)
/*  46 */       this.head = 0;
/*  47 */     this.tail = ((this.tail + 1) % this.queue.length);
/*  48 */     this.size += 1;
/*     */   }
/*     */ 
/*     */   public void push(int i) {
/*  52 */     push((byte)i);
/*     */   }
/*     */ 
/*     */   public void push(long l) {
/*  56 */     push((byte)(int)l);
/*     */   }
/*     */ 
/*     */   public void pushU2B(int i) {
/*  60 */     push((byte)(i >> 8));
/*  61 */     push((byte)i);
/*     */   }
/*     */ 
/*     */   public void pushU3B(int i) {
/*  65 */     push((byte)(i >> 16));
/*  66 */     push((byte)(i >> 8));
/*  67 */     push((byte)i);
/*     */   }
/*     */ 
/*     */   public void pushS4B(int i) {
/*  71 */     push((byte)(i >> 24));
/*  72 */     push((byte)(i >> 16));
/*  73 */     push((byte)(i >> 8));
/*  74 */     push((byte)i);
/*     */   }
/*     */ 
/*     */   public void pushU4B(long l) {
/*  78 */     push((byte)(int)(l >> 24));
/*  79 */     push((byte)(int)(l >> 16));
/*  80 */     push((byte)(int)(l >> 8));
/*  81 */     push((byte)(int)l);
/*     */   }
/*     */ 
/*     */   public void read(InputStream in, int length) throws IOException {
/*  85 */     if (length == 0) {
/*  86 */       return;
/*     */     }
/*  88 */     while (room() < length) {
/*  89 */       expand();
/*     */     }
/*  91 */     int tailLength = this.queue.length - this.tail;
/*  92 */     if (tailLength > length)
/*  93 */       readImpl(in, this.tail, length);
/*     */     else {
/*  95 */       readImpl(in, this.tail, tailLength);
/*     */     }
/*  97 */     if (length > tailLength) {
/*  98 */       readImpl(in, 0, length - tailLength);
/*     */     }
/* 100 */     if (this.head == -1)
/* 101 */       this.head = 0;
/* 102 */     this.tail = ((this.tail + length) % this.queue.length);
/* 103 */     this.size += length;
/*     */   }
/*     */ 
/*     */   private void readImpl(InputStream in, int offset, int length) throws IOException
/*     */   {
/* 108 */     while (length > 0) {
/* 109 */       int readcount = in.read(this.queue, offset, length);
/* 110 */       offset += readcount;
/* 111 */       length -= readcount;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void push(byte[] b) {
/* 116 */     push(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void push(byte[] b, int pos, int length) {
/* 120 */     if (length == 0) {
/* 121 */       return;
/*     */     }
/* 123 */     while (room() < length) {
/* 124 */       expand();
/*     */     }
/* 126 */     int tailLength = this.queue.length - this.tail;
/* 127 */     if (tailLength > length)
/* 128 */       System.arraycopy(b, pos, this.queue, this.tail, length);
/*     */     else {
/* 130 */       System.arraycopy(b, pos, this.queue, this.tail, tailLength);
/*     */     }
/* 132 */     if (length > tailLength) {
/* 133 */       System.arraycopy(b, tailLength + pos, this.queue, 0, length - tailLength);
/*     */     }
/* 135 */     if (this.head == -1)
/* 136 */       this.head = 0;
/* 137 */     this.tail = ((this.tail + length) % this.queue.length);
/* 138 */     this.size += length;
/*     */   }
/*     */ 
/*     */   public void push(ByteQueue source) {
/* 142 */     if (source.size == 0) {
/* 143 */       return;
/*     */     }
/* 145 */     if (source == this) {
/* 146 */       source = (ByteQueue)clone();
/*     */     }
/* 148 */     int firstCopyLen = source.queue.length - source.head;
/* 149 */     if (source.size < firstCopyLen)
/* 150 */       firstCopyLen = source.size;
/* 151 */     push(source.queue, source.head, firstCopyLen);
/*     */ 
/* 153 */     if (firstCopyLen < source.size)
/* 154 */       push(source.queue, 0, source.tail);
/*     */   }
/*     */ 
/*     */   public void mark() {
/* 158 */     this.markHead = this.head;
/* 159 */     this.markTail = this.tail;
/* 160 */     this.markSize = this.size;
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 164 */     this.head = this.markHead;
/* 165 */     this.tail = this.markTail;
/* 166 */     this.size = this.markSize;
/*     */   }
/*     */ 
/*     */   public byte pop() {
/* 170 */     byte retval = this.queue[this.head];
/*     */ 
/* 172 */     if (this.size == 1) {
/* 173 */       this.head = -1;
/* 174 */       this.tail = 0;
/*     */     }
/*     */     else {
/* 177 */       this.head = ((this.head + 1) % this.queue.length);
/*     */     }
/* 179 */     this.size -= 1;
/*     */ 
/* 181 */     return retval;
/*     */   }
/*     */ 
/*     */   public int popU1B() {
/* 185 */     return pop() & 0xFF;
/*     */   }
/*     */ 
/*     */   public int popU2B() {
/* 189 */     return (pop() & 0xFF) << 8 | pop() & 0xFF;
/*     */   }
/*     */ 
/*     */   public int popU3B() {
/* 193 */     return (pop() & 0xFF) << 16 | (pop() & 0xFF) << 8 | pop() & 0xFF;
/*     */   }
/*     */ 
/*     */   public int popS4B() {
/* 197 */     return (pop() & 0xFF) << 24 | (pop() & 0xFF) << 16 | (pop() & 0xFF) << 8 | pop() & 0xFF;
/*     */   }
/*     */ 
/*     */   public long popU4B() {
/* 201 */     return (pop() & 0xFF) << 24 | (pop() & 0xFF) << 16 | (pop() & 0xFF) << 8 | pop() & 0xFF;
/*     */   }
/*     */ 
/*     */   public int pop(byte[] buf)
/*     */   {
/* 206 */     return pop(buf, 0, buf.length);
/*     */   }
/*     */ 
/*     */   public int pop(byte[] buf, int pos, int length) {
/* 210 */     length = peek(buf, pos, length);
/*     */ 
/* 212 */     this.size -= length;
/*     */ 
/* 214 */     if (this.size == 0) {
/* 215 */       this.head = -1;
/* 216 */       this.tail = 0;
/*     */     }
/*     */     else {
/* 219 */       this.head = ((this.head + length) % this.queue.length);
/*     */     }
/* 221 */     return length;
/*     */   }
/*     */ 
/*     */   public int pop(int length) {
/* 225 */     if (length == 0)
/* 226 */       return 0;
/* 227 */     if (this.size == 0) {
/* 228 */       throw new ArrayIndexOutOfBoundsException(-1);
/*     */     }
/* 230 */     if (length > this.size) {
/* 231 */       length = this.size;
/*     */     }
/* 233 */     this.size -= length;
/*     */ 
/* 235 */     if (this.size == 0) {
/* 236 */       this.head = -1;
/* 237 */       this.tail = 0;
/*     */     }
/*     */     else {
/* 240 */       this.head = ((this.head + length) % this.queue.length);
/*     */     }
/* 242 */     return length;
/*     */   }
/*     */ 
/*     */   public String popString(int length, Charset charset) {
/* 246 */     byte[] b = new byte[length];
/* 247 */     pop(b);
/* 248 */     return new String(b, charset);
/*     */   }
/*     */ 
/*     */   public byte[] popAll() {
/* 252 */     byte[] data = new byte[this.size];
/* 253 */     pop(data, 0, data.length);
/* 254 */     return data;
/*     */   }
/*     */ 
/*     */   public void write(OutputStream out) throws IOException {
/* 258 */     write(out, this.size);
/*     */   }
/*     */ 
/*     */   public void write(OutputStream out, int length) throws IOException {
/* 262 */     if (length == 0)
/* 263 */       return;
/* 264 */     if (this.size == 0) {
/* 265 */       throw new ArrayIndexOutOfBoundsException(-1);
/*     */     }
/* 267 */     if (length > this.size) {
/* 268 */       length = this.size;
/*     */     }
/* 270 */     int firstCopyLen = this.queue.length - this.head;
/* 271 */     if (length < firstCopyLen) {
/* 272 */       firstCopyLen = length;
/*     */     }
/* 274 */     out.write(this.queue, this.head, firstCopyLen);
/* 275 */     if (firstCopyLen < length) {
/* 276 */       out.write(this.queue, 0, length - firstCopyLen);
/*     */     }
/* 278 */     this.size -= length;
/*     */ 
/* 280 */     if (this.size == 0) {
/* 281 */       this.head = -1;
/* 282 */       this.tail = 0;
/*     */     }
/*     */     else {
/* 285 */       this.head = ((this.head + length) % this.queue.length);
/*     */     }
/*     */   }
/*     */ 
/* 289 */   public byte tailPop() { if (this.size == 0) {
/* 290 */       throw new ArrayIndexOutOfBoundsException(-1);
/*     */     }
/* 292 */     this.tail = ((this.tail + this.queue.length - 1) % this.queue.length);
/* 293 */     byte retval = this.queue[this.tail];
/*     */ 
/* 295 */     if (this.size == 1) {
/* 296 */       this.head = -1;
/* 297 */       this.tail = 0;
/*     */     }
/*     */ 
/* 300 */     this.size -= 1;
/*     */ 
/* 302 */     return retval; }
/*     */ 
/*     */   public byte peek(int index)
/*     */   {
/* 306 */     if (index >= this.size) {
/* 307 */       throw new IllegalArgumentException("index " + index + " is >= queue size " + this.size);
/*     */     }
/* 309 */     index = (index + this.head) % this.queue.length;
/* 310 */     return this.queue[index];
/*     */   }
/*     */ 
/*     */   public byte[] peek(int index, int length) {
/* 314 */     byte[] result = new byte[length];
/*     */ 
/* 316 */     for (int i = 0; i < length; i++)
/* 317 */       result[i] = peek(index + i);
/* 318 */     return result;
/*     */   }
/*     */ 
/*     */   public byte[] peekAll() {
/* 322 */     byte[] data = new byte[this.size];
/* 323 */     peek(data, 0, data.length);
/* 324 */     return data;
/*     */   }
/*     */ 
/*     */   public int peek(byte[] buf) {
/* 328 */     return peek(buf, 0, buf.length);
/*     */   }
/*     */ 
/*     */   public int peek(byte[] buf, int pos, int length) {
/* 332 */     if (length == 0)
/* 333 */       return 0;
/* 334 */     if (this.size == 0) {
/* 335 */       throw new ArrayIndexOutOfBoundsException(-1);
/*     */     }
/* 337 */     if (length > this.size) {
/* 338 */       length = this.size;
/*     */     }
/* 340 */     int firstCopyLen = this.queue.length - this.head;
/* 341 */     if (length < firstCopyLen) {
/* 342 */       firstCopyLen = length;
/*     */     }
/* 344 */     System.arraycopy(this.queue, this.head, buf, pos, firstCopyLen);
/* 345 */     if (firstCopyLen < length) {
/* 346 */       System.arraycopy(this.queue, 0, buf, pos + firstCopyLen, length - firstCopyLen);
/*     */     }
/* 348 */     return length;
/*     */   }
/*     */ 
/*     */   public int indexOf(byte b) {
/* 352 */     return indexOf(b, 0);
/*     */   }
/*     */ 
/*     */   public int indexOf(byte b, int start) {
/* 356 */     if (start >= this.size) {
/* 357 */       return -1;
/*     */     }
/* 359 */     int index = (this.head + start) % this.queue.length;
/* 360 */     for (int i = start; i < this.size; i++) {
/* 361 */       if (this.queue[index] == b)
/* 362 */         return i;
/* 363 */       index = (index + 1) % this.queue.length;
/*     */     }
/* 365 */     return -1;
/*     */   }
/*     */ 
/*     */   public int indexOf(byte[] b) {
/* 369 */     return indexOf(b, 0);
/*     */   }
/*     */ 
/*     */   public int indexOf(byte[] b, int start) {
/* 373 */     if ((b == null) || (b.length == 0)) {
/* 374 */       throw new IllegalArgumentException("cannot search for empty values");
/*     */     }
/* 376 */     while (((start = indexOf(b[0], start)) != -1) && (start < this.size - b.length + 1)) {
/* 377 */       boolean found = true;
/* 378 */       for (int i = 1; i < b.length; i++) {
/* 379 */         if (peek(start + i) != b[i]) {
/* 380 */           found = false;
/* 381 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 385 */       if (found) {
/* 386 */         return start;
/*     */       }
/*     */ 
/* 389 */       start++;
/*     */     }
/*     */ 
/* 392 */     return -1;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 396 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 400 */     this.size = 0;
/* 401 */     this.head = -1;
/* 402 */     this.tail = 0;
/*     */   }
/*     */ 
/*     */   private int room() {
/* 406 */     return this.queue.length - this.size;
/*     */   }
/*     */ 
/*     */   private void expand() {
/* 410 */     byte[] newb = new byte[this.queue.length * 2];
/*     */ 
/* 412 */     if (this.head == -1) {
/* 413 */       this.queue = newb;
/* 414 */       return;
/*     */     }
/*     */ 
/* 417 */     if (this.tail > this.head) {
/* 418 */       System.arraycopy(this.queue, this.head, newb, this.head, this.tail - this.head);
/* 419 */       this.queue = newb;
/* 420 */       return;
/*     */     }
/*     */ 
/* 423 */     System.arraycopy(this.queue, this.head, newb, this.head + this.queue.length, this.queue.length - this.head);
/* 424 */     System.arraycopy(this.queue, 0, newb, 0, this.tail);
/* 425 */     this.head += this.queue.length;
/* 426 */     this.queue = newb;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try {
/* 432 */       ByteQueue clone = (ByteQueue)super.clone();
/*     */ 
/* 434 */       clone.queue = ((byte[])this.queue.clone());
/* 435 */       return clone;
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/*     */     }
/* 439 */     return null;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 444 */     if (this.size == 0) {
/* 445 */       return "[]";
/*     */     }
/* 447 */     StringBuffer sb = new StringBuffer();
/* 448 */     sb.append('[');
/* 449 */     sb.append(Integer.toHexString(peek(0) & 0xFF));
/* 450 */     for (int i = 1; i < this.size; i++)
/* 451 */       sb.append(',').append(Integer.toHexString(peek(i) & 0xFF));
/* 452 */     sb.append("]");
/*     */ 
/* 454 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String dumpQueue() {
/* 458 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 460 */     if (this.queue.length == 0) {
/* 461 */       sb.append("[]");
/*     */     } else {
/* 463 */       sb.append('[');
/* 464 */       sb.append(this.queue[0]);
/* 465 */       for (int i = 1; i < this.queue.length; i++) {
/* 466 */         sb.append(", ");
/* 467 */         sb.append(this.queue[i]);
/*     */       }
/* 469 */       sb.append("]");
/*     */     }
/*     */ 
/* 472 */     sb.append(", h=").append(this.head).append(", t=").append(this.tail).append(", s=").append(this.size);
/* 473 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.util.queue.ByteQueue
 * JD-Core Version:    0.6.2
 */