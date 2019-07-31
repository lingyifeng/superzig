package com.android.hz.czc.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个图片的坐标对象
 */
@Data
public class Graph {
    private List<Node> nodeList = new ArrayList<>();

    @JSONField(serialize = false)
    public Node getNodeInstance() {
        Node node = new Node();
        return node;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    @Data
    public static class Node {
        private BigDecimal x;
        private BigDecimal y;

    }
}
