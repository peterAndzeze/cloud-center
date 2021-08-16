package com.sw.cloud.center.config.nacos.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: NameSpace
 * @description: 命名空间
 * @author: sw
 * @date: 2021/7/6
 **/
public class NameSpacePoJo{


    /**
     * 命名空间id
     */
    private String namespace;
    /**
     * 命名空间名称
     */
    private String namespaceShowName;
    /**
     * 服务实例数 默认200个
     */
    private int quota;
    /**
     * 配置文件数 默认200
     */
    private int configCount;
    /**
     * 命名空间类型 0：默认 public 其它为2
     */
    private int type;


    public String getNamespace() {
        return namespace;
    }


    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespaceShowName() {
        return namespaceShowName;
    }

    public void setNamespaceShowName(String namespaceShowName) {
        this.namespaceShowName = namespaceShowName;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getConfigCount() {
        return configCount;
    }

    public void setConfigCount(int configCount) {
        this.configCount = configCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
