/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.emr.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.util.*;
@Service public class MedicationSafetyService {
    public Result evaluate(Request r){
        List<String> alerts=new ArrayList<>(); int score=0;
        if(r.knownAllergyMatch()){score+=100;alerts.add("处方药物命中已知过敏记录");}
        if("SEVERE".equalsIgnoreCase(r.interactionSeverity())){score+=80;alerts.add("存在严重药物相互作用");}
        else if("MODERATE".equalsIgnoreCase(r.interactionSeverity())){score+=35;alerts.add("存在中度药物相互作用");}
        if(r.duplicateTherapy()){score+=35;alerts.add("检测到重复治疗类别");}
        if(r.renalImpairment()&&!r.renalDoseAdjusted()){score+=45;alerts.add("肾功能异常但剂量尚未调整");}
        if(!r.medicationReconciled()){score+=20;alerts.add("入院用药核对尚未完成");}
        String status=score>=80?"BLOCK":score>=30?"REVIEW":"CLEAR"; if(alerts.isEmpty())alerts.add("未发现需要干预的用药安全信号");
        return new Result(Math.min(score,100),status,alerts);
    }
    public record Request(@NotBlank String prescriptionId,@NotNull Boolean knownAllergyMatch,@Pattern(regexp="(?i)NONE|MODERATE|SEVERE") String interactionSeverity,@NotNull Boolean duplicateTherapy,@NotNull Boolean renalImpairment,@NotNull Boolean renalDoseAdjusted,@NotNull Boolean medicationReconciled){}
    public record Result(int riskScore,String status,List<String> alerts){}
}
