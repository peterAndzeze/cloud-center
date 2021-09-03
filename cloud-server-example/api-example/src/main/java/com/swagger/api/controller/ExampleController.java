package com.swagger.api.controller;

import com.swagger.api.Result;
import com.swagger.api.form.QueryUserResourceRecodeForm;
import com.swagger.api.form.UpUserResourceRecodeForm;
import com.swagger.api.vo.UserResourceRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: ExampleController
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/8/30
 **/
@RequestMapping("user/resource")
@Api(tags = {"用户资源信息"})
@RestController
public class ExampleController {

    /* @PutMapping("upUserResourceRecord")
     @ApiOperation("上传用户资源记录")
     public Result upUserResourceRecord(@RequestBody UpUserResourceRecodeForm upUserResourceRecodeForm){

         int i=userResourceService.upUserResourceRecord(upUserResourceRecodeForm);
         return i>0?Result.ok():Result.error("上传失败");

     }
 */
    @PutMapping("updateUserResourceRecord")
     @ApiOperation("更新用户资源记录")
    public Result updateUserResourceRecord(@RequestBody UpUserResourceRecodeForm upUserResourceRecodeForm) {


        return Result.ok();
    }

    @PostMapping("queryUserResourceRecord")
    @ApiOperation("查询使用情况")
    public Result<UserResourceRecordVo> queryUserResourceRecord(@RequestBody QueryUserResourceRecodeForm queryUserResourceRecodeForm) {
        return Result.ok(new UserResourceRecordVo());

    }
}
