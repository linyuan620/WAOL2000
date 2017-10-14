/*     */ package com.serotonin.util;
/*     */  
/*     */ 
/*     */ public class ArrayUtils
/*     */ {
/*     */   public static String toHexString(byte[] bytes)
/*     */   {
/*   7 */     return toHexString(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   public static String toHexString(byte[] bytes, int start, int len) {
/*  11 */     if (len == 0) {
/*  12 */       return "[]";
/*     */     }
/*  14 */     StringBuffer sb = new StringBuffer();
/*  15 */     sb.append('[');
/*  16 */     sb.append(Integer.toHexString(bytes[start] & 0xFF));
/*  17 */     for (int i = 1; i < len; i++)
/*  18 */       sb.append(',').append(Integer.toHexString(bytes[(start + i)] & 0xFF));
/*  19 */     sb.append("]");
/*     */ 
/*  21 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String toPlainHexString(byte[] bytes) {
/*  25 */     return toPlainHexString(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   public static String toPlainHexString(byte[] bytes, int start, int len) {
/*  29 */     StringBuffer sb = new StringBuffer();
/*  30 */     for (int i = 0; i < len; i++) {
/*  31 */       String s = Integer.toHexString(bytes[(start + i)] & 0xFF);
/*  32 */       if (s.length() < 2)
/*  33 */         sb.append('0');
/*  34 */       sb.append(s);
/*     */     }
/*  36 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] bytes) {
/*  40 */     return toString(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   public static String toString(byte[] bytes, int start, int len) {
/*  44 */     if (len == 0) {
/*  45 */       return "[]";
/*     */     }
/*  47 */     StringBuffer sb = new StringBuffer();
/*  48 */     sb.append('[');
/*  49 */     sb.append(Integer.toString(bytes[start] & 0xFF));
/*  50 */     for (int i = 1; i < len; i++)
/*  51 */       sb.append(',').append(Integer.toString(bytes[(start + i)] & 0xFF));
/*  52 */     sb.append("]");
/*     */ 
/*  54 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(int[] value) {
/*  58 */     return (value == null) || (value.length == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isOneOf(int value, int[] validValues) {
/*  62 */     for (int i = 0; i < validValues.length; i++) {
/*  63 */       if (value == validValues[i])
/*  64 */         return true;
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean contains(String[] values, String value) {
/*  70 */     if (values == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     for (int i = 0; i < values.length; i++) {
/*  74 */       if (values[i].equals(value)) {
/*  75 */         return true;
/*     */       }
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean contains(int[] values, int value) {
/*  82 */     if (values == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     for (int i = 0; i < values.length; i++) {
/*  86 */       if (values[i] == value) {
/*  87 */         return true;
/*     */       }
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean contains(Object[] values, Object value) {
/*  94 */     if (values == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     for (int i = 0; i < values.length; i++) {
/*  98 */       if (values[i].equals(value)) {
/*  99 */         return true;
/*     */       }
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   public static int indexOf(String[] values, String value) {
/* 106 */     if (values == null) {
/* 107 */       return -1;
/*     */     }
/* 109 */     for (int i = 0; i < values.length; i++) {
/* 110 */       if (values[i].equals(value)) {
/* 111 */         return i;
/*     */       }
/*     */     }
/* 114 */     return -1;
/*     */   }
/*     */ 
/*     */   public static boolean containsIgnoreCase(String[] values, String value) {
/* 118 */     if (values == null) {
/* 119 */       return false;
/*     */     }
/* 121 */     for (int i = 0; i < values.length; i++) {
/* 122 */       if (values[i].equalsIgnoreCase(value)) {
/* 123 */         return true;
/*     */       }
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   public static int indexOf(byte[] src, byte[] target) {
/* 130 */     return indexOf(src, 0, src.length, target);
/*     */   }
/*     */ 
/*     */   public static int indexOf(byte[] src, int len, byte[] target) {
/* 134 */     return indexOf(src, 0, len, target);
/*     */   }
/*     */ 
/*     */   public static int indexOf(byte[] src, int start, int len, byte[] target) {
/* 138 */     int pos = start;
/*     */ 
/* 141 */     while (pos + target.length <= len)
/*     */     {
/* 143 */       if (src[pos] == target[0])
/*     */       {
/* 145 */         boolean matched = true;
/* 146 */         int i = 1;
/* 147 */         while (i < target.length) {
/* 148 */           if (src[(pos + i)] != target[i]) {
/* 149 */             matched = false;
/* 150 */             break;
/*     */           }
/* 152 */           i++;
/*     */         }
/*     */ 
/* 155 */         if (matched)
/* 156 */           return pos;
/*     */       }
/* 158 */       pos++;
/*     */     }
/*     */ 
/* 161 */     return -1;
/*     */   }
/*     */ 
/*     */   public static int sum(int[] a) {
/* 165 */     int sum = 0;
/* 166 */     for (int i = 0; i < a.length; i++)
/* 167 */       sum += a[i];
/* 168 */     return sum;
/*     */   } 
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.util.ArrayUtils
 * JD-Core Version:    0.6.2
 */