package com.RE.Engine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Transit {
    public static char[] alphabet2 = {' ','.','!','\"','#','$','%','&','\'',',','-','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','@','A','B','C','D','E','F',
            'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','[',']','^','_','`','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z','{','}','~','','\0','\01','\02','\03','\04','\05','\06','\07','\010','\011','\012','\013','\014','\015','\016','\017','\020','\021'};

    //将闭包转化为HashSet，以一个节点为起点
    public static HashSet<NFA.NFAnode> calClosure(NFA.NFAnode n){
        Queue<NFA.NFAnode> queue = new LinkedList<NFA.NFAnode>();
        HashSet<NFA.NFAnode> dstates = new HashSet<NFA.NFAnode>();
        queue.add(n);
        dstates.add(n);

        while(!queue.isEmpty()){
            NFA.NFAnode n1 = queue.poll();
            if(n1==null)
                break;
            for( int i=0;i<n1.edge.size();i++){
                //确保不将已有的节点放入队列中
                if(n1.edge.get(i).equals('\0')){
                    if(!dstates.contains(n1.desnodes.get(i))){
                        queue.offer(n1.desnodes.get(i));
                        dstates.add(n1.desnodes.get(i));
                    }
                }
            }
        }
        return dstates;
    }

    //计算一个状态set的所有闭包
    public static HashSet<NFA.NFAnode> calclosure(HashSet<NFA.NFAnode> T){
        HashSet<NFA.NFAnode> all = new HashSet<>();
        for(NFA.NFAnode node:T){
            HashSet<NFA.NFAnode> r = calClosure(node);
            all.addAll(r);
        }
        return all;
    }

    //计算一个状态集在输入一个字符s之后得到的下一个状态集
    public static HashSet<NFA.NFAnode> nextClosure(HashSet<NFA.NFAnode> T,Character s){
        HashSet<NFA.NFAnode> result = new HashSet<>();
        for(NFA.NFAnode node:T){
            for(int i=0;i<node.edge.size();i++){
                if(node.edge.get(i).equals(s))
                    result.add(node.desnodes.get(i));

            }
        }
        return result;
    }

    public static boolean inResult(HashSet<HashSet<NFA.NFAnode>> result,HashSet<NFA.NFAnode> T){
        return result.contains(T);
    }


}
