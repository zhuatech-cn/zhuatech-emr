/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.emr;
import cn.zhuatech.emr.service.MedicationSafetyService; import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class MedicationSafetyServiceTests {private final MedicationSafetyService service=new MedicationSafetyService();
 @Test void blocksKnownAllergy(){var r=service.evaluate(new MedicationSafetyService.Request("RX-1",true,"NONE",false,false,true,true));assertEquals("BLOCK",r.status());}
 @Test void clearsReconciledPrescription(){var r=service.evaluate(new MedicationSafetyService.Request("RX-2",false,"NONE",false,false,true,true));assertEquals("CLEAR",r.status());assertEquals(0,r.riskScore());}}
