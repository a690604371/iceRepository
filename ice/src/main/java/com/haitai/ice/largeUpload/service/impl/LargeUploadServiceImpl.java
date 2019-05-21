package com.haitai.ice.largeUpload.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haitai.ice.largeUpload.dao.LargeUploadEntityMapper;
import com.haitai.ice.largeUpload.entity.LargeUploadEntity;
import com.haitai.ice.largeUpload.service.FileResService;
import com.haitai.ice.largeUpload.service.LargeUploadService;
import com.haitai.ice.utils.common.BaseService;
import com.haitai.ice.utils.domain.FileRes;
import com.haitai.ice.utils.file.FileMd5Util;
import com.haitai.ice.utils.file.NameUtil;
import com.haitai.ice.utils.file.PathUtil;
import jodd.datetime.JDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = "LargeUploadService")
public class LargeUploadServiceImpl implements LargeUploadService {

    @Autowired
    private LargeUploadEntityMapper largeUploadEntityMapper;//这里会报错，但是并不会影响

    @Autowired
    private FileResService fileResService;


    @Value("${FILE.FILE_PATH}")
    private String filePath;

    @Override
    public int addUser(LargeUploadEntity user) {

        return largeUploadEntityMapper.insert(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<LargeUploadEntity> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<LargeUploadEntity> userDomains = largeUploadEntityMapper.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public Map<String, Object> upload(HttpServletRequest request, MultipartFile multipartFile) throws IOException {

        String action = request.getParameter("action");
//        String uuid = request.getParameter("uuid");
        String uuid = request.getParameter("filemd5");
        String fileName = request.getParameter("name");
        String size = request.getParameter("size");//总大小
        long total = Integer.valueOf(request.getParameter("total"));//总片数
        long index = Integer.valueOf(request.getParameter("index"));//当前是第几片
        String fileMd5 = request.getParameter("filemd5"); //整个文件的md5
        String date = request.getParameter("date"); //文件第一个分片上传的日期(如:20170122)
        String md5 = request.getParameter("md5"); //分片的md5

        //生成上传文件的路径信息，按天生成
        String savePath =  File.separator + date;
        String saveDirectory = filePath + savePath + File.separator + uuid;
        //验证路径是否存在，不存在则创建目录
        File path = new File(saveDirectory);
        if (!path.exists()) {
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(saveDirectory, uuid + "_" + index);

        //根据action不同执行不同操作. check:校验分片是否上传过; upload:直接上传分片
        Map<String, Object> map = null;
        if("check".equals(action)){
            String md5Str = FileMd5Util.getFileMD5(file);
            if (md5Str != null && md5Str.length() == 31) {
                System.out.println("check length =" + md5.length() + " md5Str length" + md5Str.length() + "   " + md5 + " " + md5Str);
                md5Str = "0" + md5Str;
            }
            if (md5Str != null && md5Str.equals(md5)) {
                System.out.println("分片已上传过=========================================================");
                //分片已上传过
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            } else {
                System.out.println("分片未上传===========================================================");
                //分片未上传
                map = new HashMap<>();
                map.put("flag", "1");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            }
        }else if("upload".equals(action)){
            //分片上传过程中出错,有残余时需删除分块后,重新上传
            if (file.exists()) {
                file.delete();
            }
            multipartFile.transferTo(new File(saveDirectory, uuid + "_" + index));
        }

        if (path.isDirectory()) {
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                if (fileArray.length == total) {
                    System.out.println("分块全部上传完毕,合并===========================================================");
                    //分块全部上传完毕,合并
                    String suffix = NameUtil.getExtensionName(fileName);

                    File newFile = new File(filePath + savePath, uuid + "." + suffix);
                    FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
                    byte[] byt = new byte[10 * 1024 * 1024];
                    int len;

                    FileInputStream temp = null;//分片文件
                    for (int i = 0; i < total; i++) {
                        int j = i + 1;
                        temp = new FileInputStream(new File(saveDirectory, uuid + "_" + j));
                        while ((len = temp.read(byt)) != -1) {
                            System.out.println("-----" + len);
                            outputStream.write(byt, 0, len);
                        }
                    }
                    //关闭流
                    temp.close();
                    outputStream.close();
                    //修改FileRes记录为上传成功
                    Example example = new Example(FileRes.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("md5",fileMd5);
                    FileRes fileRes = new FileRes();
                    fileRes.setStatus(1);
                    fileResService.updateByExampleSelective(fileRes,example);
                }else if(index == 1){
                    System.out.println("文件第一个分片上传时记录到数据库===========================================================");
                    //文件第一个分片上传时记录到数据库
                    FileRes fileRes = new FileRes();
                    String name = NameUtil.getFileNameNoEx(fileName);
                    if (name.length() > 50) {
                        name = name.substring(0, 50);
                    }
                    fileRes.setName(name);
                    fileRes.setSuffix(NameUtil.getExtensionName(fileName));
                    fileRes.setUuid(uuid);
                    fileRes.setPath(savePath + File.separator + uuid + "." + fileRes.getSuffix());
                    fileRes.setSize(Integer.parseInt(size));
                    fileRes.setMd5(fileMd5);
                    fileRes.setStatus(0);
                    fileRes.setCreateTime(new Date());
                    this.fileResService.insert(fileRes);
                }
//                System.out.println("文件第"+index+"个分片上传时记录到数据库===========================================================");
            }
        }

        map = new HashMap<>();
        map.put("flag", "3");
        map.put("fileId", uuid);
        map.put("status", true);
        return map;

    }

    @Override
    public Map<String, Object> isUpload(HttpServletRequest request) {

        String md5 = request.getParameter("md5");

        Example example = new Example(FileRes.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("md5", md5);
        List<FileRes> list = fileResService.selectByExample(example);

        Map<String, Object> map = null;
        if (list == null || list.size() == 0) {
            System.out.println("没有上传过文件===========================================================");
            //没有上传过文件
//            String uuid = UUID.randomUUID().toString();
            map = new HashMap<>();
            map.put("flag", "1");
//            map.put("fileId", uuid);
            map.put("fileId", md5);
            map.put("date", new JDateTime().toString("YYYYMMDD"));
            map.put("status", true);
        } else {
            FileRes fileRes = list.get(0);
            //求文件上传日期
            SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
            Date date=fileRes.getCreateTime();
            String strDate=sdf.format(date);
            if(fileRes.getStatus()==0){
                System.out.println("文件上传部分===========================================================");
                //文件上传部分
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", fileRes.getUuid());
                map.put("date",strDate);
                map.put("status", true);
            }else if(fileRes.getStatus()==1){
                System.out.println("文件上传成功===========================================================");
                //文件上传成功
                map = new HashMap<>();
                map.put("flag", "3");
                map.put("fileId", fileRes.getUuid());
                map.put("date",strDate);
                map.put("status", true);
            }

        }

        return map;

    }


}
