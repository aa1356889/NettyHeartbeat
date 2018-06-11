package com.liqiang.nettyTest2;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5
 * 提供对字符串的md5-->stringMD5 
 * 提供对文件的Md5-->fileMD5  
 * 
 * 对于大文件，可以使用DigestInputStream
 */
public class Md5Utils {

    protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  
    protected static MessageDigest messageDigest = null;
    
    static{  
        try{  
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            messageDigest = MessageDigest.getInstance("MD5");  
        }catch (NoSuchAlgorithmException e) {  
            System.err.println(Md5Utils.class.getName()+"初始化失败，MessageDigest不支持MD5Util.");  
            e.printStackTrace();  
        }  
    }
    
    private static String bufferToHex(byte bytes[], int m, int n) {  
       StringBuffer stringbuffer = new StringBuffer(2 * n);  
       int k = m + n;  
       for (int l = m; l < k; l++) {  
        appendHexPair(bytes[l], stringbuffer);  
       }  
       return stringbuffer.toString();  
    }  
          
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
     
       char c0 = hexDigits[(bt & 0xf0) >> 4];  
       char c1 = hexDigits[bt & 0xf];  
       stringbuffer.append(c0);  
       stringbuffer.append(c1);  
    } 
    
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }
    
    /**
     * 字符串的md5加密
     * @param input
     * @return
     */
    public static String stringMD5(String input) {
          // 输入的字符串转换成字节数组
         byte[] inputByteArray = input.getBytes();
         // inputByteArray是输入字符串转换得到的字节数组
         messageDigest.update(inputByteArray);
         // 转换并返回结果，也是字节数组，包含16个元素
         byte[] resultByteArray = messageDigest.digest();
         // 字符数组转换成字符串返回
         return bufferToHex(resultByteArray);
    }
    /**
     * 文件的md5加密
     * @param inputFile
     * @return
     * @throws IOException
     */
    public static String fileMD5(String inputFile) throws IOException {
          // 缓冲区大小（这个可以抽出一个参数）
          int bufferSize = 256 * 1024;
          FileInputStream fileInputStream = null;
          DigestInputStream digestInputStream = null;
          try {
             // 使用DigestInputStream
             fileInputStream = new FileInputStream(inputFile);
             digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
             // read的过程中进行MD5处理，直到读完文件
             byte[] buffer =new byte[bufferSize];
             while (digestInputStream.read(buffer) > 0);
             // 获取最终的MessageDigest
             messageDigest= digestInputStream.getMessageDigest();
             // 拿到结果，也是字节数组，包含16个元素
             byte[] resultByteArray = messageDigest.digest();
             // 同样，把字节数组转换成字符串
             return bufferToHex(resultByteArray);
          } finally {
             try {
                digestInputStream.close();
             } catch (Exception e) {
             }
             try {
                fileInputStream.close();
             } catch (Exception e) {
             }
          }
       }
    
   /* *//**
     * @param args
     *//*
    public static void main(String[] args) {
        //测试字符串MD5加密
        //123456: e10adc3949ba59abbe56e057f20f883e
        //eastcom:  6997c46956185a7c4d452646fc9c69e2
        System.out.println(stringMD5("eastcom"));
        
        
        try {
            long startTime = System.currentTimeMillis();
            //测试文件MD5加密
            String FilePath = "D:/ilink_ide.zip"; //4227e9fc4bd71ff34887d47867967b29
            System.out.println(fileMD5(FilePath));
            
            long endTime = System.currentTimeMillis(); 
            System.out.println((endTime - startTime)/1000); 
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }*/
}
