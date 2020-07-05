package com.alex.dragblog.commons.feign;

import com.alex.dragblog.base.vo.FileVo;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *description:  picture model相关接口
 * name:指定feignClient的名称，如果项目使用Ribbon(负载均衡),name属性作为微服务的名称，用于服务发现
 * url: url一般用于调试，可以手动指定@FeignClient调用地址
 * decode404:当发生404错误时，如果该字段为true，会调用decoder进行解密，否则抛出FeignException
 * configuration: Feign配置类，可以自定义Feign的encoder decoder，logLevel,contract
 * fallback:定义容错的处理类
 * fallbackFactory:工厂类，用于生成fallback类示例
 * path:定义当前FeignClient的统一前缀
 *author:       alex
 *createDate:   2020/6/28 21:06
 *version:      1.0.0
 */
@FeignClient(name = "drag-pricture", configuration = FeignAutoConfiguration.class, path = "/file")
public interface PictureFeignClient {

    /**
     * description :获取文件的信息接口
     * author :     alex
     * @ApiImplicitParam(name = "fileIds", value = "fileIds", required = false, dataType = "String"),
     * @ApiImplicitParam(name = "code", value = "分割符", required = false, dataType = "String")
     * @return :
     */
    @RequestMapping(value = "/file/getPicture", method = RequestMethod.GET)
    String getPicture(@RequestParam("fileIds") String fileIds, @RequestParam("code") String code);


    /**
     * description :通过url list上传图片
     * author :     alex
     * @param :     fileVo
     * @return :
     */
    @RequestMapping(value = "/file/uploadPicsByUrl2", method = RequestMethod.POST)
    String uploadPicsByUrl(FileVo fileVo);
}
