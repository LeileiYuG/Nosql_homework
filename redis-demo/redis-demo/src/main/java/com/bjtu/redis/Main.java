package com.bjtu.redis;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] arg) throws Exception {
        MyJedis myjedis=new MyJedis();
        JsonToMap jm=new JsonToMap();

        //设置文件监听
        FileMonitor m = new FileMonitor(500);//设置监听周期
        m.monitor("src/main/resources", new FileListener(jm)); //对目标文件夹添加监听
        m.start();

        //获得hashmap
        HashMap<String,HashMap> actionsMap=jm.getActions();
        HashMap<String,HashMap> countersMap=jm.getCounters();

//        //输出hashmap*********************************
//        System.out.println(actionsMap);
//        System.out.println(countersMap);

        HashMap<String,String> action,counter;

        System.out.println("********************************************************************");
        while (true){
            //显示选择菜单
            System.out.println("1.Add online users");
            System.out.println("2.Reduce online users");
            System.out.println("3.Display the current number of people online");
            System.out.println("4.Display the changes of online users in a specified period of time");
            System.out.println("5.Show Jedis data");
            System.out.println("0.Quit");
            System.out.println("Please enter the operation number:");

            //获取输入选项
            Scanner input=new Scanner(System.in);
            String choice=input.nextLine();

            if(choice.equals("1"))
            {
                action=actionsMap.get("ADD_USER");
                String oprname=(String)action.get("operation");
                counter=countersMap.get(oprname);
                String temp= (String) counter.get("valueFields");
                myjedis.addUser(temp);
                //展示增加后围观人数
                myjedis.showUserCount();
                System.out.println("--------------------------------------------------------------------");
                continue;
            }
            else if(choice.equals("2"))
            {
                action=actionsMap.get("DEL_USER");
                String oprname=(String)action.get("operation");
                counter=countersMap.get(oprname);
                String temp= (String) counter.get("valueFields");
                myjedis.delUser(temp);
                //展示减少后围观人数
                myjedis.showUserCount();
                System.out.println("--------------------------------------------------------------------");
                continue;
            }
            else if(choice.equals("3")){
                myjedis.showUserCount();
                System.out.println("--------------------------------------------------------------------");
                continue;
            }
            else if(choice.equals("4"))
            {
                action=actionsMap.get("SHOW_USER_FREQ");
                String oprname=(String)action.get("operation");
                counter=countersMap.get(oprname);
                String temp= (String)counter.get("fields");
                myjedis.showUserFreq(temp);
                System.out.println("--------------------------------------------------------------------");
                continue;
            }
            else if(choice.equals("5"))
            {
                System.out.println("a.Show list");
                System.out.println("b.Show set");
                System.out.println("c.Show ZSet");
                System.out.println("Please enter the operation: ");
                Scanner i=new Scanner(System.in);
                String c=i.nextLine();
                switch (c){
                    case "a":
                        System.out.println("Show list");
                        myjedis.ShowListAll("enterList");
                        myjedis.ShowListAll("leaveList");
                        break;
                    case "b":
                        System.out.println("Show set");
                        myjedis.ShowSetAll("enterSet");
                        myjedis.ShowSetAll("leaveSet");
                        break;
                    case "c":
                        System.out.println("Show Zset");
                        myjedis.ShowZsetAll("enterZset");
                        myjedis.ShowZsetAll("leaveZset");
                        break;

                }
                System.out.println("--------------------------------------------------------------------");
                continue;
            }
            else if(choice.equals("0")){
                System.out.println("***********************************************************************");
                System.exit(0);
            }

        }

    }

}

