package com.zwp.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.persistence.Entity;

import org.apache.commons.io.FileUtils;

public class UploadUtil {
	
	//����ͼƬ����������
	public String uploadFile(File upload,String uploadFileName,String FileName,int id){
		//����ͼƬ
        if(upload!=null && upload.length()>0 && uploadFileName!=null && uploadFileName.length()>0){	
        	//д�ϴ�����
        	
        	Calendar cal=Calendar.getInstance();//���һ����ȡʱ���ʵ��    
        	
            //��������ϴ��ļ��ĸ�Ŀ¼�����ڣ�������Ŀ¼  
            String fileName="D:\\CSDNimg\\"+FileName+"\\"+id;  
            //Ϊ������photo���ԣ�����һ������url��ȡ���·��  
            String url=FileName+"\\"+id;
            
            File file=new File(fileName);  
            if(!file.exists()){  
            	//������Ŀ¼  
                file.mkdirs();  
            }  
            
            //������Ŀ¼�����ڣ��������Ŀ¼  
            int year= cal.get(Calendar.YEAR);//�����  
            fileName=fileName+"\\"+year;  
            url=url+"\\"+year;  
            file=new File(fileName);  
            if(!file.exists()){   
            	//������Ŀ¼  
                file.mkdirs();  
            }  
              
            //����µ�Ŀ¼�����ڣ������·ݵ�Ŀ¼  
            int month=cal.get(Calendar.MONTH)+1;//�������¼�סҪ��1  
            fileName=fileName+"\\";  //�������һ�μӷ�б�ܣ���߲�����Ŀ¼  
            url=url+"\\";  
            //�����·�С��10��ʱ������(����5��)�� 05  
            if(month<10)  {  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+month;  
            url=url+month;  
            file=new File(fileName);  
            if(!file.exists()){  
                //������Ŀ¼  
                file.mkdirs();  
            }  
              
            //�����￪ʼ������Ƭ�ļ������е����������漰������Ŀ¼�������ļ������ղ���  
            int day=cal.get(Calendar.DAY_OF_MONTH);
            fileName=fileName+"\\";  
            url=url+"\\";  
            //��������С��10��ʱ������(����5��)�� 05  
            if(day<10){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+day;  
            url=url+day;  
              
            //�����ļ�����Сʱ����  
            int hour=cal.get(Calendar.HOUR_OF_DAY);  
            //���Сʱ���Ǹ�λ���ͼ�0  
            if(hour<10){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+hour;  
            url=url+hour;  
              
            //�����ļ����ķ��Ӳ���  
            int mintue=cal.get(Calendar.MINUTE);  
            if(mintue<10){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+mintue;  
            url=url+mintue;  
              
            //�����ļ������벿��  
            int second=cal.get(Calendar.SECOND);  
            if(second<10){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+second;  
            url=url+second;  
              
            //�����ļ����ĺ��벿��  
            int millisecond=cal.get(Calendar.MILLISECOND);  
            //����Ӧ�ü�������  
            if(millisecond<10){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            if(millisecond<100){  
                fileName=fileName+"0";  
                url=url+"0";  
            }  
            fileName=fileName+millisecond;  
            url=url+millisecond;  
              
            
            //�����ļ�������չ������  
            String extension=uploadFileName.substring(uploadFileName.indexOf("."));//��ȡ�ϴ��ļ�����չ��  
            fileName=fileName+extension;  
            url=url+extension;  
            
            File myfile=new File(fileName);  
            try {  
                FileUtils.copyFile(upload,myfile);//�ϴ����ͻ���  
            } catch (IOException e) {  
                e.printStackTrace();  
        }  
            
            return url;
	}
        return null;
}
}
