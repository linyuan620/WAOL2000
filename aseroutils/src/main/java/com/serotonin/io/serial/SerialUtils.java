/*    */
package com.serotonin.io.serial;
/*    */ 
/*    */

import android.serialport.SerialPort;

import java.io.File;

/*    */
/*    */  
/*    */ 
/*    */ public class SerialUtils
/*    */ {
    /*    */
    public static SerialPort openSerialPort(SerialParameters serialParameters)
/*    */     throws SerialPortException
/*    */ {
/* 34 */
        SerialPort serialPort = null;
/*    */
        try
/*    */ {
//            serialPort = new SerialPort(new File("/dev/ttyS1"), 9600, 0);
            serialPort  =  new SerialPort(new File("/dev/ttyAMA0"), 9600, 0);
/* 37 */
        }
/*    */ catch (Exception e) {
/* 53 */
            close(serialPort);/*    */
/* 55 */
            throw new SerialPortException(e);
/*    */
        }
/*    */ 
/* 58 */
        return serialPort;
/*    */
    }

    /*    */
/*    */
    public static void close(SerialPort serialPort) {
/* 62 */
        if (serialPort != null)
/* 66 */ serialPort.close();
/*    */
    }
/*    */
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.serial.SerialUtils
 * JD-Core Version:    0.6.2
 */