package com.itheima.bos.web.action.take_delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.base.WorkBill;
import com.itheima.utils.MailUtils;

/**
 * ClassName:WorkbillJob <br/>
 * Function: <br/>
 * Date: Nov 18, 2017 8:56:02 PM <br/>
 */
@Component
public class WorkbillJob {
    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendEmail() {
        List<WorkBill> list = workBillRepository.findAll();
        String msg = "编号\t工单类型\t取件状态\t工单生成时间\t追单次数\t订单备注\t短信序号<br/>";
        for (WorkBill workBill : list) {
            msg += workBill.getId() + "\t" + workBill.getType() + "\t" + workBill.getPickstate()
                    + "\t" + workBill.getBuildtime() + "\t" + workBill.getAttachbilltimes() + "\t"
                    + workBill.getRemark() + "\t" + workBill.getSmsNumber() + "<br/>";

        }
        MailUtils.sendMail("zs@store.com", "工单信息统计", msg);
    }

}
