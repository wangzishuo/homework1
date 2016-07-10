package datapreprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.StreamHandler;

/**
 * Created by bitrsky on 16/7/9.
 */
public class Preprocess {
    String Input;
    String Output;
    String[] Attrs;
    HashMap<String,HashMap<String,Integer>> DataMap;
    List<List<Integer>> data;

    HashSet<Integer> delattr;

    public Preprocess(String input, String output) {
        Input = input;
        Output = output;
        DataMap = new HashMap<String, HashMap<String, Integer>>();
        data = new ArrayList<List<Integer>>();

        delattr = new HashSet<Integer>();
        delattr.add(0);
        delattr.add(3);
        delattr.add(8);
        delattr.add(9);
        delattr.add(10);


    }
    public void Start()
    {
        PreDeal(Input);
        Putmap(Output+"/map.txt");
        PutData(Output+"/data.csv");
    }


    void PreDeal(String f)
    {

        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(f)));
            Attrs = br.readLine().split(",");
            for(int i = 1;i<Attrs.length;i++)
            {
                if(!delattr.contains(i))
                {
                    DataMap.put(Attrs[i],new HashMap<String, Integer>());
                }
            }
            String line = "";
            while((line = br.readLine())!=null)
            {
                String[] arr = line.split(",");
                String[] la;

                String l = arr[0];
                for(int i = 1;i<arr.length;i++)
                {
                    if(i==4)
                    {
                        l+= " "+arr[i];
                    }
                    else
                    {
                        l+= ","+arr[i];
                    }
                }
                la = l.split(",");
                AddIterm(la);

            }
            br.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void AddIterm(String[] Iterm)
    {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0;i<Iterm.length;i++)
        {
            switch (i)
            {
                case 0: break;
                case 1: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 2: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 3: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 4: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 5: if(!delattr.contains(i)) list.add(AddAge(Attrs[i],Iterm[i]));break;
                case 6: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 7: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 8: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 9: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 10: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
                case 11: if(!delattr.contains(i)) list.add(AddOne(Attrs[i],Iterm[i]));break;
            }
        }
        data.add(list);
    }

    Integer AddOne(String attr,String d)
    {
        HashMap<String,Integer> map = DataMap.get(attr);
        if(!map.containsKey(d))
        {
            map.put(d,map.size());
            DataMap.put(attr,map);
        }
        return map.get(d);
    }
    Integer AddAge(String attr,String d)
    {
        if(d.trim().equals(""))
        {
            return -1;
        }
        Double a = Double.valueOf(d.trim());
        int x = a.intValue();
        return x/5;
    }

    void Putmap(String f)
    {
        BufferedWriter bw = null;
        try{
            bw= new BufferedWriter(new FileWriter(new File(f)));
            for (String key:DataMap.keySet()) {
                bw.write("<"+key+">\n");
                for (String k:DataMap.get(key).keySet()) {
                    bw.write(k+"\t"+DataMap.get(key).get(k)+"\n");
                }
                bw.write("</"+key+">\n\n");
            }
            bw.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void PutData(String f)
    {
        BufferedWriter bw = null;
        try{
            bw= new BufferedWriter(new FileWriter(new File(f)));

            for(int i = 0;i<Attrs.length-1;i++)
            {
                if(!delattr.contains(i))
                {
                    bw.write(Attrs[i]+",");
                }
            }
            if(!delattr.contains(Attrs.length-1))
            {
                bw.write(Attrs[Attrs.length-1]+",\n");
            }

            for(int i= 0;i<data.size();i++)
            {
                StringBuffer line = new StringBuffer();

                for(int j = 0;j<data.get(i).size();j++)
                {
                    line.append(data.get(i).get(j)+",");
                }
                line.substring(0,line.length()-1);
                line.append("\n");
                bw.write(line.toString());
            }

            bw.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
