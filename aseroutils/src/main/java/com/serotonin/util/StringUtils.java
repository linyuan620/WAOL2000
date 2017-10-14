/*     */ package com.serotonin.util;
/*     */ 
/*     */ 
/*     */ import java.util.Collection;
/*     */  
/*     */ import java.util.Map; 
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class StringUtils
/*     */ {
/*     */   //private static final int PASSWORD_LENGTH = 7;
/*     */   //private static final String PASSWORD_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
/*  15 */   public static final Random RANDOM = new Random();
/*     */ 
/*     */   public static boolean isEqual(Object o1, Object o2) {
/*  18 */     if ((o1 == null) && (o2 == null))
/*  19 */       return true;
/*  20 */     if (o1 == null)
/*  21 */       return false;
/*  22 */     if (o2 == null)
/*  23 */       return false;
/*  24 */     return o1.equals(o2);
/*     */   }
/*     */ 
/*     */   public static boolean isEqualIgnoreCase(String s1, String s2) {
/*  28 */     if ((s1 == null) && (s2 == null))
/*  29 */       return true;
/*  30 */     if (s1 == null)
/*  31 */       return false;
/*  32 */     if (s2 == null)
/*  33 */       return false;
/*  34 */     return s1.equalsIgnoreCase(s2);
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(String s) {
/*  38 */     if ((s == null) || (s.trim().length() == 0))
/*  39 */       return true;
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   public static String trimWhitespace(String s) {
/*  44 */     if (s == null)
/*  45 */       return null;
/*  46 */     int start = 0;
/*  47 */     while ((start < s.length()) && (Character.isWhitespace(s.charAt(start))))
/*  48 */       start++;
/*  49 */     int end = s.length();
/*  50 */     while ((end > start) && (Character.isWhitespace(s.charAt(end - 1))))
/*  51 */       end--;
/*  52 */     return s.substring(start, end);
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Collection<?> value) {
/*  56 */     return (value == null) || (value.size() == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(int[] value) {
/*  60 */     return (value == null) || (value.length == 0);
/*     */   }
/*     */ 
/*     */   public static boolean isLengthBetween(String value, int min, int max) {
/*  64 */     int length = 0;
/*  65 */     if (value != null)
/*  66 */       length = value.length();
/*  67 */     return (length >= min) && (length <= max);
/*     */   }
/*     */ 
/*     */   public static boolean isLengthLessThan(String value, int min) {
/*  71 */     int length = 0;
/*  72 */     if (value != null)
/*  73 */       length = value.length();
/*  74 */     return length < min;
/*     */   }
/*     */ 
/*     */   public static boolean isLengthGreaterThan(String value, int max) {
/*  78 */     int length = 0;
/*  79 */     if (value != null)
/*  80 */       length = value.length();
/*  81 */     return length > max;
/*     */   }
/*     */ 
/*     */   public static boolean containsUppercase(String s) {
/*  85 */     if (s != null) {
/*  86 */       for (int i = 0; i < s.length(); i++) {
/*  87 */         if (Character.isUpperCase(s.charAt(i)))
/*  88 */           return true;
/*     */       }
/*     */     }
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsLowercase(String s) {
/*  95 */     if (s != null) {
/*  96 */       for (int i = 0; i < s.length(); i++) {
/*  97 */         if (Character.isLowerCase(s.charAt(i)))
/*  98 */           return true;
/*     */       }
/*     */     }
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean containsDigit(String s) {
/* 105 */     if (s != null) {
/* 106 */       for (int i = 0; i < s.length(); i++) {
/* 107 */         if (Character.isDigit(s.charAt(i)))
/* 108 */           return true;
/*     */       }
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isBetweenInc(int value, int min, int max) {
/* 115 */     return (value >= min) && (value <= max);
/*     */   }
/*     */ 
/*     */   public static String pad(String s, char padChar, int len)
/*     */   {
/* 130 */     if (s.length() >= len) {
/* 131 */       return s;
/*     */     }
/* 133 */     int needed = len - s.length();
/* 134 */     StringBuffer sb = new StringBuffer();
/* 135 */     sb.append(padChar);
/* 136 */     while (sb.length() < needed) {
/* 137 */       sb.append(sb);
/*     */     }
/* 139 */     sb.append(s);
/* 140 */     return sb.substring(sb.length() - len);
/*     */   }
/*     */ 
/*     */   public static String mask(String s, char maskChar, int unmaskedLength)
/*     */   {
/* 154 */     if (s == null) {
/* 155 */       return null;
/*     */     }
/* 157 */     if (s.length() > unmaskedLength) {
/* 158 */       return pad("", '*', s.length() - unmaskedLength) + s.substring(s.length() - unmaskedLength);
/*     */     }
/* 160 */     return pad("", '*', s.length());
/*     */   }
/*     */ 
/*     */   public static String generatePassword()
/*     */   {
/* 169 */     return generatePassword(7);
/*     */   }
/*     */ 
/*     */   public static String generatePassword(int length) {
/* 173 */     return generateRandomString(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890");
/*     */   }
/*     */ 
/*     */   public static String generateRandomString(int length, String charSet) {
/* 177 */     StringBuffer sb = new StringBuffer();
/* 178 */     for (int i = 0; i < length; i++)
/* 179 */       sb.append(charSet.charAt(RANDOM.nextInt(charSet.length())));
/* 180 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static boolean isOneOf(int value, int[] validValues) {
/* 184 */     for (int i = 0; i < validValues.length; i++) {
/* 185 */       if (value == validValues[i])
/* 186 */         return true;
/*     */     }
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isOneOf(String value, String[] validValues) {
/* 192 */     for (int i = 0; i < validValues.length; i++) {
/* 193 */       if (value.equals(validValues[i]))
/* 194 */         return true;
/*     */     }
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   public static String escapeLT(String s) {
/* 200 */     if (s == null)
/* 201 */       return null;
/* 202 */     return s.replaceAll("<", "&lt;");
/*     */   }
/*     */ 
/*     */   public static boolean globWhiteListMatchIgnoreCase(String[] values, String value) {
/* 206 */     if ((values == null) || (values.length == 0) || (value == null)) {
/* 207 */       return false;
/*     */     }
/* 209 */     int ast = 0;
/* 210 */     for (int i = 0; i < values.length; i++) {
/* 211 */       ast = values[i].indexOf("*");
/* 212 */       if (ast == -1) {
/* 213 */         if (values[i].equalsIgnoreCase(value)) {
/* 214 */           return true;
/*     */         }
/*     */       }
/* 217 */       else if ((value.length() >= ast) && 
/* 218 */         (values[i].substring(0, ast).equalsIgnoreCase(value.substring(0, ast)))) {
/* 219 */         return true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   public static String replaceMacros(String s, Properties properties) {
/* 228 */     String result = s;
/* 229 */     for(Map.Entry<Object, Object> entry: properties.entrySet())  //for (Map.Entry entry : properties.entrySet())
/* 230 */       result = replaceMacro(result, entry.getKey().toString(), entry.getValue().toString());
/* 231 */     return result;
/*     */   }
/*     */ 
/*     */   public static String replaceMacro(String s, String name, String replacement) {
/* 235 */     return s.replaceAll(Pattern.quote("${" + name + "}"), replacement);
/*     */   }
/*     */ 
/*     */   public static String replaceMacro(String s, String name, String content, String replacement) {
/* 239 */     return s.replaceAll(Pattern.quote("${" + name + ":" + content + "}"), replacement);
/*     */   }
/*     */ 
/*     */   public static String getMacroContent(String s, String name) {
/* 243 */     Matcher matcher = Pattern.compile("\\$\\{" + Pattern.quote(name) + ":(.*?)\\}").matcher(s);
/* 244 */     if (matcher.find())
/* 245 */       return matcher.group(1);
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */   public static String truncate(String s, int length) {
/* 250 */     return truncate(s, length, null);
/*     */   }
/*     */ 
/*     */   public static String truncate(String s, int length, String truncateSuffix) {
/* 254 */     if (s == null) {
/* 255 */       return s;
/*     */     }
/* 257 */     if (s.length() <= length) {
/* 258 */       return s;
/*     */     }
/* 260 */     s = s.substring(0, length);
/* 261 */     if (truncateSuffix == null)
/* 262 */       return s;
/* 263 */     return s + truncateSuffix;
/*     */   }
/*     */ 
/*     */   public static String findGroup(Pattern pattern, String s) {
/* 267 */     return findGroup(pattern, s, 1);
/*     */   }
/*     */ 
/*     */   public static String findGroup(Pattern pattern, String s, int group) {
/* 271 */     if (s == null) {
/* 272 */       return null;
/*     */     }
/* 274 */     Matcher matcher = pattern.matcher(s);
/* 275 */     if (matcher.find()) {
/* 276 */       return matcher.group(group);
/*     */     }
/* 278 */     return null;
/*     */   } 
/*     */ 
/*     */   public static boolean in(String s, String[] list) {
/* 300 */     if (list == null) {
/* 301 */       return false;
/*     */     }
/* 303 */     for (String e : list) {
/* 304 */       if (isEqual(s, e)) {
/* 305 */         return true;
/*     */       }
/*     */     }
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   public static int parseInt(String s, int defaultValue) {
/*     */     try {
/* 313 */       return Integer.parseInt(s);
/*     */     } catch (NumberFormatException e) {
/*     */     }
/* 316 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public static String durationToString(long duration)
/*     */   {
/* 321 */     if (duration < 1000L) {
/* 322 */       return duration + "ms";
/*     */     }
/* 324 */     if (duration < 10000L) {
/* 325 */       String s = "" + duration / 1000L + '.';
/* 326 */       s = s + (int)(duration % 1000L / 10.0D + 0.5D);
/* 327 */       return s + "s";
/*     */     }
/*     */ 
/* 330 */     if (duration < 60000L) {
/* 331 */       String s = "" + duration / 1000L + '.';
/* 332 */       s = s + (int)(duration % 1000L / 100.0D + 0.5D);
/* 333 */       return s + "s";
/*     */     }
/*     */ 
/* 337 */     duration /= 1000L;
/*     */ 
/* 339 */     if (duration < 600L) {
/* 340 */       return "" + duration / 60L + 'm' + duration % 60L + 's';
/*     */     }
/*     */ 
/* 343 */     duration /= 60L;
/*     */ 
/* 345 */     if (duration < 60L) {
/* 346 */       return "" + duration + 'm';
/*     */     }
/* 348 */     if (duration < 1440L) {
/* 349 */       return "" + duration / 60L + 'h' + duration % 60L + 'm';
/*     */     }
/*     */ 
/* 352 */     duration /= 60L;
/*     */ 
/* 354 */     if (duration < 24L) {
/* 355 */       return "" + duration + 'h';
/*     */     }
/* 357 */     if (duration < 168L) {
/* 358 */       return "" + duration / 24L + 'd' + duration % 24L + 'h';
/*     */     }
/*     */ 
/* 361 */     duration /= 24L;
/*     */ 
/* 363 */     return "" + duration + 'd';
/*     */   }
/*     */ 
/*     */   public static String capitalize(String s) {
/* 367 */     if (s == null)
/* 368 */       return null;
/* 369 */     return s.toUpperCase().replace(' ', '_');
/*     */   }
/*     */ }

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.util.StringUtils
 * JD-Core Version:    0.6.2
 */