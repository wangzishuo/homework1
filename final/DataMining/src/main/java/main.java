import datapreprocessing.Preprocess;

/**
 * Created by bitrsky on 16/7/9.
 */
public class main {
    public static void main(String[] args)
    {
        if(args.length< 2)
        {
            System.out.println("java -jar *.jar Input Outputdir");
            System.exit(1);
        }

        //args = new String[2];
        //args[0] = "/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data/train.csv";
        //args[1] = "/Users/bitrsky/BitrSky/Study/Student/DataMining/Second/Data";
        Preprocess deal = new Preprocess(args[0],args[1]);
        deal.Start();

    }
}
