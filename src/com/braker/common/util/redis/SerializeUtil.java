package com.braker.common.util.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化对象工具类，用于保存和读取redis数据使用
 * @author 安达
 * @createtime 2018-11-12
 * @updatetime 2018-11-12
 */
public class SerializeUtil  {

    private static Logger log = LoggerFactory.getLogger(SerializeUtil.class);  

    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {  
        ObjectOutputStream oos = null;  
        ByteArrayOutputStream baos = null;  
        byte[] bytes = null;
        try {  
            // 序列化  
            baos = new ByteArrayOutputStream();  
            oos = new ObjectOutputStream(baos);  
            oos.writeObject(object);  
            bytes = baos.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (oos != null) {  
                    oos.close();  
                }  
                if (baos != null) {  
                    baos.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return bytes;  
    }  

    /**
     * 反序列化对象
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) { 
        Object obj = null; 
        ByteArrayInputStream bais = null;  
        ObjectInputStream ois = null;
        if(bytes==null){
        	return null;
        }
        try {  
            // 反序列化  
            bais = new ByteArrayInputStream(bytes);  
            ois = new ObjectInputStream(bais);  
            obj = ois.readObject();  
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	 try {
				ois.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}   
             try {
				bais.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
        return obj;  
    }  

    /**
     * 关闭的数据源或目标。调用 close()方法可释放对象保存的资源（如打开文件）
     * 关闭此流并释放与此流关联的所有系统资源。如果已经关闭该流，则调用此方法无效。
     * @param closeable
     */
    public static void close(Closeable closeable) {  
        if (closeable != null) {  
            try {  
                closeable.close();  
            } catch (Exception e) {  
                log.info("Unable to close %s", closeable, e);  
            }  
        }  
    }

    /**
     * 列表序列化（用于Redis整存整取）
     * @param value
     * @return
     */
    public static <T> byte[] serialize(List<T> value) {  
        if (value == null) {  
            throw new NullPointerException("Can't serialize null");  
        }  
        byte[] rv=null;  
        ByteArrayOutputStream bos = null;  
        ObjectOutputStream os = null;  
        try {  
            bos = new ByteArrayOutputStream();  
            os = new ObjectOutputStream(bos);  
            for(T obj : value){  
                os.writeObject(obj);  
            }  
            os.writeObject(null);  
            os.close();  
            bos.close();  
            rv = bos.toByteArray();  
        } catch (IOException e) {  
            throw new IllegalArgumentException("Non-serializable object", e);  
        } finally {  
            close(os);
            close(bos);
        }  
        return rv;  
    }

    /**
     * 反序列化列表（用于Redis整存整取）
     * @param in
     * @return
     */
    public static <T> List<T> unserializeForList(byte[] in) {  
        List<T> list = new ArrayList<T>();  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if(in != null) {  
                bis=new ByteArrayInputStream(in);  
                is=new ObjectInputStream(bis);  
                while (true) {  
                    T obj = (T) is.readObject();  
                    if(obj == null){  
                        break;  
                    }else{  
                        list.add(obj);  
                    }  
                }  
                is.close();  
                bis.close();  
            }  
        } catch (IOException e) {  
            log.warn("Caught IOException decoding %d bytes of data",  
                    in == null ? 0 : in.length, e);  
        } catch (ClassNotFoundException e) {  
            log.warn("Caught CNFE decoding %d bytes of data",  
                    in == null ? 0 : in.length, e);  
        } finally {  
            close(is);  
            close(bis);  
        }  
        return list;  
    }  

}
