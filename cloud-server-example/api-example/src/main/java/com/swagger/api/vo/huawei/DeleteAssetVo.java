package com.swagger.api.vo.huawei;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: DeleteAssetVo
 * @description: 删除资源返回
 * @author: sw
 * @date: 2021/8/28
 **/
@Data
@ApiModel("资源编号")
public class DeleteAssetVo implements Serializable {
    private static final long serialVersionUID = -6735816743930186663L;
    @ApiModelProperty("资源编号")
    private String assetId;
    @ApiModelProperty("删除结果：DELETED ，FAILURE")
    private String status;

}
