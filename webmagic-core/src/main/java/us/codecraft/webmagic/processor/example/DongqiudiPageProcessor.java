package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyibing on 2016/11/27.
 */
public class DongqiudiPageProcessor implements PageProcessor {

    String dongqiudi = "http://www.dongqiudi.com";
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);


    @Override
    public void process(Page page) {
        List<String> ceshi = new ArrayList<String>();

        List<String> abc = new ArrayList<String>();



        ceshi =  page.getHtml().xpath("//div[@class=\"detail special\"]").links().regex("/article/\\d\\d\\d\\d\\d\\d").all();

        for(int i = 0; i < ceshi.size(); i++)
        {
            String a = dongqiudi + ceshi.get(i);
            abc.add(i,a);
            //System.out.println(list.get(i));
        }


        page.addTargetRequests(abc);
        // 部分三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("title", page.getHtml().xpath("//div[@class=\"detail]").regex("<p>.+</p>").all().toString());
        //page.putField("content", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        //page.putField("date",page.getHtml());
        if (page.getResultItems().get("title")==null){
            //skip this page
            System.out.println("失败");
            page.setSkip(true);
        }
        else {
            System.out.println("成功");
        }
        //page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args ) {
        Spider.create(new DongqiudiPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://www.dongqiudi.com/special/43")
                .addPipeline(new FilePipeline("/Users/liyibing/Documents/懂球帝女球迷采访/"))
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}
