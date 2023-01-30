package xyz.rockbdm.antlr.utils;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Finder {
    private final Map<String, List<ParseTree>> dataMap;

    public Finder(Map<String, List<ParseTree>> dataMap) {
        this.dataMap = dataMap;
    }

    /**
     * 用于搜索某个树节点下所有符合某个节点Context类型的节点
     * @param curContext 查找根节点
     * @param findType 需要查找的Context类型
     * @param <T> Context
     */
    public <T extends ParseTree> void find(ParseTree curContext, Class<? extends T> findType) {
        this.find(curContext, findType, findType.getName());
    }

    public <T extends ParseTree> void find(ParseTree curContext, Class<? extends T> findType, String keyName) {
        int childCount = curContext.getChildCount();
        if(childCount <= 0) {
            return;
        }
        for(int i = 0; i < childCount; i ++) {
            ParseTree child = curContext.getChild(i);
//            System.out.println(child.getClass().getName());
            if(child.getClass().equals(findType)) {
                // 加入集合
                if(this.dataMap.containsKey(keyName)) {
                    this.dataMap.get(keyName).add(child);
                }else {
                    List<ParseTree> newList = new ArrayList<>();
                    newList.add(child);
                    this.dataMap.put(keyName, newList);
                }
            }else {
                this.find(child, findType);
            }
        }
    }

    /**
     * 从查找结果里面获取结果
     * @param findType 需要返回的Context类型
     * @return 命中集合, 如果还没有查询结果则返回空
     * @param <T> Context集合
     */
    public <T extends ParseTree> List<ParseTree> getFind(Class<? extends T> findType) {
        String key = findType.getName();
        return this.getFind(key);
    }

    public List<ParseTree> getFind(String key) {
        if(this.dataMap.containsKey(key)) {
            System.out.println("------------" + key + "------------");
            return this.dataMap.get(key);
        }
        return null;
    }
}
