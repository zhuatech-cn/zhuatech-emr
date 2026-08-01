/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.emr.domain;
import org.springframework.stereotype.Component;
import java.util.List;
@Component public class DomainCatalog {
    public String systemName(){return "知华 EMR 电子病历协同管理平台";}
    public String sceneName(){return "门诊病历、住院记录、医嘱、检验报告、质控与归档";}
    public List<SeedItem> seedItems(){return List.of(
        new SeedItem("EMR-20260801-001","呼吸内科出院病历归档复核","处理中","病案质控组","高"),
        new SeedItem("EMR-20260801-002","急诊留观记录缺项提醒","待处理","急诊医学科","紧急"),
        new SeedItem("EMR-20260801-003","会诊意见电子签名补全","处理中","医务管理部","高"),
        new SeedItem("EMR-20260801-004","七月病历质控抽查汇总","已完成","病案统计室","中"));}
    public List<String> recommendedActions(){return List.of("优先补齐出院病历必填项与签名","核对未回传检验检查报告","对高风险病历发起科室二级质控");}
    public record SeedItem(String recordNo,String title,String status,String owner,String priority){}
}
