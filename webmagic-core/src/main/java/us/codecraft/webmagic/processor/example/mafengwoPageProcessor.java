package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyibing on 2016/11/30.
 */
public class mafengwoPageProcessor implements PageProcessor {
    String dongqiudi = "http://www.mafengwo.cn/";

    private Site site = Site.me()
            .setRetryTimes(3).setSleepTime(100)
            .setTimeOut(10 * 1000)
            .setCharset("UTF-8")
            .addHeader("X-Requested-With","XMLHttpRequest")
            //.addHeader("Request Method","GET")
            .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36 QQBrowser/4.1.4132.400");
            //.addHeader("Cookie","mfw_uuid=583e74fa-9532-8b9b-a411-5748d317fef8; _r=baidu; _rp=a%3A2%3A%7Bs%3A1%3A%22p%22%3Bs%3A18%3A%22www.baidu.com%2Flink%22%3Bs%3A1%3A%22t%22%3Bi%3A1480488186%3B%7D; oad_n=a%3A5%3A%7Bs%3A5%3A%22refer%22%3Bs%3A21%3A%22https%3A%2F%2Fwww.baidu.com%22%3Bs%3A2%3A%22hp%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A3%3A%22oid%22%3Bi%3A1026%3Bs%3A2%3A%22dm%22%3Bs%3A15%3A%22www.mafengwo.cn%22%3Bs%3A2%3A%22ft%22%3Bs%3A19%3A%222016-11-30+14%3A43%3A06%22%3B%7D; __mfwlv=1480488186; __mfwvn=1; __mfwurd=a%3A3%3A%7Bs%3A6%3A%22f_time%22%3Bi%3A1480488187%3Bs%3A9%3A%22f_rdomain%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A6%3A%22f_host%22%3Bs%3A3%3A%22www%22%3B%7D; __mfwuuid=583e74fa-9532-8b9b-a411-5748d317fef8; __mfwlt=1480488220; uva=a%3A5%3A%7Bs%3A13%3A%22host_pre_time%22%3Bs%3A10%3A%222016-11-30%22%3Bs%3A2%3A%22lt%22%3Bi%3A1480488221%3Bs%3A10%3A%22last_refer%22%3Bs%3A162%3A%22http%3A%2F%2Fwww.mafengwo.cn%2Fgroup%2Fs.php%3Fq%3D%25E9%259D%2592%25E5%25B9%25B4%25E5%25A3%25AE%25E8%25A1%258C16%25E5%25A4%25A9%25E9%25AA%2591%25E8%25A1%258C%25E5%258F%25B0%25E6%25B9%25BE%26seid%3D31F531FB-447C-4AD2-B5F4-01FA877A888F%22%3Bs%3A5%3A%22rhost%22%3Bs%3A13%3A%22www.baidu.com%22%3Bs%3A4%3A%22step%22%3Bi%3A3%3B%7D; CNZZDATA30065558=cnzz_eid%3D1089050834-1480487789-null%26ntime%3D1480487789; PHPSESSID=3vqtumnu5bt4pmc7rv9drnj873")
            //.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36 QQBrowser/4.1.4132.400");
    @Override
    public void process(Page page) {
        //List<String> targetRequest = new ArrayList<String>();
        //targetRequest.add(0,"http://www.mafengwo.cn/i/5557799.html");
        page.addTargetRequest("http://www.mafengwo.cn/i/5557799.html");

        page.putField("title", page.getUrl().xpath("div[@class=vc_article]").toString());

        if (page.getResultItems().get("title")==null){
            //skip this page
            System.out.println("chenggong");
            page.setSkip(true);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }



    public static void main(String[] args ) {
        Spider.create(new DongqiudiPageProcessor())
                .addUrl("http://www.mafengwo.cn/i/5557799.html")
                .addPipeline(new FilePipeline("/Users/liyibing/Documents/懂球帝女球迷采访/"))
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}
